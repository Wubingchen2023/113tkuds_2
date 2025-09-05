import java.io.*;

public class LC27_RemoveElement_Recycle {

    // 快速讀取工具：支援 n 至 1e5 的輸入規模
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { this.in = is; }
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

        // 讀 n 與 val；若無輸入直接結束
        int n;
        try { n = fs.nextInt(); }
        catch (Exception e) { return; }
        int val = fs.nextInt();

        // 讀入陣列
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = fs.nextInt();

        // 就地移除：write 僅寫入 ≠ val 的元素（保持原相對順序）
        int k = removeElementInPlace(nums, val);

        // 依題意輸出
        out.append(k).append('\n');
        for (int i = 0; i < k; i++) {
            if (i > 0) out.append(' ');
            out.append(nums[i]);
        }
        out.append('\n');
        System.out.print(out.toString());
    }

    /**
     * 單指針就地覆寫：保留非 val 的元素到前段，回傳新長度 k
     * 重要不變量：在任何時刻，nums[0..write-1] 為迄今為止的所有非 val 元素，且相對順序未變
     */
    static int removeElementInPlace(int[] nums, int val) {
        int write = 0; // 下一個可寫位置
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x; // 覆寫保留
            }
        }
        return write;
    }
}
