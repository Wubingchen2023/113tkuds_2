import java.util.*;

public class M06_PalindromeClean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        // 1. 清洗字串：僅保留英文字母，轉成小寫
        StringBuilder cleaned = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }

        // 2. 使用雙指標檢查是否回文
        if (isPalindrome(cleaned.toString())) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    // 雙指標回文檢測
    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false; // 若遇到不相等立即返回
            }
            left++;
            right--;
        }
        return true;
    }
}
