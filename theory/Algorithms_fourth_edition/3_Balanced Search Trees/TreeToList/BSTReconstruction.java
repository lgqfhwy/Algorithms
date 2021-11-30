// BST reconstruction. Given the preorder traversal of a BST (not including null nodes), 
// reconstruct the tree.
public class BSTReconstruction {
    // Using the preorder
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

    // use the level order
    public static void levelOrderReconstruction(int[] a) {
        Node root = new Node(a[0]);
        int k = 1;
        levelOrderReconstruction(a, k, root);
    }

    private static void levelOrderReconstruction(int[] a, int k, Node root) {
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left >= a.length)
            return;
        if (right >= a.length) {
            
        }
        if (a[left] <= root.data && a[right] > root.data) {
            Node small = new Node(a[left])
        }
    }

    public static void printTreeInOrderRecursive(Node root) {
        if (root == null)   return;
        printTreeInOrderRecursive(root.small);
        System.out.print(Integer.toString(root.data) + " ");
        printTreeInOrderRecursive(root.large);
    }

    public static void printTreePreOrderRecursive(Node root) {
        if (root == null)   return;
        System.out.print(Integer.toString(root.data) + " ");
        printTreePreOrderRecursive(root.small);
        printTreePreOrderRecursive(root.large);
    }

    public static void printTreePreOrder(Node root) {
        if (root == null)   return;
        Node parent = root;
        Node large = null;
        Node curr;
        while (parent != null) {
            curr = parent.small;
            if (curr != null) {
                // search for thread
                while (curr != large && curr.large != null)
                    curr = curr.large;
                
                if (curr != large) {
                    // insert thread
                    assert curr.large == null;
                    curr.large = parent;
                    System.out.print(Integer.toString(parent.data) + " ");
                    parent = parent.small;
                    continue;
                }
                else {
                    curr.large = null;
                }
            }
            else {
                System.out.print(Integer.toString(parent.data) + " ");
            }
            large = parent;
            parent = parent.large;
        }

    }
    // Morris Traversal
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
                // Find the inorder predecessor of current
                pre = current.small;
                while (pre.large != null && pre.large != current)
                    pre = pre.large;
                
                // Make current as right child of its inorder predecessor
                if (pre.large == null) {
                    pre.large = current;
                    current = current.small;
                }
                else {
                    // Magic of restoring the tree happens here:
                    // Revert the changes made in if part to restore the original
                    // tree. i.e. fix the right child of predecssor
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
        printTreeInOrderRecursive(root);
        System.out.println();
        printTreePreOrderRecursive(root);
        System.out.println();
        printTreePreOrder(root);
        System.out.println();
        printTreeInOrder(root);
    }
}