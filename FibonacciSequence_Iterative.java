import java.util.Scanner;

public class FibonacciSequence_Iterative {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        long a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            long temp = a + b;
            a = b;
            b = temp;
        }
        System.out.println();
        scanner.close();
    }
}