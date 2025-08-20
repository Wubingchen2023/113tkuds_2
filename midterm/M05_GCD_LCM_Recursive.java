import java.util.*;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long a = sc.nextLong();
        long b = sc.nextLong();

        long gcdValue = gcd(a, b);  // 遞迴計算 GCD
        long lcmValue = (a / gcdValue) * b; // 先除後乘避免溢位

        System.out.println("GCD: " + gcdValue);
        System.out.println("LCM: " + lcmValue);
    }

    // 遞迴歐幾里得算法
    static long gcd(long x, long y) {
        if (y == 0) return x;            // 終止條件
        return gcd(y, x % y);            // 遞迴呼叫
    }
}

/*
Time Complexity：
- GCD 遞迴歐幾里得算法的時間複雜度為 O(log(min(a, b)))，
  因為每一步驟會將問題縮小到一個模數，數值快速下降。
- LCM 計算為 O(1)，只需一次除法與乘法。
- 整體時間複雜度：O(log(min(a, b)))。

Space Complexity：
- 遞迴呼叫深度最多 O(log(min(a, b)))，使用棧空間。
- 其餘變數為常數等級，因此空間複雜度為 O(1)。
*/
