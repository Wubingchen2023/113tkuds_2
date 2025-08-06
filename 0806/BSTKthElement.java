import java.util.*;

public class BSTKthElement {

    // === 節點定義（包含節點計數） ===
    static class TreeNode {
        int val;
        int size; // 當前子樹節點數
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            this.size = 1;
        }
    }

    // === 更新節點大小 ===
    private static int getSize(TreeNode node) {
        return node == null ? 0 : node.size;
    }

    private static void updateSize(TreeNode node) {
        if (node != null) {
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }
    }

    // === 動態插入 ===
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val)
            root.left = insert(root.left, val);
        else
            root.right = insert(root.right, val);
        updateSize(root);
        return root;
    }

    // === 動態刪除 ===
    public static TreeNode delete(TreeNode root, int val) {
        if (root == null) return null;
        if (val < root.val) {
            root.left = delete(root.left, val);
        } else if (val > root.val) {
            root.right = delete(root.right, val);
        } else {
            // 找到要刪除的節點
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode minNode = getMin(root.right);
            root.val = minNode.val;
            root.right = delete(root.right, minNode.val);
        }
        updateSize(root);
        return root;
    }

    private static TreeNode getMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // === 1. 找出第 k 小元素 ===
    public static Integer kthSmallest(TreeNode root, int k) {
        if (root == null) return null;
        int leftSize = getSize(root.left);
        if (k == leftSize + 1) return root.val;
        else if (k <= leftSize) return kthSmallest(root.left, k);
        else return kthSmallest(root.right, k - leftSize - 1);
    }

    // === 2. 找出第 k 大元素 ===
    public static Integer kthLargest(TreeNode root, int k) {
        int total = getSize(root);
        return kthSmallest(root, total - k + 1);
    }

    // === 3. 找出第 k 小到第 j 小的所有元素 ===
    public static List<Integer> kthRange(TreeNode root, int k, int j) {
        List<Integer> all = new ArrayList<>();
        inOrder(root, all);
        return all.subList(k - 1, j); // index 起始為 0
    }

    private static void inOrder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inOrder(root.left, result);
        result.add(root.val);
        inOrder(root.right, result);
    }

    // === 中序列印 ===
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    // === 主程式 ===
    public static void main(String[] args) {
        int[] values = {20, 10, 30, 5, 15, 25, 35};
        TreeNode root = null;
        for (int v : values) {
            root = insert(root, v);
        }

        System.out.println("=== 原始 BST 中序 ===");
        printInOrder(root);
        System.out.println("\n");

        // 1. 第 k 小元素
        int k = 3;
        System.out.println("1. 第 " + k + " 小元素: " + kthSmallest(root, k));

        // 2. 第 k 大元素
        int k2 = 2;
        System.out.println("2. 第 " + k2 + " 大元素: " + kthLargest(root, k2));

        // 3. 第 k 小到第 j 小
        int start = 2, end = 5;
        System.out.println("3. 第 " + start + " 小到第 " + end + " 小元素: " + kthRange(root, start, end));

        // 4. 測試動態插入與刪除
        System.out.println("\n4. 動態插入 17 與刪除 10：");
        root = insert(root, 17);
        root = delete(root, 10);
        printInOrder(root);
        System.out.println("\n   第 4 小元素: " + kthSmallest(root, 4));
    }
}
