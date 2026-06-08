import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class MiniWebCrawler {
    private static final Set<String> visited = new LinkedHashSet<>();
    private static final Queue<String> queue = new LinkedList<>();
    private static final int MAX_PAGES = 5;

    static String fetchHTML(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
        }
        return sb.toString();
    }

    static Set<String> extractLinks(String html, String baseUrl) {
        Set<String> links = new LinkedHashSet<>();
        Matcher m = Pattern.compile("href=\"(https?://[^\"]+)\"").matcher(html);
        while (m.find())
            links.add(m.group(1));
        return links;
    }

    static void crawl(String startUrl) {
        queue.add(startUrl);
        while (!queue.isEmpty() && visited.size() < MAX_PAGES) {
            String url = queue.poll();
            if (visited.contains(url))
                continue;
            try {
                System.out.println("Crawling: " + url);
                String html = fetchHTML(url);
                visited.add(url);
                Set<String> links = extractLinks(html, url);
                System.out.println("  Found " + links.size() + " links");
                queue.addAll(links);
            } catch (IOException e) {
                System.out.println("  Failed: " + e.getMessage());
            }
        }
        System.out.println("\nCrawled " + visited.size() + " pages:");
        visited.forEach(v -> System.out.println("  " + v));
    }

    public static void main(String[] args) {
        crawl("https://example.com");
    }
}