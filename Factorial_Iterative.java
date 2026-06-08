import java.util.Scanner;

public class Factorial_Iterative {
    static long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        if (n < 0) {
            System.out.println("Factorial undefined for negative numbers.");
        } else {
            System.out.println(n + "! = " + factorial(n));
        }

        scanner.close();
    }
}
