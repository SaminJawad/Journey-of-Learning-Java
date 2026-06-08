import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    static class Student {
        String name;
        int age;
        String grade;

        Student(String name, int age, String grade) {
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Name: " + name + " | Age: " + age + " | Grade: " + grade;
        }
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                students.add(new Student(parts[0], Integer.parseInt(parts[1]), parts[2]));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        for (Student s : students)
            System.out.println(s);
    }
}