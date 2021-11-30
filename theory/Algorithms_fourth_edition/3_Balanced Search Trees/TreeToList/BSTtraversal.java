// Tree traversal with constant extra memory. Describe how to perform an inorder 
// tree traversal with constant extra memory (e.g., no function call stack).
// Hint: on the way down the tree, make the child node point back to the parent 
// (and reverse it on the way up the tree).
public class BSTtraversal {

    public static Node reConstruction(int[] a) {
        Node root = new Node(a[0]);
        int i = 0;
        for (i = 0; i < a.length; i++) {
            if (a[i] > a[0])
                break;
        }
        root.small = reConstruction(a, 1, i - 1);
        root.large = reConstruction(a, i, a.length - 1);
        return root;
    }

    public static Node reConstruction(int[] a, int lo, int hi) {
        if (hi < lo)    return null;
        Node root = new Node(a[lo]);
        int i = 0;
        for (i = lo; i <= hi; i++) {
            if (a[i] > a[lo])
                break;
        }
        root.small = reConstruction(a, lo + 1, i - 1);
        root.large = reConstruction(a, i, hi);
        return root;
    }

    public static void printTreePreOrder(Node root) {
        Node current, pre;
        if (root == null)
            return;
        current = root;
        while (current != null) {
            if (current.small == null) {
                System.out.print(Integer.toString(current.data) + " ");
                current = current.large;
            }
            else {
                pre = current.small;
                while (pre.large != null && pre.large != current)
                    pre = pre.large;

                if (pre.large == null) {
                    pre.large = current;
                    System.out.print(Integer.toString(current.data) + " ");
                    current = current.small;
                }
                else {
                    pre.large = null;
                    //System.out.print(Integer.toString(current.data) + " ");
                    current = current.large;
                }
            }
        }
    }

    public static void printTreeInOrder(Node root) {
        Node current, pre;
        if (root == null)
            return;
        current = root;
        while (current != null) {
            if (current.small == null) {
                System.out.print(Integer.toString(current.data) + " ");
                current = current.large;
            }
            else {
                pre = current.small;
                while (pre.large != null && pre.large != current)
                    pre = pre.large;

                if (pre.large == null) {
                    pre.large = current;
                    //System.out.print(Integer.toString(current.data) + " ");
                    current = current.small;
                }
                else {
                    pre.large = null;
                    System.out.print(Integer.toString(current.data) + " ");
                    current = current.large;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {4, 2, 1, 3, 5};
        Node root = reConstruction(a);
        printTreePreOrder(root);
        System.out.println();
        printTreeInOrder(root);
    }
}