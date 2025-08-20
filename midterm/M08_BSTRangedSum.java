import java.util.*;

// 定義二元樹節點
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class M08_BSTRangedSum {

    // 根據層序遍歷的輸入建構二元樹
    public static TreeNode buildTree(String[] values) {
        if (values.length == 0 || values[0].equals("-1")) {
            return null;
        }

        // 建立根節點
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;

        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();

            // 建立左子節點
            if (i < values.length && !values[i].equals("-1")) {
                current.left = new TreeNode(Integer.parseInt(values[i]));
                queue.add(current.left);
            }
            i++;

            // 建立右子節點
            if (i < values.length && !values[i].equals("-1")) {
                current.right = new TreeNode(Integer.parseInt(values[i]));
                queue.add(current.right);
            }
            i++;
        }
        return root;
    }

    // 計算 BST 區間總和的遞迴方法
    public static int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }

        int sum = 0;

        // 如果當前節點值在區間 [L, R] 內，則將其加入總和
        if (root.val >= L && root.val <= R) {
            sum += root.val;
        }

        // 遞迴地處理子樹
        // 解題 Tip: 若節點值 > R，只需走左子樹；< L 只需走右子樹
        // 如果節點值大於 L，則其左子樹可能還有值在區間內
        if (root.val > L) {
            sum += rangeSumBST(root.left, L, R);
        }
        // 如果節點值小於 R，則其右子樹可能還有值在區間內
        if (root.val < R) {
            sum += rangeSumBST(root.right, L, R);
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀取 n，但本題不需用到 n
        int n = sc.nextInt();
        sc.nextLine(); // 消耗換行符

        // 讀取樹的層序遍歷字串
        String[] values = sc.nextLine().split(" ");
        TreeNode root = buildTree(values);

        // 讀取 L 和 R
        int L = sc.nextInt();
        int R = sc.nextInt();

        // 計算區間總和
        int sum = rangeSumBST(root, L, R);

        // 輸出結果
        System.out.println("Sum: " + sum);

        sc.close();
    }
}