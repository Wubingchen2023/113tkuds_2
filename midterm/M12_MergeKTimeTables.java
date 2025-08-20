import java.io.*;
import java.util.*;

public class M12_MergeKTimeTables {

    // 封裝堆中每個元素的資訊
    static class Entry implements Comparable<Entry> {
        int time;       // 當前時間（分鐘）
        int listIdx;    // 來自第幾個列表
        int elemIdx;    // 該列表中的第幾個元素

        Entry(int time, int listIdx, int elemIdx) {
            this.time = time;
            this.listIdx = listIdx;
            this.elemIdx = elemIdx;
        }

        // 小的時間優先
        public int compareTo(Entry other) {
            return Integer.compare(this.time, other.time);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();  // 路線數

        List<List<Integer>> lists = new ArrayList<>();

        // 讀取每條路線的時刻表
        for (int i = 0; i < K; i++) {
            int len = sc.nextInt();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                list.add(sc.nextInt());
            }
            lists.add(list);
        }

        // 使用 Min-Heap 合併
        PriorityQueue<Entry> pq = new PriorityQueue<>();
        List<Integer> result = new ArrayList<>();

        // 初始每條列表的第一個時間放入堆
        for (int i = 0; i < K; i++) {
            if (!lists.get(i).isEmpty()) {
                pq.offer(new Entry(lists.get(i).get(0), i, 0));
            }
        }

        // 合併處理
        while (!pq.isEmpty()) {
            Entry cur = pq.poll();
            result.add(cur.time);

            // 推入同來源的下一個元素
            List<Integer> source = lists.get(cur.listIdx);
            int nextIdx = cur.elemIdx + 1;
            if (nextIdx < source.size()) {
                pq.offer(new Entry(source.get(nextIdx), cur.listIdx, nextIdx));
            }
        }

        // 輸出結果
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result.get(i));
        }
        System.out.println();
    }
}

/*
Time Complexity:
- 初始推入 K 個首元素：O(K)
- N 個元素，每個進出堆一次 ⇒ O(N log K)
- 總時間：O(N log K)，其中 N 為所有列表元素總數（≤ 1000）

Space Complexity:
- 儲存所有列表與結果：O(N)
- 儲存堆最多 K 個元素：O(K)
*/
