import java.util.Arrays;
import java.util.PriorityQueue;

public class TopKElements {

    static int[] topK(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--)
            result[i] = minHeap.poll();
        return result;
    }

    public static void main(String[] args) {
        int[] nums = { 3, 1, 5, 12, 2, 11, 7, 9 };
        int k = 3;
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Top " + k + ": " + Arrays.toString(topK(nums, k)));
    }
}