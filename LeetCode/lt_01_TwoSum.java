import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class lt_01_twosum {
    public int[] twoSum(int[] nums, int target) {
        // 創建一個 HashMap，鍵為數字，值為其索引
        Map<Integer, Integer> map = new HashMap<>();
        
        // 遍歷陣列
        for (int i = 0; i < nums.length; i++) {
            // 計算當前數字的「另一半」
            int complement = target - nums[i];
            
            // 檢查 HashMap 中是否已存在「另一半」
            if (map.containsKey(complement)) {
                // 如果存在，返回這兩個數字的索引
                // map.get(complement) 是「另一半」的索引
                // i 是當前數字的索引
                return new int[]{map.get(complement), i};
            }
            
            // 如果不存在，將當前數字及其索引存入 HashMap
            map.put(nums[i], i);
        }
        
        // 根據題目，必有唯一解，所以這行代碼通常不會被執行
        // 但為了程式碼完整性，可以返回一個空陣列或拋出異常
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        lt_01_twosum solution = new lt_01_twosum();
        
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] result = solution.twoSum(nums, target);
        
        System.out.println("輸入陣列: " + Arrays.toString(nums));
        System.out.println("目標值: " + target);
        System.out.println("解答索引: " + Arrays.toString(result));
    }
}
