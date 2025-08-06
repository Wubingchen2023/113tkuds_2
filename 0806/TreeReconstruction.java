import java.util.*;

public class TreeReconstruction {

    // ========== 節點定義 ==========
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { 
            this.val = val; 
        }
    }

    // ========== 1. 前序 + 中序 ==========
    public static TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPreIn(preorder, 0, preorder.length - 1,
                          inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPreIn(int[] pre, int ps, int pe,
                                       int[] in, int is, int ie,
                                       Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie) return null;

        TreeNode root = new TreeNode(pre[ps]);
        int inRootIdx = inMap.get(pre[ps]);
        int leftSize = inRootIdx - is;

        root.left = buildPreIn(pre, ps + 1, ps + leftSize, in, is, inRootIdx - 1, inMap);
        root.right = buildPreIn(pre, ps + leftSize + 1, pe, in, inRootIdx + 1, ie, inMap);
        return root;
    }

    // ========== 2. 後序 + 中序 ==========
    public static TreeNode buildFromPostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            inMap.put(inorder[i], i);
        return buildPostIn(postorder, 0, postorder.length - 1,
                           inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPostIn(int[] post, int ps, int pe,
                                        int[] in, int is, int ie,
                                        Map<Integer, Integer> inMap) {
        if (ps > pe || is > ie) return null;

        TreeNode root = new TreeNode(post[pe]);
        int inRootIdx = inMap.get(post[pe]);
        int leftSize = inRootIdx - is;

        root.left = buildPostIn(post, ps, ps + leftSize - 1, in, is, inRootIdx - 1, inMap);
        root.right = buildPostIn(post, ps + leftSize, pe - 1, in, inRootIdx + 1, ie, inMap);
        return root;
    }

    // ========== 3. 層序建立完全二元樹 ==========
    public static TreeNode buildFromLevelOrder(int[] levelOrder) {
        if (levelOrder.length == 0) return null;

        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (i < levelOrder.length) {
            TreeNode current = queue.poll();
            if (i < levelOrder.length) {
                current.left = new TreeNode(levelOrder[i++]);
                queue.offer(current.left);
            }
            if (i < levelOrder.length) {
                current.right = new TreeNode(levelOrder[i++]);
                queue.offer(current.right);
            }
        }
        return root;
    }

    // ========== 4. 驗證：走訪輸出 ==========
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    public static void printPreOrder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    public static void printPostOrder(TreeNode root) {
        if (root == null) return;
        printPostOrder(root.left);
        printPostOrder(root.right);
        System.out.print(root.val + " ");
    }

    public static void printLevelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            System.out.print(cur.val + " ");
            if (cur.left != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
        }
    }

    // ========== 主程式測試 ==========
    public static void main(String[] args) {
        // 測試資料
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder  = {9, 3, 15, 20, 7};
        int[] postorder = {9, 15, 7, 20, 3};
        int[] levelOrder = {1, 2, 3, 4, 5, 6, 7};

        // 1. 前序 + 中序
        System.out.println("【1】重建自前序+中序:");
        TreeNode root1 = buildFromPreIn(preorder, inorder);
        System.out.print("  InOrder: "); printInOrder(root1); System.out.println();
        System.out.print("  PreOrder: "); printPreOrder(root1); System.out.println();

        // 2. 後序 + 中序
        System.out.println("\n【2】重建自後序+中序:");
        TreeNode root2 = buildFromPostIn(postorder, inorder);
        System.out.print("  InOrder: "); printInOrder(root2); System.out.println();
        System.out.print("  PostOrder: "); printPostOrder(root2); System.out.println();

        // 3. 層序建完全二元樹
        System.out.println("\n【3】重建完全二元樹（層序）:");
        TreeNode root3 = buildFromLevelOrder(levelOrder);
        System.out.print("  LevelOrder: "); printLevelOrder(root3); System.out.println();
        System.out.print("  InOrder: "); printInOrder(root3); System.out.println();
    }
}
