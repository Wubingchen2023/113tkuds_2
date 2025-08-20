import java.io.*;
import java.util.*;

public class M11_HeapSortWithTie {
    // 封裝分數與原始索引
    static class Entry {
        int score, index;
        Entry(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Entry[] heap = new Entry[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int score = Integer.parseInt(st.nextToken());
            heap[i] = new Entry(score, i);
        }

        // 1. 建 Max-Heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(heap, i, n);
        }

        // 2. Heap Sort（每次將最大值放到最後）
        for (int i = n - 1; i >= 1; i--) {
            swap(heap, 0, i);
            heapifyDown(heap, 0, i); // 每次縮小堆的範圍
        }

        // 3. 輸出結果
        StringBuilder sb = new StringBuilder();
        for (Entry e : heap) {
            sb.append(e.score).append(' ');
        }
        System.out.println(sb.toString().trim());
    }

    // Max-Heap: score 大者優先；若相同則 index 小者優先（確保穩定性）
    static void heapifyDown(Entry[] heap, int i, int size) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1, right = 2 * i + 2;

            if (left < size && compare(heap[left], heap[largest]) > 0) {
                largest = left;
            }
            if (right < size && compare(heap[right], heap[largest]) > 0) {
                largest = right;
            }

            if (largest != i) {
                swap(heap, i, largest);
                i = largest;
            } else {
                break;
            }
        }
    }

    // 比較：score 大者優先；若 score 相同，index 小者優先
    static int compare(Entry a, Entry b) {
        if (a.score != b.score) return Integer.compare(a.score, b.score);
        return Integer.compare(b.index, a.index);
    }

    static void swap(Entry[] heap, int i, int j) {
        Entry temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

/*
Time Complexity:
- 建堆：O(n)
- 排序：每次 heapifyDown O(log n)，共 n 次 ⇒ O(n log n)
- 總時間複雜度：O(n log n)

Space Complexity:
- 使用 Entry 陣列 O(n)
- 不使用額外排序函式或額外資料結構 ⇒ O(n)
*/
