import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;   // store items at indices 1 to n.
    private int n;      // number of items on priority queue.
    private Comparator<Key> comparator;     // optional Comparator
    private Key minItem;


    // Initializes an empty priority queue with the given 
    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    // Initializes an empty priority queue.
    public MaxPQ() {
        this(1);
    }

    // Initializes an empty priority queue with the given initial capacity,
    // using the given comparator.
    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    // Initializes an empty priority queue using the given comparators.
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    // Initializes a priority queue from the array of keys.
    // Takes time proportional to the number of keys, using sink-based heap construction.
    public MaxPQ(Key[] keys) {
        n = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            pq[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }

    // Returns true if this priority queue is empty.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of keys on this priority queue.
    public int size() {
        return n;
    }

    // Returns a largest key on this priority queue.
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // helper function to double the size of the heap array.
    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 0; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    // Adds a new key to this priority queue.
    public void insert(Key x) {

        // double size of array if necessary
        if (n >= pq.length - 1)
            resize(2 * pq.length);

        // Update minmum Item
        if (n == 0) {
            minItem = x;
        }
        else if (less(x, minItem)) 
            minItem = x;

        // add x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);

        assert isMaxHeap();
    }

    // Removes and returns a largest key on this priority queue.
    public Key delMax() {
        if (isEmpty())
            throw new NoSuchElementException("Priority queue underflow.");
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n + 1] = null;   // to avoid loitering and help with garbage collection
        if ((n > 0) && (n == (pq.length - 1) / 4))  resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }

    public Key min() {
        if (n == 0)
            return null;
        return minItem;
    }

    // Helper functions to restore the heap invariant
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1))    j++;
            if (!less(k, j))    break;
            exch(k, j);
            k = j;
        }
    }
    private void show() {
        for (int i = 1; i <= n; i++) {
            StdOut.print(pq[i] + " ");
        }
        StdOut.println();
    }

    private boolean less(Key i, Key j) {
        return ((Comparable<Key>)i).compareTo(j) < 0;
    }

    // Helper functions for compares and swaps.
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pa[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > n)     return true;
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= n && less(k, left))     return false;
        if (right <= n && less(k, right))   return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    // Iterator
    // Returns an iterator that iterates over the keys on this priority queue
    // in descending order
    // The itrator doesn't implements since it's optional.
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        // create a new pq
        private MaxPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null)    copy = new MaxPQ<Key>(size());
            else                       copy = new MaxPQ<Key>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Key next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.delMax();
        }
    }

    public static void main(String[] args) {
        // MaxPQ<String> pq = new MaxPQ<String>();
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     if (!item.equals("-"))
        //         pq.insert(item);
        //     else if (!pq.isEmpty())
        //         StdOut.print(pq.delMax() + " ");
        // }
        // StdOut.println("(" + pq.size() + " left on pq)");
        MaxPQ<String> pq = new MaxPQ<String>();
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        for (String k : pq) {
            StdOut.println(k);
        }
        pq.show();
        while (!pq.isEmpty()) {
            StdOut.println("Min = " + pq.min() + "  Max = " + pq.delMax());
        }
    }

}

































