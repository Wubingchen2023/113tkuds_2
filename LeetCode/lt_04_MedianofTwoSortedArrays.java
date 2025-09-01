public class lt_04_MedianofTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 確保 nums1 較短（在較短陣列上二分）
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        int[] A = nums1, B = nums2;
        int m = A.length, n = B.length;

        int left = 0, right = m; // i 的搜尋範圍：0..m（可切在最左或最右）
        int half = (m + n + 1) / 2;

        while (left <= right) {
            int i = left + (right - left) / 2; // A 的切點
            int j = half - i;                  // B 的切點（使左半邊數量固定）

            int Aleft  = (i == 0) ? Integer.MIN_VALUE : A[i - 1];
            int Aright = (i == m) ? Integer.MAX_VALUE : A[i];
            int Bleft  = (j == 0) ? Integer.MIN_VALUE : B[j - 1];
            int Bright = (j == n) ? Integer.MAX_VALUE : B[j];

            // 檢查是否找到合法切分
            if (Aleft <= Bright && Bleft <= Aright) {
                if (((m + n) & 1) == 1) {
                    return Math.max(Aleft, Bleft); // 奇數長度
                } else {
                    int leftMax = Math.max(Aleft, Bleft);
                    int rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0; // 偶數長度
                }
            } else if (Aleft > Bright) {
                // i 太大，往左縮
                right = i - 1;
            } else {
                // Bleft > Aright，i 太小，往右擴
                left = i + 1;
            }
        }
        // 理論上永不達到
        throw new IllegalArgumentException("Input arrays not sorted or invalid.");
    }

    public static void main(String[] args) {
        lt_04_MedianofTwoSortedArrays solution = new lt_04_MedianofTwoSortedArrays();

        // 測試案例
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double result = solution.findMedianSortedArrays(nums1, nums2);
        System.out.println("Median is: " + result); // Output: 2.0

        int[] nums3 = {1, 2};
        int[] nums4 = {3, 4};
        result = solution.findMedianSortedArrays(nums3, nums4);
        System.out.println("Median is: " + result); // Output: 2.5
    }
}

