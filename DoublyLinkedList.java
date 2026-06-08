public class DoublyLinkedList {
    static class Node {
        int data;
        Node prev, next;

        Node(int data) {
            this.data = data;
        }
    }

    Node head, tail;

    void addLast(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
            return;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    void removeFirst() {
        if (head == null)
            return;
        head = head.next;
        if (head != null)
            head.prev = null;
        else
            tail = null;
    }

    void printForward() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " <-> " : ""));
            curr = curr.next;
        }
        System.out.println();
    }

    void printBackward() {
        Node curr = tail;
        while (curr != null) {
            System.out.print(curr.data + (curr.prev != null ? " <-> " : ""));
            curr = curr.prev;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addLast(40);

        System.out.print("Forward:  ");
        list.printForward();
        System.out.print("Backward: ");
        list.printBackward();
        list.removeFirst();
        System.out.print("After removeFirst: ");
        list.printForward();
    }
}