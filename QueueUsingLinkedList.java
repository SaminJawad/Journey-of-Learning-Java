import java.util.LinkedList;

public class QueueUsingLinkedList {
    static class Queue<T> {
        private LinkedList<T> list = new LinkedList<>();

        public void enqueue(T item) {
            list.addLast(item);
        }

        public T dequeue() {
            if (isEmpty())
                throw new RuntimeException("Queue is empty.");
            return list.removeFirst();
        }

        public T peek() {
            if (isEmpty())
                throw new RuntimeException("Queue is empty.");
            return list.getFirst();
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
        Queue<String> queue = new Queue<>();
        queue.enqueue("Alice");
        queue.enqueue("Bob");
        queue.enqueue("Charlie");

        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Dequeue: " + queue.dequeue());
        System.out.println("Queue after dequeue: " + queue);
    }
}