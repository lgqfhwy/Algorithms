// Program SeparateChainingLiteHashST.java is similar but does it using an
// explict Node nested class.
// A symbol table implemented with a separate-chaining hash table.
public class SeparateChainingLiteHashST<Key, Value> {
    private int n;  // number of key-value pairs
    private int m;  // hash table size
    private Node[] st;  // array of linked-list 

    // a helper linked list data type
    private static class Node {
        private Object key;
        private Object val;
        private Node next;

        public Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    // create separate chaining hash table
    public SeparateChainingLiteHashST() {
        this(97);
    }

    // create separate chaining hash table with m lists
    public SeparateChainingLiteHashST(int m) {
        this.m = m;
        st = new Node[m];
    }

    // hash value between 0 and m - 1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // return number of key-value pairs in symbol table
    public int size() {
        return n;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // is the key in the symbol table?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with key, null if no such key
    public Value get(Key key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key))
                return (Value) x.val;
        }
        return null;
    }

    // insert key-value pair into the table
    public void put(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        n++;
        st[i] = new Node(key, val, st[i]);
    }

    // delete key (and associated value) from the symbol table
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        int i = hash(key);
        if (st[i].key.equals(key)) {
            st[i] = st[i].next;
            return;
        }
        else {
            Node first = st[i].next;
            Node second = st[i];

            while (first != null) {
                if (first.key.equals(key)) {
                    break;
                }
                first = first.next;
                second = second.next;
            }
            if (first == null) {
                return;
            }
            second = first.next;
            return;
        }
    }

    // return all keys as an Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Node x = st[i]; x != null; x = x.next) {
                queue.enqueue((Key) x.key);
            }
        }
        return queue;
    }

    // Unit test client
    public static void main(String[] args) {
        SeparateChainingLiteHashST<String, Integer> st = new SeparateChainingLiteHashST<String, Integer>(97);
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print keys
        for (String s : st.keys()) 
            StdOut.println(s + " " + st.get(s)); 
    }
}