import java.util.*;

class M09_AVLValidate {
    // 定義樹節點
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    public static String validate(int[] values) {  
        if (values.length == 0) {
            return "Valid";
        }
        
        TreeNode root = buildTree(values);
        
        if (!isBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            return "Invalid BST";
        }
        
        if (!isAVL(root)) {
            return "Invalid AVL";
        }
        
        return "Valid";
    }

    // 檢查是否為BST的遞迴函式
    private static boolean isBST(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        return isBST(node.left, min, node.val) && isBST(node.right, node.val, max);
    }
    
    // 檢查是否為AVL的遞迴函式
    private static boolean isAVL(TreeNode node) {
        return getHeight(node) != -1;
    }

    // 後序遍歷計算高度並檢查平衡因子
    private static int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        int leftHeight = getHeight(node.left);
        if (leftHeight == -1) {
            return -1; // 左子樹不平衡
        }
        
        int rightHeight = getHeight(node.right);
        if (rightHeight == -1) {
            return -1; // 右子樹不平衡
        }
        
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // 當前節點不平衡
        }
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // 根據層序陣列建樹
    private static TreeNode buildTree(int[] values) {
        if (values.length == 0 || values[0] == -1) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (i < values.length) {
            TreeNode current = queue.poll();
            
            // 處理左子樹
            if (i < values.length && values[i] != -1) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            i++;
            
            // 處理右子樹
            if (i < values.length && values[i] != -1) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
            i++;
        }
        return root;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
        sc.close();

        System.out.println(validate(values));
    }
}

/*
Time Complexity：
- 建樹：每個輸入值最多處理一次 ⇒ O(n)
- 檢 BST：遞迴每節點恰一次 ⇒ O(n)
- 檢 AVL：後序每節點恰一次 ⇒ O(n)
- 總時間：O(n)

Space Complexity：
- 儲存樹節點：O(n)
- 遞迴深度：最壞 O(n)，平衡情況 O(log n)
- 佇列建樹用：最壞 O(n)
*/