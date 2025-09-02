public class lt_14_LongestCommomPrefix {
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // 先假設第一個字串是共同前綴
        String prefix = strs[0];

        // 從第二個字串開始，逐一比對
        for (int i = 1; i < strs.length; i++) {
            String current = strs[i];

            // 不斷縮短 prefix，直到當前字串以 prefix 開頭
            while (current.indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);

                // 如果縮短到空字串，直接回傳
                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        System.out.println("最長共同前綴: " + longestCommonPrefix(strs)); // Output: "fl"
    }
}

/*
解題邏輯：
1. 先假設第一個字串 strs[0] 為共同前綴 prefix。
2. 從第二個字串開始，逐一檢查：
   - 使用 current.indexOf(prefix) != 0 判斷是否以 prefix 開頭
   - 若不是，縮短 prefix（去掉最後一個字元），直到符合為止
3. 當遍歷完成後，剩下的 prefix 即為最長共同前綴
4. 若過程中 prefix 縮短到空字串，直接回傳 ""

範例：
Input: ["flower", "flow", "flight"]
- 初始 prefix = "flower"
- 與 "flow" 比較 → "flow" 不以 "flower" 開頭 → 縮短成 "flow"
- 與 "flight" 比較 → "flight" 不以 "flow" 開頭 → 縮短成 "flo" → "fl"
- 最終結果 = "fl"

時間複雜度：
O(S)，S 為所有字元總和
空間複雜度：
O(1)，只使用常數額外空間
*/
