import java.util.*;

public class AVLDeleteExercise {

    /* ---------- 節點定義 ---------- */
    private static class Node {
        int key;
        int height; // 定義：leaf=1, null=0
        Node left, right;

        Node(int k) {
            key = k;
            height = 1;
        }
    }

    private Node root;

    /* ---------- 公開 API ---------- */
    public void insert(int key) { root = insert(root, key); }

    public void delete(int key) { root = delete(root, key); }

    public boolean search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return true;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        return false;
    }

    public int height() { return h(root); }

    public boolean isAVL() { return isAVL(root).ok; }

    public void inorderPrint() { inorder(root); System.out.println(); }

    public void levelOrderPrint() {
        if (root == null) { System.out.println("(empty)"); return; }
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int sz = q.size();
            for (int i = 0; i < sz; i++) {
                Node n = q.poll();
                System.out.print(n.key + "(h=" + n.height + ",bf=" + bf(n) + ") ");
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            System.out.println();
        }
    }

    /* ---------- 內部：基本工具 ---------- */
    private static int h(Node n) { return n == null ? 0 : n.height; }

    private static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }

    private static void update(Node n) {
        n.height = 1 + Math.max(h(n.left), h(n.right));
    }

    private static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // 旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        update(y);
        update(x);
        return x;
    }

    private static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // 旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        update(x);
        update(y);
        return y;
    }

    private static Node minValueNode(Node n) {
        Node cur = n;
        while (cur.left != null) cur = cur.left;
        return cur;
    }

    /* ---------- 內部：插入 (觸發旋轉) ---------- */
    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node; // 重複鍵：忽略（也可改為更新值）

        update(node);
        int balance = bf(node);

        // LL
        if (balance > 1 && key < node.left.key) return rightRotate(node);
        // RR
        if (balance < -1 && key > node.right.key) return leftRotate(node);
        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /* ---------- 內部：刪除 (含重平衡) ---------- */
    private Node delete(Node node, int key) {
        if (node == null) return null;

        // 1) 標準 BST 刪除
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // 命中節點
            // a) 葉子節點 或 b) 只有一個子節點
            if (node.left == null || node.right == null) {
                Node child = (node.left != null) ? node.left : node.right;
                node = child; // 可能為 null（葉子直接刪）
            } else {
                // c) 兩個子節點：用「後繼」(右子樹最小)替代（也可改用前驅）
                Node succ = minValueNode(node.right);
                node.key = succ.key;
                // 刪除右子樹中的後繼
                node.right = delete(node.right, succ.key);
            }
        }

        // 刪完成為空子樹
        if (node == null) return null;

        // 2) 回溯更新高度
        update(node);

        // 3) 檢查平衡並旋轉
        int balance = bf(node);

        // LL（左子樹過重）
        if (balance > 1) {
            if (bf(node.left) >= 0) {
                // LL
                return rightRotate(node);
            } else {
                // LR
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // RR（右子樹過重）
        if (balance < -1) {
            if (bf(node.right) <= 0) {
                // RR
                return leftRotate(node);
            } else {
                // RL
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    /* ---------- 內部：驗證/輸出 ---------- */
    private static class Check {
        boolean ok; int height;
        Check(boolean ok, int h) { this.ok = ok; this.height = h; }
    }
    // 自底向上檢查：回傳 (是否AVL, 高度)
    private static Check isAVL(Node n) {
        if (n == null) return new Check(true, 0);
        Check L = isAVL(n.left), R = isAVL(n.right);
        boolean ok = L.ok && R.ok && Math.abs(L.height - R.height) <= 1;
        int height = 1 + Math.max(L.height, R.height);
        return new Check(ok, height);
    }

    private static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.key + " ");
        inorder(n.right);
    }

    /* ---------- 三種刪除情境 ---------- */
    public static void main(String[] args) {
        AVLDeleteExercise avl = new AVLDeleteExercise();

        // 建一棵樹，安排後續能示範三種刪除
        // 結構大致：
        //           30
        //        /      \
        //      20        40
        //     /  \     /    \
        //   10   25  35     50
        //  /  \              /
        // 5   15           45
        int[] keys = {30,20,40,10,25,35,50,5,15,45};
        for (int k : keys) avl.insert(k);

        System.out.println("初始中序：");
        avl.inorderPrint();
        System.out.println("初始層序(節點高度/平衡因子)：");
        avl.levelOrderPrint();
        System.out.println("isAVL? " + avl.isAVL());
        System.out.println();

        // 【情況二：刪除只有一個子節點的節點】刪除 50（其左子 45）
        System.out.println("刪除(一子) 50：");
        avl.delete(50);
        avl.inorderPrint();
        avl.levelOrderPrint();
        System.out.println("isAVL? " + avl.isAVL());
        System.out.println();

        // 【情況一：刪除葉子節點】刪除 45（此時是 40 的右子，且無子樹）
        System.out.println("刪除(葉子) 45：");
        avl.delete(45);
        avl.inorderPrint();
        avl.levelOrderPrint();
        System.out.println("isAVL? " + avl.isAVL());
        System.out.println();

        // 【情況三：刪除兩個子節點的節點】刪除 20（有左右子 10 與 25）
        System.out.println("刪除(兩子) 20：");
        avl.delete(20);
        avl.inorderPrint();
        avl.levelOrderPrint();
        System.out.println("isAVL? " + avl.isAVL());
        System.out.println();

        // 額外：查詢示範
        System.out.println("搜尋 25? " + avl.search(25));
        System.out.println("搜尋 20? " + avl.search(20));
        System.out.println("樹高度: " + avl.height());
    }
}
