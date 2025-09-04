
public class lt_35_SearchInsertPosition {

    public static int searchInsert(int[] nums, int target) {
        int l = 0, r = nums.length - 1;

        while (l <= r) {
            int mid = l + ((r - l) >> 1);

            if (nums[mid] == target) {
                return mid; // 找到目標，直接回傳
            } else if (nums[mid] < target) {
                l = mid + 1; // 往右側搜尋
            } else {
                r = mid - 1; // 往左側搜尋
            }
        }
        // 若找不到，l 會停在「插入位置」
        return l;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        System.out.println(searchInsert(nums, 5)); // 2
        System.out.println(searchInsert(nums, 2)); // 1
        System.out.println(searchInsert(nums, 7)); // 4
        System.out.println(searchInsert(nums, 0)); // 0
    }
}

/*
【解題邏輯】

題意：給定一個升冪排序的陣列，若 target 存在，回傳其索引；否則回傳它應插入的位置 (保持排序)。

核心思路（二分搜尋）：
1. 用二分搜尋找 target。
2. 若找到，直接回傳 mid。
3. 若找不到，最終 while 結束時，左指標 l 會停在「第一個大於 target 的位置」，
   那正好就是 target 應插入的位置。
   - 因為：
     • 若 target 比所有元素小，l 最後會停在 0。
     • 若 target 比所有元素大，l 最後會停在 nums.length。
     • 若 target 落在中間，l 最後會停在應插入的索引。

範例：
nums=[1,3,5,6]
target=2
流程：
mid=1(nums[1]=3 >2)，往左 → r=0
mid=0(nums[0]=1 <2)，往右 → l=1
此時 l=1, r=0 → 迴圈結束 → 回傳 l=1。

時間複雜度：O(log n)  
空間複雜度：O(1)
*/
