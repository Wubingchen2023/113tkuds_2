import java.util.*;

public class KthSmallestElement {

    // 方法1：維持大小為 K 的 Max Heap（堆頂是目前第 k 小）
    // 時間複雜度：O(n log k)，空間：O(k)
    public static int kthSmallestUsingMaxHeap(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid k or array.");
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int v : arr) {
            if (maxHeap.size() < k) {
                maxHeap.offer(v);
            } else if (v < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(v);
            }
        }
        return maxHeap.peek();
    }

    // 方法2：Min Heap 取出 k 次
    // 這裡用「一次性建堆」(由集合建構) => 建堆 O(n)，接著 poll k 次 O(k log n)
    // 總複雜度：O(n + k log n)，空間：O(n)
    public static int kthSmallestUsingMinHeap(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid k or array.");
        }
        List<Integer> list = new ArrayList<>(arr.length);
        for (int v : arr) list.add(v);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(list); // O(n) 建堆
        for (int i = 1; i < k; i++) {
            minHeap.poll(); // 每次 O(log n)
        }
        return minHeap.peek();
    }

    // ===== 測試與簡易基準 =====
    public static void main(String[] args) {
        // 指定測試案例
        int[] a1 = {7, 10, 4, 3, 20, 15}; int k1 = 3; // 答案 7
        int[] a2 = {1};                    int k2 = 1; // 答案 1
        int[] a3 = {3, 1, 4, 1, 5, 9, 2, 6}; int k3 = 4; // 答案 3

        runCase(a1, k1);
        runCase(a2, k2);
        runCase(a3, k3);

        // 簡易效率比較（隨機大陣列）
        benchmark(200_000, 500); // 可調整規模
    }

    private static void runCase(int[] arr, int k) {
        System.out.println("陣列: " + Arrays.toString(arr) + ", K=" + k);
        int ans1 = kthSmallestUsingMaxHeap(arr, k);
        int ans2 = kthSmallestUsingMinHeap(arr, k);
        System.out.println("方法1 MaxHeap(K)  => " + ans1);
        System.out.println("方法2 MinHeap(k次) => " + ans2);
        System.out.println("----------------------------------");
    }

    private static void benchmark(int n, int k) {
        Random rnd = new Random(42);
        int[] big = new int[n];
        for (int i = 0; i < n; i++) big[i] = rnd.nextInt();

        long t1 = System.nanoTime();
        int r1 = kthSmallestUsingMaxHeap(big, k);
        long t2 = System.nanoTime();

        int r2 = kthSmallestUsingMinHeap(big, k);
        long t3 = System.nanoTime();

        System.out.println("=== 簡易效能比較 (n=" + n + ", k=" + k + ") ===");
        System.out.println("方法1 MaxHeap(K)  結果: " + r1 + "，耗時 " + ((t2 - t1) / 1_000_000.0) + " ms");
        System.out.println("方法2 MinHeap(k次)結果: " + r2 + "，耗時 " + ((t3 - t2) / 1_000_000.0) + " ms");
    }
}
