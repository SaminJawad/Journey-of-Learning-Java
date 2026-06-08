import java.util.*;

public class SimpleSearchIndex {
    private final Map<String, Set<Integer>> index = new HashMap<>();
    private final Map<Integer, String> documents = new HashMap<>();

    public void addDocument(int id, String text) {
        documents.put(id, text);
        for (String word : text.toLowerCase().split("\\W+")) {
            if (!word.isEmpty())
                index.computeIfAbsent(word, k -> new HashSet<>()).add(id);
        }
    }

    public List<String> search(String query) {
        List<String> results = new ArrayList<>();
        String word = query.toLowerCase().trim();
        Set<Integer> ids = index.getOrDefault(word, Collections.emptySet());
        for (int id : ids)
            results.add("[Doc " + id + "] " + documents.get(id));
        return results;
    }

    public List<String> searchAnd(String w1, String w2) {
        Set<Integer> s1 = index.getOrDefault(w1.toLowerCase(), Collections.emptySet());
        Set<Integer> s2 = index.getOrDefault(w2.toLowerCase(), Collections.emptySet());
        Set<Integer> intersection = new HashSet<>(s1);
        intersection.retainAll(s2);
        List<String> results = new ArrayList<>();
        for (int id : intersection)
            results.add("[Doc " + id + "] " + documents.get(id));
        return results;
    }

    public void printIndex() {
        index.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  " + e.getKey() + " -> docs " + e.getValue()));
    }

    public static void main(String[] args) {
        SimpleSearchIndex idx = new SimpleSearchIndex();
        idx.addDocument(1, "Java is a powerful programming language");
        idx.addDocument(2, "Python is great for data science and machine learning");
        idx.addDocument(3, "Java and Python are both popular programming languages");
        idx.addDocument(4, "Machine learning with Java is increasingly popular");

        System.out.println("--- Inverted Index ---");
        idx.printIndex();

        System.out.println("\n--- Search: 'java' ---");
        idx.search("java").forEach(System.out::println);

        System.out.println("\n--- Search: 'machine' ---");
        idx.search("machine").forEach(System.out::println);

        System.out.println("\n--- Search AND: 'java' + 'popular' ---");
        idx.searchAnd("java", "popular").forEach(System.out::println);
    }
}