import java.util.Arrays;

public class lt_16_3SumClosest {
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums); // Step 1: 排序

        int closestSum = nums[0] + nums[1] + nums[2]; // 初始值設為前三個數的和

        // Step 2: 固定第一個數，剩下兩個數用雙指針找最接近的和
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right];

                // 如果當前的和更接近 target，就更新答案
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }

                // 移動指針：根據 currentSum 和 target 的大小關係
                if (currentSum < target) {
                    left++; // 和太小 → 右移 left 增大總和
                } else if (currentSum > target) {
                    right--; // 和太大 → 左移 right 減少總和
                } else {
                    // 完全相等，最接近了，直接返回
                    return currentSum;
                }
            }
        }

        return closestSum;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        System.out.println("最接近的三數和為: " + threeSumClosest(nums, target));
        // Output: 2
    }
}

/*
解題思路：
1. 先對 nums 排序 → 為了讓雙指針運作（找 sum 趨近 target）

2. 固定第一個數 nums[i]，然後用 left 與 right 從兩端向中間逼近
   - 這樣能有效找出三數和最接近 target 的組合

3. 對於每一組 currentSum：
   - 若更接近 target，就更新 closestSum
   - 如果 currentSum < target，代表和太小，left++ 試著找大一點的數
   - 如果 currentSum > target，代表和太大，right-- 試著找小一點的數
   - 如果剛好相等，直接回傳，因為無法比這更接近了！

4. 最終回傳最接近的三數和

範例：
Input: nums = [-1, 2, 1, -4], target = 1
排序後：[-4, -1, 1, 2]
→ 最接近 1 的組合是：-1 + 2 + 1 = 2

時間複雜度：
- O(n^2) → 雙層迴圈（外層 O(n)，內層雙指針 O(n)）

空間複雜度：
- O(1) → 除了排序外，不需要額外空間
*/
