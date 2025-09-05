import java.io.*;

public class LC19_RemoveNth_Node_Clinic {
    // 單向鏈結節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { this.val = v; }
    }

    // 讀取：n, n 個節點值, k
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();                 // 節點數
        ListNode head = null, tail = null;

        for (int i = 0; i < n; i++) {
            int v = fs.nextInt();
            ListNode node = new ListNode(v);
            if (head == null) head = tail = node;
            else { tail.next = node; tail = node; }
        }
        int k = fs.nextInt();                 // 倒數第 k 個

        head = removeNthFromEnd(head, k);     // 一趟掃描刪除
        printList(head);                      // 輸出刪除後序列
    }

    /**
     * 兩指標一次遍歷（時間 O(n), 空間 O(1)）
     * fast 先走 k 步；之後 fast、slow 同步前進。
     * 當 fast 走到 null，slow 恰位於待刪節點的前一個。
     */
    static ListNode removeNthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = head;
        // 先讓 fast 領先 k 步
        for (int i = 0; i < k; i++) {
            fast = fast.next;   // k ∈ [1..n]，此處不會越界
        }

        // slow 從 dummy 出發，與 fast 同步推進
        ListNode slow = dummy;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 此時 slow.next 即為待刪節點
        slow.next = slow.next.next;
        return dummy.next;
    }

    // 輸出鏈結串列（空串列輸出空行）
    static void printList(ListNode head) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(p.val).append(' ');
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // 去掉最後空白
        System.out.println(sb.toString());
    }

    // 輕量高速輸入工具（比 Scanner 快，n 可到 1e5）
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
            do { c = read(); } while (c <= ' '); // skip spaces
            if (c == '-') { sgn = -1; c = read(); }
            while (c > ' ') {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }
    }
}
