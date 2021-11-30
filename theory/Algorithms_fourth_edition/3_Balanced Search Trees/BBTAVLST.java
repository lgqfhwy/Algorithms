// Balanced binary tree
import java.util.NoSuchElementException;
public class BBTAVLST<Value> {  // An AVL tree and used for find ith element
    private Node root;  // The root node

    // This class represents an inner node of the AVL tree
    private class Node {
        private int size;   // the key(the index) & number of nodes in subtree
        private Value val;  // the associated value
        private int height; // height of the subtree
        private Node left, right;   // left and right subtree

        public Node(int size, Value val, int height) {
            this.size = size;
            this.val = val;
            this.height = height;
        }
    }

    // Initializes an empty symbol table
    public BBTAVLST() { }

    // Check if the symbol table is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Returns the number of size-value pairs in the symbol table
    public int size() {
        return size(root);
    }

    // Returns the number of nodes in the subtree
    private int size(Node x) {
        if (x == null)  return 0;
        return x.size;
    }

    // Return the height of the internal AVL tree. It is assumed that the 
    // height of an empty tree is -1 and the height of a tree with just one
    // node is 0.
    public int height() {
        return height(root);
    }

    // Returns the height of the subtree.
    private int height(Node x) {
        if (x == null)  return -1;
        return x.height;
    }

    // Retruns the value associated with the given index
    public Value get(int index) {
        if (index < 0 || index >= size())    throw new IllegalArgumentException("argument to get() is illegal");
        Node x = get(root, index);
        if (x == null)  return null;
        return x.val;
    }

    // Returns value associated with the given size in the subtree or
    // null if no such key.
    private Node get(Node x, int size) {
        if (x == null || size < 0)   return null;
        if (x.left != null) {
            int cmp = size - x.left.size;
            if (cmp > 0)
                return get(x.right, cmp - 1);
            else if (cmp < 0)
                return get(x.left, size);
            else 
                return x;
        }
        if (size == 0)
            return x;
        return get(x.right, size - 1);
    }

    public void put(Value val) {
        put(size(), val);
    }

    // Inserts the specified index-value pair into the symbol table, if the symbol
    // table already contains the index, all the index in the symbol table ewhich 
    // equal or higher than the index will plus 1.
    // Delete the specified index (and its associated value) from the symbol table
    // if the specified value if null.
    public void put(int index, Value val) {
        if (index < 0 || index > size())    throw new IllegalArgumentException("first argument to put() is illegal");
        if (val == null) {
            delete(index);
            return;
        }
        root = put(root, index, val);
    }

    private Node put(Node x, int size, Value val) {
        if (x == null)  return new Node(1, val, 0);
        if (x.left != null) {
           int cmp = size - x.left.size;
           if (cmp <= 0) 
               x.left = put(x.left, size, val);
           else 
               x.right = put(x.right, cmp - 1, val);
        }
        else if (size == 0)
            x.left = put(x.left, size, val);
        else 
            x.right = put(x.right, size - 1, val);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // Restores the AVL tree property of the subtree.
    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            if (balanceFactor(x.right) > 0) 
                x.right = rotateRight(x.right);
            x = rotateLeft(x);
        }
        else if (balanceFactor(x) > 1) {
            if (balanceFactor(x.left) < 0)
                x.left = rotateLeft(x.left);
            x = rotateRight(x);
        }
        return x;
    }

    // Returns the balance factor of the subtree. The balance factor is defined
    // as the difference in height of the left subtree and right subtree, in this
    // order. There, a subtree with a balance factor of -1, 0 or 1 has the AVL property
    // since the heights of the two child subtrees differ by at most one.
    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    // Rotates the given subtree to the right.
    private Node rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        y.right = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    // Rotates the given subtree to the left.
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    // Removes the specified index and its associated value from the symbol table
    // (if the index is in the symbol table)
    public void delete(int index) {
        if (index < 0 || index >= size())
            throw new IllegalArgumentException("argument to delete() is illegal");
        root = delete(root, index);
    }

    // Removes the specified index and its associated value from the given
    // subtree
    private Node delete(Node x, int size) {
        if (size < 0)
            throw new IllegalArgumentException("argument to delete() is illegal");
        if (x.left != null) {
            int cmp = size - x.left.size;
            if (cmp < 0)
                x.left = delete(x.left, size);
            else if (cmp > 0)
                x.right = delete(x.right, cmp - 1);
            else {
                if (x.left == null)
                    return x.right;
                else if (x.right == null)
                    return x.right;
                else {
                    Node y = x;
                    x = min(y.right);
                    x.right = deleteMin(y.right);
                    x.left = y.left;
                }
            }
        }
        else if (size == 0)
            return x.right;
        else 
            x.right = delete(x.right, size - 1);

        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // Removes the smallest index 0 and associated value from the symbol table.
    private void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("called deleteMin() with empty symbol table");
        root = deleteMin(root);
    }

    // Removes the smallest index and associated value from the given subtree.
    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // Removes the largest index and associated value from the symbol table.
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("called deleteMax() with empty symbol table");
        root = deleteMax(root);
    }

    // Removes the largest index and associated value from the given subtree.
    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // Retruns the smallest node in the symbol table
    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    // Returns the node with the largest index in the subtree.
    private Node max(Node x) {
        if (x.right == null)
            return x;
        return max(x.right);
    }

    // Returns all vals in the symbol table.
    public Iterable<Value> vals() {
        return valsIndexOrder();
    }

    public Iterable<Value> valsIndexOrder() {
        Queue<Value> queue = new Queue<Value>();
        valsIndexOrder(root, queue);
        return queue;
    }

    private void valsIndexOrder(Node x, Queue<Value> queue) {
        if (x == null)
            return;
        valsIndexOrder(x.left, queue);
        queue.enqueue(x.val);
        valsIndexOrder(x.right, queue);
    }

    public static void main(String[] args) {
        BBTAVLST<String> avl = new BBTAVLST<String>();
        String[] str = {"abc", "def", "rdf", "tfg"};
        for (int i = 0; i < 4; i++) {
            avl.put(str[i]);
        }
        for (String t : avl.vals())
            System.out.print(t + " ");
        System.out.println();
        avl.put(0, "hello");
        for (String t : avl.vals())
            System.out.print(t + " ");
    }

}