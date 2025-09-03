class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class lt_25_ReverseNodesinkGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        // 虛擬頭節點，幫助統一處理邊界情況
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy;

        while (true) {
            // 找到當前 group 的第 k 個節點
            ListNode kth = getKthNode(groupPrev, k);
            if (kth == null) break;  // 如果不足 k 個節點，結束處理

            ListNode groupNext = kth.next;

            // 反轉這一組 [groupPrev.next ... kth]
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;

            // 標準反轉鏈表（從 curr 開始，到 kth 結束）
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // 調整 groupPrev 的 next 指向 kth（新的子鏈表頭）
            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp; // 下一組的 groupPrev
        }

        return dummy.next;
    }

    // 工具函數：返回從 curr 開始往後第 k 個節點（含 curr），若不足 k 個則回傳 null
    private ListNode getKthNode(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }

    public static void main(String[] args) {
        // 建立測試鏈表：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);

        System.out.print("原始鏈表：");
        printList(head);

        int k = 3;
        lt_25_ReverseNodesinkGroup solution = new lt_25_ReverseNodesinkGroup();
        ListNode result = solution.reverseKGroup(head, k);

        System.out.println("每 " + k + " 個節點為一組反轉後的鏈表：");
        printList(result);
    }

    // 輔助函數：印出鏈表
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
/*
解題思路
1. **使用虛擬頭節點 (dummy)**：可避免處理頭節點的特殊情況，方便連接子鏈表。
2. **反轉每一組 k 節點的子鏈表**：
   - 使用 `getKthNode()` 確定是否有足夠的 k 個節點。
   - 若不足 k 個，表示不再反轉，直接結束。
   - 否則，記錄第 k 個節點 `kth` 和下一組開頭 `groupNext`。
   - 使用標準鏈表反轉技巧：將 k 節點反轉，尾接 `groupNext`。
3. **重新接回主鏈表**：
   - 反轉後的頭節點為 `kth`，更新 `groupPrev.next = kth`。
   - 將 `groupPrev` 指向新一組的前置節點（原 group 的第一個節點，現在變尾巴）。
4. **重複進行直到處理整條鏈表。**

- 時間複雜度：O(n)，每個節點最多被反轉一次。
- 空間複雜度：O(1)，只使用固定數量的指標變數。
*/ 
