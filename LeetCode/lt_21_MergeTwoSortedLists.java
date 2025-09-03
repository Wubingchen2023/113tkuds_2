class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class lt_21_MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 建立一個虛擬頭節點，方便統一處理邊界情況
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // 當兩個鏈表都不為空時，選擇較小者接到結果鏈表尾部
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;     // 接上 l1 節點
                l1 = l1.next;          // l1 指針前進
            } else {
                current.next = l2;     // 接上 l2 節點
                l2 = l2.next;          // l2 指針前進
            }
            current = current.next;   // 結果鏈表指針前進
        }
        
        // 若其中一個鏈表尚有剩餘，直接接到結果鏈表尾部
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        
        // 返回合併後的鏈表（跳過虛擬頭節點）
        return dummy.next;
    }

    public static void main(String[] args) {
        // 建立第一條鏈表：1 -> 3 -> 5
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(3);
        l1.next.next = new ListNode(5);

        // 建立第二條鏈表：2 -> 4 -> 6
        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(4);
        l2.next.next = new ListNode(6);

        lt_21_MergeTwoSortedLists solution = new lt_21_MergeTwoSortedLists();
        ListNode merged = solution.mergeTwoLists(l1, l2);

        // 輸出合併後的鏈表
        System.out.print("合併後的鏈表：");
        printList(merged);
    }

    // 輔助方法：列印鏈表
    public static void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.val + " -> ");
            node = node.next;
        }
        System.out.println("NULL");
    }
}

/*
解題思路：
1. 使用「虛擬頭節點」（dummy head）簡化邊界處理，不需額外判斷首節點。
2. 透過兩個指針 l1、l2 分別遍歷兩條已排序鏈表，並比較當前節點值大小：
   - 較小的節點接到結果鏈表尾部（current.next）。
   - 並將該來源指針向前移動。
3. 重複上述步驟直至某一鏈表遍歷完畢，將另一條剩餘節點一次性接在結果尾部。
4. 最後返回 dummy.next 作為合併後的頭節點。
時間複雜度：O(n + m)，n、m 分別為兩鏈表長度。
空間複雜度：O(1)，只使用常數級額外空間。
*/
