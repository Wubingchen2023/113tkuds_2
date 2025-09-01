public class lt_05_LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expand(s, i, i);     // 奇數
            int len2 = expand(s, i, i + 1); // 偶數
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                int half = (len - 1) / 2;
                start = i - half;
                end   = i + len/2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expand(String s, int L, int R) {
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--; R++;
        }
        return R - L - 1; // 擴過頭一格
    }

    public static void main(String[] args) {
        lt_05_LongestPalindromicSubstring solver = new lt_05_LongestPalindromicSubstring();

        String s1 = "babad";
        String s2 = "cbbd";
        String s3 = "a";
        String s4 = "ac";

        System.out.println("Input: " + s1 + " → Longest Palindrome: " + solver.longestPalindrome(s1));
        System.out.println("Input: " + s2 + " → Longest Palindrome: " + solver.longestPalindrome(s2));
        System.out.println("Input: " + s3 + " → Longest Palindrome: " + solver.longestPalindrome(s3));
        System.out.println("Input: " + s4 + " → Longest Palindrome: " + solver.longestPalindrome(s4));
    }
}

