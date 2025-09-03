class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class lt_19_RemoveNthNodeFromEndofList {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        // 建立 dummy 節點，避免刪除頭節點時的特殊處理
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 初始化快慢指針
        ListNode fast = dummy;
        ListNode slow = dummy;

        // Step 1: fast 先走 n+1 步，保持間距
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Step 2: fast 和 slow 同步移動，直到 fast 到尾端
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Step 3: 此時 slow 停在要刪除節點的前一個 → 調整指標刪除節點
        slow.next = slow.next.next;

        return dummy.next; // 返回新頭節點（可能已變）
    }

    public static void main(String[] args) {
        // 建立測試鏈結串列：1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        int n = 2;
        ListNode newHead = removeNthFromEnd(head, n);

        // 輸出結果：1 -> 2 -> 3 -> 5
        while (newHead != null) {
            System.out.print(newHead.val + " ");
            newHead = newHead.next;
        }
    }
}

/*
解題邏輯：
1. 使用 dummy 節點處理刪除頭節點的邊界情況。
2. 讓 fast 先走 n+1 步，這樣當 fast 到尾端時，slow 剛好停在倒數第 n 個節點的前一個。
3. 將 slow.next 指向 slow.next.next，完成刪除操作。
4. 返回 dummy.next（可能是原本的 head，也可能是新 head）。

範例：
Input: head = [1,2,3,4,5], n = 2
步驟：
- fast 先走 3 步（n+1=3） → fast 在節點 3
- fast/slow 一起移動 → fast 到 null 時，slow 在節點 3
- slow.next = 4 → 要刪掉的節點
- 刪除後：1 -> 2 -> 3 -> 5

時間複雜度：
O(L)，L = 鏈結串列長度（fast/slow 只遍歷一次）

空間複雜度：
O(1)，僅使用常數額外指標
*/
