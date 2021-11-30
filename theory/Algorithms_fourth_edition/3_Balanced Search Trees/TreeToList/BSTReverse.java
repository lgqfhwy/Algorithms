public class BSTReverse {
    public static void reverse(Node root) {
        if (root == null)   return;
        Node temp = root.small;
        root.small = root.large;
        root.large = temp;
        reverse(root.small);
        reverse(root.large);
    }
    public static void main(String[] args) {
        int[] a = {4, 2, 1, 3, 5};
        Node root = BSTtraversal.reConstruction(a);
        BSTtraversal.printTreePreOrder(root);
        System.out.println();
        BSTtraversal.printTreeInOrder(root);
        System.out.println();
        reverse(root);
        BSTtraversal.printTreePreOrder(root);
        System.out.println();
        BSTtraversal.printTreeInOrder(root);
        System.out.println();

    }
}