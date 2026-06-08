import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapPatterns {
    private static final ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();

    static void processText(String text) {
        for (String word : text.toLowerCase().split("\\W+")) {
            if (!word.isEmpty())
                wordCount.merge(word, 1, Integer::sum);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] texts = {
                "Java is great and Java is fast",
                "Concurrency in Java is powerful",
                "Java threads and Java locks are important",
                "ConcurrentHashMap is thread safe in Java"
        };

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (String text : texts)
            executor.submit(() -> processText(text));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}