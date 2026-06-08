import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            System.out.println("\n1. Add  2. List  3. Remove  4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Task: ");
                    tasks.add(scanner.nextLine());
                    break;
                case 2:
                    if (tasks.isEmpty())
                        System.out.println("No tasks.");
                    else
                        for (int i = 0; i < tasks.size(); i++)
                            System.out.println((i + 1) + ". " + tasks.get(i));
                    break;
                case 3:
                    System.out.print("Task number to remove: ");
                    int idx = scanner.nextInt() - 1;
                    if (idx >= 0 && idx < tasks.size())
                        tasks.remove(idx);
                    else
                        System.out.println("Invalid index.");
                    break;
                case 4:
                    scanner.close();
                    return;
            }
        }
    }
}