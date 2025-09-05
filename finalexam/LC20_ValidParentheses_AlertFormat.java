import java.util.*;

public class LC20_ValidParentheses_AlertFormat {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine().trim();

        System.out.println(isValid(s));
    }

    public static boolean isValid(String s) {
        // 閉括號 → 對應開括號
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (map.containsValue(c)) {
                // 開括號直接 push
                stack.push(c);
            } else if (map.containsKey(c)) {
                // 碰到閉括號時：檢查棧頂是否對應
                if (stack.isEmpty() || stack.pop() != map.get(c)) {
                    return false;
                }
            } else {
                // 若遇到非法字元（理論上題目不會給），直接 false
                return false;
            }
        }
        // 檢查是否完全匹配
        return stack.isEmpty();
    }
}
