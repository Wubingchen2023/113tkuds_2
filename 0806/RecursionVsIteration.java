import java.util.Stack;

public class RecursionVsIteration {

    // 1. 計算二項式係數 C(n, k)
    // 1a. 遞迴版本
    public static long binomialRecursive(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomialRecursive(n - 1, k - 1) + binomialRecursive(n - 1, k);
    }

    // 1b. 迭代版本（使用乘除法優化）
    public static long binomialIterative(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k > n - k) k = n - k;  // 對稱性 C(n,k)=C(n,n-k)
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + i) / i;
        }
        return result;
    }

    // 2. 陣列所有元素的乘積
    // 2a. 遞迴版本
    public static long productRecursive(int[] arr, int index) {
        if (index >= arr.length) return 1;
        return arr[index] * productRecursive(arr, index + 1);
    }

    // 2b. 迭代版本
    public static long productIterative(int[] arr) {
        long prod = 1;
        for (int v : arr) {
            prod *= v;
        }
        return prod;
    }

    // 3. 計算字串中元音字母的數量
    // 3a. 遞迴版本
    public static int countVowelsRecursive(String s, int index) {
        if (index >= s.length()) return 0;
        char c = Character.toLowerCase(s.charAt(index));
        int add = ("aeiou".indexOf(c) >= 0) ? 1 : 0;
        return add + countVowelsRecursive(s, index + 1);
    }

    // 3b. 迭代版本
    public static int countVowelsIterative(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = Character.toLowerCase(s.charAt(i));
            if ("aeiou".indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }

    // 4. 檢查括號是否配對正確，只處理 '(' 與 ')'
    // 4a. 遞迴版本（不斷移除最內層的 "()" 直到空字串或無法移除）
    public static boolean parenthesesRecursive(String s) {
        if (s.isEmpty()) return true;
        if (!s.contains("()")) return false;
        return parenthesesRecursive(s.replace("()", ""));
    }

    // 4b. 迭代版本（使用 Stack）
    public static boolean parenthesesIterative(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        // 測試參數
        int n = 20, k = 10;
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String text = "Recursion vs Iteration: Which one is better?";
        String parentheses1 = "((()()))()";
        String parentheses2 = "(()))((";

        // 1. 二項式係數
        long t1 = System.nanoTime();
        long cRec = binomialRecursive(n, k);
        long dt1 = System.nanoTime() - t1;

        long t2 = System.nanoTime();
        long cIter = binomialIterative(n, k);
        long dt2 = System.nanoTime() - t2;

        System.out.println("1. Binomial C(" + n + "," + k + ")");
        System.out.printf("   遞迴: %d (%.3f ms)%n", cRec, dt1 / 1_000_000.0);
        System.out.printf("   迭代: %d (%.3f ms)%n%n", cIter, dt2 / 1_000_000.0);

        // 2. 陣列元素乘積
        t1 = System.nanoTime();
        long pRec = productRecursive(array, 0);
        dt1 = System.nanoTime() - t1;

        t2 = System.nanoTime();
        long pIter = productIterative(array);
        dt2 = System.nanoTime() - t2;

        System.out.println("2. Array Product");
        System.out.printf("   遞迴: %d (%.3f µs)%n", pRec, dt1 / 1_000.0);
        System.out.printf("   迭代: %d (%.3f µs)%n%n", pIter, dt2 / 1_000.0);

        // 3. 計算元音字母數量
        t1 = System.nanoTime();
        int vRec = countVowelsRecursive(text, 0);
        dt1 = System.nanoTime() - t1;

        t2 = System.nanoTime();
        int vIter = countVowelsIterative(text);
        dt2 = System.nanoTime() - t2;

        System.out.println("3. Vowel Count in \"" + text + "\"");
        System.out.printf("   遞迴: %d (%.3f µs)%n", vRec, dt1 / 1_000.0);
        System.out.printf("   迭代: %d (%.3f µs)%n%n", vIter, dt2 / 1_000.0);

        // 4. 括號配對檢查
        t1 = System.nanoTime();
        boolean okRec1 = parenthesesRecursive(parentheses1);
        boolean okRec2 = parenthesesRecursive(parentheses2);
        dt1 = System.nanoTime() - t1;

        t2 = System.nanoTime();
        boolean okIter1 = parenthesesIterative(parentheses1);
        boolean okIter2 = parenthesesIterative(parentheses2);
        dt2 = System.nanoTime() - t2;

        System.out.println("4. Parentheses Matching");
        System.out.printf("   遞迴 \"%s\": %b%n", parentheses1, okRec1);
        System.out.printf("   遞迴 \"%s\": %b%n", parentheses2, okRec2);
        System.out.printf("   遞迴檢查耗時: %.3f µs%n", dt1 / 1_000.0);

        System.out.printf("   迭代 \"%s\": %b%n", parentheses1, okIter1);
        System.out.printf("   迭代 \"%s\": %b%n", parentheses2, okIter2);
        System.out.printf("   迭代檢查耗時: %.3f µs%n%n", dt2 / 1_000.0);

        // 效能總結
        System.out.println("=== Performance Comparison Summary ===");
        System.out.printf("C(%d,%d): 遞迴 %.3f ms vs 迭代 %.3f ms%n",
                n, k, dt1 / 1_000_000.0, dt2 / 1_000_000.0);
        System.out.printf("Product: 遞迴 %.3f µs vs 迭代 %.3f µs%n",
                dt1 / 1_000.0, dt2 / 1_000.0);
        System.out.printf("Vowels: 遞迴 %.3f µs vs 迭代 %.3f µs%n",
                dt1 / 1_000.0, dt2 / 1_000.0);
        System.out.printf("Parentheses: 遞迴 %.3f µs vs 迭代 %.3f µs%n",
                dt1 / 1_000.0, dt2 / 1_000.0);
    }
}
