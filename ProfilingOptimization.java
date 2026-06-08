import java.util.*;
import java.util.stream.Collectors;

public class ProfilingOptimization {

    static List<Integer> slowDuplicates(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            for (int j = i + 1; j < list.size(); j++)
                if (list.get(i).equals(list.get(j)) && !result.contains(list.get(i)))
                    result.add(list.get(i));
        return result;
    }

    static List<Integer> fastDuplicates(List<Integer> list) {
        Set<Integer> seen = new HashSet<>();
        return list.stream()
                .filter(n -> !seen.add(n))
                .distinct()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < 5000; i++)
            data.add(rand.nextInt(100));

        long start = System.nanoTime();
        List<Integer> slow = slowDuplicates(data);
        long slowTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> fast = fastDuplicates(data);
        long fastTime = System.nanoTime() - start;

        System.out.println("Duplicates found: " + slow.size());
        System.out.printf("Slow O(n²): %.2f ms%n", slowTime / 1_000_000.0);
        System.out.printf("Fast O(n):  %.2f ms%n", fastTime / 1_000_000.0);
        System.out.printf("Speedup:    %.1fx%n", (double) slowTime / fastTime);
    }
}