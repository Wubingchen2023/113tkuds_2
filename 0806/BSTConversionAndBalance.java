
public class BSTConversionAndBalance {

    // ========= 樹節點定義 =========
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // ========= 雙向串列節點定義 =========
    static class DoublyListNode {
        int val;
        DoublyListNode prev, next;
        DoublyListNode(int v) { val = v; }
    }

    // ========= 1. BST 轉排序雙向鏈結串列 =========
    static DoublyListNode prevNode = null;
    static DoublyListNode headNode = null;

    public static DoublyListNode bstToDoublyList(TreeNode root) {
        prevNode = null;
        headNode = null;
        inorderToDoublyList(root);
        return headNode;
    }

    private static void inorderToDoublyList(TreeNode root) {
        if (root == null) return;

        inorderToDoublyList(root.left);

        DoublyListNode newNode = new DoublyListNode(root.val);
        if (prevNode != null) {
            prevNode.next = newNode;
            newNode.prev = prevNode;
        } else {
            headNode = newNode;
        }
        prevNode = newNode;

        inorderToDoublyList(root.right);
    }

    // ========= 2. 排序陣列轉平衡 BST =========
    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildBSTFromArray(nums, 0, nums.length - 1);
    }

    private static TreeNode buildBSTFromArray(int[] arr, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(arr[mid]);
        root.left = buildBSTFromArray(arr, left, mid - 1);
        root.right = buildBSTFromArray(arr, mid + 1, right);
        return root;
    }

    // ========= 3. 檢查是否平衡，並計算平衡因子 =========
    static class BalanceInfo {
        boolean isBalanced;
        int height;
        int balanceFactor;
        TreeNode mostUnbalancedNode;

        BalanceInfo() {
            isBalanced = true;
            height = 0;
            balanceFactor = 0;
            mostUnbalancedNode = null;
        }
    }

    public static BalanceInfo checkBalance(TreeNode root) {
        return checkBalanceHelper(root);
    }

    private static BalanceInfo checkBalanceHelper(TreeNode node) {
        if (node == null) return new BalanceInfo();

        BalanceInfo left = checkBalanceHelper(node.left);
        BalanceInfo right = checkBalanceHelper(node.right);

        BalanceInfo current = new BalanceInfo();
        current.height = Math.max(left.height, right.height) + 1;
        current.balanceFactor = left.height - right.height;

        current.isBalanced = left.isBalanced && right.isBalanced &&
                             Math.abs(current.balanceFactor) <= 1;

        if (!current.isBalanced && current.mostUnbalancedNode == null) {
            current.mostUnbalancedNode = node;
        }

        return current;
    }

    // ========= 4. BST → Greater Sum Tree =========
    static int runningSum = 0;

    public static void convertToGreaterSumTree(TreeNode root) {
        runningSum = 0;
        reverseInorderSum(root);
    }

    private static void reverseInorderSum(TreeNode node) {
        if (node == null) return;

        reverseInorderSum(node.right);
        runningSum += node.val;
        node.val = runningSum;
        reverseInorderSum(node.left);
    }

    // ========= 中序列印 =========
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // ========= 列印雙向串列 =========
    public static void printDoublyList(DoublyListNode head) {
        System.out.print("雙向串列（正向）: ");
        DoublyListNode tail = null;
        while (head != null) {
            System.out.print(head.val + " ");
            if (head.next == null) tail = head;
            head = head.next;
        }

        System.out.print("\n雙向串列（反向）: ");
        while (tail != null) {
            System.out.print(tail.val + " ");
            tail = tail.prev;
        }
        System.out.println();
    }

    // ========= 主程式測試 =========
    public static void main(String[] args) {
        // 範例 BST
        TreeNode bst = new TreeNode(5);
        bst.left = new TreeNode(3);
        bst.right = new TreeNode(8);
        bst.left.left = new TreeNode(1);
        bst.left.right = new TreeNode(4);
        bst.right.left = new TreeNode(7);
        bst.right.right = new TreeNode(10);

        // 1. BST → 雙向串列
        System.out.println("【1】BST → 雙向串列");
        DoublyListNode dll = bstToDoublyList(bst);
        printDoublyList(dll);

        // 2. 排序陣列 → 平衡 BST
        System.out.println("\n【2】排序陣列 → 平衡 BST");
        int[] sorted = {1, 2, 3, 4, 5, 6, 7};
        TreeNode balanced = sortedArrayToBST(sorted);
        System.out.print("InOrder: ");
        printInOrder(balanced); System.out.println();

        // 3. 檢查平衡
        System.out.println("\n【3】檢查原始 BST 是否平衡");
        BalanceInfo info = checkBalance(bst);
        System.out.println("是否平衡: " + info.isBalanced);
        System.out.println("根節點平衡因子: " + info.balanceFactor);

        // 4. 節點轉換為大於等於該值的總和
        System.out.println("\n【4】轉換為 Greater Sum Tree");
        convertToGreaterSumTree(bst);
        System.out.print("轉換後中序: ");
        printInOrder(bst);
    }
}
