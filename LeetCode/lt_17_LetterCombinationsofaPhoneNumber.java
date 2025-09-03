import java.util.*;

public class lt_17_LetterCombinationsofaPhoneNumber {
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // 若輸入為空，回傳空列表
        if (digits == null || digits.length() == 0) return result;

        // 數字對應字母 Map
        Map<Character, String> phoneMap = new HashMap<>();
        phoneMap.put('2', "abc");
        phoneMap.put('3', "def");
        phoneMap.put('4', "ghi");
        phoneMap.put('5', "jkl");
        phoneMap.put('6', "mno");
        phoneMap.put('7', "pqrs");
        phoneMap.put('8', "tuv");
        phoneMap.put('9', "wxyz");

        // 呼叫回溯函數開始遞迴
        backtrack(digits, 0, new StringBuilder(), phoneMap, result);
        return result;
    }

    // 回溯函數：index 為目前處理到的 digits 字元位置
    private static void backtrack(String digits, int index, StringBuilder current,
                                  Map<Character, String> phoneMap, List<String> result) {
        // 當 current 長度等於 digits 長度，代表一組完成
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // 取得當前數字對應的所有字母
        String letters = phoneMap.get(digits.charAt(index));

        // 嘗試所有對應字母
        for (char letter : letters.toCharArray()) {
            current.append(letter); // 選擇
            backtrack(digits, index + 1, current, phoneMap, result); // 遞迴下一層
            current.deleteCharAt(current.length() - 1); // 撤銷選擇（回溯）
        }
    }

    public static void main(String[] args) {
        String digits = "23";
        System.out.println("所有可能的字母組合: " + letterCombinations(digits));
        // Output: [ad, ae, af, bd, be, bf, cd, ce, cf]
    }
}

/*
解題邏輯：
1. 使用 Map 將每個數字對應到按鍵上的字母，例如 '2' → "abc"
2. 使用 Backtracking 進行所有組合的遞迴組裝：
   - 每次遞迴根據目前 index 的數字找出對應的字母
   - 嘗試將其中一個字母加入 current 組合中
   - 繼續遞迴處理下一個 index
   - 遞迴返回時，移除最後一個字母 → 回溯
3. 終止條件為 current 長度 == digits 長度 → 加入答案

範例：
Input: "23"
對應：
- 2 → abc
- 3 → def
產出組合：
- a + d → "ad"
- a + e → "ae"
- a + f → "af"
- b + d → "bd"
...
共 3 × 3 = 9 種組合

時間複雜度：
- 每個數字最多對應 4 個字母（如 7, 9）
- 若有 n 位數，最壞情況為 O(4ⁿ)

空間複雜度：
- 遞迴深度最多為 n，空間複雜度為 O(n)
*/
