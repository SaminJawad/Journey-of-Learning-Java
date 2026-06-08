import java.util.Scanner;

public class FastPower {

    static long power(long base, int exp) {
        if (exp == 0)
            return 1;
        if (exp % 2 == 0) {
            long half = power(base, exp / 2);
            return half * half;
        }
        return base * power(base, exp - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long base = scanner.nextLong();
        int exp = scanner.nextInt();

        System.out.println(base + "^" + exp + " = " + power(base, exp));
        scanner.close();
    }
}