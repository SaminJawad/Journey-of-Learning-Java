public class LinkedList {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    Node head;

    void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node curr = head;
        while (curr.next != null)
            curr = curr.next;
        curr.next = newNode;
    }

    void remove(int data) {
        if (head == null)
            return;
        if (head.data == data) {
            head = head.next;
            return;
        }
        Node curr = head;
        while (curr.next != null && curr.next.data != data)
            curr = curr.next;
        if (curr.next != null)
            curr.next = curr.next.next;
    }

    int search(int data) {
        Node curr = head;
        int index = 0;
        while (curr != null) {
            if (curr.data == data)
                return index;
            curr = curr.next;
            index++;
        }
        return -1;
    }

    void print() {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.print();
        list.remove(20);
        list.print();
        System.out.println("Search 30: index " + list.search(30));
        System.out.println("Search 99: index " + list.search(99));
    }
}