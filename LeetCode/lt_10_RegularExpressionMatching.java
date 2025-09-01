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

