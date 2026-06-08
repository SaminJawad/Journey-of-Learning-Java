import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVToTable {
    public static void main(String[] args) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            String line;
            while ((line = br.readLine()) != null)
                rows.add(line.split(","));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        int[] widths = new int[rows.get(0).length];
        for (String[] row : rows)
            for (int i = 0; i < row.length; i++)
                widths[i] = Math.max(widths[i], row[i].trim().length());

        for (String[] row : rows) {
            StringBuilder sb = new StringBuilder("|");
            for (int i = 0; i < row.length; i++)
                sb.append(String.format(" %-" + widths[i] + "s |", row[i].trim()));
            System.out.println(sb);
        }
    }
}