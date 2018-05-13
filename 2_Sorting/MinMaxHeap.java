// Min-max heap, is based on the heap structure under the notion
// of min-max ordering: values stored at nodes on even levels are
// smaller than or equal to values stored at their descendants.
// values stored at nodes on odd levels are greater than or equal to
// values stored at their decendants.
// Thus, the smallest value of S is stored at the root of T, whereas the
// largest value is stored at one of the root's children.
// A min-max heap on n elements can be stored in an array A[1..n]. The ith
// location in the array will correspond to a node located on level [logi] in
// the heap.
import java.util.Comparator;
import java.util.NoSuchElementException;

public class MinMaxHeap<Key> {
    private Key[] pq;   // store items at indices 1 to n.
    private int n;      // number of items on priority queue

    // Initializes an empty priority queue with the given
    public MinMaxHeap(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    // Initializes an empty priority queue
    public MinMaxHeap() {
        this(1);
    }

    public boolean isEmpty() {
        return n == 0;
    }

    // Returns a largest key on this priority queue
    public void resize(int capacity) {
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 0; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public int whichLevel(int i) {
        int level = 1;
        int k = 2;
        while (k <= i) {
            level++;
            k *= 2;
        }
        return level;
    }

    // Adds a new key to this MinMaxHeap
    public void insert(Key x) {
        // double size of array if necessary
        if (n >= pq.length - 1) 
            resize(2 * pq.length);
        pq[++n] = x;
        BubbleUp(n);
    }

    public Key delMin() {
        if (isEmpty())
            throw new NoSuchElementException("Priority queue underflow.");
        Key minNum = pq[1];
        exch(1, n--);
        trickleDown(1);
        pq[n + 1] = null;   // to avoid loitering and help with garbage collection.
        if ((n > 0) && (n == (pq.length - 1) / 4))     resize(pq.length / 2);
        return minNum;
    }

    public Key delMax() {
        if (isEmpty())
            throw new NoSuchElementException("Priority queue underflow.");
        int maxIndex = max(1, 2, 3);
        Key maxNum = pq[maxIndex];
        exch(maxIndex, n--);
        trickleDown(maxIndex);
        pq[n + 1] = null;   // to avoid loitering and help with garbage collection.
        if ((n > 0) && (n == (pq.length - 1) / 4))      resize(pq.length / 2);
        return maxNum;
    }

    public Key findMin() {
        if (n == 0)
            return null;
        return pq[1];
    }

    public Key findMax() {
        if (n == 0)
            return null;
        int maxIndex = max(2, 3);
        return pq[maxIndex];
    }

    public void trickleDown(int i) {
        if (whichLevel(i) % 2 == 1)
            trickleDownMin(i);
        else 
            trickleDownMax(i);
    }

    public void trickleDownMin(int i) {
        if (i > n)  return;
        int child1 = 2 * i;
        int child2 = 2 * i + 1;
        if (child1 <= n) {      // pq[i] has children
            int m = min(child1, child2);
            m = min(m, child1 * 2, child1 * 2 + 1);
            m = min(m, child2 * 2, child2 * 2 + 1);
            if (m / 4 >= i) {   // if pq[m] is a grandchild of pq[i]
                if (less(m, i)) {
                    exch(m, i);
                }
                if (less(m / 2, m))
                    exch(m / 2, m);
                trickleDownMin(m);
            }
            else {      // pq[m] is a child of pq[i]
                if (less(m, i))
                    exch(m, i);
            }
        }
    }

    public void trickleDownMax(int i) {
        if (i > n)  return;
        if (i < 1)  return;
        int child1 = 2 * i;
        int child2 = 2 * i + 1;
        if (child1 <= n) {  // pq[i] has children
            int m = max(child1, child2);
            m = max(m, child1 * 2, child1 * 2 + 1);
            m = max(m, child2 * 2, child2 * 2 + 1);
            if (m / 4 >= i) {   // if pq[m] is a grandchild of pq[i]
                if (less(i, m)) {
                    exch(m, i);
                }
                if (less(m, m / 2))
                    exch(m, m / 2);
                trickleDownMax(m);
            }
            else {    // pq[m] is a child of pq[i]
                if (less(i, m))
                    exch(i, m);
            }
        }
    }

    public void BubbleUp(int i) {
        if (i > n)    return;
        if (whichLevel(i) % 2 == 1) {   // if i is on the min-leevel
            if (i > 1 && less(i / 2, i)) { // if i has a parent and pq[i] > pq[parent]
                exch(i, i / 2);
                BubbleUpMax(i / 2);
            }
            else
                BubbleUpMin(i);
        }
        else {
            if (i > 1 && less(i, i / 2)) {
                exch(i, i / 2);
                BubbleUpMin(i / 2);
            }
            else
                BubbleUpMax(i);
        }

    }

    public void BubbleUpMin(int i) {
        if (i / 4 >= 1) {
            if (less(i, i / 4)) {
                exch(i, i / 4);
                BubbleUpMin(i / 4);
            }
        }
    }

    public void BubbleUpMax(int i) {
        if (i / 4 >= 1) {
            if (less(i / 4, i)) {
                exch(i, i / 4);
                BubbleUpMax(i / 4);
            }
        }
    }

    private int min(int i, int j) {
        int minNum = 0;
        if (i <= n)
            minNum = i;
        if (j <= n) {
            if (less(j, i))
                minNum = j;
        }
        return minNum;
    }

    private int max(int i, int j) {
        int maxNum = 0;
        if (i <= n)
            maxNum = i;
        if (j <= n) {
            if (less(i, j))
                maxNum = j;
        }
        return maxNum;
    }

    private int min(int i, int j, int k) {
        int minNum = min(i, j);
        minNum = min(minNum, k);
        return minNum;
    }

    private int max(int i, int j, int k) {
        int maxNum = max(i, j);
        maxNum = max(maxNum, k);
        return maxNum;
    }

    private boolean less(int i, int j) {
        return ((Comparable<Key>)pq[i]).compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }


    public void show() {
        for (int i = 1; i <= n; i++) {
            StdOut.print(pq[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        MinMaxHeap<Integer> pq = new MinMaxHeap<Integer>();
        for (int i = 9; i > 0; i--) {
            pq.insert(i);
        }
        // pq.insert(2);
        // pq.insert(1);
        // pq.show();
        // pq.insert(4);
        // pq.insert(3);
        //StdOut.println();
        pq.show();
        while (!pq.isEmpty()) {
            StdOut.println("Min = " + pq.delMin());
            if (!pq.isEmpty()) 
             StdOut.println("Max = " + pq.delMax());
        }
    }

}













































