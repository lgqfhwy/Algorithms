public class Heap {
    // This class should not be instantiated
    private Heap() { }

    // Rearrange the array in ascending order, using the natural order.
    public static void sort(Comparable[] pq) {
        int n = pq.length;
        for (int k = n / 2; k >= 1; k--)
            sink(pq, k, n);
        while (n > 1) {
            exch(pq, 1, n--);
            sink(pq, 1, n);
        }
    }

    // Helper functions to restore the heap invariant.
    private static void sink(Comparable[] pq, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j + 1))    j++;
            if (!less(pq, k, j))    break;
            exch(pq, k, j);
            k = j;
        }
    }

    // Helper functions for comparisons and swaps
    // Indices are "off-by-one" to support 1-based indexing
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exch(Object[] pq, int i, int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        //Integer[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
       // Integer[] a = {4, 3, 6, 1, 2, 9, 5};
        Integer[] a = {8, 2, 22, 14, 21, 20 ,24 ,19, 16, 5 ,18, 13, 10, 23, 
            9, 11, 17, 26, 3 ,4 ,27, 25, 12 ,15, 1, 6 ,7};
        Heap.sort(a);
        show(a);
    }
}



































