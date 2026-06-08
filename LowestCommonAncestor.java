public class LowestCommonAncestor {
    static class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
        }
    }

    static Node insert(Node root, int data) {
        if (root == null)
            return new Node(data);
        if (data < root.data)
            root.left = insert(root.left, data);
        else if (data > root.data)
            root.right = insert(root.right, data);
        return root;
    }

    static int lca(Node root, int a, int b) {
        if (root == null)
            return -1;
        if (a < root.data && b < root.data)
            return lca(root.left, a, b);
        if (a > root.data && b > root.data)
            return lca(root.right, a, b);
        return root.data;
    }

    public static void main(String[] args) {
        Node root = null;
        for (int v : new int[] { 5, 3, 7, 1, 4, 6, 8 })
            root = insert(root, v);

        System.out.println("LCA(1, 4): " + lca(root, 1, 4));
        System.out.println("LCA(6, 8): " + lca(root, 6, 8));
        System.out.println("LCA(1, 8): " + lca(root, 1, 8));
    }
}