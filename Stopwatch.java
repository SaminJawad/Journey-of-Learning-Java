import java.util.Scanner;

public class Stopwatch {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        long startTime = 0, endTime = 0;

        while (true) {
            System.out.println("1. Start  2. Stop  3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startTime = System.nanoTime();
                    System.out.println("Stopwatch started.");
                    break;
                case 2:
                    endTime = System.nanoTime();
                    double elapsed = (endTime - startTime) / 1_000_000_000.0;
                    System.out.printf("Elapsed time: %.3f seconds%n", elapsed);
                    break;
                case 3:
                    scanner.close();
                    return;
            }
        }
    }
}