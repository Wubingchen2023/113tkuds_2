import java.util.Arrays;

public class lt_33_SearchinRotatedSortedArray {

    // 單趟二分搜尋：回傳 target 的索引，找不到回傳 -1
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int l = 0, r = nums.length - 1;

        while (l <= r) {
            int mid = l + ((r - l) >> 1);

            if (nums[mid] == target) return mid;

            // 判斷哪一邊是「有序（單調遞增）」的
            if (nums[l] <= nums[mid]) {
                // 左半段 [l..mid] 有序
                if (nums[l] <= target && target < nums[mid]) {
                    // 目標在左半段的有序區間內
                    r = mid - 1;
                } else {
                    // 否則到右半段尋找
                    l = mid + 1;
                }
            } else {
                // 右半段 [mid..r] 有序
                if (nums[mid] < target && target <= nums[r]) {
                    // 目標在右半段的有序區間內
                    l = mid + 1;
                } else {
                    // 否則到左半段尋找
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] arrays = {
            {4,5,6,7,0,1,2},
            {6,7,0,1,2,4,5},
            {1},
            {1,3},
            {3,1}
        };
        int[] targets = {0, 4, 0, 3, 1}; // 對應期望：4, 5, -1, 1, 1

        for (int i = 0; i < arrays.length; i++) {
            int ans = search(arrays[i], targets[i]);
            System.out.printf("nums=%s, target=%d -> idx=%d%n",
                    Arrays.toString(arrays[i]), targets[i], ans);
        }
    }
}

/*
【解題邏輯】

題意：已升冪排序的陣列經過一次旋轉（無重複值），在 O(log n) 時間內找 target 的索引。

關鍵：
- 旋轉後的陣列仍然由兩段「遞增」片段組成；對於任意 mid，至少有一側（左半 [l..mid] 或右半 [mid..r]）是**有序**的。
- 若能判斷哪一側有序，就能用「目標是否落在該有序區間」來決定收縮哪邊邊界，維持二分的 O(log n)。

演算法思路（單趟二分）：
1) 初始化 l=0, r=n-1；迴圈 while (l <= r)：
   - mid = l + (r-l)/2。
   - 若 nums[mid] == target，直接回傳 mid。
   - 判斷有序側：
     a) 若 nums[l] <= nums[mid]，則左側 [l..mid] 有序。
        - 若 nums[l] <= target < nums[mid]，目標在左側 → r = mid - 1；
        - 否則目標在右側 → l = mid + 1。
     b) 否則右側 [mid..r] 有序。
        - 若 nums[mid] < target <= nums[r]，目標在右側 → l = mid + 1；
        - 否則目標在左側 → r = mid - 1。
2) 若迴圈結束仍未找到，回傳 -1。

- 二分每次都能辨識出一個「完整有序」的半邊；若目標的值域不在該有序半邊，就可安全丟棄它，否則保留該半邊並丟棄另一半。
- 每步驟確實縮小搜尋區間的一半，因此時間複雜度為 O(log n)。

邊界與細節：
- 用 nums[l] <= nums[mid] 能穩定判定左側是否有序（等號處理了長度 2 的情況）。
- 若陣列長度為 0 或 1，直接處理即可。
- 若 target 不存在，最終會收斂到 l > r 並返回 -1。

- 時間複雜度：O(log n)。
- 空間複雜度：O(1)。

*/
