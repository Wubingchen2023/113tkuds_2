public class GradeStatisticsSystem {
    public static void main(String[] args) {
        int[] scores = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};

        int total = 0;
        int max = scores[0];
        int min = scores[0];

        // 等第統計用
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

        // 先計算總分、最高分、最低分、各等第人數
        for (int score : scores) {
            total += score;
            if (score > max) {
                max = score;
            }
            if (score < min) {
                min = score;
            }
            // 統計等第
            if (score >= 90) {
                countA++;
            }
            else if (score >= 80) {
                countB++;
            }
            else if (score >= 70) {
                countC++;
            }
            else if (score >= 60) {
                countD++;
            }
            else countF++;
        }

        double average = total / (double) scores.length;

        // 計算高於平均分的人數
        int countAboveAverage = 0;
        for (int score : scores) {
            if (score > average) {
                countAboveAverage++;
            }
        }

        // 印出統計摘要
        System.out.println("=== 成績統計報告 ===");
        System.out.printf("平均分數：%.2f\n", average);
        System.out.println("最高分數：" + max);
        System.out.println("最低分數：" + min);
        System.out.println();

        System.out.println("等第統計：");
        System.out.println("A (90-100)： " + countA + " 人");
        System.out.println("B (80-89)：  " + countB + " 人");
        System.out.println("C (70-79)：  " + countC + " 人");
        System.out.println("D (60-69)：  " + countD + " 人");
        System.out.println("F (<60)：    " + countF + " 人");
        System.out.println();

        System.out.println("高於平均分人數：" + countAboveAverage + " 人");
        System.out.println();

        // 列印完整成績報表
        System.out.println("完整成績報表：");
        System.out.println("座號\t分數\t等第");
        for (int i = 0; i < scores.length; i++) {
            char grade;
            int s = scores[i];
            if (s >= 90) {
                grade = 'A';
            }
            else if (s >= 80) {
                grade = 'B';
            }
            else if (s >= 70) {
                grade = 'C';
            }
            else if (s >= 60) {
                grade = 'D';
            }
            else grade = 'F';
            System.out.printf("%2d\t%3d\t %c\n", i + 1, s, grade);
        }
    }
}
