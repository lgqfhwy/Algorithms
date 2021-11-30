// A binary tree is threaded by making all right child pointers that would normally be
// null point to the inorder successor of the node (if it exists), and all left child 
// pointers that would normally be null point to the inorder predecessor of the node.

public class ThreadBinaryTree {
    private Node root;
    private int size;
    private Node pre;

    public class Node {
        private int data;
        private Node left;
        private boolean leftIsThread;   // Is true if left child is thread
        private Node right;
        private boolean rightIsThread;  // Is true if right child is thread

        public Node(int data) {
            this.data = data;
        }
    }

    public ThreadBinaryTree() { 
        this.root = null;
        this.size = 0;
        this.pre = null;
    }

    public ThreadBinaryTree(int[] data) {
        this.pre = null;
        this.size = data.length;
        this.root = createTree(data, 1);
    }

    public Node createTree(int[] data, int index) {
        if (index > data.length) {
            return null;
        }
        Node node = new Node(data[index - 1]);
        node.left = createTree(data, 2 * index);
        node.right = createTree(data, 2 * index + 1);
        return node;
    }

    public void inThread(Node root) {
        if (root != null) {
            inThread(root.left);
            if (root.left == null) {
                root.leftIsThread = true;
                root.left = pre;
            }
            if (pre != null && pre.right == null) {
                pre.rightIsThread = true;
                pre.right = root;
            }
            pre = root;
            inThread(root.right);
        }
    }

    public void inThreadList(Node root) {
        if (root != null) {
            while (root != null && !root.leftIsThread) {
                root = root.left;
            }
            do {
                System.out.print(root.data + " ");
                if (root.rightIsThread) {
                    root = root.right;
                }
                else {
                    root = root.right;
                    while (root != null && !root.leftIsThread) {
                        root = root.left;
                    }
                }
            } while (root != null);
        }
    }

    public static void main(String[] args) {
        int[] a= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ThreadBinaryTree tree = new ThreadBinaryTree(a);
        tree.inThread(tree.root);
        tree.inThreadList(tree.root);
    }



}