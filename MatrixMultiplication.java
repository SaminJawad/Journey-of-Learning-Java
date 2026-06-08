import java.util.Scanner;

public class MatrixMultiplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        int[][] c = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = scanner.nextInt();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                b[i][j] = scanner.nextInt();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    c[i][j] += a[i][k] * b[k][j];

        System.out.println("Result:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(c[i][j] + " ");
            System.out.println();
        }
        scanner.close();
    }
}