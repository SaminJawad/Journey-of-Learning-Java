import java.util.Arrays;

public class BubbleAndSelectionSort {

    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }

    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (arr[j] < arr[minIdx])
                    minIdx = j;
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr1 = { 64, 34, 25, 12, 22, 11, 90 };
        int[] arr2 = arr1.clone();

        bubbleSort(arr1);
        selectionSort(arr2);

        System.out.println("Bubble Sort:    " + Arrays.toString(arr1));
        System.out.println("Selection Sort: " + Arrays.toString(arr2));
    }
}