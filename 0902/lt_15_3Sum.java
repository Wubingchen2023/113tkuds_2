import java.util.*;

public class lt_15_3Sum {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // Step 1: 先排序，方便雙指針與去除重複

        for (int i = 0; i < nums.length - 2; i++) {
            // 跳過重複的固定數值
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int target = -nums[i]; // 要找的兩數和 = -nums[i]
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 移動 left 並跳過重複
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // 移動 right 並跳過重複
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < target) {
                    left++; // 和太小，左指針右移
                } else {
                    right--; // 和太大，右指針左移
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println("三數和為 0 的組合: " + threeSum(nums));
        // Output: [[-1, -1, 2], [-1, 0, 1]]
    }
}

/*
解題邏輯說明：
1. 先將 nums 排序 → 方便跳過重複元素，並進行雙指針操作
2. 對於每個 nums[i]：
   - 固定 nums[i] 為三元組的第一個數
   - 使用雙指針從 i+1 到結尾尋找另外兩個數，使總和為 0
   - target = -nums[i]
   - 如果 sum == target → 加入組合並跳過重複值
   - 如果 sum < target → 左指針右移
   - 如果 sum > target → 右指針左移
3. 重點在於：
   - 去重策略：避免重複的 i、left、right 值
   - 雙指針適用於「排序後找目標和」的場景

範例：
Input: [-1, 0, 1, 2, -1, -4]
排序後：[-4, -1, -1, 0, 1, 2]
i=-1，找 target=1，left=0, right=2 → 成立！

時間複雜度：
- 排序 O(n log n)
- 外層 O(n)，內層雙指針 O(n)
→ 總時間：O(n^2)

空間複雜度：
- O(1)（不含輸出結果）
*/

