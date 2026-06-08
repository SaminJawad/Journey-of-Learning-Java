import java.util.Scanner;
import java.util.Stack;

public class EvaluatePostfix {

    static double evaluate(String postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix.split("\\s+")) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                    break;
                }
                default:
                    stack.push(Double.parseDouble(token));
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String postfix = scanner.nextLine().trim();
        System.out.printf("Result: %.2f%n", evaluate(postfix));
        scanner.close();
    }
}