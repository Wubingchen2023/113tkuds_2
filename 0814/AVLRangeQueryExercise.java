import java.util.*;

public class AVLRangeQueryExercise {

    /* ========= 節點定義 ========= */
    private static class Node {
        int key, height = 1;  // 定義：leaf = 1, null = 0
        Node left, right;
        Node(int k) { key = k; }
    }

    private Node root;

    /* ========= 公開 API ========= */
    // 插入（AVL 自動旋轉）
    public void insert(int key) {
        root = insert(root, key);
    }

    // 範圍查詢：回傳所有位於 [min, max]（含端點）的鍵
    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> ans = new ArrayList<>();
        if (min > max) return ans; // 邊界：空結果
        rangeQuery(root, min, max, ans);
        return ans;
    }

    //（可選）中序列印，驗證排序
    public void inorderPrint() {
        inorder(root);
        System.out.println();
    }

    /* ========= 內部：AVL 插入/旋轉 ========= */
    private static int h(Node n) { return n == null ? 0 : n.height; }

    private static void update(Node n) { n.height = 1 + Math.max(h(n.left), h(n.right)); }

    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    private static Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        update(y); update(x);
        return x;
    }

    private static Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        update(x); update(y);
        return y;
    }

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node; // 重複鍵：忽略（或改成更新）

        update(node);
        int b = bf(node);

        // LL
        if (b > 1 && key < node.left.key)  return rightRotate(node);
        // RR
        if (b < -1 && key > node.right.key) return leftRotate(node);
        // LR
        if (b > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (b < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /* ========= 內部：範圍查詢 (含剪枝) ========= */
    private void rangeQuery(Node node, int min, int max, List<Integer> out) {
        if (node == null) return;

        // 只有當左子樹可能存在 >= min 的值時才走左邊
        if (node.key > min) {
            rangeQuery(node.left, min, max, out);
        }
        // 若當前節點落在區間內，加入結果
        if (node.key >= min && node.key <= max) {
            out.add(node.key);
        }
        // 只有當右子樹可能存在 <= max 的值時才走右邊
        if (node.key < max) {
            rangeQuery(node.right, min, max, out);
        }
    }

    private void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.key + " ");
        inorder(n.right);
    }

    public static void main(String[] args) {
        AVLRangeQueryExercise avl = new AVLRangeQueryExercise();
        int[] keys = {20, 4, 15, 70, 50, 100, 80, 90, 3, 5, 16, 60};
        for (int k : keys) avl.insert(k);

        System.out.print("中序（排序）：");
        avl.inorderPrint();

        // 測試範圍查詢（含端點）
        testRange(avl, 5, 50);     // 一般情形
        testRange(avl, 1, 2);      // 全空
        testRange(avl, 70, 100);   // 尾段
        testRange(avl, 16, 16);    // 單點
        testRange(avl, 101, 50);   // min > max（空）
    }

    private static void testRange(AVLRangeQueryExercise avl, int min, int max) {
        List<Integer> res = avl.rangeQuery(min, max);
        System.out.println("rangeQuery[" + min + "," + max + "] -> " + res);
    }
}
