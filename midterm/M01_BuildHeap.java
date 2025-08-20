import java.util.*;

public class M01_BuildHeap {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String type = sc.next(); // "max" or "min"
        int n = sc.nextInt();
        int[] heap = new int[n];

        for (int i = 0; i < n; i++) {
            heap[i] = sc.nextInt();
        }

        // 建堆：自底向上（Bottom-up）
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapify(heap, n, i, type.equals("max"));
        }

        // 輸出結果
        for (int i = 0; i < n; i++) {
            System.out.print(heap[i] + (i == n - 1 ? "\n" : " "));
        }
    }

    // heapifyDown 遞迴實作（支援 max-heap 與 min-heap）
    private static void heapify(int[] arr, int n, int i, boolean isMax) {
        int target = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (isMax) {
            if (left < n && arr[left] > arr[target]) target = left;
            if (right < n && arr[right] > arr[target]) target = right;
        } else {
            if (left < n && arr[left] < arr[target]) target = left;
            if (right < n && arr[right] < arr[target]) target = right;
        }

        if (target != i) {
            // 交換後繼續向下調整
            int temp = arr[i];
            arr[i] = arr[target];
            arr[target] = temp;
            heapify(arr, n, target, isMax);
        }
    }
}

// Time Complexity：O(n)
//   - 由最後一個非葉節點（n/2 - 1）向上做 sift-down（heapifyDown）。
//   - 單一節點的 heapifyDown 最壞 O(log n)，但所有節點總和為 O(n)（經典分析：深層節點多、可下沉高度小）。
// Space Complexity：O(1) 額外空間（就地建堆）。