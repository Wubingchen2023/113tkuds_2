public class lt_09_PalindromeNumber {
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        int left = 0, right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        lt_09_PalindromeNumber solution = new lt_09_PalindromeNumber();

        int num1 = 121;
        int num2 = -121;
        int num3 = 12321;
        int num4 = 123;

        System.out.println(num1 + " 是否為回文數？ " + solution.isPalindrome(num1));
        System.out.println(num2 + " 是否為回文數？ " + solution.isPalindrome(num2));
        System.out.println(num3 + " 是否為回文數？ " + solution.isPalindrome(num3));
        System.out.println(num4 + " 是否為回文數？ " + solution.isPalindrome(num4));
    }
}
