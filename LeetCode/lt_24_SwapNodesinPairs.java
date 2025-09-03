class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class lt_24_SwapNodesinPairs {
    public ListNode swapPairs(ListNode head) {
        // 建立一個虛擬頭節點 dummy，方便處理頭部交換
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy;

        // 每次檢查後面是否還有兩個節點可交換
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;        // 第一個節點
            ListNode second = prev.next.next;  // 第二個節點

            // 執行交換：prev → second → first → (原 first.next)
            first.next = second.next;
            second.next = first;
            prev.next = second;

            // 移動 prev 指針到下一組 pair 前
            prev = first;
        }

        // 返回新頭節點（dummy.next）
        return dummy.next;
    }

    public static void main(String[] args) {
        // 建立測試鏈表：1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.print("原始鏈表：");
        printList(head);

        lt_24_SwapNodesinPairs solution = new lt_24_SwapNodesinPairs();
        ListNode swapped = solution.swapPairs(head);

        System.out.print("交換後鏈表：");
        printList(swapped);
    }

    // 輔助方法：打印鏈表
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("NULL");
    }
}

/*
解題思路：
1. 題目要求兩兩交換鏈表的相鄰節點（不改變節點值，只調整指標）。
2. 使用「虛擬頭節點 dummy」簡化處理（特別是首節點被交換的情況），dummy.next 指向 head。
3. 用 prev 來記錄前一個已處理節點，進行如下操作：
   a. 每次檢查 prev 後的兩個節點（first, second）是否存在可交換。
   b. 執行交換：讓 prev.next 指向 second，second.next 指向 first，first.next 指向 second 後的下一節點。
   c. prev 指向 first，準備處理下一組 pair。
4. 直到剩下不到兩個節點則結束。
5. 最後返回 dummy.next 作為新頭節點。
6. 時間複雜度：O(n)，只遍歷一次鏈表。
   空間複雜度：O(1)，就地交換，僅用常數額外空間。
*/
