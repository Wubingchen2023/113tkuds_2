import java.util.Arrays;

public class lt_26_RemoveDuplicatesfromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        // i 指向新陣列的最後一個「不重複」元素位置
        int i = 0;

        // j 從 1 開始，遍歷整個陣列
        for (int j = 1; j < nums.length; j++) {
            // 當前元素 nums[j] 與最後一個不重複元素不同，代表找到新值
            if (nums[j] != nums[i]) {
                i++;                // 移動到下一個存放位置
                nums[i] = nums[j];  // 覆蓋掉重複的元素
            }
        }

        // i 是最後一個不重複元素的索引，因此不重複長度為 i+1
        return i + 1;
    }

    public static void main(String[] args) {
        lt_26_RemoveDuplicatesfromSortedArray solution = new lt_26_RemoveDuplicatesfromSortedArray();
        
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println("原始陣列: " + Arrays.toString(nums));

        int len = solution.removeDuplicates(nums);

        System.out.println("不重複元素長度: " + len);
        System.out.println("處理後陣列前 " + len + " 個元素: " 
                           + Arrays.toString(Arrays.copyOfRange(nums, 0, len)));
    }
}

/*
解題思路

題意：
給定一個「已排序」的整數陣列 nums，要求「就地」移除重複元素，使每個元素只出現一次，
並返回不重複元素的個數。額外空間只能是 O(1)。

1. 陣列已排序 ⇒ 所有重複元素必然相鄰。
2. 使用「雙指標法」：
   - i：指向「已處理過的不重複陣列」的最後一個位置。
   - j：從左到右掃描原陣列。
   - 若 nums[j] 與 nums[i] 不同，代表出現新元素 ⇒ i++，並將 nums[j] 放到 nums[i]。
   - 否則跳過，因為重複。
3. 遍歷完成後，i 的位置是最後一個不重複元素，故不重複數量 = i + 1。

範例：
nums = [0,0,1,1,1,2,2,3,3,4]
過程：
i=0, j=1 → 相同跳過
j=2 → nums[2]=1 != nums[0]=0 → i=1, nums[1]=1
j=5 → nums[5]=2 != nums[1]=1 → i=2, nums[2]=2
…最後 i=4，返回 5
結果 nums 前 5 個元素為 [0,1,2,3,4]

- 時間複雜度：O(n)，每個元素僅被掃描一次。
- 空間複雜度：O(1)，僅用常數變數 i, j。
*/ 
