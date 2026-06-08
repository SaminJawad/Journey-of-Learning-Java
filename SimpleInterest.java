import java.util.Scanner;

public class SimpleInterest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double principal = scanner.nextDouble();
        double rate = scanner.nextDouble();
        double time = scanner.nextDouble();

        double si = (principal * rate * time) / 100;

        System.out.printf("Simple Interest: %.2f%n", si);

        scanner.close();
    }
}