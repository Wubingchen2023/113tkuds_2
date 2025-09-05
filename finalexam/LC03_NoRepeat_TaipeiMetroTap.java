import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.hasNextLine() ? sc.nextLine() : "";
        System.out.println(lengthOfLongestSubstring(s));
    }

    // 核心：滑動視窗 + 記錄每個字元最後一次出現的位置
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int ans = 0;
        int L = 0; // 視窗左界（含）

        for (int R = 0; R < s.length(); R++) {
            char c = s.charAt(R);

            // 若 c 先前出現過，且位置在目前視窗內，則把左界推進到 (上次位置 + 1)
            if (last.containsKey(c) && last.get(c) >= L) {
                L = last.get(c) + 1;
            }

            // 更新 c 的最後出現位置
            last.put(c, R);

            // 更新答案 = 目前視窗長度
            ans = Math.max(ans, R - L + 1);
        }
        return ans;
    }
}

/*
解題思路：
1. 使用兩個指標 L、R 表示目前的連續區間 [L, R]，以及一個 Map<char, lastIndex> 紀錄每個字元最後出現位置。
2. 右指標 R 由左至右擴張：
   - 若 s[R] 在視窗內曾出現（last.containsKey(s[R]) 且 last.get(s[R]) >= L），
     代表出現重複，將左界 L 推到「該字元上次出現位置 + 1」以移除重複。
   - 無論如何，更新 last[s[R]] = R。
   - 每步都用 ans = max(ans, 視窗長度=R-L+1) 更新答案。
3. 一次遍歷即可得到最長不重複子字串長度。

邊界案例對應：
- "" → 回傳 0（for 迴圈不進入，初始 ans=0）
- "aaaa" → 始終因重複把 L 前推，最大長度維持 1
- 全不重複 → 視窗一路擴張，最後 ans=|s|
- "abba"：
  R=0:'a' → L=0, ans=1
  R=1:'b' → L=0, ans=2 ("ab")
  R=2:'b' → 重複且 last['b']=1≥L(0) → L=1+1=2，ans=max(2, 2-2+1=1)=2
  R=3:'a' → last['a']=0 < L(2) 不影響 → ans=max(2, 3-2+1=2)=2，最終 2

- 時間複雜度：O(n)（每字元至多被 L、R 指到一次）
- 空間複雜度：O(k)（k 為字元集合大小，ASCII 可視作常數級）

*/
