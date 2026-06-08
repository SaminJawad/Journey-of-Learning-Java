import java.io.*;
import java.util.*;

public class WordCountAcrossFiles {

    static Map<String, Integer> countWords(String filePath) throws IOException {
        Map<String, Integer> freq = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String word : line.toLowerCase().split("\\W+")) {
                    if (!word.isEmpty())
                        freq.merge(word, 1, Integer::sum);
                }
            }
        }
        return freq;
    }

    static Map<String, Integer> mergecounts(List<Map<String, Integer>> maps) {
        Map<String, Integer> merged = new HashMap<>();
        for (Map<String, Integer> map : maps)
            map.forEach((k, v) -> merged.merge(k, v, Integer::sum));
        return merged;
    }

    public static void main(String[] args) throws IOException {
        List<String> files = List.of("file1.txt", "file2.txt");
        List<Map<String, Integer>> counts = new ArrayList<>();
        for (String f : files)
            counts.add(countWords(f));

        Map<String, Integer> merged = mergecuts(counts);
        merged.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }
}