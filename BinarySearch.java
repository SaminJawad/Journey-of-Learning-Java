import java.util.Scanner;

public class BinarySearch {

    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target)
                return mid;
            else if (arr[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = { 2, 5, 8, 12, 16, 23, 38, 45, 67, 90 };
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();

        int result = binarySearch(arr, target);
        System.out.println(result != -1 ? "Found at index: " + result : "Not found");
        scanner.close();
    }
}