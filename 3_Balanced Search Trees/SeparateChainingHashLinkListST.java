// Develop an alternate implementation of SeparateChainingHashST that 
// directly uses the linked-list code from SequentialSearchST.
public class SeparateChainingHashLinkListST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;    // number of key-value pairs
    private int m;    // hash table size
    private Node[] st;  // array of node

    private static class Node {
        Comparable key;
        Object val;
        int num;
        Node next;

        public Node(Comparable key, Object val, int num, Node next) {
            this.key = key;
            this.val = val;
            this.num = num;
            this.next = next;
        }
    }

    // Initializes an empty symbol table
    public SeparateChainingHashLinkListST() {
        this(INIT_CAPACITY);
    }

    // Initializes an empty symbol table with m chairs.
    // m the initial number of chairs.
    public SeparateChainingHashLinkListST(int m) {
        this.m = m;
        n = 0;
        st = new Node[m];
    }

    // resize the hash table to have the given number of chairs,
    // rehashing all of the keys.
    private void resize(int chairs) {
        Node[] temp = new Node[chairs];
        for (int i = 0; i < m; i++) {
            temp[i] = st[i];
        }
        this.m = chairs;
        this.st = temp;
    }

    // hash value between 0 and m - 1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return n;
    }

    // Returns true if this symbol table is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    // Returns true if this symbol table contains the specified key.
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // Returns the value associated with the specified key in this symbol table.
    public Value get(Key key) {
        if (key == null)   throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (eq(x.key, key)) {
                return (Value)x.val;
            }
        }
        return null;
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
        if (!contains(key)) {
            n++;
            Node x = new Node(key, val, n, st[i]);
            st[i] = x;
        }
    }

    // Removes the specified key and its associated value from this symbol table
    // (if the key is in this symbol table).
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        int i = hash(key);
        if (contains(key)) {
            n--;
            if (st[i].key.equals(key)) {
                st[i] = st[i].next;
            }
            else {
                Node x1 = st[i];
                Node x2 = st[i].next;
                while (x2 != null) {
                    if (eq(x2.key, key)) {
                        break;
                    }
                    x1 = x1.next;
                    x2 = x2.next;
                }
                x1.next = x2.next;
            }
        }
        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2 * m)    resize(m / 2);
    }

    public void deleteKeysGreaterThan(int k) {
        for (int i = 0; i < m; i++) {
            for (Node x = st[i]; x != null; x = x.next) {
                if (x.num > k) {
                    delete((Key)x.key);
                }
            }
        }
    }

    // return keys in symbol table as an Iterable.
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Node x = st[i]; x != null; x = x.next) {
                queue.enqueue((Key)x.key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        SeparateChainingHashLinkListST<Integer, String> st = new SeparateChainingHashLinkListST<Integer, String>();
        String[] str = {"a", "b", "x", "e", "r", "t"};
        for (int i = 0; i < 6; i++) {
            st.put(i, str[i]);
        }
        for (Integer s: st.keys())
            System.out.println(s + " " + st.get(s));
    }




}