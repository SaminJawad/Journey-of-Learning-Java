import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteCSV {
    public static void main(String[] args) {
        List<String[]> students = new ArrayList<>();
        students.add(new String[]{"Name", "Age", "Grade"});
        students.add(new String[]{"Samin", "20", "A"});
        students.add(new String[]{"Alice", "21", "B"});
        students.add(new String[]{"Bob", "22", "A+"});

        try (FileWriter fw = new FileWriter("students.csv")) {
            for (String[] row : students) {
                fw.write(String.join(",", row) + "\n");
            }
            System.out.println("CSV written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}