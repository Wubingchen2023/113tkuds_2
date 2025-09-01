// 鏈結串列定義
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class lt_02_AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // 虛擬頭節點
        ListNode curr = dummy;
        int carry = 0;

        while (l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            int sum = x + y + carry;

            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        // 建立 l1 = [2 -> 4 -> 3] (代表數字 342)
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        // 建立 l2 = [5 -> 6 -> 4] (代表數字 465)
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        lt_02_AddTwoNumbers solver = new lt_02_AddTwoNumbers();
        ListNode result = solver.addTwoNumbers(l1, l2);

        // 輸出結果 [7 -> 0 -> 8]，代表 807
        System.out.print("結果: ");
        printList(result);
    }

    // 輔助方法：印出鏈結串列
    public static void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.val + (node.next != null ? " -> " : ""));
            node = node.next;
        }
        System.out.println();
    }
}
