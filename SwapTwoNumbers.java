import java.util.Scanner;

public class SwapTwoNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // With temp variable
        int temp = a;
        a = b;
        b = temp;
        System.out.println("With temp: a=" + a + ", b=" + b);

        // Reset
        a = scanner.nextInt();
        b = scanner.nextInt();

        // Without temp variable
        a = a + b;
        b = a - b;
        a = a - b;
        System.out.println("Without temp: a=" + a + ", b=" + b);

        scanner.close();
    }
}