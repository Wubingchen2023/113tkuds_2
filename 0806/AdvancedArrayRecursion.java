import java.util.Arrays;

public class AdvancedArrayRecursion {

    // 1. 遞迴快速排序（Quicksort）
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int pivot = arr[(left + right) / 2];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;
            if (i <= j) {
                swap(arr, i++, j--);
            }
        }
        // 遞迴排序左右子區間
        if (left < j) quickSort(arr, left, j);
        if (i < right) quickSort(arr, i, right);
    }

    // 2. 遞迴合併兩個已排序的陣列
    public static int[] mergeRecursive(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        mergeHelper(a, 0, b, 0, result, 0);
        return result;
    }
    private static void mergeHelper(int[] a, int i, int[] b, int j, int[] r, int k) {
        // 若 a 或 b 已耗盡，直接複製剩餘元素
        if (i == a.length) {
            System.arraycopy(b, j, r, k, b.length - j);
            return;
        }
        if (j == b.length) {
            System.arraycopy(a, i, r, k, a.length - i);
            return;
        }
        // 比較當前元素，選小的放入 r[k]
        if (a[i] <= b[j]) {
            r[k] = a[i];
            mergeHelper(a, i + 1, b, j, r, k + 1);
        } else {
            r[k] = b[j];
            mergeHelper(a, i, b, j + 1, r, k + 1);
        }
    }

    // 3. 遞迴尋找陣列中的第 k 小元素（Quickselect）
    public static int kthSmallest(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];
        int pivotIndex = partition(arr, left, right);
        int len = pivotIndex - left + 1;
        if (k == len) {
            return arr[pivotIndex];
        } else if (k < len) {
            return kthSmallest(arr, left, pivotIndex - 1, k);
        } else {
            return kthSmallest(arr, pivotIndex + 1, right, k - len);
        }
    }
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                swap(arr, i++, j);
            }
        }
        swap(arr, i, right);
        return i;
    }

    // 4. 遞迴檢查陣列是否存在子序列總和等於目標值（Subset Sum）
    public static boolean subsetSum(int[] arr, int n, int target) {
        // 基本情況
        if (target == 0) return true;
        if (n == 0) return false;
        // 若當前元素大於 target，必須跳過
        if (arr[n - 1] > target) {
            return subsetSum(arr, n - 1, target);
        }
        // 選擇「包含」或「不包含」當前元素
        return subsetSum(arr, n - 1, target - arr[n - 1])
            || subsetSum(arr, n - 1, target);
    }

    // 交換輔助
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        // 測試資料
        int[] data1 = {7, 2, 1, 6, 8, 5, 3, 4};
        System.out.println("原始陣列: " + Arrays.toString(data1));

        // 1. 快速排序
        int[] qs = data1.clone();
        quickSort(qs, 0, qs.length - 1);
        System.out.println("1. QuickSort 排序後: " + Arrays.toString(qs));

        // 2. 遞迴合併已排序陣列
        int[] a = {1, 3, 5, 7}, b = {2, 4, 6, 8, 9};
        System.out.println("陣列 A: " + Arrays.toString(a));
        System.out.println("陣列 B: " + Arrays.toString(b));
        int[] merged = mergeRecursive(a, b);
        System.out.println("2. MergeRecursive 合併後: " + Arrays.toString(merged));

        // 3. 第 k 小元素
        int k = 4;
        int[] qs2 = data1.clone();
        int kth = kthSmallest(qs2, 0, qs2.length - 1, k);
        System.out.printf("3. 第 %d 小元素: %d (陣列現狀: %s)%n", k, kth, Arrays.toString(qs2));

        // 4. 子序列和
        int[] data2 = {3, 34, 4, 12, 5, 2};
        int target = 9;
        boolean exists = subsetSum(data2, data2.length, target);
        System.out.println("4. 子序列和 target = " + target + " 是否存在? " + exists);
    }
}
