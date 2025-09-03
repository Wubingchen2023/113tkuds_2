public class lt_29_DivideTwoInteger {
    public int divide(int dividend, int divisor) {
        // 除數為 0
        if (divisor == 0) {
            throw new ArithmeticException("Divide by zero");
        }

        // 處理邊界情況：最小整數除以 -1，會超出 int 範圍
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 使用 long 避免溢出，取絕對值做計算
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        long result = 0L; // 用 long 累加，避免中間值溢出

        // 核心：透過位移加速「減法除法」
        while (a >= b) {
            long temp = b, multiple = 1;
            while (a >= (temp << 1)) {
                temp <<= 1;        // 除數翻倍
                multiple <<= 1;    // 倍數翻倍
            }
            a -= temp;             // 減去對應倍數的除數
            result += multiple;    // 累加倍數
        }

        // 判斷正負號：異號為負，同號為正
        boolean negative = (dividend > 0) ^ (divisor > 0);
        long signed = negative ? -result : result;

        return (int) signed; // 這裡安全（MIN/-1 已特判）
    }

    public static void main(String[] args) {
        lt_29_DivideTwoInteger s = new lt_29_DivideTwoInteger();

        System.out.println("10 / 3 = " + s.divide(10, 3));                 // 3
        System.out.println("7 / -3 = " + s.divide(7, -3));                  // -2
        System.out.println("-7 / 3 = " + s.divide(-7, 3));                  // -2
        System.out.println("0 / 1 = " + s.divide(0, 1));                    // 0
        System.out.println("Integer.MIN_VALUE / 1 = " + s.divide(Integer.MIN_VALUE, 1));   // -2147483648
        System.out.println("Integer.MIN_VALUE / -1 = " + s.divide(Integer.MIN_VALUE, -1)); // 2147483647

        // 除以 0（示範會丟出例外）
        try {
            System.out.println("1 / 0 = " + s.divide(1, 0));
        } catch (ArithmeticException ex) {
            System.out.println("1 / 0 觸發例外: " + ex.getMessage());
        }
    }
}


/*
解題邏輯

題意：
實作「整數除法」，不允許使用乘法 (*)、除法 (/) 和取模 (%)。
要求返回「商」，需向 0 取整。
若結果超出 int 範圍，返回 Integer.MAX_VALUE。

思路：
1. 特殊情況：
   - Integer.MIN_VALUE / -1 = 2^31，超過 int 最大值 2^31 - 1，需要特判。

2. 轉為正數計算：
   - 先將 dividend、divisor 轉為 long 避免溢出，再取絕對值計算。
   - 最後根據原始正負號決定結果正負。

3. 使用「倍增減法」（類似二分法）加速：
   - 傳統做法一個一個減效率太差 O(n)。
   - 我們使用位移技巧：
     - 每次找到 divisor * 2^k ≤ dividend 的最大 k。
     - 然後從 dividend 減去 divisor * 2^k。
     - 結果加上 2^k。
   - 如此每次至少減掉一半以上的數量，被除數會快速縮小。

4. 判斷符號：
   - 異號結果為負，同號為正。
   - 利用 XOR 判斷 (dividend > 0) ^ (divisor > 0)。

範例：
dividend = 43, divisor = 3
43 ≥ 3 → 倍增到 24 (3*8)，累積 8，剩 19
19 ≥ 3 → 倍增到 12 (3*4)，累積 4，剩 7
7 ≥ 3 → 倍增到 6 (3*2)，累積 2，剩 1
總和 = 8+4+2 = 14
結果：43 / 3 = 14

- 時間複雜度：O(log(n))，每次倍增最多 31 次 (int 範圍)。
- 空間複雜度：O(1)，僅用常數額外變數。
*/ 
