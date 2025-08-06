import java.util.*;

public class BinaryTreeBasicOperations {

    // 樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 計算節點總和與平均值
    static class SumCount {
        int sum = 0;
        int count = 0;
    }

    public static SumCount sumAndCount(TreeNode root) {
        SumCount sc = new SumCount();
        sumAndCountHelper(root, sc);
        return sc;
    }

    private static void sumAndCountHelper(TreeNode node, SumCount sc) {
        if (node == null) return;
        sc.sum += node.val;
        sc.count++;
        sumAndCountHelper(node.left, sc);
        sumAndCountHelper(node.right, sc);
    }

    // 2. 找最大值與最小值節點
    public static int findMax(TreeNode root) {
        if (root == null) return Integer.MIN_VALUE;
        int leftMax = findMax(root.left);
        int rightMax = findMax(root.right);
        return Math.max(root.val, Math.max(leftMax, rightMax));
    }

    public static int findMin(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        int leftMin = findMin(root.left);
        int rightMin = findMin(root.right);
        return Math.min(root.val, Math.min(leftMin, rightMin));
    }

    // 3. 計算樹的寬度（BFS 每層節點數最大值）
    public static int treeWidth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // 當層節點數
            maxWidth = Math.max(maxWidth, levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

        return maxWidth;
    }

    // 4. 判斷是否為完全二元樹（BFS + flag）
    public static boolean isCompleteBinaryTree(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean encounteredNull = false;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                encounteredNull = true;
            } else {
                if (encounteredNull) return false;
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return true;
    }

    // 主程式
    public static void main(String[] args) {
        /*
                 10
                /  \
               5    15
              / \   / 
             3   7 12 
        */
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(12);

        // 1. 總和與平均
        SumCount sc = sumAndCount(root);
        System.out.println("1. 節點總和: " + sc.sum);
        System.out.printf("   平均值: %.2f\n", (double) sc.sum / sc.count);

        // 2. 最大值與最小值
        System.out.println("2. 最大值: " + findMax(root));
        System.out.println("   最小值: " + findMin(root));

        // 3. 最大寬度
        System.out.println("3. 樹的最大寬度: " + treeWidth(root));

        // 4. 是否為完全二元樹
        System.out.println("4. 是否為完全二元樹: " + isCompleteBinaryTree(root));
    }
}
