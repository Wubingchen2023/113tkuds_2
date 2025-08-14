public class AVLRotationExercise {

    /* ========= 節點定義 ========= */
    private static class Node {
        int key, height = 1;
        Node left, right;
        Node(int k) { key = k; }
    }

    /* ========= AVL 核心 ========= */
    private static int h(Node n) { return n == null ? 0 : n.height; }

    private static int getBalance(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    /* ------ 基本旋轉 ------ */
    private static Node rightRotate(Node y) {
        Node x  = y.left;
        Node T2 = x.right;

        x.right = y;      // 旋轉
        y.left  = T2;

        y.height = 1 + Math.max(h(y.left), h(y.right));
        x.height = 1 + Math.max(h(x.left), h(x.right));
        return x;         // 新根
    }

    private static Node leftRotate(Node x) {
        Node y  = x.right;
        Node T2 = y.left;

        y.left  = x;      // 旋轉
        x.right = T2;

        x.height = 1 + Math.max(h(x.left), h(x.right));
        y.height = 1 + Math.max(h(y.left), h(y.right));
        return y;         // 新根
    }

    /* ------ 插入 (觸發旋轉) ------ */
    private static Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key)  node.left  = insert(node.left,  key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node; // duplicate → 忽略

        node.height = 1 + Math.max(h(node.left), h(node.right));
        int bal = getBalance(node);

        // LL
        if (bal > 1 && key < node.left.key)   return rightRotate(node);
        // RR
        if (bal < -1 && key > node.right.key) return leftRotate(node);
        // LR
        if (bal > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (bal < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /* ========= 測試工具 ========= */
    private static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.key + " ");
        inorder(n.right);
    }

    private static void testRotation(int[] keys, String title) {
        Node root = null;
        for (int k : keys) root = insert(root, k);
        System.out.println("=== " + title + " ===");
        System.out.println("插入序列: " + java.util.Arrays.toString(keys));
        System.out.println("根節點: " + root.key);
        System.out.print("中序結果: "); inorder(root); System.out.println();
        System.out.println("平衡因子(root): " + getBalance(root));
        System.out.println();
    }

    public static void main(String[] args) {
        testRotation(new int[]{30, 20, 10}, "LL → 右旋 (Right Rotate)");
        testRotation(new int[]{10, 20, 30}, "RR → 左旋 (Left Rotate)");
        testRotation(new int[]{30, 10, 20}, "LR → 左右旋 (Left-Right Rotate)");
        testRotation(new int[]{10, 30, 20}, "RL → 右左旋 (Right-Left Rotate)");
    }
}
