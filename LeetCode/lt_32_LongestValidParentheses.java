import java.util.Stack;

public class lt_32_LongestValidParentheses {

    // 回傳最長合法括號子字串長度
    public static int longestValidParentheses(String s) {
        int maxLen = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); // 初始基底，處理邊界情形

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                // 遇到 '('，記錄索引進 stack
                stack.push(i);
            } else {
                // 遇到 ')'，彈出 stack 頂部
                stack.pop();
                if (stack.isEmpty()) {
                    // 若 stack 空，push 當前 i 當新基底
                    stack.push(i);
                } else {
                    // 若 stack 不空，maxLen 取 i - stack.peek()
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    // 測試範例
    public static void main(String[] args) {
        System.out.println(longestValidParentheses("(()"));      // 輸出 2
        System.out.println(longestValidParentheses(")()())"));   // 輸出 4
        System.out.println(longestValidParentheses(""));         // 輸出 0
        System.out.println(longestValidParentheses("()(()"));    // 輸出 2
        System.out.println(longestValidParentheses("()(())"));   // 輸出 6
    }
}

/*
【解題邏輯】

題目：給定一字串，只包含 '(' 和 ')'，找出最長合法（有效、匹配）的括號子字串長度。

核心思路（用 stack 儲存 index）：
1. 用 stack 記錄「尚未配對的 '(' 的 index」。
2. 初始化 stack 放入 -1，代表 base index（幫助處理從頭開始的有效子字串，像 "()()"）。
3. 循環掃描每個字元 s[i]：
    - 若遇到 '('：push(i) 進 stack。
    - 若遇到 ')'：pop stack 頂端（表示一組配對），
        - 若 pop 完 stack 空了（表示當前 ')' 沒有可配對的 '('，即失配），把這個 i 當作新基底 push 進 stack。
        - 若 stack 還有元素，說明前面有對應 '('，計算長度為 i - stack.peek()，取 max。
4. 掃描結束後，maxLen 即為答案。

重點：
- stack 用來追蹤尚未配對的 '('；遇到 ')', 優先配對。
- stack 維護了每個「有效區段」的分界點。
- 用 index 差計算長度（不是直接計數，而是依靠失配斷點分段）。

範例分析：
以 ")()())" 為例：
i=0, c=')'，pop stack 後為空，push(0) 新基底；
i=1, c='('，push(1)；
i=2, c=')'，pop(1)，stack.peek()=0，maxLen=2;
i=3, c='('，push(3)；
i=4, c=')'，pop(3)，stack.peek()=0，maxLen=4；
i=5, c=')'，pop(0)，stack 空，push(5)。

最長有效子字串長度=4。

時間複雜度：O(n)（一次掃描+stack push/pop 都是 O(1)）
空間複雜度：O(n)（最壞所有 '(' 都進 stack）

*/
