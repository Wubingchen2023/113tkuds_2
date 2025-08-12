import java.util.*;

/** 會議室排程最佳化 */
public class MeetingRoomScheduler {

    /** ========== Part A：最少需要多少個會議室 ========== */
    // 思路：按開始時間排序，維護一個 min-heap 存各房間的「結束時間」。
    // 若下一場的開始 >= 最早結束時間，就可沿用該房間(彈出後再加入新的結束)；否則需開新房間。
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<Integer> endHeap = new PriorityQueue<>(); // 存結束時間
        int maxRooms = 0;

        for (int[] itv : intervals) {
            int start = itv[0], end = itv[1];
            // 釋放所有已經結束的會議室
            while (!endHeap.isEmpty() && endHeap.peek() <= start) {
                endHeap.poll();
            }
            endHeap.offer(end);
            maxRooms = Math.max(maxRooms, endHeap.size());
        }
        return maxRooms;
    }

    /** ========== Part B：只有 N 間會議室時，最大化總會議時間 ========== */
    // 目標：挑選一組區間，使得任何時間點同時進行的已選區間數 ≤ N，且總時長最大。
    // 事件按時間排序（同時刻先處理結束再處理開始，確保[4,6]可接在[1,4]之後）。
    // 掃描到「開始」時，把該會議加入 min-heap（以「會議時長」排序）。
    // 若超過 N 間，丟掉時長最短的那場（最不影響總時長）。
    // 掃描到「結束」時，若該會議仍「被保留」，就把它的時長納入答案。
    public static class Node {
        int id, start, end, dur;
        boolean chosen = false; // 是否仍被保留（未在衝突中被淘汰）
        Node(int id, int s, int e) { this.id = id; this.start = s; this.end = e; this.dur = Math.max(0, e - s); }
        @Override public String toString() { return "[" + start + "," + end + "]"; }
    }
    public static class Event {
        int t, type; // type: 0=end, 1=start（同時刻先 end 後 start）
        Node node;
        Event(int t, int type, Node node) { this.t = t; this.type = type; this.node = node; }
    }

    public static class ScheduleResult {
        int totalDuration;
        List<int[]> selected; // 被選的會議清單
        ScheduleResult(int totalDuration, List<int[]> selected) {
            this.totalDuration = totalDuration; this.selected = selected;
        }
    }

    public static ScheduleResult maximizeTotalDurationWithNRooms(int[][] intervals, int N) {
        if (intervals == null || intervals.length == 0 || N <= 0) {
            return new ScheduleResult(0, new ArrayList<>());
        }
        int n = intervals.length;
        Node[] nodes = new Node[n];
        List<Event> events = new ArrayList<>(2 * n);
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i, intervals[i][0], intervals[i][1]);
            events.add(new Event(nodes[i].end,   0, nodes[i])); // end first
            events.add(new Event(nodes[i].start, 1, nodes[i])); // then start
        }
        events.sort((a, b) -> {
            if (a.t != b.t) return Integer.compare(a.t, b.t);
            return Integer.compare(a.type, b.type); // end(0) before start(1)
        });

        // 以會議時長為 key 的小根堆：當同時進行數量 > N，就丟掉時長最短的
        PriorityQueue<Node> active = new PriorityQueue<>(Comparator.comparingInt(x -> x.dur));
        int total = 0;
        List<int[]> chosenIntervals = new ArrayList<>();

        for (Event e : events) {
            Node nd = e.node;
            if (e.type == 0) { // end
                // 若這場仍被保留，計入答案
                if (nd.chosen) {
                    total += nd.dur;
                    chosenIntervals.add(new int[]{nd.start, nd.end});
                }
                // 從 active 中移除此會議（若早已被淘汰，remove 會返回 false，無妨）
                active.remove(nd);
            } else { // start
                nd.chosen = true;
                active.offer(nd);
                if (active.size() > N) {
                    Node drop = active.poll(); // 丟掉最短的
                    drop.chosen = false;
                }
            }
        }
        return new ScheduleResult(total, chosenIntervals);
    }

    public static void main(String[] args) {
        int[][] a1 = {{0,30},{5,10},{15,20}};
        int[][] a2 = {{9,10},{4,9},{4,17}};
        int[][] a3 = {{1,5},{8,9},{8,9}};
        System.out.println("需要會議室數 (a1) = " + minMeetingRooms(a1)); // 2
        System.out.println("需要會議室數 (a2) = " + minMeetingRooms(a2)); // 2
        System.out.println("需要會議室數 (a3) = " + minMeetingRooms(a3)); // 2

        // Part B：只有 1 間會議室的最佳安排示例
        int[][] b1 = {{1,4},{2,3},{4,6}};
        ScheduleResult r1 = maximizeTotalDurationWithNRooms(b1, 1);
        System.out.println("\n只有 1 間會議室時的最佳總時長: " + r1.totalDuration);
        System.out.println("被選會議: " + toStringIntervals(r1.selected));
        // 期望：選 [1,4],[4,6]，總時長=3+2=5

        // 再多測幾組
        int[][] b2 = {{1,3},{2,5},{4,7},{6,8}}; // N=2
        ScheduleResult r2 = maximizeTotalDurationWithNRooms(b2, 2);
        System.out.println("\nN=2，最佳總時長: " + r2.totalDuration);
        System.out.println("被選會議: " + toStringIntervals(r2.selected));

        int[][] b3 = {{0,10},{0,5},{5,10},{3,4}}; // N=1
        ScheduleResult r3 = maximizeTotalDurationWithNRooms(b3, 1);
        System.out.println("\nN=1，最佳總時長: " + r3.totalDuration);
        System.out.println("被選會議: " + toStringIntervals(r3.selected));
    }

    private static String toStringIntervals(List<int[]> list) {
        list.sort(Comparator.comparingInt(a -> a[0]));
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            int[] itv = list.get(i);
            sb.append("[").append(itv[0]).append(",").append(itv[1]).append("]");
            if (i + 1 < list.size()) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
