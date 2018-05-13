// Symbol table implementation with binary search in an ordered array.
// The BST class represents an ordered symbol table of generic key-value pairs.
// It supports the ususal put, get, contains, delete, size, and is-empty methods.
// It also provides ordered methods for finding the minimum, maximum, floor, select,
// and ceiling.
// It also provides a keys method for iterating over all of the keys.
// A symbol table implements the associative array abstraction:
// when associating a value with a key that is already in the symbol table,
// the convention is to replace the old value with the new value.
// Unlike {java.util.Map}, this class uses the convention that values cannot be
// null -- setting the value associated with a key to null is equivalent to deleting 
// the key from the symbol table.

// This implementation uses a sorted array. It requires that the key type implements the
// Comparable interface and calls the compareTo() and method to compare two keys. It does not
// call either equals() or hashCode().
// The put and remove operations each take linear time in the worst case; the contains, ceiling, floor
// and rank operations take logarithmic time; the size, is-empty, minimum, maximum, and select
// operations take constant time. Construction takes constant time.
import java.util.NoSuchElementException;
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    // Initializes an empty symbol table.
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    // Initializes an empty symbol table with the specified initial capacity.
    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Comparable[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return n;
    }

    // Returns true if this symbol table is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Does this symbol table contain the given key?
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to contains() is null.");
        return get(key) != null;
    }

    // Returns the value associated with the given key in this symbol table.
    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty())     return null;
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0)   return vals[i];
        return null;
    }

    // Returns the number of keys in this symbol table strictly less than key.
    public int rank(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to rank() is null");
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if      (cmp < 0)   hi = mid - 1;
            else if (cmp > 0)   lo = mid + 1;
            else                return mid;
        }
        return lo;
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old
    // value with the new value if the symbol table already contains the specified key.
    // Deletes the specified key (and its associated value) from this symbol table 
    // if the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        int i = rank(key);
        // key is already in table
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        // Insert new key-value pair
        if (n == keys.length)   resize(2 * keys.length);
        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;
        assert check();
    }

    // Removes the specified key and associated value from this symbol table.
    // (if the key is in the symbol table).
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty())     return;
        // compute rank
        int i = rank(key);
        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }
        n--;
        keys[n] = null;     // to avoid loitering
        vals[n] = null;
        // resize if 1/4 full
        if (n > 0 && n == keys.length / 4)      resize(keys.length / 2);
        assert check();
    }

    // Removes the smallest key and associated value from this symbol table
    public void deleteMin() {
        if (isEmpty())     throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    // Removes the largest key and associated value from this symbol table.
    public void deleteMax() {
        if (isEmpty())     throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    // Ordered symbol table methods.

    // Returns the smallest key in this symbol table.
    public Key min() {
        if (isEmpty())     throw new NoSuchElementException("called min() with empty symbol table");
        return keys[0];
    }

    // Returns the largest key in this symbol table.
    public Key max() {
        if (isEmpty())     throw new NoSuchElementException("called max() with empty symbol table");
        return keys[n - 1];
    }

    // Return the kth smallest key in this symbol table.
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    // Returns the largest key in this symbol table less than or equal to key
    public Key floor(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to floor() is null");
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0)      return keys[i];
        if (i == 0)     return null;
        else            return keys[i - 1];
    }

    // Returns the smallest key in this symbol table greater than or equal to key
    public Key ceiling(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to ceiling() is null");
        int i = rank(key);
        if (i == n)     return null;
        else            return keys[i];
    }

    // Returns the number of keys in this symbol table in the specified range.
    public int size(Key lo, Key hi) {
        if (lo == null)     throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null)     throw new IllegalArgumentException("second argument to size() is null");
        if (lo.compareTo(hi) > 0)   return 0;
        if (contains(hi))   return rank(hi) - rank(lo) + 1;
        else                return rank(hi) - rank(lo);
    }

    // Returns all keys in this symbol table as an Iterable.
    // To itrable over all of the keys in the symbol table named st,
    // use the foreach notation: for (Key key: st.keys()).
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    // Returns all keys in this symbol table in the given range,
    // as an Iterable.
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null)    throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null)    throw new IllegalArgumentException("second argument to keys() is null");
        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0)   return queue;
        for (int i = rank(lo); i < rank(hi); i++)
            queue.enqueue(keys[i]);
        if (contains(hi))    queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    // Check internal invariants.
    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[i - 1]) < 0)
                return false;
        }
        return true;
    }

    // check that rank(select(i)) = i;
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i)))
                return false;
        }
        for (int i = 0; i < size(); i++) {
            if (keys[i].compareTo(select(rank(keys[i]))) != 0)
                return false;
        }
        return true;
    }

    // Unit tests the BinarySearchST data type.
    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s: st.keys())
            StdOut.println(s + " " + st.get(s));
    }

}