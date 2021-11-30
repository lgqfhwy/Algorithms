// The ST class represents an ordered symbol table of generic
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

// This implementation uses a balanced bianry search tree. It requires that 
// the key type implements the {Comparable} interface and calls the 
// { compareTo } and method to compare two keys. It does not call either 
// { equals() } or { hashCode() }.
// The put, contains, remove, minimum, maximum, ceiling, and floor operations each take
// logarithmic time in the worst case.
// The size, and is-empty operations take constant time.
// Construction takes constant time.

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
    private TreeMap<Key, Value> st;

    // Initializes an empty symbol table.
    public ST() {
        st = new TreeMap<Key, Value>();
    }

    // Returns the value associated with the given key in this symbol table.
    public Value get(Key key) {
        if (key == null)    throw new IllegalArgumentException("called get() with null key");
        return st.get(key);
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old
    // value with the new value if the symbol table already contains the specified key.
    // Deletes the specified key (and its associated value) from this symbol table
    // if the specified value is null.
    public void put(Key key, Value val) {
        if (key == null)    throw new IllegalArgumentException("called put() with null key");
        if (val == null)    st.remove(key);
        else                st.put(key, val);
    }

    // Removes the specified key and its associated value from this symbol table
    // (if the key is in this symbol table).
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("called delete() with null key");
        st.remove(key);
    }

    // Returns true if this symbol table contain the given key.
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("called contains() with null key");
        return st.containsKey(key);
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return st.size();
    }

    // Returns true if this symbol table is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns all keys in this symbol table.
    // To iterate over all of the keys in the symbol table named st, 
    // use the foreach notation: for (Key key: st.keys()).
    public Iterable<Key> keys() {
        return st.keySet();
    }

    // Returns all of the keys in this symbol table.
    // To iterate over all of the keys in a symbol table named st, use the 
    // foreach notation: for (Key key: st).
    // This method is provided for backward compatibility with the version from
    // Introduction to Programming in Java: An Interdisciplinary Approach.
    @Deprecated
    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    // Returns the smallest key in this symbol table.
    public Key min() {
        if (isEmpty())     throw new NoSuchElementException("called min() with empty symbol table");
        return st.firstKey();
    }

    // Returns the largest key in this symbol table.
    public Key max() {
        if (isEmpty())     throw new NoSuchElementException("called max() with empty symbol table");
        return st.lastKey();
    }

    // Returns the smallest key in this symbol table greater than or equal to key.
    public Key ceiling(Key key) {
        if (key == null)    throw new IllegalArgumentException("called ceiling() with null key");
        Key k = st.ceilingKey(key);
        if (k == null)     throw new NoSuchElementException("all keys are less than " + key);
        return k;
    }

    // Returns the largest key in this symbol table less than or equal to key.
    public Key floor(Key key) {
        if (key == null)    throw new IllegalArgumentException("called floor() with null key");
        Key k = st.floorKey(key);
        if (k == null)    throw new NoSuchElementException("all keys are greater than " + key);
        return k;
    }

    // Unit tests the ST data type
    public static void main(String[] args) {
        ST<String, Integer> st = new ST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s: st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}