public class BinarySearchTree {
    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
        }
    }

    Node root;

    Node insert(Node root, int data) {
        if (root == null)
            return new Node(data);
        if (data < root.data)
            root.left = insert(root.left, data);
        else if (data > root.data)
            root.right = insert(root.right, data);
        return root;
    }

    boolean find(Node root, int data) {
        if (root == null)
            return false;
        if (data == root.data)
            return true;
        return data < root.data ? find(root.left, data) : find(root.right, data);
    }

    void inorder(Node root) {
        if (root == null)
            return;
        inorder(root.left);
        System.out.print(root.data + " ");
        inorder(root.right);
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] values = { 5, 3, 7, 1, 4, 6, 8 };
        for (int v : values)
            bst.root = bst.insert(bst.root, v);

        System.out.print("Inorder: ");
        bst.inorder(bst.root);
        System.out.println();
        System.out.println("Find 4: " + bst.find(bst.root, 4));
        System.out.println("Find 9: " + bst.find(bst.root, 9));
    }
}