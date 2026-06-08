import java.util.Scanner;

public class BasicCalculator {

    static double calculate(double a, double b, int op) {
        switch (op) {
            case 1:
                return a + b;
            case 2:
                return a - b;
            case 3:
                return a * b;
            case 4:
                return b != 0 ? a / b : Double.NaN;
            default:
                return Double.NaN;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Add  2. Subtract  3. Multiply  4. Divide");
        int op = scanner.nextInt();
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();

        double result = calculate(a, b, op);

        if (Double.isNaN(result))
            System.out.println("Invalid operation or division by zero.");
        else
            System.out.printf("Result: %.2f%n", result);

        scanner.close();
    }
}