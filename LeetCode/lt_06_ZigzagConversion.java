public class lt_06_ZigzagConversion {
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) return s;

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();

        int row = 0, dir = 1; // 往下=+1，往上=-1
        for (int i = 0; i < s.length(); i++) {
            rows[row].append(s.charAt(i));
            // 觸頂或觸底時反轉方向
            if (row == 0) dir = 1;
            else if (row == numRows - 1) dir = -1;
            row += dir;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder sb : rows) ans.append(sb);
        return ans.toString();
    }

    public static void main(String[] args) {
        lt_06_ZigzagConversion sol = new lt_06_ZigzagConversion();

        String s = "PAYPALISHIRING";
        int numRows = 3;
        String result = sol.convert(s, numRows);

        System.out.println("Input: " + s);
        System.out.println("numRows: " + numRows);
        System.out.println("Output: " + result);
        // 預期結果: "PAHNAPLSIIGYIR"
    }
}

