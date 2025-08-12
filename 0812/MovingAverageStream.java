import java.util.*;

public class MovingAverageStream {

    private final int k;
    private final Deque<Integer> window = new ArrayDeque<>();
    private long sum = 0L;

    // 兩個堆：左半部(小值)用 Max Heap，右半部(大值)用 Min Heap
    private final PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> hi = new PriorityQueue<>();

    // 延遲刪除表：元素值 -> 待刪次數（配合兩堆使用）
    private final Map<Integer, Integer> delayed = new HashMap<>();
    // 合法元素數（分別在 lo/hi 中）
    private int loSize = 0, hiSize = 0;

    // 極值：用 TreeMap 當 multiset（支援 getMin()/getMax() 與刪除）
    private final TreeMap<Integer, Integer> multiset = new TreeMap<>();

    public MovingAverageStream(int size) {
        if (size <= 0) throw new IllegalArgumentException("Window size must be positive.");
        this.k = size;
    }

    // 1) 加入新值並回傳移動平均
    public double next(int val) {
        // 進窗
        window.addLast(val);
        sum += val;
        addNum(val);
        multisetAdd(val);

        // 若超過視窗大小，移出最舊
        if (window.size() > k) {
            int out = window.removeFirst();
            sum -= out;
            removeNum(out);
            multisetRemove(out);
        }
        return sum * 1.0 / window.size();
    }

    // 2) 取得目前視窗中位數
    public double getMedian() {
        if (window.isEmpty()) throw new NoSuchElementException("Window is empty.");
        // 清理堆頂的延遲刪除元素
        prune(lo);
        prune(hi);
        if ((window.size() & 1) == 1) {
            return lo.peek();
        } else {
            return (lo.peek() + hi.peek()) / 2.0;
        }
    }

    // 3) 取得最小值
    public int getMin() {
        if (multiset.isEmpty()) throw new NoSuchElementException("Window is empty.");
        return multiset.firstKey();
    }

    // 4) 取得最大值
    public int getMax() {
        if (multiset.isEmpty()) throw new NoSuchElementException("Window is empty.");
        return multiset.lastKey();
    }

    // ====== 內部：兩堆 + lazy deletion 維護中位數 ======

    private void addNum(int x) {
        if (lo.isEmpty() || x <= lo.peek()) {
            lo.offer(x);
            loSize++;
        } else {
            hi.offer(x);
            hiSize++;
        }
        balance();
    }

    private void removeNum(int x) {
        // 標記延遲刪除
        delayed.put(x, delayed.getOrDefault(x, 0) + 1);

        // 根據與 lo.peek 的關係，決定它原本屬於哪邊（影響 size 計數）
        if (!lo.isEmpty() && x <= lo.peek()) {
            loSize--;
            if (!lo.isEmpty() && lo.peek() == x) prune(lo);
        } else {
            hiSize--;
            if (!hi.isEmpty() && hi.peek() == x) prune(hi);
        }
        balance();
    }

    private void balance() {
        // lo 允許比 hi 多 1 個
        if (loSize > hiSize + 1) {
            hi.offer(lo.poll());
            loSize--; hiSize++;
            prune(lo);
        } else if (loSize < hiSize) {
            lo.offer(hi.poll());
            loSize++; hiSize--;
            prune(hi);
        }
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int x = heap.peek();
            Integer cnt = delayed.get(x);
            if (cnt == null || cnt == 0) break;
            // 真正移除
            delayed.put(x, cnt - 1);
            if (cnt - 1 == 0) delayed.remove(x);
            heap.poll();
        }
    }

    // ====== 內部：極值 multiset ======

    private void multisetAdd(int x) {
        multiset.put(x, multiset.getOrDefault(x, 0) + 1);
    }

    private void multisetRemove(int x) {
        int c = multiset.getOrDefault(x, 0);
        if (c <= 1) multiset.remove(x);
        else multiset.put(x, c - 1);
    }

    public static void main(String[] args) {
        MovingAverageStream ma = new MovingAverageStream(3);
        System.out.printf("ma.next(1)  = %.2f%n", ma.next(1));   // 1.00
        System.out.printf("ma.next(10) = %.2f%n", ma.next(10));  // 5.50
        System.out.printf("ma.next(3)  = %.2f%n", ma.next(3));   // 4.67
        System.out.printf("ma.next(5)  = %.2f%n", ma.next(5));   // 6.00
        // 目前視窗是 [10,3,5]
        System.out.printf("ma.getMedian() = %.1f%n", ma.getMedian()); // 5.0
        System.out.println("ma.getMin() = " + ma.getMin());           // 3
        System.out.println("ma.getMax() = " + ma.getMax());           // 10
    }
}
