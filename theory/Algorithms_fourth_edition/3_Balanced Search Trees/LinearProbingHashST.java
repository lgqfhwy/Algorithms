// Symbol-table implementation with linear-probing hash table.
/**
 *  The {@code LinearProbingHashST} class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty methods.
 *  It also provides a keys method for iterating over all of the keys.
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  This implementation uses a linear probing hash table. It requires that
 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
 *  The expected time per put, contains, or remove
 *  operation is constant, subject to the uniform hashing assumption.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 */
public class LinearProbingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // number of key-value pairs in the symbol table
    private int m;  // size of linear probing table
    private Key[] keys;    // the keys
    private Value[] vals;   // the values

    // Initializes an empty symbol table.
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    // Initializes an empty symbol table with the specified initial capacity.
    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return n;
    }

    // Returns true if this symbol table is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns true if this symbol table contains the specified key.
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash functions for keys - returns value between 0 and M - 1.
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // resize the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
    }

    // Insert the specified key-value pair into the symbol table, overwriting the old
    // value with the new value if the symbol table already contains the specified key.
    // Deletes the specified key (and its associated value) from this symbol table
    // if the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        // double table size of 50% full
        if (n >= m / 2)    resize(2 * m);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
    }

    // Last come, first served linear probing.  exch item is inserted where it arrives; if
    // the cell is already occupied, then that item moves one entry to the right (where the
    // rule is repreated.)
    public void putLastComeFirstServed(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        // double table size of 50% full
        if (n >= m / 2)    resize(2 * m);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        int k = hash(key);
        for (int h = i; h > k; h--) {
            keys[h] = keys[h - 1];
            vals[h] = vals[h - 1];
        }
        keys[k] = key;
        vals[i] = val;
        n++;
    }

    // Robin Hood linear probing. When an item probes a cell that is already occupied, the item
    // (of the two) with the larger current displacement gets the cell and the other item is
    // moved one entry to the right (where the rule is repeated).
    public void putLargerFirstServed(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        // double table size of 50% full
        if (n >= m / 2)    resize(2 * m);
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        Key xkey = key;
        Value xval = val;
        Key ykey = key;
        Value yval = val;
        for (int k = hash(key); k < i; k++) {
            if (large(xkey, keys[k])) {
                ykey = keys[k];
                yval = vals[k];
                keys[k] = xkey;
                vals[k] = xval;
                xkey = ykey;
                xval = yval;
            }
        }
        keys[i] = xkey;
        vals[i] = xval;
        n++;
    }

    private boolean large(Key key1, Key key2) {
        return ((Comparable) key1).compareTo((Comparable) key2) >= 0;
    }

    // Returns the value associated with the specified key
    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key))
                return vals[i];
        }
        return null;
    }

    // Computes the average cost of a search hit in the table, assuming that each key in the 
    // table is equally likely to be sought;
    public double averageCostOfSearchHit() {
        int sum = 0;
        for (int i = 0; i < m; i++) {
            int k = hash(keys[i]);
            sum += (i - k + 1);
        }
        return sum * 1.0 / n;
    }

    // Computes the average cost of a search miss in the table, assuming a random
    // hash function.
    public double averageCostOfSearchMiss() {
        int sum = 0;
        int i = 0;
        while (i < m) {
            if (keys[i] != null) {
                int k = 0;
                while (keys[i] != null) {
                    i++;
                    k++;
                }
                sum += k * k;
                continue;
            }
            i++;
        }
        return sum * 1.0 / n;
    }

    // Remove the specified key and its associated value from this symbol table
    // (if the key is in this symbol table)
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key))    return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
        n--;
        // halves size of array if it's 12.5% full or less.
        if (n > 0 && n <= m / 8)    resize(m / 2);
        assert check();
    }

    // Return all keys in this symbol table as an Iterable.
    // To iterate over all of the keys in the symbol table named st.
    // use the foreach notation: for (Key key: st.keys()).
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null)
                queue.enqueue(keys[i]);
        }
        return queue;
    }

    // integrity check - don't check after exch put() because
    // integrity not maintained during a delete()
    private boolean check() {
        // check that hash table is at most 50% full
        if (m < 2 * n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }
        // check that exch key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null)
                continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

    // Unit tests
    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        // print keys
        for (String s: st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}