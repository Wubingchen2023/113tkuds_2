import java.io.*;
import java.util.*;

public class LC01_TwoSum_THSRHoliday {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        long target = fs.nextLong();

        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextLong();

        // key: 仍「需要的座位數」(target - a[i])； value: 對應需要它的索引 i
        Map<Long, Integer> need = new HashMap<>(Math.max(16, n * 2)); // 預估容量以減少 rehash

        for (int i = 0; i < n; i++) {
            long x = a[i];
            // 若有人「需要」現在的 x，就配對成功
            if (need.containsKey(x)) {
                int j = need.get(x);
                // j 與 i 一定不同，因為 j 只會是先前的索引
                System.out.println(j + " " + i);
                return;
            }
            long want = target - x;
            // 紀錄「還需要的數字」-> 目前索引
            // 若 want 已存在也無妨，題意允許輸出任一組；保留最早需要它的索引即可
            need.putIfAbsent(want, i);
        }

        // 走完整個陣列仍無法精準湊到
        System.out.println("-1 -1");
    }

    /* ------------ Fast I/O ------------ */
    private static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c; long sgn = 1, val = 0;
            do { c = read(); } while (c <= ' ');   // skip whitespaces
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sgn;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}

/*
解題思路
------------------------------------------------
觀念：當掃到座位數 x 時，若「之前」已經有人在等 x（也就是 map.containsKey(x)），
就能立即回傳配對 (map.get(x), i)。否則，把「還需要的數」(target - x) 記錄到 map 中，
代表未來若遇到該數即可完成配對。

資料結構：
- HashMap<Long, Integer> need
  key  : 仍需的數（target - a[i]）
  value: 對應的索引 i（等著被補齊）

重點：
1) 索引必不同：need 只記錄先前元素的索引，不會將同一索引用於兩次。
2) 重複數處理：若 target 為偶數且陣列有兩個 target/2，第一次遇到 target/2 會在 need 放入
   key=target/2；第二次遇到 target/2 時會因 containsKey(x) 成立而輸出兩個不同索引。
3) 任一解皆可：我們在遇到第一組可行配對時立即輸出並結束，符合「多組解任一即可」。

邊界測試建議：
- n=2 且剛好相加成功（最小邊界）
- 陣列中沒有任何兩數可湊出 target → 輸出 -1 -1
- 兩數相同（例如 target=10，陣列含 {5,5}）
- 大量元素且答案在尾端，確認整趟掃描與 HashMap 可靠性

複雜度：
- 時間：O(n) 單次遍歷
- 空間：O(n) 最壞情況需記錄近乎 n 個「需要的數」
*/
