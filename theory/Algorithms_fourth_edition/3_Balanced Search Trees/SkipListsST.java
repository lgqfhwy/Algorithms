// Skip lists are a probabilistic alternative to balance trees. Skip lists are 
// balanced by consulting a random number generator. Although skip lists have 
// bad worst-case performance, no input sequence consistently produces the 
// worst-case performance (much like quicksort when the pivot element is
// chosen randomly). It is very unlikely a skip list data structure will be
// significantly unbalanced.

import java.util.NoSuchElementException;
public class SkipListsST<Key extends Comparable<Key>, Value> {
    private Node<Key, Value> head;
    private int n;  // the number of Node<Key, Value>s
    private int level;
    private int maxLevel;

    private static final double p = 0.5;

    private class Node<Key extends Comparable<Key>, Value> {
        private Key key;
        private Value val;
        private Node<Key, Value>[] forward;


        public Node(int level) {
            forward =  new Node[level];
        }
        public Node(Key key, Value val, int level) {
            this.key = key;
            this.val = val;
            forward =  new Node[level];
        }
    }

    public SkipListsST(int maxLevel) {
        this.maxLevel = maxLevel;
        this.head = new Node<Key, Value>(maxLevel);
        this.level = 1;
    }

    public SkipListsST() {
        this(16);   // 
    }


    public int randomLevel() {
        int v = 1;
        while (Math.random() < p && v < maxLevel)
            v++;
        return v;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public int currentLevel() {
        return level;
    }

    public Value search(Key key) {
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].key.compareTo(key) < 0)
                x = x.forward[i - 1];
        }
        x = x.forward[0];
        if (x.key.compareTo(key) == 0)
            return x.val;
        return null;
    }

    // Does this symbol table contain the given key ?
    public boolean contains(Key key) {
        return search(key) != null;
    }


    public void insert(Key key, Value val) {
        if (key == null)  throw new IllegalArgumentException("first argument to insert() is null");
        if (val == null) {
            delete(key);
            return;
        }
        Node<Key, Value>[] update = new Node[maxLevel];
        Node<Key, Value> x = head;
        int v = 0;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].key.compareTo(key) < 0)
                x = x.forward[i - 1];
            update[i - 1] = x;
        }
        x = x.forward[0];
        if (x != null && x.key.compareTo(key) == 0)  x.val = val;
        else {
            v = randomLevel();
            if (v > level) {
                for (int i = level + 1; i <= v; i++) {
                    update[i - 1] = head;
                }
                level = v;
            }
            x = new Node<Key, Value>(key, val, v);
            for (int i = 1; i <= v; i++) {
                x.forward[i - 1] = update[i - 1].forward[i - 1];
                update[i - 1].forward[i - 1] = x;
            }
        }
        n++;
    }

    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key))   return;

        Node<Key, Value>[] update = (Node<Key, Value>[])new Object[maxLevel];
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].key.compareTo(key) < 0)
                x = x.forward[i - 1];
            update[i - 1] = x;
        }
        x = x.forward[0];
        if (x.key.compareTo(key) == 0) {
            for (int i = 1; i <= level; i++) {
                if (update[i - 1].forward[i - 1] != x)
                    break;
                update[i - 1].forward[i - 1] = x.forward[i - 1];
            }
            x = null;
            while (level > 1 && head.forward[level - 1] == null)
                level--;
        }
        n--;
    }

    // Remove the smallest key and associated value from the symbol table.
    public void deleteMin() {
        if (isEmpty())  throw new NoSuchElementException("SkipListsST underflow");
        Node<Key, Value> x = head.forward[0];
        for (int i = 1; i <= level; i++) {
            if (head.forward[i - 1] != x)
                break;
            head.forward[i - 1] = x.forward[i - 1];
        }
        x = null;
        while (level > 1 && head.forward[level - 1] == null)
            level--;
        n--;
    }

    // Remove the largest key and associated value from the symbol table.
    public void deleteMax() {
        if (isEmpty())  throw new NoSuchElementException("SkipListsST underflow");
        Node<Key, Value>[] update = (Node<Key, Value>[])new Object[maxLevel];
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].forward[i - 1] != null)
                x = x.forward[i - 1];
            update[i - 1] = x;
        }
        x = x.forward[0];
        if (x.forward[0] == null) {
            for (int i = 1; i <= level; i++) {
                if (update[i - 1].forward[i - 1] != x)
                    break;
                update[i - 1].forward[i - 1] = null;
            }
            x = null;
            while (level > 1 && head.forward[level - 1] == null)
                level--;
        }
        n--;
    }

    // Returns the smallest key in the symbol table.
    public Key min() {
        if (isEmpty())  throw new NoSuchElementException("called min() with empty symbol table");
        return head.forward[0].key;
    }

    // Returns the largest key in the symbol table.
    public Key max() {
        if (isEmpty())  throw new NoSuchElementException("called max() with empty symbol table");
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null)
                x = x.forward[i - 1];
        }
        return x.key;
    }

    // Returns the largest key in the symbol table less than or equal to the key.
    public Key floor(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty())      throw new IllegalArgumentException("called floor() with empty symbol table");
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].key.compareTo(key) < 0)
                x = x.forward[i - 1];
        }
        if (x.forward[0].key.compareTo(key) == 0)
            return x.forward[0].key;
        return x.key;
    }

    // Returns the smallest key in the symbol table greater than or equal to key.
    public Key ceiling(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty())  throw new IllegalArgumentException("called floor() with empty table");
        Node<Key, Value> x = head;
        for (int i = level; i > 0; i--) {
            while (x.forward[i - 1] != null && x.forward[i - 1].key.compareTo(key) < 0)
                x = x.forward[i - 1];
        }
        x = x.forward[0];
        return x.key;
    }

    // Return the kth smallest key in the symbol table.
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        Node<Key, Value> x = head;
        int i = 0;
        while (i < k && x != null) {
            x = x.forward[0];
            i++;
        }
        if (i != k - 1) {
            return null;
        }
        return x.key;
    }

    // Returns the number of keys in the symbol table strictly less than key.
    public int rank(Key key) {
        if (key == null)    throw new IllegalArgumentException("argument to rank() is null");
        int i = 0;
        Node<Key, Value> x = head.forward[0];
        while (x != null && x.key.compareTo(key) < 0) {
            i++;
            x = x.forward[0];
        }
        return i;
    }

    public Iterable<Key> keys() {
        if (isEmpty())  return new Queue<Key>();
        Queue<Key> queue = new Queue<Key>();
        Node<Key, Value> x = head.forward[0];
        while (x != null) {
            queue.enqueue(x.key);
            x = x.forward[0];
        }
        return queue;
    }

    // Unit tests the SkipListsST data type.
    public static void main(String[] args) {
        Integer[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        SkipListsST<Integer, Integer> st = new SkipListsST<Integer, Integer>();
        for (int i = 0; i < 9; i++) {
            st.insert(i, a[i]);
        }
        for(Integer key: st.keys())
            System.out.println(key + " " + st.search(key));
    }



}