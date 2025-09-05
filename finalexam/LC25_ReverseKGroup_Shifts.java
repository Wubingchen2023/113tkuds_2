import java.io.*;

public class LC25_ReverseKGroup_Shifts {
    /** 單向鏈結節點 */
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader + StringBuilder 進行快速 I/O
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        // 讀入 k
        line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            // 無 k，直接結束
            return;
        }
        int k = Integer.parseInt(line.trim());

        // 讀入整條班表（節點值序列），可能為空行
        line = br.readLine();
        ListNode head = null;
        if (line != null && !line.trim().isEmpty()) {
            String[] toks = line.trim().split("\\s+");
            head = buildList(toks);
        }

        // 執行 k 群反轉
        head = reverseKGroup(head, k);

        // 輸出反轉後序列
        StringBuilder sb = new StringBuilder();
        ListNode cur = head;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append(' ');
            cur = cur.next;
        }
        System.out.println(sb.toString());
    }

    /** 根據輸入值陣列構建單向鏈結串列 */
    private static ListNode buildList(String[] vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (String s : vals) {
            tail.next = new ListNode(Integer.parseInt(s));
            tail = tail.next;
        }
        return dummy.next;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) return head; // k=1 或空串列 → 不變

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prevGroupEnd = dummy;

        while (true) {
            // 1) 檢查從 prevGroupEnd.next 開始是否至少還有 k 個節點
            ListNode groupEnd = prevGroupEnd;
            for (int i = 0; i < k && groupEnd != null; i++) {
                groupEnd = groupEnd.next;
            }
            if (groupEnd == null) break; // 不足 k 終止

            // 記錄分組的起點與下一組的起點
            ListNode groupStart = prevGroupEnd.next;
            ListNode nextGroupHead = groupEnd.next;

            // 2) 反轉 [groupStart ... groupEnd]
            reverseSegment(groupStart, groupEnd);

            // 3) 重新連接反轉後的片段
            prevGroupEnd.next = groupEnd;     // 前段接上反轉後的頭 (原 groupEnd)
            groupStart.next = nextGroupHead;  // 反轉後尾 (原 groupStart) 接上下一組

            // 4) 移動 prevGroupEnd 至本組尾端（原 groupStart）
            prevGroupEnd = groupStart;
        }

        return dummy.next;
    }

    /**
     * 原地反轉從 start 到 end 的鏈結串列區間（含兩端）
     * 例如 a->b->c->d, start=b, end=d → d->c->b
     *
     * 區間起點 (反轉後位於尾端)
     * 區間終點 (反轉後位於起點)
     */
    private static void reverseSegment(ListNode start, ListNode end) {
        ListNode prev = null;
        ListNode curr = start;
        // 當前要反轉的區間以 end.next 為終止條件
        ListNode stop = end.next;

        while (curr != stop) {
            ListNode nxt = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nxt;
        }
    }
}
