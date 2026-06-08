import java.util.Scanner;

public class PalindromeChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase().replaceAll("[^a-z0-9]", "");
        String reversed = new StringBuilder(input).reverse().toString();

        System.out.println(input.equals(reversed) ? "Palindrome" : "Not Palindrome");
        scanner.close();
    }
}