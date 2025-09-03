public class lt_12_IntegertoRoman {
    public static String intToRoman(int num) {
        // 整數對應的羅馬數字值與字串，從大到小排列
        int[] values =    {1000, 900, 500, 400, 100, 90,  50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"M",  "CM","D", "CD", "C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder sb = new StringBuilder();

        // 從最大數值開始，逐一匹配
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];           // 減去對應的數值
                sb.append(romans[i]);       // 加上對應的羅馬符號
            }
        }

        return sb.toString(); // 回傳轉換後的羅馬數字
    }

    public static void main(String[] args) {
        int num = 1994;
        System.out.println("羅馬數字為: " + intToRoman(num)); // Output: MCMXCIV
    }
}

/*
解題邏輯：
1. 羅馬數字的組成是以幾個固定的符號和規則組合而成
   - 例如 900 = CM, 40 = XL, 4 = IV，這些都是為了避免 4 個重複字元

2. 設計策略：
   - 建立 values[] 和 romans[] 兩個對照表（從大到小排序）
   - 每次從最大值開始試，能減就減，能加就加上對應羅馬字元
   - 重複直到輸入值為 0

3. 使用 while 迴圈處理重複情況：
   - 例如 3000 → "M" * 3

範例分析：
Input: 1994
步驟：
- 1000 ≤ 1994 → 減 1000 加 "M"       → 剩 994
- 900 ≤ 994  → 減 900 加 "CM"       → 剩 94
- 90 ≤ 94    → 減 90 加 "XC"        → 剩 4
- 4 ≤ 4      → 減 4 加 "IV"         → 剩 0
=> MCMXCIV

時間複雜度：
- O(1)（最大數值固定為 3999）
空間複雜度：
- O(1)（使用固定大小的陣列和字串建構器）
*/

