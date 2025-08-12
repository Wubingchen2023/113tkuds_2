// 檔名：MultiLevelCacheSystem.java
import java.util.*;

public class MultiLevelCacheSystem {

    // ===== 基本參數（可調）=====
    private static final int L1_CAP = 2, L2_CAP = 5, L3_CAP = 10;
    private static final int L1_COST = 1, L2_COST = 3, L3_COST = 10;

    // 升級門檻（頻率）
    private static final int THRESH_L3_TO_L2 = 1;
    private static final int THRESH_L2_TO_L1 = 2;

    // ===== 節點 =====
    static class Node {
        final int key;
        String value;
        int level;              // 1,2,3
        int freq;               // 存取次數
        long lastAccess;        // 最後存取時間戳
        long heapStamp;         // 熱度項目的版本（lazy 刪除）

        Node(int key, String value, int level, long now) {
            this.key = key;
            this.value = value;
            this.level = level;
            this.freq = 0;
            this.lastAccess = now;
            this.heapStamp = 0;
        }

        long score(long now) {
            // 熱度分數：頻率為主，加入近期性（分數越大越熱）
            return (long)freq * 1000L - (now - lastAccess);
        }
    }

    // ===== 層級資料結構 =====
    static class Level {
        final int id;               // 1/2/3
        final int capacity;
        final int cost;

        // LRU（插入訪問順序）: accessOrder=true
        final LinkedHashMap<Integer, Node> lru =
            new LinkedHashMap<>(16, 0.75f, true);

        // 熱度 Max-Heap（用 lazy-stale）
        final PriorityQueue<long[]> hotHeap = new PriorityQueue<>(
            (a, b) -> Long.compare(b[0], a[0]) // [score, key, stamp]，分數大者先
        );

        Level(int id, int capacity, int cost) {
            this.id = id; this.capacity = capacity; this.cost = cost;
        }

        void touchAndPush(Node n, long now) {
            n.lastAccess = now;
            long score = n.score(now);
            long stamp = ++n.heapStamp;
            hotHeap.offer(new long[]{score, n.key, stamp});
            // 放進 LRU 的動作由外部 putIfAbsent 或 get 觸發（LinkedHashMap 的 accessOrder）
        }

        // 取得有效的「最熱」節點（頂端可能是過期版本）
        Node peekHottestValid(Map<Integer, Node> directory, long now) {
            while (!hotHeap.isEmpty()) {
                long[] top = hotHeap.peek();
                int key = (int) top[1];
                long stamp = top[2];
                Node n = directory.get(key);
                if (n == null || n.level != id || n.heapStamp != stamp) {
                    hotHeap.poll(); // 丟掉過期
                } else {
                    return n;
                }
            }
            return null;
        }

        // 彈出 LRU 的 key（LinkedHashMap 最「冷」的是最早使用的在迭代開頭）
        Integer popLRUKey() {
            Iterator<Map.Entry<Integer, Node>> it = lru.entrySet().iterator();
            if (it.hasNext()) {
                Integer k = it.next().getKey();
                it.remove();
                return k;
            }
            return null;
        }

        boolean isFull() { return lru.size() >= capacity; }
    }

    // ===== 快取本體 =====
    static class MultiLevelCache {
        final Level L1 = new Level(1, L1_CAP, L1_COST);
        final Level L2 = new Level(2, L2_CAP, L2_COST);
        final Level L3 = new Level(3, L3_CAP, L3_COST);

        // 目錄：key -> Node（查找 O(1)）
        final Map<Integer, Node> directory = new HashMap<>();

        long clock = 0; // 單調時間戳

        public String get(int key) {
            clock++;
            Node n = directory.get(key);
            if (n == null) return null;

            n.freq++;
            Level cur = levelOf(n.level);
            // 觸碰：更新 LRU 與熱度堆
            cur.lru.get(key);        // 觸發 accessOrder
            cur.touchAndPush(n, clock);

            // 嘗試升級
            tryPromote(n);
            return n.value;
        }

        public void put(int key, String value) {
            clock++;
            Node n = directory.get(key);
            if (n != null) {
                // 更新並當作一次存取
                n.value = value;
                n.freq++;
                Level cur = levelOf(n.level);
                cur.lru.get(key);
                cur.touchAndPush(n, clock);
                tryPromote(n);
                return;
            }

            // 新進資料放 L1（快取命中最有利）
            Node m = new Node(key, value, 1, clock);
            m.freq++;
            ensureSpaceUpwards(1);          // L1 滿則往下讓位
            L1.lru.put(key, m);
            directory.put(key, m);
            L1.touchAndPush(m, clock);
            // 新資料先在 L1，若之後少用會被 LRU 往下層移
        }

        // 當某層滿了，要為「升級/插入」讓位：把該層 LRU 往下層移（串連往下）
        private void ensureSpaceUpwards(int targetLevel) {
            Level target = levelOf(targetLevel);
            if (!target.isFull()) return;

            // 取出 LRU
            Integer evictKey = target.popLRUKey();
            if (evictKey == null) return;
            Node victim = directory.get(evictKey);
            if (victim == null) return; // 理論不會發生

            // 往下一層
            int nextLevel = targetLevel + 1;
            if (nextLevel > 3) {
                // 已經在 L3，再滿只能淘汰
                directory.remove(evictKey);
                return;
            }
            Level next = levelOf(nextLevel);
            ensureSpaceUpwards(nextLevel); // 先確保下一層有空間
            // 放進下一層
            victim.level = nextLevel;
            next.lru.put(evictKey, victim);
            next.touchAndPush(victim, clock);
        }

        // 嘗試把節點往上層提升（依頻率門檻）
        private void tryPromote(Node n) {
            boolean moved = false;
            while (n.level > 1) {
                if (n.level == 3) {
                    if (n.freq >= THRESH_L3_TO_L2) {
                        moved |= promoteOneLevel(n); // 3->2
                    } else break;
                } else if (n.level == 2) {
                    if (n.freq >= THRESH_L2_TO_L1) {
                        moved |= promoteOneLevel(n); // 2->1
                    } else break;
                }
                else break;
            }
            if (moved) {
                // 升上去之後，若上層還有空間且該層裡面有更熱的候選，也能再連續升
                //（已在 while 處理）
            }
        }

        // 將節點往上提升一層
        private boolean promoteOneLevel(Node n) {
            int from = n.level;
            int to = from - 1;
            Level src = levelOf(from);
            Level dst = levelOf(to);

            // 目標層沒空間就先把目標層 LRU 往下一層挪
            ensureSpaceUpwards(to);

            // 從原層移除 LRU 結構中的自己（透過移除再 put 方式）
            src.lru.remove(n.key);

            // 放進目標層
            n.level = to;
            dst.lru.put(n.key, n);
            dst.touchAndPush(n, clock);
            return true;
        }

        private Level levelOf(int id) {
            if (id == 1) return L1;
            if (id == 2) return L2;
            return L3;
        }

        // ===== 觀察用：列印各層（LRU 從老到新）=====
        public void printState(String tag) {
            System.out.println("---- " + tag + " ----");
            System.out.print("L1: ");
            printLRU(L1.lru);
            System.out.print("L2: ");
            printLRU(L2.lru);
            System.out.print("L3: ");
            printLRU(L3.lru);
            System.out.println();
        }

        private void printLRU(LinkedHashMap<Integer, Node> lru) {
            List<Integer> order = new ArrayList<>(lru.keySet());
            System.out.println(order);
        }
    }

    // ===== 測試 =====
    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();

        // put(1,"A"); put(2,"B"); put(3,"C");
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printState("after put 1,2,3");
        // 期望：L1: [2, 3], L2: [1], L3: []

        // get(1); get(1); get(2);
        System.out.println("get(1) -> " + cache.get(1));
        System.out.println("get(1) -> " + cache.get(1));
        System.out.println("get(2) -> " + cache.get(2));
        cache.printState("after get(1), get(1), get(2)");
        // 期望：1 被頻繁存取，上到 L1；L1: [1,2], L2: [3], L3: []

        // put(4,"D"); put(5,"E"); put(6,"F");
        cache.put(4, "D");
        cache.put(5, "E");
        cache.put(6, "F");
        cache.printState("after put 4,5,6");

        // 你可以再加更多 get/put 觀察升降級行為
    }
}
