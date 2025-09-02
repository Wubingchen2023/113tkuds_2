import java.util.HashMap;
import java.util.Map;

public class lt_13_RomantoInteger {
    public static int romanToInt(String s) {
        // 建立羅馬字母對應表
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0;

        // 遍歷字串
        for (int i = 0; i < s.length(); i++) {
            int value = map.get(s.charAt(i)); // 當前值

            // 如果右邊還有字母，且右邊值比較大 → 減去當前值
            if (i + 1 < s.length() && value < map.get(s.charAt(i + 1))) {
                result -= value;
            } else {
                result += value; // 否則加上
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String roman = "MCMXCIV";
        System.out.println("整數為: " + romanToInt(roman)); // Output: 1994
    }
}

/*
解題邏輯：
1. 建立羅馬字母與數值對照表：
   I=1, V=5, X=10, L=50, C=100, D=500, M=1000

2. 從左到右遍歷：
   - 如果當前字母的值 < 下一個字母，代表是特殊情況（如 IV, IX, XL, XC, CD, CM）
     → result -= 當前值
   - 否則直接累加：
     → result += 當前值

3. 為什麼這樣做正確？
   - 羅馬數字只有這 6 種「小數字在前」的減法情況
   - 其餘情況全部都是加法

範例：
Input: "MCMXCIV"
逐步：
M=1000 → 加 = 1000
C=100 < M=1000 → 減 = 900
M=1000 → 加 = 1900
X=10 < C=100 → 減 = 1890
C=100 → 加 = 1990
I=1 < V=5 → 減 = 1989
V=5 → 加 = 1994

時間複雜度：
O(n)，n 為字串長度
空間複雜度：
O(1)，僅使用固定大小的 Map
*/
