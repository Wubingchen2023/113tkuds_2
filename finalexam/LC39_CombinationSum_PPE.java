import java.io.*;
import java.util.*;

public class LC39_CombinationSum_PPE {
    public static void main(String[] args) throws Exception {
        // 讀取：第一行 n target；第二行 n 個整數
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(a); // 為了升序輸出與剪枝
        StringBuilder out = new StringBuilder();
        dfsI(a, 0, target, new ArrayList<>(), out);

        // 若無任何組合，依題意可不輸出；此處直接印出結果（可能為空字串）
        System.out.print(out.toString());
    }

    // 回溯：I 版（可重複使用當前數字 ⇒ 下一層仍傳 i）
    private static void dfsI(int[] a, int start, int remain, List<Integer> path, StringBuilder out) {
        if (remain == 0) {
            printPath(path, out);
            return;
        }
        for (int i = start; i < a.length; i++) {
            int v = a[i];
            if (v > remain) break; // 剪枝：排序後後續更大，無需再試
            path.add(v);
            dfsI(a, i, remain - v, path, out); // 仍傳 i：可重複使用
            path.remove(path.size() - 1);
        }
    }

    private static void printPath(List<Integer> path, StringBuilder out) {
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) out.append(' ');
            out.append(path.get(i));
        }
        out.append('\n');
    }
}
