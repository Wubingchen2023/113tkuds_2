public class AVLRotations {

    // 內部節點類別：提供 key、left/right、height 以及 updateHeight()
    static class AVLNode {
        int key;
        AVLNode left, right;
        int height;

        AVLNode(int key) {
            this.key = key;
            this.height = 1; // 單節點高度從 1 開始
        }

        void updateHeight() {
            int lh = heightOf(left);
            int rh = heightOf(right);
            this.height = Math.max(lh, rh) + 1;
        }

        static int heightOf(AVLNode n) {
            return (n == null) ? 0 : n.height;
        }

        int balanceFactor() {
            return heightOf(left) - heightOf(right);
        }

        @Override
        public String toString() {
            return "Node(" + key + ", h=" + height + ", bf=" + balanceFactor() + ")";
        }
    }

    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = (x != null) ? x.right : null;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度（先更新子、再更新父）
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = (y != null) ? y.left : null;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度（先更新子、再更新父）
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    // ——— 測試用：前序列印樹與高度/平衡因子 ———
    private static void preorderPrint(AVLNode root) {
        if (root == null) return;
        System.out.println(root);
        preorderPrint(root.left);
        preorderPrint(root.right);
    }

    public static void main(String[] args) {
        // 建立一棵會觸發右旋的樹：
        //     y(30)
        //    /
        //  x(20)
        //  /
        // 10
        AVLNode y = new AVLNode(30);
        AVLNode x = new AVLNode(20);
        AVLNode z = new AVLNode(10);

        y.left = x;
        x.left = z;

        // 初始化高度
        z.updateHeight();
        x.updateHeight();
        y.updateHeight();

        System.out.println("=== 原始樹 (前序) ===");
        preorderPrint(y);

        // 右旋：以 y 為支點
        AVLNode rootAfterRight = rightRotate(y);
        System.out.println("\n=== 右旋後 (前序) ===");
        preorderPrint(rootAfterRight);

        // 再做一次左旋恢復：
        AVLNode rootAfterLeft = leftRotate(rootAfterRight);
        System.out.println("\n=== 左旋後 (前序) ===");
        preorderPrint(rootAfterLeft);
    }
}
