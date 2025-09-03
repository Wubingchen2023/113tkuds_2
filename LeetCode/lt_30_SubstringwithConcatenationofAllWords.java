import java.util.*;

public class lt_30_SubstringwithConcatenationofAllWords {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return result;

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        // 建立每個單詞應該出現的次數
        Map<String, Integer> wordMap = new HashMap<>();
        for (String w : words) {
            wordMap.put(w, wordMap.getOrDefault(w, 0) + 1);
        }

        // 因為所有單詞等長，採用滑動視窗 (window) 跨 wordLen 分別起點檢查
        for (int i = 0; i < wordLen; i++) {
            int left = i, right = i, count = 0;
            Map<String, Integer> windowMap = new HashMap<>();

            while (right + wordLen <= s.length()) {
                // 取當前右側單詞
                String w = s.substring(right, right + wordLen);
                right += wordLen;

                if (wordMap.containsKey(w)) {
                    windowMap.put(w, windowMap.getOrDefault(w, 0) + 1);
                    count++;

                    // 若此單詞出現次數超過預期，要把左側窗口收縮直到不超過
                    while (windowMap.get(w) > wordMap.get(w)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowMap.put(leftWord, windowMap.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }

                    // 若已經湊齊所有單詞，記錄答案
                    if (count == wordCount) {
                        result.add(left);
                    }
                } else {
                    // 若遇到非目標單詞，直接清空視窗
                    windowMap.clear();
                    count = 0;
                    left = right;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        lt_30_SubstringwithConcatenationofAllWords sol = new lt_30_SubstringwithConcatenationofAllWords();

        // 範例 1
        String s1 = "barfoothefoobarman";
        String[] w1 = {"foo", "bar"};
        printResult(s1, w1, sol.findSubstring(s1, w1)); // [0, 9]

        // 範例 2：重複單詞
        String s2 = "wordgoodgoodgoodbestword";
        String[] w2 = {"word","good","best","good"};
        printResult(s2, w2, sol.findSubstring(s2, w2)); // [8]

        // 範例 3：沒有匹配
        String s3 = "aaaaaa";
        String[] w3 = {"aa","bb"};
        printResult(s3, w3, sol.findSubstring(s3, w3)); // []

        // 範例 4：邊界 - words 只有一個
        String s4 = "foobar";
        String[] w4 = {"bar"};
        printResult(s4, w4, sol.findSubstring(s4, w4)); // [3]

        // 範例 5：多起點、交錯情況
        String s5 = "barfoofoobarthefoobarman";
        String[] w5 = {"bar","foo","the"};
        printResult(s5, w5, sol.findSubstring(s5, w5)); // [6, 9, 12]
    }

    private static void printResult(String s, String[] words, List<Integer> result) {
        System.out.println("Input: s = \"" + s + "\", words = " + Arrays.toString(words));
        System.out.println("Output: " + result);
    }
}

/*
解題邏輯

題意：
給定一字串 s 和一組長度相同的單詞陣列 words，
找出所有「起始索引」，使得這些索引開始的子字串是 words 所有單詞「無重疊且無間隔」串接的排列之一。

思路：
1. 單詞長度一致，可將 s 視為多個 wordLen 長度的「格子」。
2. 使用滑動視窗法，對每個 0 ~ wordLen-1 的偏移做起點，能保證不會遺漏任意組合。
3. 用一個 Map 統計 words 中每個單詞出現次數（wordMap）。
4. 對每個窗口，用 windowMap 統計當前視窗內單詞出現次數。
   - 當某個單詞超過預期次數，收縮左邊窗口，直到回到預期。
   - 若收集到 words.length 個單詞且全部合法，則左側為起始索引。
5. 若遇到不是目標單詞的片段，直接清空視窗，重來。
6. 多起點可覆蓋所有可能組合（防止出現跨格狀況）。

範例：
s = "barfoothefoobarman", words = ["foo","bar"]
答案 = [0,9]（"barfoo" 和 "foobar"）

- 時間複雜度：O(L * N)，L = s 長度，N = words.length（每格掃一次）。
- 空間複雜度：O(N) 作為字典統計單詞數。
*/ 
