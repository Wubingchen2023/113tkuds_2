import java.util.*;

public class NumberArrayProcessor {
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 3, 5, 7, 7, 9};
        int[] arr2 = {2, 3, 4, 4, 8};

        // 1. 移除重複元素
        int[] unique = removeDuplicates(arr1);
        System.out.println("1. 移除重複後: " + Arrays.toString(unique));

        // 2. 合併已排序陣列
        int[] merged = mergeSortedArrays(arr1, arr2);
        System.out.println("2. 合併排序後: " + Arrays.toString(merged));

        // 3. 頻率最高的元素
        int mode = findMostFrequent(arr1);
        System.out.println("3. 出現頻率最高的元素: " + mode);

        // 4. 分割成兩個子陣列
        int[][] split = splitArray(arr1);
        System.out.println("4. 分割後子陣列 A: " + Arrays.toString(split[0]));
        System.out.println("   分割後子陣列 B: " + Arrays.toString(split[1]));
    }

    // 1. 移除重複元素（保留原排序）
    public static int[] removeDuplicates(int[] arr) {
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        for (int num : arr) {
            set.add(num);
        }
        int[] result = new int[set.size()];
        int idx = 0;
        for (int num : set) {
            result[idx++] = num;
        }
        return result;
    }

    // 2. 合併兩個已排序的陣列
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int n = a.length, m = b.length;
        int[] merged = new int[n + m];
        int i = 0, j = 0, k = 0;
        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                merged[k++] = a[i++];
            }
            else merged[k++] = b[j++];
        }
        while (i < n) {
            merged[k++] = a[i++];
        }
        while (j < m) {
            merged[k++] = b[j++];
        }
        return merged;
    }

    // 3. 找出出現頻率最高的元素（若多個，同回傳第一個）
    public static int findMostFrequent(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : arr) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        int mode = arr[0], maxCount = 0;
        for (int num : arr) {
            int count = freq.get(num);
            if (count > maxCount) {
                maxCount = count;
                mode = num;
            }
        }
        return mode;
    }

    // 4. 將陣列分割成兩個相等（或近似相等）子陣列
    public static int[][] splitArray(int[] arr) {
        int n = arr.length;
        int mid = n / 2;
        int sizeA = mid;
        int sizeB = n - mid;
        int[] a = Arrays.copyOfRange(arr, 0, sizeA);
        int[] b = Arrays.copyOfRange(arr, sizeA, n);
        return new int[][]{a, b};
    }
}
