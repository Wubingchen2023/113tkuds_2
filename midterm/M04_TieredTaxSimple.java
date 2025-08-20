import java.util.*;

public class M04_TieredTaxSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());

        int totalTax = 0;   // 總稅額累計
        int[] results = new int[n];

        for (int i = 0; i < n; i++) {
            int income = Integer.parseInt(sc.nextLine());
            int tax = computeTax(income);
            results[i] = tax;
            totalTax += tax;
        }

        // 輸出每筆稅額
        for (int tax : results) {
            System.out.println("Tax: " + tax);
        }

        // 平均稅額（整數）
        int avg = totalTax / n;
        System.out.println("Average: " + avg);
    }

    // 稅額計算函式：逐段累進
    static int computeTax(int income) {
        int tax = 0;

        if (income > 1000000) {
            tax += (income - 1000000) * 30 / 100;
            income = 1000000;
        }
        if (income > 500000) {
            tax += (income - 500000) * 20 / 100;
            income = 500000;
        }
        if (income > 120000) {
            tax += (income - 120000) * 12 / 100;
            income = 120000;
        }
        if (income > 0) {
            tax += income * 5 / 100;
        }

        return tax;
    }
}

/*
Time Complexity：
- 每筆收入計算稅額只需常數次比較與加減 ⇒ O(1)
- 共 n 筆 ⇒ 總時間複雜度 O(n)
Space Complexity：
- 僅存稅額陣列 O(n)
*/
