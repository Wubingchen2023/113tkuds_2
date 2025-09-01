import java.util.HashMap;

public class lt_03_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> last = new HashMap<>();
        int left = 0, ans = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (last.containsKey(c)) {
                left = Math.max(left, last.get(c) + 1); // 縮左界
            }
            last.put(c, right);                          // 更新最後位置
            ans = Math.max(ans, right - left + 1);      // 更新答案
        }
        return ans;
    }

    public static void main(String[] args) {
        lt_03_LongestSubstringWithoutRepeatingCharacters solver =
            new lt_03_LongestSubstringWithoutRepeatingCharacters();

        String[] testCases = {
            "abcabcbb",   // 預期 3 ("abc")
            "bbbbb",      // 預期 1 ("b")
            "pwwkew",     // 預期 3 ("wke")
            "",           // 預期 0
            "dvdf"        // 預期 3 ("vdf")
        };

        for (String s : testCases) {
            int result = solver.lengthOfLongestSubstring(s);
            System.out.println("Input: \"" + s + "\" → 最長長度 = " + result);
        }
    }
}

