import java.util.*;

public class lt_39_CombinationSum {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // 排序有助於剪枝（提前結束不可能解）
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    // 回溯主體
    private static void backtrack(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> res) {
        if (target == 0) {
            // 找到合法組合，需複製 path
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) break; // 剪枝
            path.add(candidates[i]); // 選取目前元素
            // 注意「每個數可重複選取」，所以下一層仍從 i 開始
            backtrack(candidates, target - candidates[i], i, path, res);
            path.remove(path.size() - 1); // 回溯
        }
    }

    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> ans = combinationSum(candidates, target);
        System.out.println(ans); // [[2,2,3], [7]]
    }
}

/*
【解題邏輯】

題目：給一個正整數陣列 candidates（不重複），和整數 target。找出所有「組合總和為 target」的組合。
- 每個數可無限次重複選取。
- 答案不重複（組合內順序不重要）。

關鍵思路：**回溯法（Backtracking）**
1. 先對 candidates 排序（便於剪枝）。
2. 用一個遞迴 backtrack，每次維護：
   - 當前組合 path（List<Integer>）
   - 當前剩餘 target
   - 當前可選擇元素的起始下標 start
3. 進入遞迴：
   a. 若 target==0，代表找到一組合法解，複製 path 加入答案。
   b. 若 target<0，無效，return。
   c. 否則遍歷從 start 開始的每個候選數：
      • 若 candidates[i] > target，直接 break（排序保證後面更大）。
      • 選取 candidates[i]，加入 path，遞迴呼叫（下一層仍從 i 開始，允許重複選同數）。
      • 回溯（移除剛剛加的數）。

補充說明：
- 為避免重複組合，循環 always 從當前 start 開始，不倒退。
- 路徑 path 必須 new ArrayList<>(path) 存入答案，否則會被後續改動影響。

時間複雜度：
- 最壞 O(2^t)，t = target/min(candidates)，因為每個點都可能嘗試（實際通常小於這個上限）。

空間複雜度：
- 遞迴深度與答案儲存空間，最壞 O(target)。
*/
