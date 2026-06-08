import java.util.Scanner;

public class GCD_LCM {
    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println("GCD: " + gcd(a, b));
        System.out.println("LCM: " + lcm(a, b));
        scanner.close();
    }
}
