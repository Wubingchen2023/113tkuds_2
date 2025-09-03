import java.util.Arrays;

public class lt_27_RemoveElement {
    public int removeElement(int[] nums, int val) {
        // i 指向下一個「有效元素」應該放置的位置
        int i = 0;

        // j 掃描整個陣列
        for (int j = 0; j < nums.length; j++) {
            // 如果當前元素不是要刪除的 val，就保留下來
            if (nums[j] != val) {
                nums[i] = nums[j]; // 覆蓋到前面的位置
                i++;               // 更新下一個存放位置
            }
        }

        // i 代表不等於 val 的元素個數
        return i;
    }

    public static void main(String[] args) {
        lt_27_RemoveElement solution = new lt_27_RemoveElement();

        int[] nums = {3, 2, 2, 3};
        int val = 3;
        System.out.println("原始陣列: " + Arrays.toString(nums));
        System.out.println("要刪除的值: " + val);

        int len = solution.removeElement(nums, val);

        System.out.println("新陣列長度: " + len);
        System.out.println("處理後陣列前 " + len + " 個元素: "
                           + Arrays.toString(Arrays.copyOfRange(nums, 0, len)));
    }
}

/*
解題邏輯

題意：
給定一個整數陣列 nums 和一個值 val，請就地刪除所有數值等於 val 的元素，
並返回新陣列的長度。不要求保持原陣列順序，但通常保留前綴部分比較簡單。

思路：
1. 使用「雙指標法」：
   - j：遍歷整個陣列。
   - i：指向下一個「應保留元素」存放位置。
2. 遍歷時：
   - 若 nums[j] != val ⇒ 把 nums[j] 放到 nums[i]，然後 i++。
   - 若 nums[j] == val ⇒ 跳過，相當於丟棄這個元素。
3. 遍歷完成後，i 的值就是新陣列的長度（即保留下來的元素數量）。
   - 前 i 個元素就是不等於 val 的所有元素。
   - 後面的元素內容不重要，因為題目只要求前 i 個。

範例：
nums = [3,2,2,3], val = 3
過程：
j=0: nums[0]=3 == val → 跳過
j=1: nums[1]=2 != val → nums[0]=2, i=1
j=2: nums[2]=2 != val → nums[1]=2, i=2
j=3: nums[3]=3 == val → 跳過
結果：返回長度 2，nums 前兩個元素為 [2,2]

- 時間複雜度：O(n)，掃描一次陣列。
- 空間複雜度：O(1)，就地修改，不需要額外空間。
*/ 
