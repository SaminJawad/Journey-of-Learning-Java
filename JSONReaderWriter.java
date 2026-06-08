import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONReaderWriter {
    static class Student {
        public String name;
        public int age;
        public String grade;

        public Student() {
        }

        public Student(String name, int age, String grade) {
            this.name = name;
            this.age = age;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Student{name='" + name + "', age=" + age + ", grade='" + grade + "'}";
        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Student> students = List.of(
                new Student("Samin", 20, "A"),
                new Student("Alice", 21, "B"),
                new Student("Bob", 22, "A+"));

        mapper.writeValue(new File("students.json"), students);
        System.out.println("Written to students.json");

        List<Student> loaded = mapper.readValue(
                new File("students.json"),
                mapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        loaded.forEach(System.out::println);
    }
}