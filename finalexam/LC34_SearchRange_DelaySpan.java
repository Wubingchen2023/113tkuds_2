import java.util.*;

public class LC34_SearchRange_DelaySpan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();        // 陣列長度
        int target = sc.nextInt();   // 查詢目標等級
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int[] result = searchRange(nums, target);
        System.out.println(result[0] + " " + result[1]);
    }

    // 主函式：返回 [firstIndex, lastIndex]
    public static int[] searchRange(int[] nums, int target) {
        int first = lowerBound(nums, target);
        int last = lowerBound(nums, target + 1) - 1;

        // 檢查 target 是否存在
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{first, Math.max(first, last)};
    }

    // 找到第一個 ≥ target 的索引
    public static int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
