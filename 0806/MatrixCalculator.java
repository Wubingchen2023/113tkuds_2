public class MatrixCalculator {

    public static void main(String[] args) {
        // 測試資料
        int[][] A = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] B = {
            {7, 8, 9},
            {10, 11, 12}
        };

        int[][] C = {
            {1, 2},
            {3, 4},
            {5, 6}
        };

        // 1. 矩陣加法
        System.out.println("矩陣 A + B：");
        printMatrix(addMatrices(A, B));
        System.out.println();

        // 2. 矩陣乘法 A × C
        System.out.println("矩陣 A × C：");
        printMatrix(multiplyMatrices(A, C));
        System.out.println();

        // 3. 矩陣轉置 A^T
        System.out.println("矩陣 A 的轉置：");
        printMatrix(transposeMatrix(A));
        System.out.println();

        // 4. 最大最小值
        System.out.println("矩陣 B 的最大值與最小值：");
        int[] result = findMaxMin(B);
        System.out.println("最大值: " + result[0]);
        System.out.println("最小值: " + result[1]);
    }

    // 1. 矩陣加法
    public static int[][] addMatrices(int[][] m1, int[][] m2) {
        int rows = m1.length;
        int cols = m1[0].length;
        int[][] sum = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                sum[i][j] = m1[i][j] + m2[i][j];
        return sum;
    }

    // 2. 矩陣乘法
    public static int[][] multiplyMatrices(int[][] m1, int[][] m2) {
        int rows = m1.length;
        int cols = m2[0].length;
        int common = m1[0].length;
        int[][] product = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                for (int k = 0; k < common; k++)
                    product[i][j] += m1[i][k] * m2[k][j];
        return product;
    }

    // 3. 矩陣轉置
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transposed[j][i] = matrix[i][j];
        return transposed;
    }

    // 4. 尋找最大與最小值
    public static int[] findMaxMin(int[][] matrix) {
        int max = matrix[0][0];
        int min = matrix[0][0];
        for (int[] row : matrix)
            for (int value : row) {
                if (value > max) max = value;
                if (value < min) min = value;
            }
        return new int[]{max, min};
    }

    // 輔助列印函式
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row)
                System.out.printf("%4d", value);
            System.out.println();
        }
    }
}
