import java.util.*;

public class TreePathProblems {

    // ========== 節點定義 ==========
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { 
            val = v; 
        }
    }

    // ========== 1. 所有根到葉節點路徑 ==========
    public static List<String> allRootToLeafPaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        dfsAllPaths(root, "", result);
        return result;
    }

    private static void dfsAllPaths(TreeNode node, String path, List<String> result) {
        if (node == null) return;

        path += node.val;
        if (node.left == null && node.right == null) {
            result.add(path);
        } else {
            path += "->";
            dfsAllPaths(node.left, path, result);
            dfsAllPaths(node.right, path, result);
        }
    }

    // ========== 2. 是否存在總和為目標的路徑 ==========
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null)
            return targetSum == root.val;
        return hasPathSum(root.left, targetSum - root.val)
            || hasPathSum(root.right, targetSum - root.val);
    }

    // ========== 3. 最大總和根到葉路徑 ==========
    static class MaxPathResult {
        int sum = Integer.MIN_VALUE;
        String path = "";
    }

    public static String maxRootToLeafPath(TreeNode root) {
        MaxPathResult result = new MaxPathResult();
        dfsMaxSumPath(root, "", 0, result);
        return result.path + " (總和: " + result.sum + ")";
    }

    private static void dfsMaxSumPath(TreeNode node, String path, int sum, MaxPathResult result) {
        if (node == null) return;

        sum += node.val;
        path += node.val;

        if (node.left == null && node.right == null) {
            if (sum > result.sum) {
                result.sum = sum;
                result.path = path;
            }
        } else {
            path += "->";
            dfsMaxSumPath(node.left, path, sum, result);
            dfsMaxSumPath(node.right, path, sum, result);
        }
    }

    // ========== 4. 任意兩節點的最大路徑和（直徑） ==========
    static class MaxPathSumHolder {
        int max = Integer.MIN_VALUE;
    }

    public static int maxPathSum(TreeNode root) {
        MaxPathSumHolder holder = new MaxPathSumHolder();
        dfsMaxPathSum(root, holder);
        return holder.max;
    }

    private static int dfsMaxPathSum(TreeNode node, MaxPathSumHolder holder) {
        if (node == null) return 0;

        int left = Math.max(0, dfsMaxPathSum(node.left, holder));
        int right = Math.max(0, dfsMaxPathSum(node.right, holder));
        int currentSum = left + right + node.val;
        holder.max = Math.max(holder.max, currentSum);

        return node.val + Math.max(left, right);
    }

    // ========== 主程式測試 ==========
    public static void main(String[] args) {
        /*
               10
              /  \
             5    12
            / \     \
           4   7     15
        */

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(12);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(15);

        // 1. 所有根到葉路徑
        System.out.println("1. 根到葉所有路徑:");
        List<String> paths = allRootToLeafPaths(root);
        for (String p : paths) {
            System.out.println(" - " + p);
        }

        // 2. 是否存在和為 22 的路徑
        int targetSum = 22;
        boolean hasPath = hasPathSum(root, targetSum);
        System.out.println("\n2. 是否存在總和為 " + targetSum + " 的路徑: " + hasPath);

        // 3. 最大總和路徑
        System.out.println("\n3. 最大總和根到葉路徑: ");
        String maxPath = maxRootToLeafPath(root);
        System.out.println(" - " + maxPath);

        // 4. 最大路徑總和（任意兩節點）
        int maxAnyPathSum = maxPathSum(root);
        System.out.println("\n4. 任意兩節點間最大路徑和: " + maxAnyPathSum);
    }
}
