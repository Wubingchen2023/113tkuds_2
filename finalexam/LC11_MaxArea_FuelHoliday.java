import java.util.*;

public class LC11_MaxArea_FuelHoliday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int[] h = new int[n];
        for (int i = 0; i < n; i++) h[i] = sc.nextInt();

        System.out.println(maxArea(h));
    }

    // 兩指標夾逼：唯有提升短邊才可能使面積變大
    public static long maxArea(int[] h) {
        int l = 0, r = h.length - 1;
        long ans = 0;

        while (l < r) {
            int leftH = h[l], rightH = h[r];
            int minH = Math.min(leftH, rightH);
            long width = r - l;
            long area = width * (long) minH; // 注意用 long 避免溢位 (最大約 1e5 * 1e9 = 1e14)
            if (area > ans) ans = area;

            // 移動較短邊；因為寬度縮小，只有提高短邊高度才可能彌補面積
            if (leftH <= rightH) {
                l++;
            } else {
                r--;
            }
        }
        return ans;
    }
}

/* 
範例運算:

輸入:
9
1 8 6 2 5 4 8 3 7

運算過程 (部份):
l=0,r=8 → area=1*8=8
l=1,r=8 → area=8*7=56 → max=56
l=1,r=7 → area=8*6=48
...
最後 max=49 (在 l=1,r=6 時: 8*5)

 */