import java.util.*;

public class BSTRangeQuerySystem {

    // === 節點定義 ===
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { 
            this.val = val; 
        }
    }

    // === 插入節點至 BST ===
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val){
            root.left = insert(root.left, val);
        }
        else {
            root.right = insert(root.right, val);
        }
        return root;
    }

    // === 1. 範圍查詢：列出所有在 [min, max] 範圍內的節點值 ===
    public static List<Integer> rangeQuery(TreeNode root, int min, int max) {
        List<Integer> result = new ArrayList<>();
        rangeQueryHelper(root, min, max, result);
        return result;
    }

    private static void rangeQueryHelper(TreeNode node, int min, int max, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.val > min) {
            rangeQueryHelper(node.left, min, max, result);
        }
        if (node.val >= min && node.val <= max) {
            result.add(node.val);
        }
        if (node.val < max) {
            rangeQueryHelper(node.right, min, max, result);
        }
    }

    // === 2. 範圍計數：計算範圍內節點個數 ===
    public static int rangeCount(TreeNode root, int min, int max) {
        if (root == null) {
            return 0;
        }
        if (root.val < min) {
            return rangeCount(root.right, min, max);
        }
        if (root.val > max) {
            return rangeCount(root.left, min, max);
        }
        return 1 + rangeCount(root.left, min, max) + rangeCount(root.right, min, max);
    }

    // === 3. 範圍總和：計算範圍內節點值總和 ===
    public static int rangeSum(TreeNode root, int min, int max) {
        if (root == null) {
            return 0;
        }
        if (root.val < min) {
            return rangeSum(root.right, min, max);
        }
        if (root.val > max) {
            return rangeSum(root.left, min, max);
        }
        return root.val + rangeSum(root.left, min, max) + rangeSum(root.right, min, max);
    }

    // === 4. 最接近查詢：找出最接近 target 的節點值 ===
    public static int closestValue(TreeNode root, int target) {
        int closest = root.val;
        TreeNode current = root;

        while (current != null) {
            if (Math.abs(current.val - target) < Math.abs(closest - target)) {
                closest = current.val;
            }
            if (target < current.val) {
                current = current.left;
            }
            else if (target > current.val) {
                current = current.right;
            }
            else break; // 完全相等
        }

        return closest;
    }

    // === 主程式測試 ===
    public static void main(String[] args) {
        int[] values = {15, 10, 20, 8, 12, 17, 25};
        TreeNode root = null;
        for (int val : values) {
            root = insert(root, val);
        }

        int min = 10, max = 20;
        int target = 16;

        // 1. 範圍查詢
        List<Integer> rangeNodes = rangeQuery(root, min, max);
        System.out.println("1. 範圍查詢 [" + min + ", " + max + "] 範圍內節點:");
        System.out.println("   " + rangeNodes);

        // 2. 範圍計數
        int count = rangeCount(root, min, max);
        System.out.println("2. 範圍計數: " + count + " 個");

        // 3. 範圍總和
        int sum = rangeSum(root, min, max);
        System.out.println("3. 範圍總和: " + sum);

        // 4. 最接近查詢
        int closest = closestValue(root, target);
        System.out.println("4. 與目標值 " + target + " 最接近的節點值: " + closest);
    }
}
