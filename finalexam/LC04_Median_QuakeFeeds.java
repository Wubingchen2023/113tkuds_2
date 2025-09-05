import java.util.*;

public class LC04_Median_QuakeFeeds {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(), m = sc.nextInt();
        double[] A = new double[n];
        double[] B = new double[m];

        for (int i = 0; i < n; i++) A[i] = sc.nextDouble();
        for (int j = 0; j < m; j++) B[j] = sc.nextDouble();

        double ans = findMedianSortedArrays(A, B);
        System.out.printf("%.1f%n", ans);   // 依題意：輸出保留 1 位小數
    }

    // 經典：在較短陣列做二分切割；時間 O(log(min(n,m)))、空間 O(1)
    static double findMedianSortedArrays(double[] A, double[] B) {
        // 確保 A 為較短陣列（可讓二分邊界更小）
        if (A.length > B.length) return findMedianSortedArrays(B, A);

        int n = A.length, m = B.length;
        int totalLeft = (n + m + 1) / 2; // 左半部的元素個數
        int lo = 0, hi = n;

        // 邊界用 ±INF 處理 i=0 或 i=n / j=0 或 j=m
        final double NEG_INF = Double.NEGATIVE_INFINITY;
        final double POS_INF = Double.POSITIVE_INFINITY;

        while (lo <= hi) {
            int i = (lo + hi) >>> 1;     // A 的切割點（左邊放 i 個）
            int j = totalLeft - i;       // B 的切割點

            double Aleft  = (i == 0) ? NEG_INF : A[i - 1];
            double Aright = (i == n) ? POS_INF : A[i];
            double Bleft  = (j == 0) ? NEG_INF : B[j - 1];
            double Bright = (j == m) ? POS_INF : B[j];

            // 驗證切割是否正確：左半最大 <= 右半最小
            if (Aleft <= Bright && Bleft <= Aright) {
                // 總長度奇數：取左半最大；偶數：取左半最大與右半最小平均
                if (((n + m) & 1) == 1) {
                    return Math.max(Aleft, Bleft);
                } else {
                    double leftMax = Math.max(Aleft, Bleft);
                    double rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0;
                }
            } else if (Aleft > Bright) {
                // A 左邊太大，i 要往左縮
                hi = i - 1;
            } else {
                // B 左邊太大（或 A 右邊太小），i 往右擴
                lo = i + 1;
            }
        }

        // 正常不會到這裡（輸入皆為已排序，且 n+m>=1）
        throw new IllegalArgumentException("Invalid input arrays");
    }
}
