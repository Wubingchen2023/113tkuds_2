import java.util.*;

public class lt_40_CombinationSumII {

    // 找出所有不重複組合，和為 target，每個數只能用一次
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(candidates); // 先排序，便於剪枝 + 去重
        backtrack(candidates, 0, target, new ArrayList<>(), ans);
        return ans;
    }

    // 回溯函式
    private static void backtrack(int[] nums, int start, int remaining, List<Integer> path, List<List<Integer>> ans) {
        if (remaining == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < nums.length; i++) {
            int val = nums[i];

            // 若超出 target，剪枝（因為已排序）
            if (val > remaining) break;

            // 去重剪枝：同一層中若前一個相同數字已處理過，略過
            if (i > start && nums[i] == nums[i - 1]) continue;

            path.add(val);
            // 注意：不能重複使用 → 下一層從 i+1 開始
            backtrack(nums, i + 1, remaining - val, path, ans);
            path.remove(path.size() - 1);
        }
    }

    // 測試
    public static void main(String[] args) {
        int[] candidates1 = {10,1,2,7,6,1,5};
        int[] candidates2 = {2,5,2,1,2};
        System.out.println(combinationSum2(candidates1, 8));
        // 預期：[[1,1,6],[1,2,5],[1,7],[2,6]]
        System.out.println(combinationSum2(candidates2, 5));
        // 預期：[[1,2,2],[5]]
    }
}

/*
【解題邏輯與思路】

題意：
- 找出所有「不重複」的組合，使組合中數字加總為 target。
- 不同於 Leetcode 39，每個數字只能使用「一次」。
- 輸出中不得有重複組合（即使順序不同也算重複）。

差異：
- Leetcode 39（每個元素可重複使用 → 下一層從 i 開始）
- Leetcode 40（每個元素只能用一次 → 下一層從 i+1 開始 + 去重處理）

步驟與策略：

1. 排序 `candidates`：
   - 排序後可以方便「剪枝」（若當前數 > 剩餘 target 可直接跳過）
   - 也可以在同一層中進行「去重」（避免 [1,2,2] 和 [1,2,2] 出現兩次）

2. 回溯：
   - 每次從 `start` 開始枚舉候選數字。
   - 若遇到 `nums[i] > remaining`，代表無法再往下 → `break` 剪枝。
   - 若遇到相同數字 `nums[i] == nums[i-1]`，且 `i > start`，代表是同層重複選擇 → `continue` 去重。

3. 不允許重複使用元素：
   - 遞迴呼叫下一層時要傳入 `i+1`（非 i）

範例說明：
Input: candidates = [10,1,2,7,6,1,5], target = 8
排序後：[1,1,2,5,6,7,10]

1. 第一層選 1：
   - 第二層再選 1 → 第三層選 6 → 成功 [1,1,6]
   - 第二層選 2 → 第三層選 5 → 成功 [1,2,5]
   - 第二層選 7 → 成功 [1,7]

2. 第一層選 2：
   - 第二層選 6 → 成功 [2,6]

3. 第一層選 1 (i=1)，與前一個相同且同層 → 被跳過（去重成功）

時間複雜度（最壞）：
- O(2^n)，因為每個數都有選/不選兩種情況（排列樹）
- 實務中剪枝與去重可有效減少分支

空間複雜度：
- O(n)：遞迴最深可達 n（候選數字數量）

*/
