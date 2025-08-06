import java.util.*;

public class AdvancedStringRecursion {

    // 1. 遞迴產生字串的所有排列組合
    public static List<String> permutations(String str) {
        List<String> result = new ArrayList<>();
        permuteHelper(str.toCharArray(), 0, result);
        return result;
    }

    private static void permuteHelper(char[] arr, int index, List<String> result) {
        if (index == arr.length - 1) {
            result.add(new String(arr));
            return;
        }
        Set<Character> seen = new HashSet<>();  // 去重：避免相同字符重複排列
        for (int i = index; i < arr.length; i++) {
            if (seen.add(arr[i])) {
                swap(arr, index, i);
                permuteHelper(arr, index + 1, result);
                swap(arr, index, i);  // 回溯
            }
        }
    }

    // 2. 遞迴實作字串匹配：檢查 pattern 是否出現在 text 中
    public static boolean recursiveMatch(String text, String pattern) {
        return matchHelper(text, pattern, 0);
    }

    private static boolean matchHelper(String text, String pattern, int pos) {
        int n = text.length(), m = pattern.length();
        if (m == 0) return true;       // 空 pattern 永遠匹配
        if (pos + m > n) return false; // 剩餘長度不足
        if (text.substring(pos, pos + m).equals(pattern)) {
            return true;
        } else {
            return matchHelper(text, pattern, pos + 1);
        }
    }

    // 3. 遞迴移除字串中的重複字符
    public static String removeDuplicates(String str) {
        return removeDupHelper(str, new HashSet<>(), 0);
    }

    private static String removeDupHelper(String str, Set<Character> seen, int index) {
        if (index >= str.length()) {
            return "";
        }
        char c = str.charAt(index);
        String rest = removeDupHelper(str, seen, index + 1);
        if (seen.contains(c)) {
            return rest;  // 已見過，跳過
        } else {
            seen.add(c);
            return c + rest;
        }
    }

    // 4. 遞迴計算所有子字串組合（不重複，同一字串只出現一次）
    public static Set<String> allSubstrings(String str) {
        Set<String> result = new LinkedHashSet<>();
        generateSubstrHelper(str, 0, 1, result);
        return result;
    }

    private static void generateSubstrHelper(String str, int start, int length, Set<String> result) {
        if (start >= str.length()) {
            return;
        }
        if (start + length > str.length()) {
            // 移動到下一個起始位置，重置長度
            generateSubstrHelper(str, start + 1, 1, result);
        } else {
            result.add(str.substring(start, start + length));
            // 同一 start，增加 substring 長度
            generateSubstrHelper(str, start, length + 1, result);
        }
    }

    // 輔助：交換字元
    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 主程式
    public static void main(String[] args) {
        // 1. 測試排列組合
        String s1 = "ABA";
        System.out.println("1. Permutations of \"" + s1 + "\":");
        List<String> perms = permutations(s1);
        for (String p : perms) {
            System.out.println("   " + p);
        }
        System.out.println("   Total: " + perms.size() + " unique permutations\n");

        // 2. 測試遞迴字串匹配
        String text = "abracadabra";
        String pat1 = "cada", pat2 = "cadabra", pat3 = "xyz";
        System.out.printf("2. Does \"%s\" contain \"%s\"? %b%n", text, pat1, recursiveMatch(text, pat1));
        System.out.printf("   Does \"%s\" contain \"%s\"? %b%n", text, pat2, recursiveMatch(text, pat2));
        System.out.printf("   Does \"%s\" contain \"%s\"? %b%n%n", text, pat3, recursiveMatch(text, pat3));

        // 3. 測試移除重複字符
        String s2 = "abracadabra";
        System.out.println("3. Remove duplicates from \"" + s2 + "\":");
        System.out.println("   Result: \"" + removeDuplicates(s2) + "\"\n");

        // 4. 測試所有子字串組合
        String s3 = "ABC";
        System.out.println("4. All substrings of \"" + s3 + "\":");
        Set<String> substrs = allSubstrings(s3);
        for (String sub : substrs) {
            System.out.println("   " + sub);
        }
        System.out.println("   Total: " + substrs.size() + " substrings");
    }
}
