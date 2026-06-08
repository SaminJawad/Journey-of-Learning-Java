import java.util.HashMap;
import java.util.Map;

public class RecursiveFactorial {
    static Map<Integer, Long> memo = new HashMap<>();

    static long factorial(int n) {
        if (n <= 1)
            return 1;
        if (memo.containsKey(n))
            return memo.get(n);
        long result = n * factorial(n - 1);
        memo.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++)
            System.out.println(i + "! = " + factorial(i));
    }
}