import java.util.Scanner;
import java.util.Stack;

public class InfixToPostfix {

    static int precedence(char op) {
        if (op == '+' || op == '-')
            return 1;
        if (op == '*' || op == '/')
            return 2;
        return 0;
    }

    static String convert(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result.append(stack.pop());
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c))
                    result.append(stack.pop());
                stack.push(c);
            }
        }

        while (!stack.isEmpty())
            result.append(stack.pop());
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String infix = scanner.nextLine().trim();
        System.out.println("Postfix: " + convert(infix));
        scanner.close();
    }
}