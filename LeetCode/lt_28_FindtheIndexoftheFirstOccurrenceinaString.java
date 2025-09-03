public class lt_28_FindtheIndexoftheFirstOccurrenceinaString {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();

        if (m == 0) return 0; // 空字串預設回傳 0

        // 遍歷 haystack，每次檢查長度 m 的子字串
        for (int i = 0; i <= n - m; i++) {
            // 檢查從 i 開始的子字串是否等於 needle
            if (haystack.substring(i, i + m).equals(needle)) {
                return i; // 找到第一個匹配，返回索引
            }
        }

        return -1; // 沒找到返回 -1
    }

    public static void main(String[] args) {
        lt_28_FindtheIndexoftheFirstOccurrenceinaString solution = new lt_28_FindtheIndexoftheFirstOccurrenceinaString();

        String[][] testCases = {
            {"hello", "ll"},
            {"aaaaa", "bba"},
            {"", ""},
            {"abc", ""},
            {"", "a"},
            {"mississippi", "issip"},
            {"leetcode", "code"},
            {"needle in haystack", "hay"}
        };

        for (String[] test : testCases) {
            String haystack = test[0];
            String needle = test[1];
            int index = solution.strStr(haystack, needle);
            System.out.printf("haystack=\"%s\", needle=\"%s\" → index: %d\n", haystack, needle, index);
        }
    }
}

/*
解題邏輯

題意：
給定兩個字串 haystack 與 needle，找出 needle 在 haystack 中第一次出現的索引。
若 needle 不存在於 haystack 中，返回 -1。
若 needle 為空字串，按照定義返回 0。

思路：
1. 最直接的方法是「滑動視窗比對」：
   - haystack 長度 = n，needle 長度 = m。
   - 在 haystack 上從索引 0 ~ n-m 遍歷。
   - 每次取長度 m 的子字串，檢查是否等於 needle。
   - 若相等，立即返回索引。
2. 若遍歷結束仍未找到，返回 -1。

- 時間複雜度：O((n-m+1) * m)，最壞情況需檢查每個起點各 m 字元。
- 空間複雜度：O(1)，除了輸入外不需要額外空間。
*/ 
