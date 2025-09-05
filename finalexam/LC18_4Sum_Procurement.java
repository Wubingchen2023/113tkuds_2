import java.util.*;

public class LC18_4Sum_Procurement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long target = sc.nextLong(); // target 可能達 1e9；為安全用 long
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        List<int[]> ans = fourSum(nums, target);

        // 輸出：每行一個升序四元組；若無解，不輸出任何行
        StringBuilder sb = new StringBuilder();
        for (int[] q : ans) {
            sb.append(q[0]).append(' ')
              .append(q[1]).append(' ')
              .append(q[2]).append(' ')
              .append(q[3]).append('\n');
        }
        System.out.print(sb.toString());
    }

    /** 回傳所有不重複、升序四元組（每個四元組內部升序，整體去重） */
    public static List<int[]> fourSum(int[] nums, long target) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        if (n < 4) return res;

        for (int i = 0; i < n - 3; i++) {
            // 去重：相同的 nums[i] 跳過，避免重複四元組
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // 剪枝1：以當前 i，最小可能和 > target，後面 i 只會更大 → 直接 break
            long minI = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (minI > target) break;

            // 剪枝2：以當前 i，最大可能和 < target，換下一個 i
            long maxI = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            if (maxI < target) continue;

            for (int j = i + 1; j < n - 2; j++) {
                // 去重：相同的 nums[j] 跳過
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                // 剪枝3：以當前 i、j，最小可能和 > target → 內層 j 不必再增加，直接 break
                long minJ = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (minJ > target) break;

                // 剪枝4：以當前 i、j，最大可能和 < target → 換下一個 j
                long maxJ = (long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2];
                if (maxJ < target) continue;

                int L = j + 1, R = n - 1;
                while (L < R) {
                    long sum = (long) nums[i] + nums[j] + nums[L] + nums[R];
                    if (sum == target) {
                        res.add(new int[]{nums[i], nums[j], nums[L], nums[R]});

                        // 命中後：同步去重 L、R
                        int leftVal = nums[L], rightVal = nums[R];
                        while (L < R && nums[L] == leftVal) L++;
                        while (L < R && nums[R] == rightVal) R--;
                    } else if (sum < target) {
                        L++;
                    } else {
                        R--;
                    }
                }
            }
        }
        return res;
    }
}
