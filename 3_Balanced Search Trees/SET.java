/**
 *  The { SET} class represents an ordered set of comparable keys.
 *  It supports the usual add, contains, and delete methods. It also 
 *  provides ordered methods for finding the minimum, maximum, floor, 
 *  and ceiling and set methods for union, intersection, and equality.
 *  Even though this implementation include the method { equals()}, it
 *  does not support the method { hashCode()} because sets are mutable.
 *  This implementation uses a balanced binary search tree. It requires that
 *  the key type implements the { Comparable} interface and calls the
 *  { compareTo()} and method to compare two keys. It does not call either
 *  { equals()} or { hashCode()}.
 *  The add, contains, delete, minimum, maximum, ceiling, and floor methods take
 *  logarithmic time in the worst case. The size, and is-empty operations take 
 *  constant time. Construction takes constant time.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class SET<Key extends Comparable<Key>> implements Iterable<Key> {
    private TreeSet<Key> set;

    // Initializes an empty set.
    public SET() {
        set = new TreeSet<Key>();
    }

    // Initializes a new set that is an independent copy of the specified set.
    public SET(SET<Key> x) {
        set = new TreeSet<Key>(x.set);
    }

    // Adds the key to this set (if it is not already present).
    public void add(Key key) {
        if (key == null)    throw new IllegalArgumentException("called add() with a null key");
        set.add(key);
    }

    // Returns true if this set contains the given key.
    public boolean contains(Key key) {
        if (key == null)    throw new IllegalArgumentException("called contains() with a null key");
        return set.contains(key);
    }

    // Removes the specified key from this set (if the set contains the specified key).
    public void delete(Key key) {
        if (key == null)    throw new IllegalArgumentException("called delete() with a null key");
        set.remove(key);
    }

    // Returns the number of keys in this set.
    public int size() {
        return set.size();
    }

    // Returns true if this set is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Returns all of the keys in this set, as an iterator.
    // To iterate over all of the keys in a set named set, use the
    // foreach notation: for (Key key : set)
    public Iterator<Key> iterator() {
        return set.iterator();
    }

    // Returns the largest key in this set.
    public Key max() {
        if (isEmpty())    throw new NoSuchElementException("called max() with empty set");
        return set.last();
    }

    // Returns the smallest key in this set.
    public Key min() {
        if (isEmpty())    throw new NoSuchElementException("called min() with empty set");
        return set.first();
    }

    // Returns the smallest key in this set greater than or equal to key.
    public Key ceiling(Key key) {
        if (key == null)    throw new IllegalArgumentException("called ceiling() with a null key");
        Key k = set.ceiling(key);
        if (k == null)    throw new NoSuchElementException("all keys are less than " + key);
        return k;
    }

    // Returns the largest key in this set less than or equal to key.
    public Key floor(Key key) {
        if (key == null)    throw new IllegalArgumentException("called floor() with a null key");
        Key k = set.floor(key);
        if (k == null)    throw new NoSuchElementException("all keys are greater than " + key);
        return k;
    }

    // Returns the union of this set and that set.
    public SET<Key> union(SET<Key> that) {
        if (that == null)   throw new IllegalArgumentException("called union() with a null argument");
        SET<Key> c = new SET<Key>();
        for (Key x : this) {
            c.add(x);
        }
        for (Key x : that) {
            c.add(x);
        }
        return c;
    }

    // Returns the intersection of this set and that set.
    public SET<Key> intersects(SET<Key> that) {
        if (that == null)   throw new IllegalArgumentException("called intersects() with a null argument");
        SET<Key> c = new SET<Key>();
        if (this.size() < that.size()) {
            for (Key x : this) {
                if (that.contains(x))
                    c.add(x);
            }
        }
        else {
            for (Key x : that) {
                if (this.contains(x)) 
                    c.add(x);
            }
        }
        return c;
    }

    // Compares this set to the specified set.
    // Note that this method declares two empty sets to be equal
    // even if they are parameterized by different generic types.
    // This is consistent with the behavior of equals() within
    // Java's Collections framework.
    @Override
    public boolean equals(Object other) {
        if (other == this)  return true;
        if (other == null)  return false;
        if (other.getClass() != this.getClass())    return false;
        SET that = (SET) other;
        return this.set.equals(that.set);
    }

    // This operation is not supported because sets are mutable.
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because sets are mutable");
    }

    // Returns a string representation of this set.
    @Override
    public String toString() {
        String s = set.toString();
        //System.out.println("###" + s);
        return "{ " + s.substring(1, s.length() - 1) + " }";
    }

    // Unit tests the SET data type.
    public static void main(String[] args) {
        SET<String> set = new SET<String>();
        StdOut.println("set = " + set);

        // insert some keys
        set.add("www.cs.princeton.edu");
        set.add("www.cs.princeton.edu");    // overwrite old value
        set.add("www.princeton.edu");
        set.add("www.math.princeton.edu");
        set.add("www.yale.edu");
        set.add("www.amazon.com");
        set.add("www.simpsons.com");
        set.add("www.stanford.edu");
        set.add("www.google.com");
        set.add("www.ibm.com");
        set.add("www.apple.com");
        set.add("www.slashdot.com");
        set.add("www.whitehouse.gov");
        set.add("www.espn.com");
        set.add("www.snopes.com");
        set.add("www.movies.com");
        set.add("www.cnn.com");
        set.add("www.iitb.ac.in");


        StdOut.println(set.contains("www.cs.princeton.edu"));
        StdOut.println(!set.contains("www.harvardsucks.com"));
        StdOut.println(set.contains("www.simpsons.com"));
        StdOut.println();

        StdOut.println("ceiling(www.simpsonr.com) = " + set.ceiling("www.simpsonr.com"));
        StdOut.println("ceiling(www.simpsons.com) = " + set.ceiling("www.simpsons.com"));
        StdOut.println("ceiling(www.simpsont.com) = " + set.ceiling("www.simpsont.com"));
        StdOut.println("floor(www.simpsonr.com)   = " + set.floor("www.simpsonr.com"));
        StdOut.println("floor(www.simpsons.com)   = " + set.floor("www.simpsons.com"));
        StdOut.println("floor(www.simpsont.com)   = " + set.floor("www.simpsont.com"));
        StdOut.println();

        StdOut.println("set = " + set);
        StdOut.println();

        // print out all keys in this set in lexicographic order
        for (String s : set) {
            StdOut.println(s);
        }

        StdOut.println();
        SET<String> set2 = new SET<String>(set);
        StdOut.println(set.equals(set2));
    }
}