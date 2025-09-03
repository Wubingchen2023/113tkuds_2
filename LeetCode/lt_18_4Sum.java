import java.util.*;

public class lt_18_4Sum {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums); // Step 1: 排序以利雙指針和去重

        int n = nums.length;
        // Step 2: 第一層迴圈 - 固定第一個數 nums[i]
        for (int i = 0; i < n - 3; i++) {
            // 跳過重複值
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Step 3: 第二層迴圈 - 固定第二個數 nums[j]
            for (int j = i + 1; j < n - 2; j++) {
                // 跳過重複值
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                // Step 4: 雙指針找剩下兩數
                int left = j + 1;
                int right = n - 1;

                // 為了防止整數溢位，使用 long 或轉型
                while (left < right) {
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];

                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // 跳過重複的 left/right
                        while (left < right && nums[left] == nums[left + 1]) left++;
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
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 0, -1, 0, -2, 2};
        int target = 0;
        System.out.println("所有和為 target 的四元組: " + fourSum(nums, target));
        // Output: [[-2, -1, 1, 2], [-2,  0, 0, 2], [-1, 0, 0, 1]]
    }
}

/*
解題邏輯：
1. 為了找出四數和等於 target，我們固定前兩個數 nums[i], nums[j]，
   然後在剩下的數中用雙指針（left, right）去尋找使四數總和為 target 的組合。

2. 為了避免重複組合：
   - i > 0 時跳過 nums[i] == nums[i-1]
   - j > i+1 時跳過 nums[j] == nums[j-1]
   - 找到一組後，也要略過重複的 nums[left] 與 nums[right]

3. 為了防止整數溢位（如極大極小整數），建議使用 long 或將和強制轉型為 long

範例分析：
Input: [1, 0, -1, 0, -2, 2], target = 0
排序後: [-2, -1, 0, 0, 1, 2]
可產生：
- [-2, -1, 1, 2]
- [-2, 0, 0, 2]
- [-1, 0, 0, 1]

時間複雜度：
- 排序：O(n log n)
- 雙層迴圈 + 雙指針：O(n^3)

空間複雜度：
- O(1)（不包含輸出結果）
*/
