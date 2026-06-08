import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class CustomSerializer {
    static class Person {
        String name;
        int age;
        double salary;

        Person(String name, int age, double salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", salary=" + salary + "}";
        }
    }

    static byte[] serialize(Person p) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(p.name);
        dos.writeInt(p.age);
        dos.writeDouble(p.salary);
        dos.flush();
        return baos.toByteArray();
    }

    static Person deserialize(byte[] data) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        String name = dis.readUTF();
        int age = dis.readInt();
        double salary = dis.readDouble();
        return new Person(name, age, salary);
    }

    public static void main(String[] args) throws IOException {
        Person original = new Person("Samin", 20, 75000.50);
        System.out.println("Original:     " + original);

        byte[] bytes = serialize(original);
        System.out.println("Serialized:   " + bytes.length + " bytes");
        System.out.println("Hex:          " + bytesToHex(bytes));

        Person restored = deserialize(bytes);
        System.out.println("Deserialized: " + restored);
        System.out.println("Match:        " + original.toString().equals(restored.toString()));
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02X ", b));
        return sb.toString().trim();
    }
}