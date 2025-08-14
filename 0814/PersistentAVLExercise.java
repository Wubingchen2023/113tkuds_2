// 檔案：PersistentAVLExercise.java
import java.util.*;

/**
 * 持久化 AVL 樹（整數鍵）
 * - 不可變節點 (final)，路徑複製
 * - 每次插入產生新版本
 * - 版本之間共享未變動之子樹
 */
public class PersistentAVLExercise {

    /* ==================== 不可變節點定義 ==================== */
    private static final class Node {
        final int key;
        final int height;   // leaf=1, null=0
        final Node left;
        final Node right;

        private Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(h(left), h(right));
        }

        static Node of(int key, Node left, Node right) {
            return new Node(key, left, right);
        }
    }

    /* ==================== 版本管理 ==================== */
    // versions.get(i) = 第 i 版的根節點；0 版為空樹
    private final ArrayList<Node> versions = new ArrayList<>();

    public PersistentAVLExercise() {
        versions.add(null); // version 0 = empty
    }

    /** 回傳目前最新版本索引（0-based） */
    public int latestVersion() { return versions.size() - 1; }

    /** 取得某版的節點數（以中序計數；示範用途） */
    public int size(int version) {
        Node r = rootOf(version);
        return count(r);
    }

    /* ==================== Public APIs ==================== */

    /**
     * 插入 key 並產生新版本；回傳新版本的索引。
     * 若 key 已存在，結構不變（新版本根節點會直接共享舊根）。
     */
    public int insert(int baseVersion, int key) {
        Node baseRoot = rootOf(baseVersion);
        Node newRoot = insertPersistent(baseRoot, key);
        versions.add(newRoot);
        return latestVersion();
    }

    /** 於指定版本搜尋 key 是否存在 */
    public boolean search(int version, int key) {
        Node cur = rootOf(version);
        while (cur != null) {
            if (key == cur.key) return true;
            cur = (key < cur.key) ? cur.left : cur.right;
        }
        return false;
    }

    /** 回傳指定版本的高度（空樹=0） */
    public int height(int version) {
        return h(rootOf(version));
    }

    /** 列印指定版本的中序走訪（排序） */
    public void inorderPrint(int version) {
        inorder(rootOf(version));
        System.out.println();
    }

    /* ==================== 內部：AVL 基礎 ==================== */
    private static int h(Node n) { return (n == null) ? 0 : n.height; }
    private static int bf(Node n) { return (n == null) ? 0 : h(n.left) - h(n.right); }

    // 不可變「旋轉」：產生新節點，並盡量重用既有子樹
    private static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = (x == null) ? null : x.right;

        // x 成為新根；y 成為 x.right
        Node newY = Node.of(y.key, y.left == null ? null : T2, y.right); // 先臨時，待修正
        // 注意：newY 的左子應是 T2，右子是原 y.right，key 是 y.key
        newY = Node.of(y.key, T2, y.right);

        Node newX = Node.of(x.key, x.left, newY);
        return newX;
    }

    private static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = (y == null) ? null : y.left;

        Node newX = Node.of(x.key, x.left, T2);
        Node newY = Node.of(y.key, newX, y.right);
        return newY;
    }

    // 建構一個新節點（不可變），並做平衡（回傳新根）
    private static Node rebalance(Node n) {
        int balance = bf(n);
        if (balance > 1) { // 左重
            if (bf(n.left) < 0) { // LR
                Node newLeft = leftRotate(n.left);
                n = Node.of(n.key, newLeft, n.right);
            }
            return rightRotate(n); // LL
        }
        if (balance < -1) { // 右重
            if (bf(n.right) > 0) { // RL
                Node newRight = rightRotate(n.right);
                n = Node.of(n.key, n.left, newRight);
            }
            return leftRotate(n); // RR
        }
        return n;
    }

    // 不可變插入（路徑複製）：沿路建立新節點；沒變的分支直接共享
    private static Node insertPersistent(Node node, int key) {
        if (node == null) return Node.of(key, null, null);

        if (key < node.key) {
            Node newLeft = insertPersistent(node.left, key);
            if (newLeft == node.left) return node; // key 已存在（未變）
            Node rebuilt = Node.of(node.key, newLeft, node.right);
            return rebalance(rebuilt);
        } else if (key > node.key) {
            Node newRight = insertPersistent(node.right, key);
            if (newRight == node.right) return node; // key 已存在（未變）
            Node rebuilt = Node.of(node.key, node.left, newRight);
            return rebalance(rebuilt);
        } else {
            // duplicate：直接共享舊節點（不產生新節點）
            return node;
        }
    }

    /* ==================== 便利/除錯 ==================== */
    private Node rootOf(int version) {
        if (version < 0 || version >= versions.size()) {
            throw new IllegalArgumentException("Invalid version index: " + version);
        }
        return versions.get(version);
    }

    private static int count(Node n) {
        if (n == null) return 0;
        return 1 + count(n.left) + count(n.right);
    }

    private static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.key + " ");
        inorder(n.right);
    }

    // 取出指定版本中「路徑上第一個 >= key」的節點參考（示範共享用，可能為 null）
    private Node findNodeRefGE(int version, int key) {
        Node cur = rootOf(version);
        Node cand = null;
        while (cur != null) {
            if (cur.key >= key) {
                cand = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return cand;
    }

    /* ==================== Demo ==================== */
    public static void main(String[] args) {
        PersistentAVLExercise pavl = new PersistentAVLExercise();

        int v0 = 0; // 空樹
        int v1 = pavl.insert(v0, 20);
        int v2 = pavl.insert(v1, 4);
        int v3 = pavl.insert(v2, 15);  // 可能觸發旋轉
        int v4 = pavl.insert(v3, 70);
        int v5 = pavl.insert(v4, 50);
        int v6 = pavl.insert(v5, 100);
        int v7 = pavl.insert(v6, 80);
        int v8 = pavl.insert(v7, 90);

        System.out.println("版本索引: v0..v8 = " + v0 + ".." + v8);
        System.out.print("v3 中序: "); pavl.inorderPrint(v3);
        System.out.print("v8 中序: "); pavl.inorderPrint(v8);
        System.out.println("v3 高度: " + pavl.height(v3));
        System.out.println("v8 高度: " + pavl.height(v8));
        System.out.println("v3 是否含 70? " + pavl.search(v3, 70)); // false
        System.out.println("v8 是否含 70? " + pavl.search(v8, 70)); // true

        // 示範「共享不變節點」：比較 v7 與 v8 在某個未受影響的子樹的節點參考
        Node a = pavl.findNodeRefGE(v7, 20); // v7 路徑上的某節點
        Node b = pavl.findNodeRefGE(v8, 20); // v8 同一路徑上的對應節點
        if (a != null && b != null) {
            System.out.println("v7 與 v8 是否共享該節點? " + (a == b));
            System.out.println("identity v7: " + System.identityHashCode(a)
                             + " | v8: " + System.identityHashCode(b));
        }
    }
}
