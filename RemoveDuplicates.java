import java.util.Arrays;
import java.util.LinkedHashSet;

public class RemoveDuplicates {
    public static void main(String[] args) {
        int[] arr = { 1, 3, 2, 5, 3, 1, 7, 2, 8 };

        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        for (int x : arr)
            set.add(x);

        int[] result = set.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("Original: " + Arrays.toString(arr));
        System.out.println("Unique:   " + Arrays.toString(result));
    }
}