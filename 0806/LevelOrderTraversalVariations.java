import java.util.*;

public class LevelOrderTraversalVariations {

    // 定義二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { 
            this.val = val; 
        }
    }

    // 1. 每層節點分層存儲
    public static List<List<Integer>> levelOrderGrouped(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> current = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                current.add(node.val);
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(current);
        }

        return result;
    }

    // 2. Zigzag（之字形）層序走訪
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        boolean leftToRight = true;
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (leftToRight)
                    level.addLast(node.val);
                else
                    level.addFirst(node.val);

                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }

    // 3. 每層最後一個節點
    public static List<Integer> lastNodeEachLevel(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode last = null;

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                last = node;
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            if (last != null) result.add(last.val);
        }

        return result;
    }

    // 4. 垂直層序走訪（使用水平座標）
    static class Pair {
        TreeNode node;
        int col;
        Pair(TreeNode n, int c) {
            node = n;
            col = c;
        }
    }

    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));

        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            map.putIfAbsent(p.col, new ArrayList<>());
            map.get(p.col).add(p.node.val);

            if (p.node.left  != null) queue.offer(new Pair(p.node.left,  p.col - 1));
            if (p.node.right != null) queue.offer(new Pair(p.node.right, p.col + 1));
        }

        result.addAll(map.values());
        return result;
    }

    // 主程式
    public static void main(String[] args) {
        /*
                   1
                 /   \
                2     3
               / \   / \
              4   5 6   7
        */
        TreeNode root = new TreeNode(1);
        root.left  = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left   = new TreeNode(4);
        root.left.right  = new TreeNode(5);
        root.right.left  = new TreeNode(6);
        root.right.right = new TreeNode(7);

        // 1. 分層 List 儲存
        System.out.println("1. 每層節點分層儲存：");
        List<List<Integer>> grouped = levelOrderGrouped(root);
        grouped.forEach(System.out::println);

        // 2. Zigzag 層序走訪
        System.out.println("\n2. Zigzag（之字形）層序走訪：");
        List<List<Integer>> zigzag = zigzagLevelOrder(root);
        zigzag.forEach(System.out::println);

        // 3. 每層最後一個節點
        System.out.println("\n3. 每層最後一個節點：");
        List<Integer> last = lastNodeEachLevel(root);
        System.out.println(last);

        // 4. 垂直層序走訪
        System.out.println("\n4. 垂直層序走訪（Vertical Order）：");
        List<List<Integer>> vertical = verticalOrder(root);
        vertical.forEach(System.out::println);
    }
}
