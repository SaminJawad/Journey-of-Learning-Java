import java.util.concurrent.*;

public class SimpleThreadPool {
    static int fibonacci(int n) {
        if (n <= 1)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        int[] inputs = { 10, 20, 25, 30, 35 };
        Future<Integer>[] futures = new Future[inputs.length];

        for (int i = 0; i < inputs.length; i++) {
            final int n = inputs[i];
            futures[i] = executor.submit(() -> fibonacci(n));
        }

        for (int i = 0; i < inputs.length; i++)
            System.out.println("fib(" + inputs[i] + ") = " + futures[i].get());

        executor.shutdown();
    }
}