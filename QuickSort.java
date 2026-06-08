import java.util.Arrays;

public class QuickSort {

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                int temp = arr[++i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 10, 7, 8, 9, 1, 5 };
        System.out.println("Before: " + Arrays.toString(arr));
        quickSort(arr, 0, arr.length - 1);
        System.out.println("After:  " + Arrays.toString(arr));
    }
}