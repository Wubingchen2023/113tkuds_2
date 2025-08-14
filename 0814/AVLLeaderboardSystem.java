import java.util.*;

/**
 * 排行榜（分數高者名次前）
 * 鍵排序：score DESC，若同分以 playerId ASC
 * 中序走訪順序即為名次 1, 2, 3, ...
 */
public class AVLLeaderboardSystem {

    /* ---------- 封裝玩家分數 ---------- */
    public static class PlayerScore {
        public final int playerId;
        public final int score;
        public PlayerScore(int playerId, int score) {
            this.playerId = playerId;
            this.score = score;
        }
        @Override public String toString() { return "(" + playerId + "," + score + ")"; }
    }

    /* ---------- 排序鍵 (唯一) ---------- */
    private static final class Key implements Comparable<Key> {
        final int score;
        final int playerId;
        Key(int score, int playerId) { this.score = score; this.playerId = playerId; }

        // 分數高者排前（當作「較小」），同分以 playerId 升冪
        @Override public int compareTo(Key o) {
            int c = Integer.compare(o.score, this.score); // 高分在前
            if (c != 0) return c;
            return Integer.compare(this.playerId, o.playerId);
        }
    }

    /* ---------- AVL 節點：帶 height + size ---------- */
    private static final class Node {
        Key key;
        int height = 1; // leaf=1, null=0
        int size   = 1; // 子樹節點數
        Node left, right;
        Node(Key k) { this.key = k; }
    }

    private Node root;
    // 快速定位玩家目前分數（支援 update/rank）
    private final Map<Integer, Integer> id2score = new HashMap<>();

    /* ================= 公開 API ================= */

    /* 1) 添加玩家分數（玩家不可重覆）。成功回傳 true；若已存在則 false。 */
    public boolean addPlayerScore(int playerId, int score) {
        if (id2score.containsKey(playerId)) return false;
        Key k = new Key(score, playerId);
        root = insert(root, k);
        id2score.put(playerId, score);
        return true;
    }

    /* 2) 更新玩家分數。成功回傳 true；不存在回傳 false。 */
    public boolean updatePlayerScore(int playerId, int newScore) {
        Integer old = id2score.get(playerId);
        if (old == null) return false;
        // 先刪除舊鍵，再插入新鍵
        root = delete(root, new Key(old, playerId));
        root = insert(root, new Key(newScore, playerId));
        id2score.put(playerId, newScore);
        return true;
    }

    /* 3) 查詢玩家排名（1 = 最高分）。不存在回傳 null。 */
    public Integer getPlayerRank(int playerId) {
        Integer score = id2score.get(playerId);
        if (score == null) return null;
        return rank(root, new Key(score, playerId));
    }

    /* 4) 查詢前 K 名玩家（若 K > 人數則回傳全部）。 */
    public List<PlayerScore> topK(int k) {
        k = Math.max(0, Math.min(k, size(root)));
        List<PlayerScore> ans = new ArrayList<>(k);
        // 透過 order-statistics select 取第 1..k 名
        for (int i = 1; i <= k; i++) {
            Key key = select(root, i);
            ans.add(new PlayerScore(key.playerId, key.score));
        }
        return ans;
    }

    /* （可選）輸出前 N 名（偵錯用） */
    public void printTopN(int n) {
        List<PlayerScore> t = topK(n);
        System.out.println("Top " + n + ": " + t);
    }

    /* ================= AVL 基礎與維護 ================= */

    private static int height(Node n) { return (n == null) ? 0 : n.height; }
    private static int size(Node n)   { return (n == null) ? 0 : n.size;   }

    private static void pull(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
        n.size   = 1 + size(n.left) + size(n.right);
    }

    private static int balance(Node n) { return height(n.left) - height(n.right); }

    private static Node rightRotate(Node y) {
        Node x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        pull(y); pull(x);
        return x;
    }

    private static Node leftRotate(Node x) {
        Node y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        pull(x); pull(y);
        return y;
    }

    private static Node rebalance(Node n) {
        int bf = balance(n);
        if (bf > 1) {
            if (balance(n.left) < 0) n.left = leftRotate(n.left); // LR
            return rightRotate(n);                                // LL
        }
        if (bf < -1) {
            if (balance(n.right) > 0) n.right = rightRotate(n.right); // RL
            return leftRotate(n);                                      // RR
        }
        return n;
    }

    private static Node insert(Node node, Key key) {
        if (node == null) return new Node(key);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        } else {
            // 不會發生：Key 唯一（playerId 決定 tie），保險起見保留
            node.key = key;
            return node;
        }
        pull(node);
        return rebalance(node);
    }

    private static Node minNode(Node n) {
        while (n.left != null) n = n.left;
        return n;
    }

    private static Node delete(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = delete(node.left, key);
        else if (cmp > 0) node.right = delete(node.right, key);
        else {
            // 命中
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                // 取右子樹中的最小（在本排序下=下一名）
                Node succ = minNode(node.right);
                node.key = succ.key;
                node.right = delete(node.right, succ.key);
            }
        }
        if (node == null) return null;
        pull(node);
        return rebalance(node);
    }

    /* ================= Order-Statistics：rank / select ================= */

    /** rank：回傳名次（1-based），中序走訪的索引。 */
    private static Integer rank(Node node, Key key) {
        int rank = 0;
        Node cur = node;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {                 // 在左邊（更前面）
                cur = cur.left;
            } else if (cmp > 0) {          // 在右邊（更後面）
                rank += size(cur.left) + 1;
                cur = cur.right;
            } else {                        // 命中
                rank += size(cur.left) + 1;
                return rank;
            }
        }
        return null; // 未找到
    }

    /** select：回傳第 k 名（1-based）的 Key。假設 1 <= k <= size(root) */
    private static Key select(Node node, int k) {
        Node cur = node;
        while (cur != null) {
            int leftSize = size(cur.left);
            if (k == leftSize + 1) return cur.key;
            if (k <= leftSize) cur = cur.left;
            else {
                k -= leftSize + 1;
                cur = cur.right;
            }
        }
        throw new IllegalArgumentException("k out of range");
    }

    public static void main(String[] args) {
        AVLLeaderboardSystem lb = new AVLLeaderboardSystem();

        // 新增玩家分數
        lb.addPlayerScore(101, 2500);
        lb.addPlayerScore(102, 3200);
        lb.addPlayerScore(103, 3200); // 同分，用 playerId 決定順序：102 在 103 前
        lb.addPlayerScore(104, 1800);
        lb.addPlayerScore(105, 2900);

        System.out.println("初始 Top 5: " + lb.topK(5));
        System.out.println("Rank of 102: " + lb.getPlayerRank(102)); // 1
        System.out.println("Rank of 103: " + lb.getPlayerRank(103)); // 2
        System.out.println("Rank of 105: " + lb.getPlayerRank(105)); // 3

        // 更新分數
        lb.updatePlayerScore(104, 3300);   // 104 躍升為第一
        lb.updatePlayerScore(101, 3200);   // 101 與 102/103 同分，按 id 排序：101 在 102 前？

        System.out.println("更新後 Top 5: " + lb.topK(5));
        System.out.println("Rank of 104: " + lb.getPlayerRank(104)); // 1
        System.out.println("Rank of 101: " + lb.getPlayerRank(101));
        System.out.println("Rank of 102: " + lb.getPlayerRank(102));
        System.out.println("Rank of 103: " + lb.getPlayerRank(103));

        // 取前 K 名
        System.out.println("Top 3: " + lb.topK(3));
        System.out.println("Top 10(多於人數自動裁切): " + lb.topK(10));
    }
}
