public class lt_08_StringtoInteger {
    public int myAtoi(String s) {
        // 1. 去除前後空白
        s = s.trim();
        if (s.isEmpty()) return 0;

        int i = 0, n = s.length();
        int sign = 1;
        long result = 0; // 用 long 暫存，避免中途溢出

        // 2. 處理正負號
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // 3. 讀取數字
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            result = result * 10 + digit;

            // 4. 檢查溢出 (邊界處理)
            if (sign == 1 && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (sign == -1 && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            i++;
        }

        return (int) (sign * result);
    }

    public static void main(String[] args) {
        lt_08_StringtoInteger sol = new lt_08_StringtoInteger();
        
        System.out.println(sol.myAtoi("42"));            // 輸出: 42
        System.out.println(sol.myAtoi("   -42"));        // 輸出: -42
        System.out.println(sol.myAtoi("4193 with words")); // 輸出: 4193
        System.out.println(sol.myAtoi("words and 987")); // 輸出: 0
        System.out.println(sol.myAtoi("-91283472332"));  // 輸出: -2147483648 (Integer.MIN_VALUE)
        System.out.println(sol.myAtoi("91283472332"));   // 輸出: 2147483647 (Integer.MAX_VALUE)
    }
}
