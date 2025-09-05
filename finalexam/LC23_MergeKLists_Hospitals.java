import java.io.*;
import java.util.*;

public class LC23_MergeKLists_Hospitals {

    /** 單向鏈結節點定義 */
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { this.val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            // 未提供 k，視為無輸入
            System.out.println();
            return;
        }

        int k = Integer.parseInt(line.trim());
        ListNode[] lists = new ListNode[k];

        // 讀入 k 行，每行以 -1 結尾；可能有空串列（僅 -1）
        for (int i = 0; i < k; i++) {
            String row = br.readLine();
            if (row == null) { // 沒有更多資料，視為空串列
                lists[i] = null;
                continue;
            }
            row = row.trim();
            if (row.isEmpty()) { // 空白行，視為空串列（不符合題意，但做容錯）
                lists[i] = null;
                continue;
            }
            lists[i] = buildListFromLine(row);
        }

        ListNode merged = mergeKLists(lists);

        // 以單行輸出合併結果；若為空，輸出空行
        StringBuilder out = new StringBuilder();
        for (ListNode cur = merged; cur != null; cur = cur.next) {
            out.append(cur.val);
            if (cur.next != null) out.append(' ');
        }
        out.append('\n');
        System.out.print(out.toString());
    }

    /** 將「以 -1 結尾」的一行資料建成單向鏈結串列 */
    private static ListNode buildListFromLine(String row) {
        String[] toks = row.trim().split("\\s+");
        ListNode dummy = new ListNode(0), tail = dummy;
        for (String t : toks) {
            int v = Integer.parseInt(t);
            if (v == -1) break;
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    /**
     * 合併 K 條已排序串列（最小堆）
     * lists 可能包含 null（空串列）
     * r合併後的升序串列頭
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        // 以節點值作比較；若值相同，順序不影響排序正確性
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));

        // 初始將所有非空串列頭放入堆
        for (ListNode head : lists) {
            if (head != null) pq.offer(head);
        }

        ListNode dummy = new ListNode(0), tail = dummy;

        while (!pq.isEmpty()) {
            ListNode min = pq.poll(); // 目前全體最小值
            tail.next = min;
            tail = tail.next;

            if (min.next != null) {
                pq.offer(min.next); // 推進該串列
            }
        }
        return dummy.next;
    }
}
