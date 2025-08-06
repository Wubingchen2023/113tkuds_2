public class TreeMirrorAndSymmetry {

    // 樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1. 判斷是否為對稱樹
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    // 比較左右子樹是否為鏡像（輔助用）
    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isMirror(t1.left, t2.right)
                && isMirror(t1.right, t2.left);
    }

    // 2. 將一棵樹轉換為其鏡像（原地遞迴交換左右子樹）
    public static void mirror(TreeNode root) {
        if (root == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        mirror(root.left);
        mirror(root.right);
    }

    // 3. 比較兩棵樹是否互為鏡像
    public static boolean areMirror(TreeNode t1, TreeNode t2) {
        return isMirror(t1, t2);
    }

    // 4. 檢查 subtree 是否為 root 的子樹
    public static boolean isSubtree(TreeNode root, TreeNode subtree) {
        if (root == null) return false;
        if (isSameTree(root, subtree)) return true;
        return isSubtree(root.left, subtree) || isSubtree(root.right, subtree);
    }

    // 判斷兩棵樹是否完全相同
    private static boolean isSameTree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                && isSameTree(t1.left, t2.left)
                && isSameTree(t1.right, t2.right);
    }

    // 中序列印
    public static void inorderPrint(TreeNode root) {
        if (root == null) return;
        inorderPrint(root.left);
        System.out.print(root.val + " ");
        inorderPrint(root.right);
    }

    // 主程式
    public static void main(String[] args) {
        /*
               原始樹 root:
                    1
                   / \
                  2   2
                 / \ / \
                3  4 4  3
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        System.out.println("1. 該樹是否為對稱樹？");
        System.out.println("   " + isSymmetric(root)); // true

        System.out.println("\n2. 鏡像該樹後的中序遍歷：");
        mirror(root);
        inorderPrint(root);
        System.out.println();

        System.out.println("\n3. 該鏡像樹是否與原始對稱樹互為鏡像？");
        TreeNode original = new TreeNode(1);
        original.left = new TreeNode(2);
        original.right = new TreeNode(2);
        original.left.left = new TreeNode(3);
        original.left.right = new TreeNode(4);
        original.right.left = new TreeNode(4);
        original.right.right = new TreeNode(3);
        System.out.println("   " + areMirror(original, root)); // true

        System.out.println("\n4. 測試一棵子樹是否為主樹的子樹：");
        TreeNode sub = new TreeNode(2);
        sub.left = new TreeNode(4);
        sub.right = new TreeNode(3);
        System.out.println("   子樹為主樹的子樹？ " + isSubtree(root, sub)); // true
    }
}
