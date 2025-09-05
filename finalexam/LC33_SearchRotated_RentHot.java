import java.util.*;

public class LC33_SearchRotated_RentHot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();     // 陣列長度
        int target = sc.nextInt(); // 要查找的設備 ID
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int result = search(nums, target);
        System.out.println(result);
    }

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // 命中
            if (nums[mid] == target) return mid;

            // 判斷左半是否升序
            if (nums[left] <= nums[mid]) {
                // target 是否在左半區間？
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;  // 往左找
                } else {
                    left = mid + 1;   // 往右找
                }
            } else {
                // 右半是升序
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;   // 往右找
                } else {
                    right = mid - 1;  // 往左找
                }
            }
        }

        return -1; // 找不到
    }
}
