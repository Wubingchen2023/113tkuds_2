import java.io.*;
import java.util.*;

public class M10_RBPropertiesCheck {

    // 節點資料結構
    static class Node {
        int val;
        char color; // 'R' 或 'B'
        Node left, right;
        int index; // 層序索引，用於錯誤報告
        Node(int val, char color, int index) {
            this.val = val;
            this.color = color;
            this.index = index;
        }
    }

    static boolean isValid = true;
    static String violationMessage = "";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀入節點數量
        int n = Integer.parseInt(br.readLine().trim());

        // 讀入每個節點 (val color)
        StringTokenizer st = new StringTokenizer(br.readLine());
        Node[] tree = new Node[n];

        for (int i = 0; i < n; i++) {
            String valStr = st.nextToken();
            String colorStr = st.nextToken();

            int val = Integer.parseInt(valStr);
            char color = colorStr.charAt(0);

            if (val != -1) {
                tree[i] = new Node(val, color, i);
            }
        }

        // 建立連結：left = 2i+1, right = 2i+2
        for (int i = 0; i < n; i++) {
            Node cur = tree[i];
            if (cur == null) continue;
            int li = 2 * i + 1, ri = 2 * i + 2;
            if (li < n) cur.left = tree[li];
            if (ri < n) cur.right = tree[ri];
        }

        Node root = tree[0];

        // 檢查性質 1：根為黑
        if (root == null || root.color != 'B') {
            System.out.println("RootNotBlack");
            return;
        }

        // 檢查性質 2 & 3：紅紅、黑高度一致
        isValid = true;
        checkProperties(root);

        if (!isValid) {
            System.out.println(violationMessage);
        } else {
            System.out.println("RB Valid");
        }
    }

    // 回傳該子樹的黑高度；若發現錯誤則設 isValid = false 並記錄錯誤訊息
    static int checkProperties(Node node) {
        if (node == null) return 1; // null 節點視為黑色，黑高度 +1

        int leftBlackHeight = checkProperties(node.left);
        int rightBlackHeight = checkProperties(node.right);

        if (!isValid) return -1; // 已發現錯誤，早停

        // 性質 2：紅節點不可有紅子
        if (node.color == 'R') {
            if ((node.left != null && node.left.color == 'R') ||
                (node.right != null && node.right.color == 'R')) {
                isValid = false;
                violationMessage = "RedRedViolation at index " + node.index;
                return -1;
            }
        }

        // 性質 3：黑高度一致
        if (leftBlackHeight != rightBlackHeight) {
            isValid = false;
            violationMessage = "BlackHeightMismatch";
            return -1;
        }

        // 回傳目前子樹的黑高度
        return leftBlackHeight + (node.color == 'B' ? 1 : 0);
    }
}