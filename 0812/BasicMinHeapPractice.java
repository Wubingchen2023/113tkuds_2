import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BasicMinHeapPractice {

    static class MinHeap {
        private final ArrayList<Integer> heap = new ArrayList<>();

        // 1) 插入一個元素：O(log n)
        public void insert(int val) {
            heap.add(val);                  // 先放在最後
            heapifyUp(heap.size() - 1);     // 往上調整
        }

        // 2) 取出並返回最小元素：O(log n)
        public int extractMin() {
            if (isEmpty()) throw new NoSuchElementException("Heap is empty.");
            int min = heap.get(0);
            int last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);          // 將最後一個補到根
                heapifyDown(0);             // 往下調整
            }
            return min;
        }

        // 3) 查看最小元素但不移除：O(1)
        public int getMin() {
            if (isEmpty()) throw new NoSuchElementException("Heap is empty.");
            return heap.get(0);
        }

        // 4) 返回 heap 的大小：O(1)
        public int size() {
            return heap.size();
        }

        // 5) 檢查 heap 是否為空：O(1)
        public boolean isEmpty() {
            return heap.isEmpty();
        }

        // 往上調整：子節點若比父節點小就交換
        private void heapifyUp(int idx) {
            while (idx > 0) {
                int parent = (idx - 1) / 2;
                if (heap.get(idx) < heap.get(parent)) {
                    swap(idx, parent);
                    idx = parent;
                } else {
                    break;
                }
            }
        }

        // 往下調整：根與較小的子節點比較並下沉
        private void heapifyDown(int idx) {
            int n = heap.size();
            while (true) {
                int left = 2 * idx + 1;
                int right = 2 * idx + 2;
                int smallest = idx;

                if (left < n && heap.get(left) < heap.get(smallest)) {
                    smallest = left;
                }
                if (right < n && heap.get(right) < heap.get(smallest)) {
                    smallest = right;
                }
                if (smallest != idx) {
                    swap(idx, smallest);
                    idx = smallest;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            int tmp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, tmp);
        }
    }

    public static void main(String[] args) {
        MinHeap h = new MinHeap();

        int[] inputs = {15, 10, 20, 8, 25, 5};
        for (int v : inputs) {
            h.insert(v);
        }

        // 驗證 getMin()（此時最小應該是 5）
        System.out.println("Current Min (peek): " + h.getMin());

        // 依序 extractMin 應得到：5, 8, 10, 15, 20, 25
        System.out.print("extractMin order: ");
        boolean first = true;
        while (!h.isEmpty()) {
            int x = h.extractMin();
            if (!first) System.out.print(", ");
            System.out.print(x);
            first = false;
        }
        System.out.println();

        // 尺寸/空堆測試
        System.out.println("size after all extracts: " + h.size());
        System.out.println("isEmpty: " + h.isEmpty());
    }
}
