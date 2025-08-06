public class RecursiveMathCalculator {

    // 1. 組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
    public static int combination(int n, int k) {
        if (k == 0 || k == n) return 1;
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    // 2. 卡塔蘭數 Catalan(n) = Σ C(i) × C(n-1-i)
    public static long catalan(int n) {
        if (n <= 1) return 1;
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalan(i) * catalan(n - 1 - i);
        }
        return result;
    }

    // 3. 漢諾塔移動步數 hanoi(n) = 2 * hanoi(n-1) + 1
    public static long hanoiMoves(int n) {
        if (n == 1) return 1;
        return 2 * hanoiMoves(n - 1) + 1;
    }

    // 4. 判斷是否為回文數（使用遞迴）
    public static boolean isPalindrome(int n) {
        return isPalindromeHelper(Integer.toString(n));
    }

    private static boolean isPalindromeHelper(String s) {
        if (s.length() <= 1) return true;
        if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
        return isPalindromeHelper(s.substring(1, s.length() - 1));
    }

    // 主程式
    public static void main(String[] args) {
        // 組合數 C(5, 2)
        int n1 = 5, k1 = 2;
        System.out.printf("C(%d, %d) = %d\n", n1, k1, combination(n1, k1));

        // 卡塔蘭數 Catalan(4)
        int cataN = 4;
        System.out.printf("Catalan(%d) = %d\n", cataN, catalan(cataN));

        // 漢諾塔移動步數 hanoi(3)
        int hanoiN = 3;
        System.out.printf("Hanoi(%d) = %d moves\n", hanoiN, hanoiMoves(hanoiN));

        // 判斷是否為回文數
        int num1 = 12321, num2 = 12345;
        System.out.printf("%d 是回文數嗎？ %b\n", num1, isPalindrome(num1));
        System.out.printf("%d 是回文數嗎？ %b\n", num2, isPalindrome(num2));
    }
}
