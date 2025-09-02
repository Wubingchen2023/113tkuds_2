public class lt_11_ContainerWithMostWater {
    public static int maxArea(int[] height) {
        int left = 0;                  // 左指標從最左邊開始
        int right = height.length - 1; // 右指標從最右邊開始
        int maxArea = 0;               // 儲存最大容積

        while (left < right) {
            int h = Math.min(height[left], height[right]); // 取左右中較矮的作為水高
            int w = right - left;                          // 寬度為兩指標之間的距離
            int area = h * w;                              // 計算目前容積
            maxArea = Math.max(maxArea, area);             // 更新最大容積

            // 移動較矮的那邊的指標，有機會找到更高的柱子來增加容量
            if (height[left] < height[right]) {
                left++; // 嘗試右移左指標
            } else {
                right--; // 嘗試左移右指標
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println("最大容積為: " + maxArea(height)); // Output: 49
    }
}

/*
解題邏輯：
1. 每個容器的面積 = 寬度 * 高度
   - 寬度 = 兩柱之間的距離 (right - left)
   - 高度 = 兩柱之間的較短柱子（因為水不能高於矮的那邊）

2. 為了找到最大面積：
   - 從最外層兩端開始，向內夾逼
   - 每次比較兩端高度，移動較矮的一邊，因為這是限制面積的因素
   - 如果移動較高的那邊，寬度變小但高度未必變大 → 無法保證面積增加

3. 重點策略：
   - 使用 Two Pointers 技巧：每次往內移動較矮的那根柱子
   - 時間複雜度 O(n)，空間 O(1)

範例：
Input: [1,8,6,2,5,4,8,3,7]
最大面積出現在 index=1（8） 和 index=8（7） → min(8,7) × (8-1) = 7 × 7 = 49
*/
