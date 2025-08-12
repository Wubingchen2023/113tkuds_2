import java.util.*;

public class SlidingWindowMedian {

    private PriorityQueue<Integer> maxHeap; // 左半部（大根堆）
    private PriorityQueue<Integer> minHeap; // 右半部（小根堆）
    private Map<Integer, Integer> delayed;  // 延遲移除表（記錄要移除但還在 heap 裡的元素）

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new double[0];

        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
        delayed = new HashMap<>();

        double[] result = new double[n - k + 1];

        // 初始化第一個窗口
        for (int i = 0; i < k; i++) {
            add(nums[i]);
        }
        result[0] = getMedian(k);

        // 往右滑動窗口
        for (int i = k; i < n; i++) {
            int toRemove = nums[i - k];
            int toAdd = nums[i];
            add(toAdd);
            remove(toRemove);
            result[i - k + 1] = getMedian(k);
        }

        return result;
    }

    private void add(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        balanceHeaps();
    }

    private void remove(int num) {
        // 延遲移除登記
        delayed.put(num, delayed.getOrDefault(num, 0) + 1);

        // 實際移除時機：堆頂時才移除
        if (!maxHeap.isEmpty() && num <= maxHeap.peek()) {
            prune(maxHeap);
        } else {
            prune(minHeap);
        }

        balanceHeaps();
    }

    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int top = heap.peek();
            if (delayed.containsKey(top)) {
                delayed.put(top, delayed.get(top) - 1);
                if (delayed.get(top) == 0) delayed.remove(top);
                heap.poll();
            } else {
                break;
            }
        }
    }

    private void balanceHeaps() {
        // MaxHeap 最多只能比 MinHeap 多 1 個
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
            prune(maxHeap);
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
            prune(minHeap);
        }
    }

    private double getMedian(int k) {
        if (k % 2 == 1) {
            return maxHeap.peek(); // 奇數：中位數為左堆頂
        } else {
            return ((double) maxHeap.peek() + minHeap.peek()) / 2;
        }
    }

    public static void main(String[] args) {
        SlidingWindowMedian swm = new SlidingWindowMedian();

        int[] nums1 = {1,3,-1,-3,5,3,6,7};
        int k1 = 3;
        System.out.println("輸入：" + Arrays.toString(nums1) + ", K=" + k1);
        System.out.println("輸出：" + Arrays.toString(swm.medianSlidingWindow(nums1, k1)));
        System.out.println();

        int[] nums2 = {1,2,3,4};
        int k2 = 2;
        System.out.println("輸入：" + Arrays.toString(nums2) + ", K=" + k2);
        System.out.println("輸出：" + Arrays.toString(swm.medianSlidingWindow(nums2, k2)));
    }
}
