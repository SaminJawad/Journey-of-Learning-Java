import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine().trim();

        System.out.println(EMAIL_PATTERN.matcher(email).matches() ? "Valid email" : "Invalid email");
        scanner.close();
    }
}