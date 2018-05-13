import java.util.Comparator;
public class MergeX {
    private static final int CUTOFF = 7; // cutoff to insertion sort

    // This class should not be instantiated.
    private MergeX() { }

    private static void merge(Comparable[] src, Comparable[] dst, int lo, int mid, int hi) {
        //precondition: src[lo .. mid] and src[mid + 1 .. hi] are sorted subarrays
        assert isSorted(src, lo, mid);
        assert isSorted(src, mid + 1, hi);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    dst[k] = src[j++];
            if (j > hi)     dst[k] = src[i++];
            else if (less(src[j], src[i]))  dst[k] = src[j++];
            else                            dst[k] = src[i++];
        }
        //postcondition: dst[lo .. hi] is sorted subarray
        assert isSorted(dst, lo, hi);
    }

    private static void sort(Comparable[] src, Comparable[] dst, int lo, int hi) {
        // if (hi <= lo) return ;
        if (hi <= lo + CUTOFF) {
            insertionSort(dst, lo, hi);
            return ;
        }
        int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid);
        sort(dst, src, mid + 1, hi);

        // if (!less(src[mid + 1], src[mid])) {
        //     for (int i = lo; i <= hi; i++)
        //         dst[i] = src[i];
        //     return;
        // }

        // using System.arraycopy() is a bit faster than the above loop
        if (!less(src[mid + 1], src[mid])) {
            System.arraycopy(src, lo, dst, lo, hi - lo + 1);
            return;
        }

        merge(src, dst, lo, mid, hi);
    }

    // Rearranges the array in acsending order, using the natural order.
    public static void sort(Comparable[] a) {
        Comparable[] aux = a.clone();
        sort(aux, a, 0, a.length - 1);
        assert isSorted(a);
    }

    // Sort from a[lo] to a[hi] using insertion sort
    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
    }

    // Utility methods.
    // exchange a[i] and a[j].
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // is a[i] < a[j] ?
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    // print array to standard output
    private static void show(Object[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        MergeX.sort(a);
        show(a);
    }
}

































