import java.util.*;

public class LC28_StrStr_NoticeSearch {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String haystack = sc.nextLine(); // 公告全文
        String needle = sc.nextLine();   // 欲搜尋片段

        int index = strStrKMP(haystack, needle); // KMP 實作
        // int index = strStrBruteForce(haystack, needle); // 或改用暴力法
        System.out.println(index);
    }

    // 暴力法 (O(n * m)) 適合小字串
    public static int strStrBruteForce(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;

        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && haystack.charAt(i + j) == needle.charAt(j)) {
                j++;
            }
            if (j == m) return i; // 找到完整匹配
        }
        return -1;
    }

    // KMP (O(n + m)) 適合長文本搜尋
    public static int strStrKMP(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;

        // 建立 KMP 的 prefix table（又稱 "部分匹配表" 或 LPS 陣列）
        int[] lps = buildLPS(needle);

        int i = 0, j = 0; // i for haystack, j for needle
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
                if (j == m) return i - j; // 完整匹配
            } else {
                if (j > 0) {
                    j = lps[j - 1]; // 回溯到上個可能的前綴
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    // 建立 LPS（Longest Prefix which is also Suffix）陣列
    private static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0, i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i++] = len;
            } else {
                if (len > 0) {
                    len = lps[len - 1]; // 縮短前綴
                } else {
                    lps[i++] = 0;
                }
            }
        }
        return lps;
    }
}
