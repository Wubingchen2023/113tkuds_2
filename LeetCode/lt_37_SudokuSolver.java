public class lt_37_SudokuSolver {

    // 直接修改 board 解出答案
    public static void solveSudoku(char[][] board) {
        backtrack(board);
    }

    // 回溯函式：嘗試填數字，回傳是否成功
    private static boolean backtrack(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 只填空格
                if (board[i][j] == '.') {
                    // 嘗試填入 1~9
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; // 試填

                            if (backtrack(board)) return true; // 若成功，直接回傳

                            board[i][j] = '.'; // 回溯（撤銷填入）
                        }
                    }
                    // 9 個都不合法，回傳 false（trigger backtrack）
                    return false;
                }
            }
        }
        // 所有格子都填好了
        return true;
    }

    // 判斷 c 是否可填入 (row, col)
    private static boolean isValid(char[][] board, int row, int col, char c) {
        for (int k = 0; k < 9; k++) {
            // 檢查同列
            if (board[row][k] == c) return false;

            // 檢查同欄
            if (board[k][col] == c) return false;

            // 檢查同 box
            int boxRow = 3 * (row / 3) + k / 3;
            int boxCol = 3 * (col / 3) + k % 3;
            if (board[boxRow][boxCol] == c) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };

        solveSudoku(board);

        // 印出解完的棋盤
        for (char[] row : board) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}

/*
【解題邏輯】

題目：解出一個部分填好的 9x9 數獨，每行、每列、每個 3x3 小方塊都只能出現一次數字 1~9。
- 保證輸入題目有解，且唯一解。

解法：Backtracking 回溯
- 每次從左到右、上到下掃描棋盤：
  1. 如果格子是 '.'，代表要填數字。
  2. 嘗試填入 '1'~'9'：
     - 若合法（不違反數獨規則），則填入並遞迴呼叫下一格。
     - 若後續發現填不下去（死路），則回溯（撤銷該格數字，繼續試下一個）。
- 所有格子都填完時代表成功，直接回傳 true 結束遞迴。

判斷填入數字是否合法的條件：
- 同列不能有相同數字。
- 同欄不能有相同數字。
- 所在的 3x3 方格內不能有相同數字。

算 box 的方法：
- box 的 row = `3 * (row / 3) + (k / 3)`
- box 的 col = `3 * (col / 3) + (k % 3)`
  （k = 0~8, 共遍歷 box 裡的 9 格）

時間複雜度：O(9^(n))，其中 n 為空格數量（最多 81）。
空間複雜度：O(n)（遞迴深度）
*/
