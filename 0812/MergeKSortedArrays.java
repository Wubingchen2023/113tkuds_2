import java.util.*;

public class MergeKSortedArrays {

    // 節點：記錄值、來自哪個陣列(arrIdx)與該陣列中的哪個位置(elemIdx)
    static class Node {
        int value;
        int arrIdx;
        int elemIdx;
        Node(int value, int arrIdx, int elemIdx) {
            this.value = value;
            this.arrIdx = arrIdx;
            this.elemIdx = elemIdx;
        }
    }

    // 合併 K 個有序陣列
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) return new int[0];

        // 先計算總長度，方便建立結果陣列
        int totalLen = 0;
        for (int[] a : arrays) {
            if (a != null) totalLen += a.length;
        }
        if (totalLen == 0) return new int[0];

        // Min Heap 以節點的 value 排序
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.value));

        // 初始化：每個非空陣列的第 1 個元素放入 heap
        for (int i = 0; i < arrays.length; i++) {
            int[] arr = arrays[i];
            if (arr != null && arr.length > 0) {
                minHeap.offer(new Node(arr[0], i, 0));
            }
        }

        int[] result = new int[totalLen];
        int idx = 0;

        // 每次取出最小值，並把該來源陣列的下一個元素放入 heap
        while (!minHeap.isEmpty()) {
            Node cur = minHeap.poll();
            result[idx++] = cur.value;

            int nextIdx = cur.elemIdx + 1;
            int[] fromArr = arrays[cur.arrIdx];
            if (nextIdx < fromArr.length) {
                minHeap.offer(new Node(fromArr[nextIdx], cur.arrIdx, nextIdx));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] t1 = { {1,4,5}, {1,3,4}, {2,6} };
        int[][] t2 = { {1,2,3}, {4,5,6}, {7,8,9} };
        int[][] t3 = { {1}, {0} };

        runCase(t1); // 期望：[1,1,2,3,4,4,5,6]
        runCase(t2); // 期望：[1,2,3,4,5,6,7,8,9]
        runCase(t3); // 期望：[0,1]
    }

    private static void runCase(int[][] arrays) {
        int[] merged = mergeKSortedArrays(arrays);
        System.out.println(Arrays.deepToString(arrays) + " -> " + Arrays.toString(merged));
    }
}
