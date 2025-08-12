import java.util.*;

public class StockMaximizer {

    // 方法：使用「上升區間 + Min Heap」求最多 K 次交易的最大利潤
    // 時間複雜度：O(n log K)，空間：O(K)
    public static int maxProfitWithKUsingHeap(int[] prices, int K) {
        if (prices == null || prices.length < 2 || K <= 0) return 0;

        // 小根堆，保留目前前 K 大的交易利潤
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        int n = prices.length;
        int i = 0;
        while (i < n - 1) {
            // 1) 找谷 (局部最小)
            while (i < n - 1 && prices[i] >= prices[i + 1]) i++;
            int valley = i;

            // 2) 找峰 (局部最大)
            while (i < n - 1 && prices[i] <= prices[i + 1]) i++;
            int peak = i;

            // 3) 若形成上升區間，計算利潤
            int profit = prices[peak] - prices[valley];
            if (profit > 0) {
                minHeap.offer(profit);
                if (minHeap.size() > K) {
                    minHeap.poll(); // 只留前 K 大
                }
            }
        }

        int ans = 0;
        for (int p : minHeap) ans += p;
        return ans;
    }

    public static void main(String[] args) {
        runCase(new int[]{2,4,1}, 2, 2);            // 期望 2
        runCase(new int[]{3,2,6,5,0,3}, 2, 7);      // 期望 7
        runCase(new int[]{1,2,3,4,5}, 2, 4);        // 期望 4

        runCase(new int[]{5,4,3,2,1}, 3, 0);        // 全下跌 → 0
        runCase(new int[]{1,3,2,4,3,5}, 2, 4);      // 區間 (1→3=2), (2→4=2), (3→5=2); 前兩筆 2+2=4
        runCase(new int[]{1,3,7,5,10,3}, 3, 11);    // (1→7=6), (5→10=5), 共 11；K=3 但只有兩段上升
    }

    private static void runCase(int[] prices, int K, int expect) {
        int res = maxProfitWithKUsingHeap(prices, K);
        System.out.println("價格=" + Arrays.toString(prices) + ", K=" + K
                + " -> 最大利潤=" + res + (expect >= 0 ? " (期望 " + expect + ")" : ""));
    }
}
