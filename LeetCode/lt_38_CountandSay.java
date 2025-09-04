public class lt_38_CountandSay {

    // 回傳第 n 項 count-and-say 字串
    public static String countAndSay(int n) {
        String s = "1"; // 第一項

        for (int i = 2; i <= n; i++) {
            s = nextSequence(s);
        }
        return s;
    }

    // 根據前一項產生下一項字串
    private static String nextSequence(String s) {
        StringBuilder sb = new StringBuilder();

        int count = 1;
        char prev = s.charAt(0);

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == prev) {
                count++; // 同樣的數字累加
            } else {
                // 遇到不同數字，輸出前一段
                sb.append(count).append(prev);
                count = 1;
                prev = c;
            }
        }
        // 記得輸出最後一段
        sb.append(count).append(prev);

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++) {
            System.out.println("n=" + i + " : " + countAndSay(i));
        }
    }
}

/*
【解題邏輯】

題意：
- 題目給定 n，請輸出「count and say」序列的第 n 項。
- 規則如下：
    • 第一項是 "1"
    • 第二項為「讀出上一項」：一個 1 → "11"（一個一）
    • 第三項：「兩個 1」→ "21"
    • 第四項：「一個 2、一個 1」→ "1211"
    • …依此類推

核心思路：
1. 用一個字串 s 保存當前項，初始為 "1"。
2. 每次迴圈從第 2 項到第 n 項：
   a. 呼叫 `nextSequence(s)` 產生下一項。
   b. `nextSequence()` 將字串逐字掃描，遇到連續相同字元就計數，直到遇到不同字元或結尾。
   c. 每遇到新字元時，輸出「前面累計的數量」+「那個字元」。
3. 迴圈結束，s 就是第 n 項。

範例：
n=1: "1"
n=2: "11"   // 一個1
n=3: "21"   // 兩個1
n=4: "1211" // 一個2一個1
n=5: "111221" // 一個1一個2兩個1
...

時間複雜度：O(2^n)
- 每一項長度近似倍增（最大到第 n 項約 2^(n-1) 長度），但 n 通常不大，效能可接受。

空間複雜度：O(2^n)
*/
