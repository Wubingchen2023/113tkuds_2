import java.util.*;

public class M03_TopKConvenience {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀取 n 和 K
        int n = sc.nextInt();
        int k = sc.nextInt();

        // 建立 Min-Heap
        // PriorityQueue 預設是 Min-Heap
        // 陣列 [0] 存銷量，[1] 存品名
        // 根據銷量由小到大排序，若銷量相同則依輸入順序決定
        PriorityQueue<Object[]> minHeap = new PriorityQueue<>(k, (a, b) -> {
            Integer qtyA = (Integer) a[0];
            Integer qtyB = (Integer) b[0];
            return qtyA.compareTo(qtyB);
        });

        // 讀取 n 筆商品資料
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int qty = sc.nextInt();
            
            // 將新商品加入 Min-Heap
            if (minHeap.size() < k) {
                minHeap.add(new Object[]{qty, name});
            } else {
                // 如果新商品的銷量大於 Min-Heap 的最小銷量
                if (qty > (Integer) minHeap.peek()[0]) {
                    // 移除最小的商品
                    minHeap.poll();
                    // 加入新商品
                    minHeap.add(new Object[]{qty, name});
                }
            }
        }

        // 將 Min-Heap 中的元素依序取出並存入 List
        List<Object[]> resultList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            resultList.add(minHeap.poll());
        }

        // 將 List 反轉，以高到低排序
        Collections.reverse(resultList);

        // 輸出結果
        for (Object[] item : resultList) {
            System.out.println(item[1] + " " + item[0]);
        }
        
        sc.close();
    }
}
/*
Time Complexity（時間複雜度）：
- 讀入每筆資料時，最多做一次堆操作（插入／替換）：
  每次 O(log K)，共 n 筆 ⇒ O(n log K)。
- 最後把至多 K 筆做排序輸出：O(K log K)。
- 總體：O(n log K + K log K) ≈ O(n log K)（當 K ≪ n 時特別有效）。

Space Complexity（空間複雜度）：
- Min-Heap 儲存最多 K 筆：O(K)。
- 其餘為輸出用暫存：O(K)。
*/