public class SelectionSortImplementation {

    // 用以保存排序統計資料
    static class SortStats {
        long comparisons = 0;  // 比較次數
        long swaps       = 0;  // 交換次數
        long durationNs  = 0;  // 執行時間 (奈秒)
    }

    public static void main(String[] args) {
        int[] original = {64, 25, 12, 22, 11, 90, 34, 78};  // 測試資料

        // 1. 選擇排序
        int[] arr1 = original.clone();
        System.out.println("=== 選擇排序 (Selection Sort) ===");
        SortStats statsSel = selectionSort(arr1);
        System.out.printf("最終排序結果：%s%n", java.util.Arrays.toString(arr1));
        System.out.printf("比較次數：%d，交換次數：%d，耗時：%d µs%n%n",
                statsSel.comparisons, statsSel.swaps, statsSel.durationNs / 1_000);

        // 2. 氣泡排序
        int[] arr2 = original.clone();
        System.out.println("=== 氣泡排序 (Bubble Sort) ===");
        SortStats statsBub = bubbleSort(arr2);
        System.out.printf("最終排序結果：%s%n", java.util.Arrays.toString(arr2));
        System.out.printf("比較次數：%d，交換次數：%d，耗時：%d µs%n%n",
                statsBub.comparisons, statsBub.swaps, statsBub.durationNs / 1_000);

        // 3. 效能比較
        System.out.println("=== 效能比較 ===");
        System.out.printf("次數比較：Selection vs Bubble = %d vs %d%n",
                statsSel.comparisons, statsBub.comparisons);
        System.out.printf("交換比較：Selection vs Bubble = %d vs %d%n",
                statsSel.swaps, statsBub.swaps);
        System.out.printf("時間比較：Selection vs Bubble = %d µs vs %d µs%n",
                statsSel.durationNs / 1_000, statsBub.durationNs / 1_000);
    }

    /**
     * 1. 實作基本的選擇排序
     * 2. 顯示每一輪的排序過程
     * 3. 計算比較次數和交換次數
     */
    public static SortStats selectionSort(int[] arr) {
        SortStats stats = new SortStats();
        long start = System.nanoTime();

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            // 找出從 i 到 n-1 的最小值
            for (int j = i + 1; j < n; j++) {
                stats.comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // 如果最小值不在 i，就交換
            if (minIdx != i) {
                swap(arr, i, minIdx);
                stats.swaps++;
            }
            // 印出本輪結果
            System.out.printf("第 %d 輪後：%s%n", i + 1, java.util.Arrays.toString(arr));
        }

        stats.durationNs = System.nanoTime() - start;
        return stats;
    }

    /**
     * 氣泡排序，同樣計算比較次數與交換次數
     */
    public static SortStats bubbleSort(int[] arr) {
        SortStats stats = new SortStats();
        long start = System.nanoTime();

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                stats.comparisons++;
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    stats.swaps++;
                    swapped = true;
                }
            }
            // 若某一輪沒有任何交換，提前結束
            System.out.printf("氣泡排序第 %d 輪後：%s%n", i + 1, java.util.Arrays.toString(arr));
            if (!swapped) break;
        }

        stats.durationNs = System.nanoTime() - start;
        return stats;
    }

    // 交換陣列內兩個位置的值
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

