public class lt_07_ReverseInteger {
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            // 溢出檢查（正數邊界）
            if (rev > Integer.MAX_VALUE / 10 || 
               (rev == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            // 溢出檢查（負數邊界）
            if (rev < Integer.MIN_VALUE / 10 || 
               (rev == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }

            rev = rev * 10 + digit;
        }
        return rev;
    }

    public static void main(String[] args) {
        lt_07_ReverseInteger solution = new lt_07_ReverseInteger();

        int[] testCases = {123, -123, 120, 0, 1534236469};
        for (int x : testCases) {
            System.out.println("Input: " + x + " -> Output: " + solution.reverse(x));
        }
    }
}
