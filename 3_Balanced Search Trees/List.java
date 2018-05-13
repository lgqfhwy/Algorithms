// List. Implement the following list operations: size(), addFront(item), 
// addBack(item), delFront(item), delBack(item), contains(item), delete(item), 
// add(i, item), delete(i), iterator(). All operations should be efficient (logarithmic). 
// Hint: use two symbol tables, one to find the ith element in the list efficiently, 
// and the other to efficiently search by item. Java's List interface contains these 
// methods, but does not supply any implementation that supports all ops efficiently.
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
public class List<Item> implements Iterable<Item> {

    private Node root;  // to find ith item in the list efficiently
    private HashMap<Item, Node> hm;  // efficiently search by item

    // This class represents an inner node of the AVL tree.
    private class Node {
        private int size;   // the key (the index) & number of nodes
        private Item val;  // the associated value
        private int height; // height of the subtree
        private Node left, right;   // left and right subtree
        private Node parent;    // point to parent

        public Node(int size, Item val, int height) {
            this.size = size;
            this.val = val;
            this.height = height;
        }
    }

    // Initializes an empty list 
    public List() {
        hm = new HashMap<Item, Node>();
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return hm.isEmpty();
    }

    // Returns the number of items in list
    public int size() {
        return hm.size();
    }

    public int sizeTree() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        return x.size;
    }

    // is item in the list ?
    public boolean contains(Item item) {
        return hm.containsKey(item);
    }

    // Return the height of the internal AVL tree. It is assumed that the 
    // height of an empty tree is -1 and the height of a tree with just one
    // node is 0.
    public int height() {
        return height(root);
    }

    // Retruns the height of the subtree
    private int height(Node x) {
        if (x == null)
            return -1;
        return x.height;
    }

    // Retruns the value associated with the given index
    public Item getVal(int index) {
        if (index < 0 || index >= size())
            throw new IllegalArgumentException("argument to get() is illegal");
        Node x = getVal(root, index);
        if (x == null)
            return null;
        return x.val;
    }

    // Return value associated with the given size in the subtree or
    // null if no such key.
    private Node getVal(Node x, int size) {
        if (x == null)
            return null;
        if (x.left != null) {
            int cmp = size - x.left.size;
            if (cmp > 0)
                return getVal(x.right, cmp - 1);
            else if (cmp < 0)
                return getVal(x.left, size);
            else
                return x;
        }
        else if (size == 0) {
            return x;
        }
        else {
            return getVal(x.right, size - 1);
        }
    }

    // Returns the index associated with the given value
    public int getIndex(Item val) {
        Node x = hm.get(val);
        int sum = 0;
        return getIndex(x, sum);
    }

    public int getIndex(Node x, int sum) {
        if (x == root)
            return sum;
        if (x.parent.right == x) {
            sum += 1;
            if (x.left != null)
                sum += x.left.size;
            if (x.parent.left != null)
                sum += x.parent.left.size;
            return getIndex(x.parent, sum);
        }
        else {
            if (x.left != null)
                sum += x.left.size;
            return getIndex(x.parent, sum);
        }
    }

    // add item as the ith in the list
    public void add(int i, Item item) {
        if (i < 0 || i > size())
            throw new IllegalArgumentException("first argument to add() is illegal");
        if (item == null) {
            delete(i);
            return;
        }
        root = put(root, i, item);
        root.parent = root;
    }

    private Node put(Node x, int size, Item item) {
        if (x == null) {
            Node p = new Node(1, item, 0);
            hm.put(item, p);
            return p;
        }
        if (x.left != null) {
            int cmp = size - x.left.size;
            if (cmp <= 0)
                x.left = put(x.left, size, item);
            else
                x.right = put(x.right, cmp - 1, item);
        }
        else if (size == 0)
            x.left = put(x.left, size, item);
        else
            x.right = put(x.right, size - 1, item);
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        if (x.left != null)
            x.left.parent = x;
        if (x.right != null)
            x.right.parent = x;
        return balance(x);
    }

    // add item to the front
    public void addFront(Item item) {
        add(0, item);
    }

    // add item to the back
    public void addBack(Item item) {
        add(size(), item);
    }

    // add item to the back
    public void add(Item item) {
        add(size(), item);
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
        y.parent = x.parent;
        x.left = y.right;
        y.right = x;
        y.right.parent = y;
        if (x.left != null)
            x.left.parent = x;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    // Rotates the given subtree to the left.
    private Node rotateLeft(Node x) {
        Node y = x.right;
        y.parent = x.parent;
        x.right = y.left;
        y.left = x;
        y.size = x.size;
        y.left.parent = y;
        if (x.right != null)
            x.right.parent = x;
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
                hm.remove(x.val);
                if (x.left == null)
                    return x.right;
                else if (x.right == null)
                    return x.left;
                else {
                    Node y = x;
                    x = min(y.right);
                    x.right = deleteMin(y.right);
                    hm.put(x.val, x);
                    x.left = y.left;
                }
            }
        }
        else if (size == 0) {
            hm.remove(x.val);
            return x.right;
        }
        else 
            x.right = delete(x.right, size - 1);
        
        if (x.left != null)
            x.left.parent = x;
        if (x.right != null)
            x.right.parent = x;
        
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // remove from the front
    public Item deleteFront() {
        Item item = min();
        deleteMin();
        return item;
    }

    // remove from the back
    public Item deleteBack() {
        Item item = max();
        deleteMax();
        return item;
    }

    // remove item from the list
    public void delete(Item item) {
        int index = getIndex(item);
        delete(index);
    }

    // Removes the smallest index 0 and associated value from the symbol table
    private void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("called deleteMin() with empty symbol table");
        root = deleteMin(root);
    }

    // Removes the smallest index and associated value from the given subtree.
    private Node deleteMin(Node x) {
        if (x.left == null) {
            hm.remove(x.val);
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.left.parent = x;
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

    // Removes the largest index and associated value from the given subtree
    private Node deleteMax(Node x) {
        if (x.right == null) {
            hm.remove(x.val);
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.right.parent = x;
        x.size = 1 + size(x.left) + size(x.right);
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    // Returns the smallest index with item in the symbol table
    public Item min() {
        if (root == null)
            return null;
        return min(root).val;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    // Returns the largest index with item in the symbol table
    public Item max() {
        if (root == null)
            return null;
        return max(root).val;
    }

    public Node max(Node x) {
        if (x.right == null) 
            return x;
        return max(x.right);
    }

    // Returns an iterator that iterates over the items in the symbol table
    public Iterator<Item> iterator() {
        return new ListIterator<Item>();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        int i;
        public ListIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < size();
        }

        public void remove() { }

        public Item next() {
            Object item = getVal(i);
            i++;
            return (Item)item;
        }



    }

    public static void main(String[] args) {
        List<String> list = new List<String>();
        String[] str = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            list.add(str[i]);
        }
        for (String t : list)
            System.out.print(t + " ");
        System.out.println();
        System.out.println(list.size() + " ###");
        list.delete(1);
        System.out.println(list.size() + " ###");
        System.out.println(list.sizeTree() + " ###");
        for (String t : list)
            System.out.print(t + " ");
        System.out.println();
    }
}