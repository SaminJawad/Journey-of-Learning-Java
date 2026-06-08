import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class URLShortener {
    private static final Map<String, String> store = new HashMap<>();
    private static final String BASE_URL = "https://short.ly/";

    static String shorten(String url) {
        String key = UUID.randomUUID().toString().substring(0, 6);
        store.put(key, url);
        return BASE_URL + key;
    }

    static String resolve(String shortUrl) {
        String key = shortUrl.replace(BASE_URL, "");
        return store.getOrDefault(key, "URL not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter URL to shorten: ");
        String url = scanner.nextLine().trim();
        String shortened = shorten(url);
        System.out.println("Shortened: " + shortened);

        System.out.print("Resolve shortened URL: ");
        String input = scanner.nextLine().trim();
        System.out.println("Original: " + resolve(input));

        scanner.close();
    }
}