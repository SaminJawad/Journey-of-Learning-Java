import java.util.Scanner;

public class CountWords {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        int count = input.isEmpty() ? 0 : input.split("\\s+").length;
        System.out.println("Word count: " + count);
        scanner.close();
    }
}