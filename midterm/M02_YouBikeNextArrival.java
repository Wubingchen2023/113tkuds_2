import java.util.*;

public class M02_YouBikeNextArrival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());
        int[] times = new int[n];

        // 讀入所有時間並轉成分鐘數
        for (int i = 0; i < n; i++) {
            times[i] = toMinutes(sc.nextLine());
        }

        // 讀入查詢時間並轉成分鐘
        int queryTime = toMinutes(sc.nextLine());

        // 二分搜尋：找第一個大於 queryTime 的時間
        int idx = binarySearchNext(times, queryTime);

        if (idx == -1) {
            System.out.println("No bike");
        } else {
            System.out.println(toHHMM(times[idx]));
        }
    }

    // 轉換 HH:mm 字串成分鐘數
    static int toMinutes(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);
        return hour * 60 + min;
    }

    // 轉換分鐘數回 HH:mm 格式
    static String toHHMM(int totalMinutes) {
        int h = totalMinutes / 60;
        int m = totalMinutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    // 二分搜尋找第一個大於 queryTime 的時間索引
    static int binarySearchNext(int[] arr, int query) {
        int left = 0, right = arr.length - 1;
        int ans = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > query) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }
}
