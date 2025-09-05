import java.io.*;

public class LC24_SwapPairs_Shifts {
    // 基本單向節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();

        // 允許空輸入（空行）→ 直接輸出空行
        if (line == null || line.trim().isEmpty()) {
            System.out.println();
            return;
        }

        // 將一行整數建成鏈結串列
        String[] toks = line.trim().split("\\s+");
        ListNode head = buildList(toks);

        // 兩兩交換
        head = swapPairs(head);

        // 輸出結果
        StringBuilder out = new StringBuilder();
        for (ListNode cur = head; cur != null; cur = cur.next) {
            out.append(cur.val);
            if (cur.next != null) out.append(' ');
        }
        System.out.println(out.toString());
    }

    private static ListNode buildList(String[] vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (String s : vals) {
            if (s.isEmpty()) continue;
            tail.next = new ListNode(Integer.parseInt(s));
            tail = tail.next;
        }
        return dummy.next;
    }

    // 迭代法：Dummy + prev + (a,b) 指標重接
    private static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        // 只要還有成對 (a,b) 就交換
        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;       // 第一個
            ListNode b = a.next;          // 第二個

            // 交換 (a,b)
            prev.next = b;
            a.next = b.next;
            b.next = a;

            // prev 前進到 a（a 已位於 b 後）
            prev = a;
        }
        return dummy.next;
    }
}
