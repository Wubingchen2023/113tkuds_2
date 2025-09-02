public class lt_10_RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        Boolean[][] memo = new Boolean[s.length() + 1][p.length() + 1];
        return dfs(s, 0, p, 0, memo);
    }

    // 回傳：s[i..] 是否能被 p[j..] 完整匹配
    private boolean dfs(String s, int i, String p, int j, Boolean[][] memo) {
        if (memo[i][j] != null) return memo[i][j];

        // p 用完 → 要求 s 也用完
        if (j == p.length()) {
            return memo[i][j] = (i == s.length());
        }

        // 判斷當下是否能匹配一個字元
        boolean firstMatch = (i < s.length()) &&
                (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

        // 看下一個是不是 '*'
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            // 兩種路徑：
            // 1) 當作 0 次 → 跳過「x*」
            // 2) 若可匹配，消耗 s 一個字元，p 停在 x*（讓 * 多吃幾個）
            boolean ans = dfs(s, i, p, j + 2, memo) ||
                          (firstMatch && dfs(s, i + 1, p, j, memo));
            return memo[i][j] = ans;
        } else {
            // 正常往前一格
            boolean ans = firstMatch && dfs(s, i + 1, p, j + 1, memo);
            return memo[i][j] = ans;
        }
    }

    public static void main(String[] args) {
        lt_10_RegularExpressionMatching solution = new lt_10_RegularExpressionMatching();

        // 測試案例
        String s1 = "aa";
        String p1 = "a";
        System.out.println("Test 1: " + solution.isMatch(s1, p1));  // false

        String s2 = "aa";
        String p2 = "a*";
        System.out.println("Test 2: " + solution.isMatch(s2, p2));  // true

        String s3 = "ab";
        String p3 = ".*";
        System.out.println("Test 3: " + solution.isMatch(s3, p3));  // true

        String s4 = "mississippi";
        String p4 = "mis*is*p*.";
        System.out.println("Test 4: " + solution.isMatch(s4, p4));  // false

        String s5 = "mississippi";
        String p5 = "mis*is*ip*.";
        System.out.println("Test 5: " + solution.isMatch(s5, p5));  // true
    }
}

/*
解題邏輯與思路（動態規劃，支援 '.' 與 '*'）：

1) 定義狀態：
   dp[i][j] 代表：s 的前 i 個字元（s[0..i-1]）是否能與 p 的前 j 個字元（p[0..j-1]）匹配。

2) 初始條件：
   - dp[0][0] = true（空字串對空樣式匹配）。
   - 對於 p 中可能出現的「x*」組合，若它們可以整段當成空（出現 0 次），
     則 dp[0][j] = dp[0][j-2]（僅在 p[j-1] == '*' 時成立）。用來處理像 "a*b*c*" 比對空字串。

3) 狀態轉移：
   設當前樣式字元為 pc = p[j-1]。
   - 若 pc != '*'：
       必須同時滿足：
       (a) 前綴匹配 dp[i-1][j-1] 為 true；
       (b) 當前字元相等 或 pc 為 '.' ：(s[i-1] == p[j-1] || p[j-1] == '.')
       => dp[i][j] = dp[i-1][j-1] && charMatch

   - 若 pc == '*'（表示前一個樣式字元 pre = p[j-2] 可以重複 0 次或多次）：
       兩種可能其一為真即可：
       (a) 出現 0 次：丟掉 "pre*" ⇒ dp[i][j] = dp[i][j-2]
       (b) 出現 >=1 次：當前 s[i-1] 能與 pre 匹配（pre == s[i-1] 或 pre == '.'），
           且保持樣式不動、消耗 s 的當前字元 ⇒ dp[i][j] = dp[i-1][j]

4) 回傳答案：
   dp[m][n] 即為整個 s 與 p 是否匹配。

5) 時間複雜度 O(m * n)，空間複雜度 O(m * n)。
*/
