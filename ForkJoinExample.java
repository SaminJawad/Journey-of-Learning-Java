import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinExample {
    static class ArraySumTask extends RecursiveTask<Long> {
        private final int[] arr;
        private final int start, end;
        private static final int THRESHOLD = 5;

        ArraySumTask(int[] arr, int start, int end) {
            this.arr = arr;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++)
                    sum += arr[i];
                return sum;
            }
            int mid = (start + end) / 2;
            ArraySumTask left = new ArraySumTask(arr, start, mid);
            ArraySumTask right = new ArraySumTask(arr, mid, end);
            left.fork();
            return right.compute() + left.join();
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[20];
        for (int i = 0; i < arr.length; i++)
            arr[i] = i + 1;

        ForkJoinPool pool = new ForkJoinPool();
        long result = pool.invoke(new ArraySumTask(arr, 0, arr.length));

        System.out.println("Sum (1 to 20): " + result);
        pool.shutdown();
    }
}