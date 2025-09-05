import java.util.*;

public class LC15_3Sum_THSRStops {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        List<int[]> res = threeSum(nums);

        // 輸出：每行一個遞增三元組（若無解，依題意不輸出任何行）
        StringBuilder sb = new StringBuilder();
        for (int[] t : res) {
            sb.append(t[0]).append(' ')
              .append(t[1]).append(' ')
              .append(t[2]).append('\n');
        }
        System.out.print(sb.toString());
    }

    // 排序 + 固定 i，用雙指針在右側找 -nums[i]；全程去重
    public static List<int[]> threeSum(int[] nums) {
        List<int[]> ans = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // 若當前最小值已大於 0，後面也只會更大，可提前結束
            if (nums[i] > 0) break;

            // 跳過相同的 nums[i]，避免重複三元組
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int target = -nums[i];
            int l = i + 1, r = n - 1;

            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum == target) {
                    ans.add(new int[]{nums[i], nums[l], nums[r]});

                    // 移動到下一個不同的 l、r（同步去重）
                    int lv = nums[l], rv = nums[r];
                    while (l < r && nums[l] == lv) l++;
                    while (l < r && nums[r] == rv) r--;
                } else if (sum < target) {
                    l++; // 需要更大 → 左指針右移
                } else {
                    r--; // 需要更小 → 右指針左移
                }
            }
        }
        return ans;
    }
}
