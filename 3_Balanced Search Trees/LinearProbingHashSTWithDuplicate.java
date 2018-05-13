// Modify LinearProbingHashST to keep duplicate keys in the table. Return any
// value associated with the given key for get(), and remove all items in the
// table that have keys equal to the given key for delete().
public class LinearProbingHashSTWithDuplicate<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // number of key-value pairs in the symbol table
    private int m;  // size of linear probing table
    private Key[] keys;    // the keys
    private Value[] vals;   // the values

    // Initializes an empty symbol table.
    public LinearProbingHashSTWithDuplicate() {
        this(INIT_CAPACITY);
    }

    // Initializes an empty symbol table with the specified initial capacity.
    public LinearProbingHashSTWithDuplicate(int capacity) {
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

    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        int i =  hash(key);
        for ( ; i < m; i++) {
            if (keys[i] != null && keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    // resize the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashSTWithDuplicate<Key, Value> temp = new LinearProbingHashSTWithDuplicate<Key, Value>(capacity);
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
            if (keys[i].equals(key) && vals[i].equals(val)) {
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
            if (keys[i].equals(key) && vals[i].equals(val)) {
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


    // Returns the value associated with the specified key
    public Iterable<Value> getAll(Key key) {
        Queue<Value> queue = new Queue<Value>();
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                while (keys[i] != null && keys[i].equals(key)) {
                    queue.enqueue(vals[i]);
                    i = (i + 1) % m;
                    //StdOut.println(vals[i]);
                }
                return queue;
            }
        }
        return null;
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
        while (key.equals(keys[i])) {
            keys[i] = null;
            vals[i] = null;
            i = (i + 1) % m;
        }

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
    }

    // Return all keys in this symbol table as an Iterable.
    // To iterate over all of the keys in the symbol table named st.
    // use the foreach notation: for (Key key: st.keys()).
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null)
                queue.enqueue(keys[i]);
                i++;
                while (i < m && keys[i] != null && keys[i-1] != null && keys[i].equals(keys[i - 1]))
                    i++;
        }
        return queue;
    }


    // Unit tests
    public static void main(String[] args) {
        LinearProbingHashSTWithDuplicate<String, Integer> st = new LinearProbingHashSTWithDuplicate<String, Integer>();
        System.out.println("Hello");
        for (int i = 0; i < 10; i++) {
            st.put("Hello", i);
        }
        System.out.println("Hi");
        System.out.println(st.size() + "####");

        StdOut.println();
        // print keys
        for (String s: st.keys()) {
            System.out.println("HiHi");
            StdOut.print(s + " ");

            for (Integer t : st.getAll(s)) {
                StdOut.print(t + " ");
            }
        }
        StdOut.println();
        StdOut.println(st.getAll("Hello"));
    }
}