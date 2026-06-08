import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CharFrequency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : input.toCharArray())
            freq.put(c, freq.getOrDefault(c, 0) + 1);

        for (Map.Entry<Character, Integer> entry : freq.entrySet())
            System.out.println("'" + entry.getKey() + "' : " + entry.getValue());

        scanner.close();
    }
}