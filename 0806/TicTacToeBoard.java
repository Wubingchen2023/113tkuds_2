import java.util.Scanner;

public class TicTacToeBoard {
    private char[][] board;      // 棋盤本體
    private final int SIZE = 3;  // 棋盤大小

    // 初始化棋盤
    public TicTacToeBoard() {
        board = new char[SIZE][SIZE];
        initBoard();
    }

    // 1. 初始化 3x3 的遊戲棋盤（空格用空白 ' ' 表示）
    private void initBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // 輔助：列印當前棋盤狀態
    public void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    // 2. 放置棋子：player 為 'X' 或 'O'
    // 回傳 true 表示放置成功，false 表示格子已被佔用或位置不合法
    public boolean placePiece(int row, int col, char player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println(">> 無效位置，請輸入 0 到 " + (SIZE-1) + " 之間的數字。");
            return false;
        }
        if (board[row][col] != ' ') {
            System.out.println(">> 此格已被佔用，請選擇其他位置。");
            return false;
        }
        board[row][col] = player;
        return true;
    }

    // 3. 檢查獲勝條件（行、列、兩條對角線）
    // 回傳 'X' 或 'O' 表示該玩家已勝；若無勝方則回傳空格 ' '
    public char checkWin() {
        // 檢查每一行
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != ' ' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        // 檢查每一列
        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] != ' ' &&
                board[0][j] == board[1][j] &&
                board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        // 檢查主要對角線
        if (board[0][0] != ' ' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {
            return board[0][0];
        }
        // 檢查副對角線
        if (board[0][2] != ' ' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return ' ';
    }

    // 4. 判斷是否平手（所有格子皆被填滿且無勝方）
    public boolean isDraw() {
        if (checkWin() != ' ') {
            return false;   // 已有勝方就不是平手
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;  // 尚有空位
                }
            }
        }
        return true;  // 滿格且無勝方就是平手
    }

    // 判斷遊戲是否結束（勝利或平手）
    public boolean isGameOver() {
        return checkWin() != ' ' || isDraw();
    }

    // 主程式：示範互動式遊戲流程
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicTacToeBoard game = new TicTacToeBoard();
        char currentPlayer = 'X';

        System.out.println("=== 井字遊戲 ===");
        game.printBoard();

        // 反覆輪流讓 X、O 下棋，直到遊戲結束
        while (!game.isGameOver()) {
            System.out.printf("玩家 %c，請輸入放置位置（row col）：", currentPlayer);
            int row = sc.nextInt();
            int col = sc.nextInt();
            // 如果放置失敗，重覆此玩家的回合
            if (!game.placePiece(row, col, currentPlayer)) {
                continue;
            }
            game.printBoard();

            // 若遊戲結束，就跳出
            if (game.isGameOver()) {
                break;
            }
            // 換下一位玩家
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
        // 結果輸出
        char winner = game.checkWin();
        if (winner != ' ') {
            System.out.println("恭喜玩家 " + winner + " 獲勝！");
        } else {
            System.out.println("遊戲平手！");
        }
        sc.close();
    }
}
