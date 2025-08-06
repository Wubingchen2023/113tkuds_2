import java.util.*;

public class BSTValidationAndRepair {

    // 節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { 
            this.val = val; 
        }
    }

    // ============================================================
    // 1. 驗證是否為 BST
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return isValidBSTHelper(node.left, min, node.val) &&
               isValidBSTHelper(node.right, node.val, max);
    }

    // ============================================================
    // 2. 找出 BST 中不符合規則的節點
    static class ErrorNodes {
        TreeNode first, second, prev;
    }

    public static List<Integer> findErrorNodes(TreeNode root) {
        ErrorNodes en = new ErrorNodes();
        inOrderFind(root, en);
        List<Integer> result = new ArrayList<>();
        if (en.first != null) result.add(en.first.val);
        if (en.second != null) result.add(en.second.val);
        return result;
    }

    private static void inOrderFind(TreeNode root, ErrorNodes en) {
        if (root == null) return;
        inOrderFind(root.left, en);

        if (en.prev != null && en.prev.val > root.val) {
            if (en.first == null) en.first = en.prev;
            en.second = root;
        }
        en.prev = root;

        inOrderFind(root.right, en);
    }

    // ============================================================
    // 3. 修復一棵有兩個節點位置錯誤的 BST
    public static void recoverBST(TreeNode root) {
        ErrorNodes en = new ErrorNodes();
        inOrderFind(root, en);
        if (en.first != null && en.second != null) {
            int temp = en.first.val;
            en.first.val = en.second.val;
            en.second.val = temp;
        }
    }

    // ============================================================
    // 4. 計算需要移除多少個節點才能讓樹變成有效的 BST
    public static int minRemovalsToBST(TreeNode root) {
        int total = countNodes(root);
        int maxBST = largestBSTSubtree(root).size;
        return total - maxBST;
    }

    // 計算節點數
    private static int countNodes(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    // 用於計算最大 BST 子樹
    static class BSTInfo {
        boolean isBST;
        int size;
        int minVal, maxVal;
        BSTInfo(boolean isBST, int size, int minVal, int maxVal) {
            this.isBST = isBST;
            this.size = size;
            this.minVal = minVal;
            this.maxVal = maxVal;
        }
    }

    private static BSTInfo largestBSTSubtree(TreeNode node) {
        if (node == null) return new BSTInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);

        BSTInfo left = largestBSTSubtree(node.left);
        BSTInfo right = largestBSTSubtree(node.right);

        if (left.isBST && right.isBST && node.val > left.maxVal && node.val < right.minVal) {
            return new BSTInfo(
                true,
                left.size + right.size + 1,
                Math.min(left.minVal, node.val),
                Math.max(right.maxVal, node.val)
            );
        }

        return new BSTInfo(false, Math.max(left.size, right.size), 0, 0);
    }

    // ============================================================
    // 中序列印
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // ============================================================
    // 主程式
    public static void main(String[] args) {
        /*
            建立一棵錯誤 BST:
                   10
                  /  \
                 5   15
                    /  \
                  12    8   <- 8 和 15 位置交換了
        */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(8);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(15);

        System.out.println("原始樹中序遍歷：");
        printInOrder(root);
        System.out.println("\n");

        // 1. 驗證 BST
        System.out.println("1. 該樹是否為有效 BST？ " + isValidBST(root));

        // 2. 找出錯誤節點
        List<Integer> errors = findErrorNodes(root);
        System.out.println("2. 錯誤節點: " + errors);

        // 3. 修復 BST
        recoverBST(root);
        System.out.println("3. 修復後的 BST 中序遍歷：");
        printInOrder(root);
        System.out.println("\n   是否為有效 BST？ " + isValidBST(root));

        // 4. 最少刪除節點
        int removals = minRemovalsToBST(root);
        System.out.println("4. 需要刪除的節點數: " + removals);
    }
}
