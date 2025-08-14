public class AVLTree {
    private AVLNode root;

    // 取得節點高度
    private int getHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }

    // 插入節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void insert(int data) {
        root = insertNode(root, data);
    }

    private AVLNode insertNode(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }

        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node; // 重複值不插入
        }

        // 2. 更新高度
        node.updateHeight();

        // 3. 檢查平衡因子
        int balance = node.getBalance();

        // 4. 處理不平衡情況
        // Left Left 情況
        if (balance > 1 && data < node.left.data) {
            return AVLRotations.rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && data > node.left.data) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && data < node.right.data) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    // 搜尋節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public boolean search(int data) {
        return searchNode(root, data);
    }

    private boolean searchNode(AVLNode node, int data) {
        if (node == null) return false;
        if (data == node.data) return true;
        if (data < node.data) return searchNode(node.left, data);
        return searchNode(node.right, data);
    }

    // 找最小值節點
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 刪除節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void delete(int data) {
        root = deleteNode(root, data);
    }

    private AVLNode deleteNode(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) return null;

        if (data < node.data) {
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                // 有 0 或 1 個子節點
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    // 沒有子節點
                    node = null;
                } else {
                    // 有一個子節點，直接用該子節點取代
                    node = temp;
                }
            } else {
                // 有兩個子節點：用右子樹最小節點取代
                AVLNode temp = findMin(node.right);
                node.data = temp.data;
                node.right = deleteNode(node.right, temp.data);
            }
        }

        // 如果刪除之後成為 null，直接回傳
        if (node == null) return null;

        // 2. 更新高度
        node.updateHeight();

        // 3. 檢查平衡因子並修復
        int balance = node.getBalance();

        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return AVLRotations.rightRotate(node);
        }

        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }

        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return AVLRotations.leftRotate(node);
        }

        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }

        return node;
    }

    // 驗證是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVL(root) != -1;
    }

    private int checkAVL(AVLNode node) {
        if (node == null) return 0;

        int leftHeight = checkAVL(node.left);
        int rightHeight = checkAVL(node.right);

        if (leftHeight == -1 || rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // 列印樹狀結構（中序，顯示平衡因子）
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }

    // ----- AVLNode 類別 -----
    private static class AVLNode {
        int data;
        AVLNode left, right;
        int height;

        AVLNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1; // leaf 的高度為 1
        }

        // 更新高度（根據子節點的 height）
        void updateHeight() {
            int lh = (left != null) ? left.height : 0;
            int rh = (right != null) ? right.height : 0;
            this.height = Math.max(lh, rh) + 1;
        }

        // 平衡因子 = 左子樹高度 - 右子樹高度
        int getBalance() {
            int lh = (left != null) ? left.height : 0;
            int rh = (right != null) ? right.height : 0;
            return lh - rh;
        }
    }

    // ----- AVL 旋轉工具 -----
    private static class AVLRotations {
        // 右旋 (y = root, x = y.left)
        static AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            // 執行旋轉
            x.right = y;
            y.left = T2;

            // 更新高度（先 y 再 x）
            y.updateHeight();
            x.updateHeight();

            return x; // 新的根
        }

        // 左旋 (x = root, y = x.right)
        static AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            // 執行旋轉
            y.left = x;
            x.right = T2;

            // 更新高度（先 x 再 y）
            x.updateHeight();
            y.updateHeight();

            return y; // 新的根
        }
    }

    // ----- main 範例 -----
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // 插入示範
        int[] values = {10, 20, 30, 40, 50, 25};
        System.out.print("插入順序: ");
        for (int v : values) {
            System.out.print(v + " ");
            tree.insert(v);
        }
        System.out.println();

        System.out.print("中序列印 (value(balance)): ");
        tree.printTree();

        System.out.println("是否為有效 AVL? " + tree.isValidAVL());

        // 搜尋
        System.out.println("搜尋 25: " + tree.search(25)); // true
        System.out.println("搜尋 100: " + tree.search(100)); // false

        // 刪除
        System.out.println("刪除 40");
        tree.delete(40);
        System.out.print("刪除後中序列印: ");
        tree.printTree();
        System.out.println("是否為有效 AVL? " + tree.isValidAVL());
        System.out.println("搜尋 40: " + tree.search(40));
    }
}
