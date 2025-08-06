import java.util.*;

public class MultiWayTreeAndDecisionTree {

    // ===== 1. 多路樹節點定義 =====
    static class MultiNode {
        String data;
        List<MultiNode> children;

        MultiNode(String data) {
            this.data = data;
            this.children = new ArrayList<>();
        }

        void addChild(MultiNode child) {
            children.add(child);
        }

        int getDegree() {
            return children.size();
        }
    }

    // ===== 2. DFS（前序）走訪 =====
    public static void dfs(MultiNode root) {
        if (root == null) return;
        System.out.print(root.data + " ");
        for (MultiNode child : root.children) {
            dfs(child);
        }
    }

    // ===== 3. BFS 走訪 =====
    public static void bfs(MultiNode root) {
        if (root == null) return;
        Queue<MultiNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MultiNode current = queue.poll();
            System.out.print(current.data + " ");
            queue.addAll(current.children);
        }
    }

    // ===== 4. 計算樹高 =====
    public static int getHeight(MultiNode root) {
        if (root == null) return 0;
        int maxHeight = 0;
        for (MultiNode child : root.children) {
            maxHeight = Math.max(maxHeight, getHeight(child));
        }
        return maxHeight + 1;
    }

    // ===== 5. 印出每個節點的度數 =====
    public static void printDegrees(MultiNode root) {
        if (root == null) return;
        System.out.println("節點: " + root.data + "，度數: " + root.getDegree());
        for (MultiNode child : root.children) {
            printDegrees(child);
        }
    }

    // ===== 6. 模擬簡易決策樹：猜數字遊戲 =====
    static class DecisionNode {
        String question;
        DecisionNode yesBranch;
        DecisionNode noBranch;

        DecisionNode(String question) {
            this.question = question;
        }
    }

    public static void playGuessNumber(DecisionNode node, Scanner scanner) {
        if (node.yesBranch == null && node.noBranch == null) {
            System.out.println("我猜的是：" + node.question);
            return;
        }
        System.out.println(node.question + " (yes/no)");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("yes")) {
            playGuessNumber(node.yesBranch, scanner);
        } else {
            playGuessNumber(node.noBranch, scanner);
        }
    }

    // ===== 主程式測試 =====
    public static void main(String[] args) {
        // === 建立一棵多路樹 ===
        MultiNode root = new MultiNode("A");
        MultiNode b = new MultiNode("B");
        MultiNode c = new MultiNode("C");
        MultiNode d = new MultiNode("D");
        MultiNode e = new MultiNode("E");
        MultiNode f = new MultiNode("F");

        root.addChild(b);
        root.addChild(c);
        root.addChild(d);
        b.addChild(e);
        b.addChild(f);

        System.out.println("【1】DFS 深度優先走訪:");
        dfs(root);
        System.out.println("\n\n【2】BFS 廣度優先走訪:");
        bfs(root);

        System.out.println("\n\n【3】樹的高度: " + getHeight(root));
        System.out.println("【4】各節點的度數:");
        printDegrees(root);

        System.out.println("\n【5】模擬簡單決策樹：猜數字遊戲");
        DecisionNode q1 = new DecisionNode("你想的數字 > 50 嗎？");
        q1.yesBranch = new DecisionNode("你想的數字是 75？");
        q1.noBranch = new DecisionNode("你想的數字是 25？");

        Scanner sc = new Scanner(System.in);
        playGuessNumber(q1, sc);
        sc.close();
    }
}
