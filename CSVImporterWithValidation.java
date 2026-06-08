import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVImporterWithValidation {
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
            return "Student{name='" + name + "', age=" + age + ", grade='" + grade + "'}";
        }
    }

    static class ValidationError {
        int line;
        String message;

        ValidationError(int line, String message) {
            this.line = line;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Line " + line + ": " + message;
        }
    }

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();
        int lineNum = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                lineNum++;
                String[] parts = line.split(",");

                if (parts.length != 3) {
                    errors.add(new ValidationError(lineNum, "Invalid column count."));
                    continue;
                }

                String name = parts[0].trim();
                String ageStr = parts[1].trim();
                String grade = parts[2].trim();

                if (name.isEmpty()) {
                    errors.add(new ValidationError(lineNum, "Name is empty."));
                    continue;
                }

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                    if (age < 1 || age > 100)
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    errors.add(new ValidationError(lineNum, "Invalid age: " + ageStr));
                    continue;
                }

                if (!grade.matches("[A-F][+]?")) {
                    errors.add(new ValidationError(lineNum, "Invalid grade: " + grade));
                    continue;
                }

                students.add(new Student(name, age, grade));
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        System.out.println("Valid Records:");
        students.forEach(System.out::println);

        System.out.println("\nValidation Errors:");
        if (errors.isEmpty())
            System.out.println("None");
        else
            errors.forEach(System.out::println);
    }
}