import java.util.Scanner;

public class ArraySumAverage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            arr[i] = scanner.nextInt();

        long sum = 0;
        for (int x : arr)
            sum += x;

        System.out.println("Sum: " + sum);
        System.out.printf("Average: %.2f%n", (double) sum / n);
        scanner.close();
    }
}