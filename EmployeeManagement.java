import java.util.ArrayList;

public class EmployeeManagement {
    static class Employee {
        String name;
        int id;
        double salary;

        Employee(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "ID: " + id + " | Name: " + name + " | Salary: " + salary;
        }
    }

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Samin", 50000));
        employees.add(new Employee(2, "Jawad", 60000));
        employees.add(new Employee(3, "Nafis", 55000));

        System.out.println("All Employees:");
        for (Employee e : employees)
            System.out.println(e);

        System.out.println("\nSearch by name 'Alice':");
        for (Employee e : employees)
            if (e.name.equalsIgnoreCase("Alice"))
                System.out.println(e);
    }
}