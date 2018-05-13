import java.util.Comparator;
// The selection class provide static methods for sorting 
// an array using selection sort.
public class Selection {
    //This class should not be instantiated.
    private Selection() { }

    // Rearranges the array in ascending order, using the natural order.
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            exch(a, i, min);
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    // Rearrange the array in ascending order, using a comparator.
    // public static void sort(Object[] a, Comparator comparator) {
    //     int n = a.length;
    //     for (int i = 0; i < n; i++) {
    //         int min = i;
    //         for (int j = i + 1; j < n; j++) {
    //             if (less(comparator, a[j], a[min]))
    //                 min = j;
    //         }
    //         exch(a, i, min);
    //         assert isSorted(a, comparator, 0, i);
    //     }
    //     assert isSorted(a, comparator);
    // }

    // Helper sorting functions.

    // is v < w?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w?
    // private static boolean less(Comparator comparator, Object v, Object w) {
    //     return comparator.compare(v, w) < 0;
    // }

    // exchange a[i] and a[j]
    // private static void exch(Object[] a, int i, int j) {
    //     Object swap = a[i];
    //     a[i] = a[j];
    //     a[j] = swap;
    // }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // Check if array is sorted-useful for debugging.
    // Is the array a[] sorted?
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    // Is the array sorted from a[lo] to a[hi].
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    // Is the array a[] sorted?
    // private static boolean isSorted(Object[] a, Comparator comparator) {
    //     return isSorted(a, comparator, 0, a.length - 1);
    // }

    // Is the array sorted from a[lo] to a[hi].
    // private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
    //     for (int i = lo + 1; i <= hi; i++) {
    //         if (less(comparator, a[i], a[i - 1]))
    //             return false;
    //     }
    //     return true;
    // }

    // print array to standard output.
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    // Reads in a sequence of strings from standard input; Selection
    // sorts them; and prints them to standard output in ascending order.
    public static void main(String[] args) {
        //String[] a = StdIn.readAllStrings();
        Integer[] a = new Integer[10];
        for (int i = 0; i < 10; i++) 
            a[i] = 10 - i;
        Selection.sort(a);
        Selection.show(a);
    }
}















































