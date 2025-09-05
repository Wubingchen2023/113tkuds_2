import java.util.*;

public class LC32_LongestValidParen_Metro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.hasNextLine() ? sc.nextLine().trim() : "";
        System.out.println(longestValidParentheses(s));
    }

    // 核心：索引堆疊（棧底放 -1 當基準）
    public static int longestValidParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 初始基準，對應於「合法片段起點的前一位」
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // 進站：記錄索引，等待未來的出站配對
                st.push(i);
            } else { // c == ')'
                // 出站：嘗試配對最近的進站
                st.pop();

                if (st.isEmpty()) {
                    // 沒有可以配對的進站，這個 ')' 作為新基準
                    st.push(i);
                } else {
                    // 以當前棧頂作為最新基準計長度
                    ans = Math.max(ans, i - st.peek());
                }
            }
        }
        return ans;
    }
}
