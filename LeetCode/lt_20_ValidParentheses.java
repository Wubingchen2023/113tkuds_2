import java.util.*;

public class lt_20_ValidParentheses {
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // 如果是左括號，推入堆疊
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // 如果是右括號，檢查堆疊是否為空
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                // 檢查是否為匹配的括號組合
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }

        // 如果最後堆疊為空，代表完全匹配
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s1 = "()[]{}";
        String s2 = "(]";
        String s3 = "([{}])";

        System.out.println(s1 + " → " + isValid(s1)); // true
        System.out.println(s2 + " → " + isValid(s2)); // false
        System.out.println(s3 + " → " + isValid(s3)); // true
    }
}

/*
解題邏輯：
1. 使用 Stack 來追蹤尚未閉合的左括號。
2. 當遇到右括號時，檢查堆疊頂端是否為對應的左括號：
   - 若匹配 → 彈出堆疊
   - 若不匹配 → 回傳 false
3. 遍歷完成後，堆疊若仍有元素，代表有未匹配的左括號 → false
   - 否則 → true

範例：
Input: "([{}])"
Step:
- ( → push
- [ → push
- { → push
- } → pop '{'
- ] → pop '['
- ) → pop '('
最後堆疊為空 → true

時間複雜度：
O(n)，遍歷字串一次

空間複雜度：
O(n)，最壞情況（全是左括號）需存入 n 個字元
*/
