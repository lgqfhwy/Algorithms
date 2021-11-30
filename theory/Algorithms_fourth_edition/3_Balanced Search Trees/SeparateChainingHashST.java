// A symbol table implemented with a separate-chaining hash table.
/**
 *  The {@code SeparateChainingHashST} class represents a symbol table of generic
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
 *  This implementation uses a separate chaining hash table. It requires that
 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
 *  The expected time per put, contains, or remove
 *  operation is constant, subject to the uniform hashing assumption.
 *  The size, and is-empty operations take constant time.
 *  Construction takes constant time.
 */
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACIPY = 4;

    private int n;  // number of key-value pairs
    private int m;  // hash table size
    private SequentialSearchST<Key, Value>[] st;    // array of linked-list symbol table

    // Initializes an empty symbol table.
    public SeparateChainingHashST() {
        this(INIT_CAPACIPY);
    }

    // Initializes an empty symbol table with m chairs.
    // m the initial number of chairs
    public SeparateChainingHashST(int m) {
        this.m = m;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    }

    // resize the hash table to have the given number of chairs,
    // rehashing all of the keys
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key: st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    // hash value between 0 and m - 1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    // private int hash(Key x) {
    //     int t = x.hashCode() & 0x7fffffff;
    //     if (Math.log10(M) / Math.log10(2) < 26)
    //         t = t % primes[Math.log10(M) / Math.log10(2) + 5];
    //     return t % M;
    // }

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

    // Returns the value associated with the specified key in this symbol table.
    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old value
    // with the new value if the symbol table already contains the specified key. Deletes the 
    // specified key (and its associated value) from this symbol table if the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        // double table size if average length of list >= 10
        if (n >= 10 * m)    resize(2 * m);

        int i = hash(key);
        if (!st[i].contains(key))   n++;
        st[i].put(key, val);
    }

    // Removes the specified key and its associated value from this symbol table
    // (if the key is in this symbol table).
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        int i = hash(key);
        if (st[i].contains(key))    n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACIPY && n <= 2 * m)    resize(m / 2);
    }

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key: st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }

    // Unit tests the SeparateChainingHashST data type.
    public static void main(String[] args) {
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s: st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}