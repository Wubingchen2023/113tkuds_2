import java.util.*;

public class lt_22_GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
        // 若字串長度達到 2 * n，表示已經是一組合法的括號
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }

        // 若還可以加入左括號，則嘗試加入
        if (open < max) {
            current.append('(');
            backtrack(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1); // 回溯
        }

        // 若右括號數量小於左括號，則可以加入右括號
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1); // 回溯
        }
    }

    public static void main(String[] args) {
        lt_22_GenerateParentheses solution = new lt_22_GenerateParentheses();
        int n = 3; // 測試輸入
        List<String> result = solution.generateParenthesis(n);
        
        System.out.println("n = " + n + " 時的所有合法括號組合：");
        for (String s : result) {
            System.out.println(s);
        }
    }
}

/*
解題思路：
1. 題目要求生成所有「有效的括號組合」，也就是左括號與右括號數量相等，並且任何前綴中左括號數量 ≥ 右括號數量。
2. 採用 **回溯法 (Backtracking)**：
   - 用變數 `open` 記錄已使用的左括號數量。
   - 用變數 `close` 記錄已使用的右括號數量。
   - 若 `open < n`，表示還能加入左括號。
   - 若 `close < open`，表示右括號數量小於左括號，可以合法地加入右括號。
3. 當字串長度達到 `2 * n` 時，說明形成了一組完整的合法組合，加入結果集中。
4. 每一步選擇後，需要回溯（刪除最後加入的字元），以嘗試其他可能的路徑。
5. 時間複雜度：O(4^n / √n)（卡塔蘭數，C_n 組合數量的大小）。
   空間複雜度：O(n)，遞迴深度最多為 2n。
*/ 
