import java.util.Arrays;
import java.util.Scanner;

public class AnagramChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");
        String s2 = scanner.nextLine().toLowerCase().replaceAll("\\s+", "");

        char[] a = s1.toCharArray();
        char[] b = s2.toCharArray();
        Arrays.sort(a);
        Arrays.sort(b);

        System.out.println(Arrays.equals(a, b) ? "Anagram" : "Not Anagram");
        scanner.close();
    }
}