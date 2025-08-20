import java.io.*;
import java.util.*;

public class M07_BinaryTreeLeftView {

    // 樹節點定義
    static class Node {
        int val;
        Node left, right;
        Node(int v) { this.val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 n
        int n = Integer.parseInt(br.readLine().trim());
        // 讀入接下來 n 個整數（可能分散多行）
        int[] level = readIntArray(br, n);

        // 建樹
        Node root = buildTreeFromLevelOrder(level);

        // 取左視圖
        List<Integer> leftView = getLeftView(root);

        // 輸出
        StringBuilder sb = new StringBuilder();
        sb.append("LeftView:");
        if (!leftView.isEmpty()) {
            sb.append(' ');
            for (int i = 0; i < leftView.size(); i++) {
                if (i > 0) sb.append(' ');
                sb.append(leftView.get(i));
            }
        }
        System.out.println(sb.toString());
    }

    // 將接下來的 token 累積到長度為 n 的整數陣列
    private static int[] readIntArray(BufferedReader br, int n) throws IOException {
        int[] arr = new int[n];
        int filled = 0;
        while (filled < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                arr[filled++] = Integer.parseInt(st.nextToken());
            }
        }
        return arr;
    }

    // 依層序（-1 表 null）建樹：只對非 null 節點讀取其左右子
    private static Node buildTreeFromLevelOrder(int[] level) {
        if (level.length == 0) return null;
        if (level[0] == -1) return null; // 空樹

        Node root = new Node(level[0]);
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);

        int idx = 1; // 指向下一個要消耗的層序值
        while (!q.isEmpty() && idx < level.length) {
            Node cur = q.poll();
            if (cur == null) continue; // 理論上不會放入 null，但防禦性保留

            // 左子節點
            if (idx < level.length) {
                int lv = level[idx++];
                if (lv != -1) {
                    cur.left = new Node(lv);
                    q.offer(cur.left);
                }
            }
            // 右子節點
            if (idx < level.length) {
                int rv = level[idx++];
                if (rv != -1) {
                    cur.right = new Node(rv);
                    q.offer(cur.right);
                }
            }
        }
        return root;
    }

    // BFS 取得左視圖：每層第 1 個出佇列者
    private static List<Integer> getLeftView(Node root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) return ans;

        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                if (i == 0) {
                    ans.add(cur.val); // 本層最左側
                }
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
        }
        return ans;
    }
}
