import java.io.*;

public class LC26_RemoveDuplicates_Scores {
    // 速度較快的讀取：支援 n 到 1e5
    static class FastScanner {
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
        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            do c = read(); while (c <= ' ' && c != -1);
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int n;
        try {
            n = fs.nextInt(); // 可能拋出 EOF（無輸入）
        } catch (Exception e) {
            // 無輸入：不輸出任何東西
            return;
        }

        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = fs.nextInt();

        // 進行就地去重，回傳去重後長度 k（前 k 個位置為去重結果）
        int k = removeDuplicatesInPlace(a);

        // 按題意輸出
        out.append(k).append('\n');
        for (int i = 0; i < k; i++) {
            if (i > 0) out.append(' ');
            out.append(a[i]);
        }
        out.append('\n');
        System.out.print(out.toString());
    }

    /**
     * 就地去重：把唯一值穩定地搬到前段，回傳新長度 k
     * 寫入規則：
     *   write 指向下一個可寫位置
     *   先寫 a[0]；之後 i 從 1 到 n-1，如 a[i] != a[write-1] 則寫入並遞增 write
     * 重要：陣列已排序（非遞減）→ 重複值必連續
     */
    static int removeDuplicatesInPlace(int[] a) {
        int n = a.length;
        if (n == 0) return 0;

        int write = 1;        // 下一個可寫位置；先保留 a[0]
        // a[0] 已視為「前一個保留值」，從 i=1 開始比較
        for (int i = 1; i < n; i++) {
            if (a[i] != a[write - 1]) {
                a[write] = a[i];
                write++;
            }
        }
        return write; // 新長度
    }
}
