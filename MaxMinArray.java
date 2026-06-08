import java.util.Scanner;

public class MaxMinArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            arr[i] = scanner.nextInt();

        int max = arr[0], min = arr[0];
        for (int x : arr) {
            if (x > max)
                max = x;
            if (x < min)
                min = x;
        }

        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
        scanner.close();
    }
}