import java.util.Iterator;
import java.util.NoSuchElementException;
// The IndexMinPQ class represents an indexed priority queue of generic keys.
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int maxN;   // maxium number of elements on PQ.
    private int n;      // number of elements on PQ.
    private int[] pq;   // binary heap using 1-based indexing.
    private int[] qp;   // inverse of qp -- qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys; // keys[i] = priority of i

    // Initializes an empty indexed priority queue with indices between 0
    // and maxN - 1.
    // maxN the keys on this priority queue are index from 0 to maxN - 1
    public IndexMinPQ(int maxN) {
        if (maxN < 0)
            throw new IllegalArgumentException();
        this.maxN = maxN;
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }

    // Returns true if this priority queue is empty.
    public boolean isEmpty() {
        return n == 0;
    }

    // Is i an index on this priority queue?
    public boolean contains(int i) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    // Returns the number of keys on this priority queue.
    public int size() {
        return n;
    }

    // Associates key with index i
    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (contains(i))
            throw new IllegalArgumentException("index is already in the priority queue.");
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    // Returns an index associated with a minimum key.
    public int minIndex() {
        if (n == 0)
            throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // Returns a minmum key.
    public Key minKey() {
        if (n == 0)
            throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    // Removes a minmum key and returns its associated index.
    public int delMin() {
        if (n == 0)
            throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, n--);
        sink(1);
        assert min == pq[n + 1];
        qp[min] = -1;   // delete
        keys[min] = null;   // to help with garbage collection
        pq[n + 1] = -1;     // not needed
        return min;
    }

    // Returns the key associated with index i
    public Key keyOf(int i) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue.");
        else
            return keys[i];
    }

    // Change the key associated with index i to the specified value.
    public void changeKey(int i, Key key) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue.");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    // Change the key associated with index i to the specified value.
    public void change(int i, Key key) {
        changeKey(i, key);
    }

    // Decrease the key associated with index i to the specified value.
    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given " 
                + "argument would not strictly decrease the key");
        keys[i] = key;
        swim(qp[i]);
    }

    // Increase the key associated with index i to the specified value.
    public void increaseKey(int i, Key key) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseKey() with given argument would not strictly increase the key");
        keys[i] = key;
        sink(qp[i]);
    }

    // Remove the key associated with index i
    public void delete(int i) {
        if (i < 0 || i >= maxN)
            throw new IndexOutOfBoundsException();
        if (!contains(i))
            throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exch(index, n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    // General helper functions.
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    // Heap helper functions.
    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1))    j++;
            if (!greater(k, j))    break;
            exch(k, j);
            k = j;
        }
    }

    // Iterators
    // Returns an iterator that iterates over the keys on the 
    // priority queue in ascending order.
    // The iterator doesn't implement remove() since it's optional.
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        // create a new pq
        private IndexMinPQ<Key> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Integer next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    public static void main(String[] args) {
        String[] strings = {"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};
        IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete and print exch key
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            StdOut.println(i + " " + strings[i]);
        }

        StdOut.println();

        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        for (int i: pq) {
            StdOut.println(i + " " + strings[i]);
        }

        while (!pq.isEmpty()) {
            pq.delMin();
        }
    }
}































