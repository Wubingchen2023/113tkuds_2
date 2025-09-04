import java.util.Arrays;

public class lt_34_FindFirstandLastPositionofElementinSortedArray {

    public static int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);   // 找左邊界
        int last = findBound(nums, target, false);   // 找右邊界
        return new int[]{first, last};
    }

    // binary search 找邊界
    private static int findBound(int[] nums, int target, boolean isFirst) {
        int l = 0, r = nums.length - 1, ans = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);

            if (nums[mid] == target) {
                ans = mid; // 先記錄答案
                if (isFirst) {
                    // 繼續往左找，可能還有更左的 target
                    r = mid - 1;
                } else {
                    // 繼續往右找，可能還有更右的 target
                    l = mid + 1;
                }
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] arrays = {
            {5,7,7,8,8,10},
            {5,7,7,8,8,10},
            {5,7,7,8,8,10},
            {},
            {1}
        };
        int[] targets = {8, 6, 7, 0, 1};
        for (int i = 0; i < arrays.length; i++) {
            int[] res = searchRange(arrays[i], targets[i]);
            System.out.printf("nums=%s, target=%d -> %s%n",
                Arrays.toString(arrays[i]), targets[i], Arrays.toString(res));
        }
    }
}

/*
【解題思路】

題目：已排序好的陣列 nums，要求 O(log n) 找出 target 的起始與結束位置，若不存在回傳 [-1, -1]。

解法：二分搜尋兩次
1. 找「最左邊的 target」：
   - 每次遇到 nums[mid] == target 時，不馬上停下，而是記錄 ans=mid，
     然後把右邊界 r 移到 mid-1，繼續往左尋找，直到沒有更左的。
   - 最終 ans 就是最左出現位置，若沒找到保持 -1。

2. 找「最右邊的 target」：
   - 類似，遇到 nums[mid] == target 時記錄 ans=mid，
     但這次把左邊界 l 移到 mid+1，繼續往右尋找，直到沒有更右的。

3. 把兩個結果組合成 [first, last] 回傳。

- 一次普通二分只能保證找到某個 target，但不能確定是最左或最右。
- 透過「命中時繼續往一側縮小」的方法，可以精確逼近邊界。
- 由於每次搜尋都會縮小一半範圍，因此時間複雜度仍然是 O(log n)。

範例：
nums = [5,7,7,8,8,10], target=8
- 找左邊界：第一次命中 mid=3(值=8)，記錄 ans=3，往左繼續；再命中 mid=2(值=7 不符)，最後得到 first=3。
- 找右邊界：第一次命中 mid=3，記錄 ans=3，往右繼續；再命中 mid=4(值=8)，記錄 ans=4，往右繼續；最後得到 last=4。
結果 [3,4]。

邊界情況：
- target 不存在 → 回傳 [-1,-1]
- 陣列空 → [-1,-1]
- 單一元素，target 存在/不存在都正確處理。

時間複雜度：O(log n)（兩次二分）
空間複雜度：O(1)
*/
