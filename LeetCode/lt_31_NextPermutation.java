import java.util.Arrays;

public class lt_31_NextPermutation {

    // 就地（in-place）把 nums 改成下一個字典序排列
    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        // 1) 從右找第一個「上升轉折點」(pivot)：nums[i] < nums[i+1]
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;

        if (i >= 0) {
            // 2) 在右側尾段找「最右邊」且 > nums[i] 的元素，交換之
            int j = nums.length - 1;
            while (j > i && nums[j] <= nums[i]) j--;
            swap(nums, i, j);
        }

        // 3) 無論是否找到 pivot，都需把 i 右邊的遞減尾段反轉成遞增，得到最小後綴
        reverse(nums, i + 1, nums.length - 1);
    }

    private static void swap(int[] a, int x, int y) {
        int t = a[x]; a[x] = a[y]; a[y] = t;
    }

    private static void reverse(int[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }

    public static void main(String[] args) {
        int[][] tests = {
            {1,2,3},        // -> 1,3,2
            {3,2,1},        // -> 1,2,3 (已為最大，回到最小)
            {1,1,5},        // -> 1,5,1
            {1,3,2},        // -> 2,1,3
            {2,3,1},        // -> 3,1,2
            {1,5,1},        // -> 5,1,1
            {1}             // -> 1
        };
        for (int[] t : tests) {
            nextPermutation(t);
            System.out.println(Arrays.toString(t));
        }
    }
}

/*
解題邏輯

題目：在所有排列的字典序中，將當前排列 nums 原地改為「下一個更大」的排列；若已是最大排列，返回最小（遞增）排列。

核心思路：
1) 從右往左，第一個出現「上升」的位置 i（nums[i] < nums[i+1]）稱為 pivot。
   - 若找不到 pivot（整陣列單調遞減），表示已是最大排列，下一個就是最小排列：直接把整個陣列反轉為遞增。
2) 若找到 pivot=i，想要得到「略大於原排列、且儘可能小」的下一個排列：
   a. 必須在 i 右側（本為遞減尾段）的元素中，找一個「剛好比 nums[i] 大的最小值」來替換。
      因為右側是遞減，從最右邊往左找第一個 > nums[i] 的 j，即為「最小且大於 pivot」的元素。
   b. 交換 nums[i] 與 nums[j] 後，i 右側仍是遞減，但此時為了讓整體「盡可能小」（即緊鄰的下一個），
      需要把 i 右側整段反轉為遞增，得到最小後綴。
      
- 右側尾段原本是「最長遞減後綴」，代表已無法在該尾段內得到更大的順序；因此必須「向左借位」（提升 pivot）。
- 以右側最小可替代（但仍大於 pivot）的元素來交換，能讓提升幅度最小化。
- 反轉尾段成遞增，保證得到「所有可行排列中」恰好緊鄰的下一個。

邊界與重複元素：
- 若整列遞減 -> 直接全反轉。
- 允許重複值：尋找 j 用「> nums[i]」而非「>=」，確保嚴格變大；尾段反轉同樣正確。

時間複雜度：O(n)（一次線性掃描找 i、一次線性掃描找 j、一次線性反轉）
空間複雜度：O(1)（原地交換與反轉）

例子：
- [1,2,3]：pivot=1(值=2)，右側找 >2 的最右元素=3，交換得[1,3,2]，反轉後綴(僅 2)不變 -> [1,3,2]
- [3,2,1]：無 pivot，整列反轉 -> [1,2,3]
- [1,3,2]：pivot=0(值=1)，右側找 >1 的最右元素=2，交換得[2,3,1]，反轉後綴[3,1]->[1,3] -> [2,1,3]
*/
