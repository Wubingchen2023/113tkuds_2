public class AVLBasicExercise {

    // 節點結構
    private static class Node {
        int key;
        int height; // 節點高度 (leaf = 1)
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    private Node root = null;

    /* --------------------
       公開接口
       -------------------- */
    // 插入（對外呼叫）
    public void insert(int key) {
        root = insert(root, key);
    }

    // 搜尋
    public boolean search(int key) {
        return search(root, key);
    }

    // 取得樹高度 (空樹回傳 0)
    public int height() {
        return nodeHeight(root);
    }

    // 檢查是否為有效 AVL（每個節點平衡因子 ∈ [-1,1]）
    public boolean isAVL() {
        return checkIsAVL(root);
    }

    // 中序走訪印出（驗證）
    public void inorderPrint() {
        inorderPrint(root);
        System.out.println();
    }

    /* --------------------
       內部實作（AVL 插入 + 旋轉）
       -------------------- */
    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            // 重複鍵：此實作直接忽略（也可改為更新值）
            return node;
        }

        // 更新高度
        node.height = 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));

        // 計算平衡因子
        int balance = getBalance(node);

        // 四種不平衡情況：LL, RR, LR, RL
        // LL
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }
        // RR
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }
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

        // 其餘情況，節點已平衡
        return node;
    }

    // 右旋 (y 的左子樹過重)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // 旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.height = 1 + Math.max(nodeHeight(y.left), nodeHeight(y.right));
        x.height = 1 + Math.max(nodeHeight(x.left), nodeHeight(x.right));

        return x; // 新根
    }

    // 左旋 (x 的右子樹過重)
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // 旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.height = 1 + Math.max(nodeHeight(x.left), nodeHeight(x.right));
        y.height = 1 + Math.max(nodeHeight(y.left), nodeHeight(y.right));

        return y; // 新根
    }

    // 取得節點高度（null -> 0）
    private int nodeHeight(Node n) {
        return (n == null) ? 0 : n.height;
    }

    // 計算平衡因子：height(left) - height(right)
    private int getBalance(Node n) {
        if (n == null) return 0;
        return nodeHeight(n.left) - nodeHeight(n.right);
    }

    /* --------------------
       其他輔助方法
       -------------------- */
    private boolean search(Node node, int key) {
        if (node == null) return false;
        if (key == node.key) return true;
        return key < node.key ? search(node.left, key) : search(node.right, key);
    }

    private void inorderPrint(Node node) {
        if (node == null) return;
        inorderPrint(node.left);
        System.out.print(node.key + " ");
        inorderPrint(node.right);
    }

    // 檢查每個節點的平衡因子是否在 [-1, 1] 範圍內
    // 使用遞迴計算每個子樹高度（不依賴 Node.height），以驗證正確性
    private boolean checkIsAVL(Node node) {
        if (node == null) return true;
        int lh = computeHeight(node.left);
        int rh = computeHeight(node.right);
        int bf = lh - rh;
        if (bf < -1 || bf > 1) return false;
        // 再遞迴檢查左右子樹
        return checkIsAVL(node.left) && checkIsAVL(node.right);
    }

    // 遞迴計算高度（leaf = 1, null = 0）
    private int computeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(computeHeight(node.left), computeHeight(node.right));
    }

    public static void main(String[] args) {
        AVLBasicExercise avl = new AVLBasicExercise();

        // 範例：插入一組數字（任意順序）
        int[] keys = {20, 4, 15, 70, 50, 100, 80, 90};
        System.out.print("插入順序: ");
        for (int k : keys) {
            System.out.print(k + " ");
            avl.insert(k);
        }
        System.out.println();

        // 中序走訪（應為排序）
        System.out.print("中序走訪 (sorted): ");
        avl.inorderPrint();

        // 搜尋測試
        System.out.println("搜尋 15? " + avl.search(15)); // true
        System.out.println("搜尋 99? " + avl.search(99)); // false

        // 樹高
        System.out.println("樹高度: " + avl.height());

        // 是否為 AVL
        System.out.println("isAVL? " + avl.isAVL());
    }
}
