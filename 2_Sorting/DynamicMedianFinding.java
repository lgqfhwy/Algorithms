import java.util.Comparator;
public class DynamicMedianFinding<Key> {
    private MinPQ<Key> minHeap;     // min-oriented heap for keys greater than the key of v.
    private Key v;
    private MaxPQ<Key> maxHeap;     // max-oriented heap for keys less than the key of v;
    private int n;

    public DynamicMedianFinding() {
        minHeap = new MinPQ<Key>();
        maxHeap = new MaxPQ<Key>();
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(Key x) {
        if (n == 0) {
            minHeap.insert(x);
            v = x;
        }
        else if (less(v, x)) 
            minHeap.insert(x);
        else
            maxHeap.insert(x);

        if (minHeap.size() == maxHeap.size() + 1) {
            v = minHeap.min();
        }
        else if (minHeap.size() == maxHeap.size() + 2) {
            maxHeap.insert(minHeap.delMin());
            v = minHeap.min();
        }
        else if (minHeap.size() == maxHeap.size() + 1) {
            v = maxHeap.delMax();
            minHeap.insert(v);
        }
        n++;
    }

    public Key findMedian() {
        return v;
    }

    public Key removeMedian() {
        Key mid = v;
        minHeap.delMin();
        if (minHeap.size() == maxHeap.size()) {
            v = minHeap.min();
        }
        else {
            v = maxHeap.delMax();
            minHeap.insert(v);
        }
        n--;
        return mid;
    }

    private boolean less(Key i, Key j) {
        return ((Comparable<Key>) i).compareTo(j) < 0;
    }

    public static void main(String[] args) {
        DynamicMedianFinding<Integer> pq = new DynamicMedianFinding<Integer>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            pq.insert(i);
            StdOut.println(pq.findMedian());
        }
    }
}










































