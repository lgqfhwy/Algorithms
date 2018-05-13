// The BST class represents an ordered symbol table of generic 
// key-value pairs.
// It supports the usual put, get, contains, delete, size, and is-empty methods.
// It also provides ordered methods for finding the minimum, maximum, floor, and ceiling.
// It also provides a keys method for iterating over all of the keys.
// A symbol table implements the associative array abstration:
// when associating a value with a key that is already in the symbol table,
// the convention is to replace the old value with the new value.
// Unlike {java.util.Map}, this class uses the convention that values cannot be null
// -- setting the value associated with a key to null is equivalent to deleting the key
// from the symbol table.

// This implemetation uses an unbalanced binary search tree. It requires that the key
// type implements the Comparable interface and calls the compareTo() and method to
// compare two keys. It does not call either equals() or hashCode().

// The implementation uses an unbalanced binary search tree. It requires that
// the key type implements the Comparable interface and calls the compareTo() and
// method to compare two keys. It does not call either equals or hashCode().
// The put, contains, remove, minimum, maximum, ceiling, floor, select, and rank
// operations exch take linear time in the worst case, if the tree becomes unbalanced.
// The size and is-empty operations take constant time.
// Construction takes constant time.
import java.util.NoSuchElementException;


public class BST<Key extends Comparable<Key>, Value> {
    private Node root;     // root of BST
    private class Node {
        private Key key;    // sorted by key.
        private Value val;  // associated data
        private Node left, right;   // left and right subtree
        private int size;    // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    // Initializes an empty symbol table
    public BST() { }

    // Returns true if this symbol table is empty
    // false otherwise
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns the number of key-value pairs in this symbol table
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null)     return 0;
        else               return x.size;
    }

    // Does this symbol table contain the given key?
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // Returns the value associated with the given key.
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null)    throw new IllegalArgumentException("called get() with a null key");
        if (x == null)     return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   return get(x.left, key);
        else if (cmp > 0)   return get(x.right, key);
        else                return x.val;    
    }

    public Value getNonrecursive(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)    x = x.left;
            else if (cmp > 0)   x = x.right;
            else if (cmp == 0)  return x.val;
        }
        return null;
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old
    // value with the new value if the symbol table already contains the specified key.
    // Delete the specified key (and its associated value) from this symbol table if
    // the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("called put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)  return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)    x.left = put(x.left, key, val);
        else if (cmp > 0)    x.right = put(x.right, key, val);
        else                 x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void putNonrecursive(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("called put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        if (isEmpty()) {
            root = new Node(key, val, 1);
            return;
        }
        if (get(key) != null) {
            Node x = root;
            while (x != null) {
                int cmp = key.compareTo(x.key);
                if (cmp < 0)    x = x.left;
                else if (cmp > 0)   x = x.right;
                else {
                    x.val = val;
                }
            }
        }
        else {
            Node x = root;
            while (x != null) {
                x.size++;
                //StdOut.println("&&&" + x.key);
                int cmp = key.compareTo(x.key);
                if (cmp < 0) {
                    if (x.left == null) {
                        x.left = new Node(key, val, 1);
                        return;
                    }
                    x = x.left;
                }
                else if (cmp > 0)  {
                    if (x.right == null) {
                        x.right = new Node(key, val, 1);
                        return;
                    }
                    x = x.right;
                }
            }
        }
    }

    // Removes the smallest key and associated value from the symbol table
    public void deleteMin() {
        if (isEmpty())     throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null)    return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    // Removes the largest key and associated value from the symbol table
    public void deleteMax() {
        if (isEmpty())     throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null)    return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x; 
    }

    // Remove the specified key and its associated value from this symbol table.
    // (if the key is in this symbol table)
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("called delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null)  return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)    x.left = delete(x.left, key);
        else if (cmp > 0)   x.right = delete(x.right, key);
        else {
            if (x.right == null)    return x.left;
            if (x.left == null)     return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    // Returns the smallest key in the symbol table.
    public Key min() {
        if (isEmpty())     throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)    return x;
        else                   return min(x.left);
    }

    public Key minNonrecursive() {
        if (isEmpty())     throw new NoSuchElementException("called min() with empty symbol table");
        Node x = root;
        while (x.left != null)
            x = x.left;
        return x.key;
    }

    // Returns the largest key in the symbol table
    public Key max() {
        if (isEmpty())     throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)    return x;
        else                    return max(x.right);
    }

    public Key maxNonrecursive() {
        if (isEmpty())
            throw new NoSuchElementException("called max() with empty symbol table");
        Node x = root;
        while (x.right != null)
            x = x.right;
        return x.key;
    }

    // Returns the largest key in the symbol table less than or equal to key.
    public Key floor(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty())      throw new NoSuchElementException("called floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null)  return null;
        else            return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null)     return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp < 0)    return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null)  return t;
        else            return x;
    }

    public Key floorNonrecursive(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty())
            throw new NoSuchElementException("called floorNonrecursive() with empty symbol table");
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)   return x.key;
            else if (cmp < 0) {
                if (x.left == null) {
                    return null;
                }
                x = x.left;
            }
            else {
                if (x.right == null) {
                    return null;
                }
                if (key.compareTo(min(x.right).key) < 0) {
                    return x.key;
                }
                x = x.right;
            }
        }
        return null;
    }

    // Returns the smallest key in the symbol table greater than or equal to key
    public Key ceiling(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty())      throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null)     return null;
        else               return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null)     return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)   return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null)     return t;
            else               return x;
        }
        return ceiling(x.right, key);
    }

    public Key ceilingNonrecursive(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to ceilingNonrecursive() is null");
        if (isEmpty())    throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)   return x.key;
            else if (cmp > 0) {
                if (x.right == null) {
                    return null;
                }
                x = x.right;
            }
            else {
                if (x.left == null) {
                    return null;
                }
                if (key.compareTo(max(x.left).key) > 0) {
                    return x.key;
                }
                x = x.left;
            }
        }
        return null;
    }

    // Return the kth smallest key in the symbol table
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k
    private Node select(Node x, int k) {
        if (x == null)     return null;
        int t = size(x.left);
        if      (t > k)     return select(x.left, k);
        else if (t < k)     return select(x.right, k - t - 1);
        else                return x;
    }

    public Key randomKey() {
        int num = (int)(Math.random() * size());
        return select(num);
    }

    public Key selectNonrecursive(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        Node x = root;
        int num = k;
        while (x != null) {
            int t = size(x.left);
            if (t > num)
                x = x.left;
            else if (t < num) {
                x = x.right;
                num = num - t - 1;
            }
            else {
                return x.key;
            }
        }
        return null;
    }

    // Return the number of keys in the symbol table strictly less than key.
    public int rank(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null)    return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)   return rank(key, x.left);
        else if (cmp > 0)   return 1 + size(x.left) + rank(key, x.right);
        else                return size(x.left);
    }

    public int rankNonrecursive(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to rank() is null");
        int sizeNum = 0;
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left == null) {
                    return sizeNum;
                }
                x = x.left;
            }
            else if (cmp > 0) {
                sizeNum += 1 + size(x.left);
                if (x.right == null) {
                    return sizeNum;
                }
                x = x.right;
            }
            else {
                sizeNum += size(x.left);
                return sizeNum;
            }
        }
        return sizeNum;
    }

    // Returns all keys in the symbol table as an Iterable.
    // To iterate over all of the keys in the symbol table named st,
    // use the foreach notation: for (key key: st.keys())
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    // Returns all keys in the symbol table in the given range,
    // as an Iterable
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null)    throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null)    throw new IllegalArgumentException("second argument to keys() is null");
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null)     return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)  keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)   queue.enqueue(x.key);
        if (cmphi > 0)    keys(x.right, queue, lo, hi);
    }

    // Returns the number of keys in the symbol table in the given range.
    public int size(Key lo, Key hi) {
        if (lo == null)    throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null)    throw new IllegalArgumentException("second argument to size() is null");
        if (lo.compareTo(hi) > 0)   return 0;
        if (contains(hi))   return rank(hi) - rank(lo) + 1;
        else                return rank(hi) - rank(lo);
    }

    // Returns the height of the BST (for debugging).
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null)  return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    

    // Returns the keys in the BST in level order (for debugging).
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null)  continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    // check integrity of BST data structure.
    private boolean check() {
        if (!isBST())   StdOut.println("Not in symmetric order");
        if (!isSizeConsistent())    StdOut.println("Subtree counts not consistent");
        if (!isSizeConsistent())    StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null)  return true;
        if (min != null && x.key.compareTo(min) <= 0)   return false;
        if (max != null && x.key.compareTo(max) >= 0)   return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null)     return true;
        if (x.size != size(x.left) + size(x.right) + 1)
            return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    public boolean isBinaryTree(Node x) {
        if (x == null)    return true;
        if (x.size != size(x.left) + size(x.right) + 1)
            return false;
        return isBinaryTree(x.left) && isBinaryTree(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i)))
                return false;
        }
        for (Key key: keys()) {
            if (key.compareTo(select(rank(key))) != 0)
                return false;
        }
        return true;
    }

    // Order check. Write a recursive method isOrdered() that takes a Node and two keys min and max
    // as arguments and returns true if all the keys in the tree are between min and max; min and max
    // are indeed the smallest and largest keys in the tree, respectively; and the BST ordering property
    // holds for all keys in the tree; false otherwise.
    public boolean isOrdered(Node x, Key min, Key max) {
        Key minNum = min(x).key;
        Key maxNum = max(x).key;
        return minNum.compareTo(min) <= 0 && maxNum.compareTo(max) >= 0;
    }

    // Equal key check. Take a Node as argument and returns true if there are no
    // equal keys in the binary tree rooted at the argument node.
    public boolean hasNoDuplicates() {
        return hasNoDuplicates(root);
    }

    public boolean hasNoDuplicates(Node x) {
        if (x == null)  return true;
        if (x.left != null) {
            if (x.key.compareTo(x.left.key) == 0) {
                return false;
            }
        }
        return hasNoDuplicates(x.left) && hasNoDuplicates(x.right);
    }

    private boolean isBSTMyself() {
        if (!isBinaryTree(root))    return false;
        if (!isOrdered(root, min(), max()))     return false;
        if (!hasNoDuplicates(root))    return false;
        return true;
    }

    // Unit tests the BST data type
    public static void main(String[] args) {
        BST<String, Integer> st = new BST<String, Integer>();
        String[] str = {"word", "sad", "happy", "people", "hard", "strong", "haha"};
        for (int i = 0; i < 7; i++) {
            String key = str[i];
            st.putNonrecursive(key, i);
            //StdOut.println(st.isEmpty() + "##");
        }
        for (String s: st.levelOrder())
            StdOut.println(s + " " + st.get(s));
        StdOut.println(st.size() + "##");
        for (String s: st.keys())
            StdOut.println(s + " " + st.get(s));
    }

}  