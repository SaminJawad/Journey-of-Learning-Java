import java.util.ArrayList;

public class StackUsingArrayList {
    static class Stack<T> {
        private ArrayList<T> list = new ArrayList<>();

        public void push(T item) {
            list.add(item);
        }

        public T pop() {
            if (isEmpty())
                throw new RuntimeException("Stack is empty.");
            return list.remove(list.size() - 1);
        }

        public T peek() {
            if (isEmpty())
                throw new RuntimeException("Stack is empty.");
            return list.get(list.size() - 1);
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public int size() {
            return list.size();
        }

        @Override
        public String toString() {
            return list.toString();
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
    }
}