import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Phonebook {
    private static final Map<String, String> phonebook = new HashMap<>();

    static void add(String name, String number) {
        phonebook.put(name.toLowerCase(), number);
        System.out.println("Added: " + name + " -> " + number);
    }

    static void searchByPrefix(String prefix) {
        boolean found = false;
        for (Map.Entry<String, String> entry : phonebook.entrySet()) {
            if (entry.getKey().startsWith(prefix.toLowerCase())) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
                found = true;
            }
        }
        if (!found)
            System.out.println("No contacts found with prefix: " + prefix);
    }

    public static void main(String[] args) {
        add("Samin", "01700000001");
        add("Jawad", "01700000002");
        add("Nafis", "01700000003");
        add("Mahin", "01700000004");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Search prefix: ");
        String prefix = scanner.nextLine().trim();
        searchByPrefix(prefix);
        scanner.close();
    }
}