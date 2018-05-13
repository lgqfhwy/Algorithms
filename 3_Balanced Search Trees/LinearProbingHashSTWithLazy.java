// Lazy delete for linear probing. Add to LinearProbingHashST a delete() 
// method that deletes a key-value pair by setting the value to null (but 
// not removing the key) and later removing the pair from the table in 
// resize(). Your primary chal- lenge is to decide when to call resize(). 
// Note : You should overwrite the null value if a subsequent put() operation 
// associates a new value with the key. Make sure that your program takes into 
// account the number of such tombstone items, as well as the number of empty 
// positions, in making the decision whether to expand or contract the table.
public class LinearProbingHashSTWithLazy<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;  // number of key-value pairs in the symbol table.
    private int m;  // size of linear probing table.
    private Key[] keys;    // the keys
    private Value[] vals;   // the values
    private boolean deleteJust;    // whether implement delete just now

    // Initializes an empty symbol table.
    public LinearProbingHashSTWithLazy() {
        this(INIT_CAPACITY);
    }

    // Initializes an empty symbol table with the specified initial capacity.
    public LinearProbingHashSTWithLazy(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
        deleteJust = false;
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
        LinearProbingHashSTWithLazy<Key, Value> temp = new LinearProbingHashSTWithLazy<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null && vals[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m = temp.m;
        deleteJust = false;
    }
    // Insert the specified key-value pair into the symbol table, overwriting the old
    // value with the new value if the symbol table already contains the specified key.
    // Deletes the specified key (and its associated value) from this symbol table
    // if the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            deleteJust = true;
            return;
        }
        if (deleteJust || (n >= m / 2)) {
            if (deleteJust) {
                resize(m);
            }
            else {
                resize(2 * m);
            }
        }
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


    // Returns the value associated with the specified key
    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key))
                return vals[i];
        }
        return null;
    }

    // Remove the specified key and its associated value from this symbol table
    // (if the key is in this symbol table)
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;
        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }
        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        deleteJust = true;
        n--;

        // halves size of array if it's 12.5% full or less.
        if (n > 0 && n <= m / 8)
            resize(m / 2);
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

    public static void main(String[] args) {
        LinearProbingHashSTWithLazy<String, Integer> st = new LinearProbingHashSTWithLazy<String, Integer>();
        String[] str = {"s", "b", "e", "q", "a", "c", "d", "t"};
        for (int i = 0; i < str.length; i++) {
            st.put(str[i], i);
        }
        for (String s: st.keys())
            System.out.println(s + " " + st.get(s));
        // print keys
        for (String s: st.keys())
            System.out.print(s + " ");
        System.out.println();
        st.delete("q");
        st.delete("b");
        st.put("a", 9);
        for (String s: st.keys())
            System.out.print(s + " " );

    }


}
