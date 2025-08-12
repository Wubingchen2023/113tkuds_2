import java.util.*;

public class PriorityQueueWithHeap {

    // 任務類別
    static class Task {
        String name;
        int priority;
        long timestamp; // 用來確保相同優先級時，先加入的先執行

        public Task(String name, int priority, long timestamp) {
            this.name = name;
            this.priority = priority;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return name + " (優先級: " + priority + ")";
        }
    }

    // 優先級比較器（數字越大優先級越高）
    private final Comparator<Task> taskComparator = (a, b) -> {
        if (b.priority != a.priority) {
            return Integer.compare(b.priority, a.priority); // 高優先級先
        } else {
            return Long.compare(a.timestamp, b.timestamp); // 同優先級先加入的先
        }
    };

    private PriorityQueue<Task> heap = new PriorityQueue<>(taskComparator);
    private long timeCounter = 0; // 模擬時間戳記

    // 1) 添加任務
    public void addTask(String name, int priority) {
        heap.offer(new Task(name, priority, timeCounter++));
    }

    // 2) 執行優先級最高的任務
    public Task executeNext() {
        return heap.poll();
    }

    // 3) 查看下一個要執行的任務
    public Task peek() {
        return heap.peek();
    }

    // 4) 修改任務優先級
    public void changePriority(String name, int newPriority) {
        List<Task> temp = new ArrayList<>();
        boolean found = false;

        while (!heap.isEmpty()) {
            Task t = heap.poll();
            if (t.name.equals(name) && !found) {
                t.priority = newPriority;
                t.timestamp = timeCounter++; // 更新時間戳記
                found = true;
            }
            temp.add(t);
        }
        // 重建 Heap
        heap.addAll(temp);
    }

    public static void main(String[] args) {
        PriorityQueueWithHeap pq = new PriorityQueueWithHeap();

        pq.addTask("備份", 1);
        pq.addTask("緊急修復", 5);
        pq.addTask("更新", 3);

        System.out.println("下一個任務: " + pq.peek());

        System.out.println("執行: " + pq.executeNext());
        System.out.println("執行: " + pq.executeNext());
        System.out.println("執行: " + pq.executeNext());

        // 測試 changePriority
        pq.addTask("掃描", 2);
        pq.addTask("同步", 4);
        pq.changePriority("掃描", 6);
        System.out.println("\n修改優先級後，下一個任務: " + pq.peek());
    }
}
