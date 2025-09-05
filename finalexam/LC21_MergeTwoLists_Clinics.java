import java.io.*;

public class LC21_MergeTwoLists_Clinics {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        String[] sizes = reader.readLine().trim().split(" ");
        int n = Integer.parseInt(sizes[0]);
        int m = Integer.parseInt(sizes[1]);

        ListNode l1 = null, l2 = null;
        if (n > 0) l1 = buildList(reader.readLine().trim().split(" "));
        if (m > 0) l2 = buildList(reader.readLine().trim().split(" "));

        ListNode merged = mergeTwoLists(l1, l2);

        // 輸出合併後結果
        while (merged != null) {
            out.append(merged.val);
            if (merged.next != null) out.append(' ');
            merged = merged.next;
        }
        out.append('\n');
        System.out.print(out);
    }

    /** 由輸入字串陣列構建鏈結串列 */
    private static ListNode buildList(String[] vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (String s : vals) {
            tail.next = new ListNode(Integer.parseInt(s));
            tail = tail.next;
        }
        return dummy.next;
    }

    /** 合併兩已排序鏈結串列 */
    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(-1);  // Dummy 頭節點
        ListNode tail = dummy;

        // 雙指針合併
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        // 接上剩下的節點（至多一條還有）
        tail.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }
}
