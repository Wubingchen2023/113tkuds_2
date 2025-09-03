import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class lt_23_MergekSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        // 最小堆：根據節點值從小到大排序
        PriorityQueue<ListNode> heap = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.val)
        );

        // 將每條鏈表的頭節點加入堆中
        for (ListNode node : lists) {
            if (node != null) {
                heap.offer(node);
            }
        }

        // 建立虛擬頭節點，統一處理
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // 當堆不為空時，取出最小節點並加入結果鏈表
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            current.next = smallest;
            current = current.next;
            // 如果該節點還有後繼，將後繼節點加入堆中
            if (smallest.next != null) {
                heap.offer(smallest.next);
            }
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        // 建立三條已排序的鏈表
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(7);

        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(5);
        l2.next.next = new ListNode(8);

        ListNode l3 = new ListNode(3);
        l3.next = new ListNode(6);
        l3.next.next = new ListNode(9);

        // 放入陣列中
        ListNode[] lists = new ListNode[] { l1, l2, l3 };

        // 執行合併
        lt_23_MergekSortedLists solution = new lt_23_MergekSortedLists();
        ListNode mergedHead = solution.mergeKLists(lists);

        // 輸出合併結果
        System.out.print("合併後的鏈表：");
        printList(mergedHead);
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
1. 使用「最小堆」（PriorityQueue）來管理 k 條已排序鏈表的當前最小節點：
   - 初始時，將每條鏈表的頭節點入堆，堆頂即所有頭節點中值最小者。
2. 每次從堆頂彈出最小節點，將其接到結果鏈表尾部（current.next），並將該節點所在鏈表的下一節點（若存在）入堆。
3. 重複上述步驟，直到堆空，意味所有節點都已被合併。
4. 使用虛擬頭節點（dummy head）簡化鏈表拼接與邊界處理，最終返回 dummy.next 作為合併後的頭節點。

時間複雜度：O(N log k)，其中 N 為所有節點總數，k 為鏈表數量。每個節點入堆與出堆各一次，堆操作成本 O(log k)。  
空間複雜度：O(k)，堆中最多同時存放 k 個節點。  
*/
