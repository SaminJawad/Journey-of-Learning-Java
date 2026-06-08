import java.util.Scanner;

public class LinearSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++)
            arr[i] = scanner.nextInt();

        int target = scanner.nextInt();
        int index = -1;

        for (int i = 0; i < n; i++) {
            if (arr[i] == target) {
                index = i;
                break;
            }
        }

        System.out.println(index != -1 ? "Found at index: " + index : "Not found");
        scanner.close();
    }
}