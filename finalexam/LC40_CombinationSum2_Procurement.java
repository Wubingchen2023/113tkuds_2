import java.io.*;
import java.util.*;

public class LC40_CombinationSum2_Procurement {
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

        Arrays.sort(a);
        StringBuilder out = new StringBuilder();
        dfsII(a, 0, target, new ArrayList<>(), out);
        System.out.print(out.toString());
    }

    // 回溯：II 版（每個元素最多用一次 ⇒ 下一層傳 i+1；同層去重）
    private static void dfsII(int[] a, int start, int remain, List<Integer> path, StringBuilder out) {
        if (remain == 0) {
            printPath(path, out);
            return;
        }
        for (int i = start; i < a.length; i++) {
            // 同層去重：避免產生數值組成相同的組合（例如兩個 1 只取第一個作為本層起點）
            if (i > start && a[i] == a[i - 1]) continue;
            int v = a[i];
            if (v > remain) break; // 剪枝
            path.add(v);
            dfsII(a, i + 1, remain - v, path, out); // 每個值僅用一次
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
