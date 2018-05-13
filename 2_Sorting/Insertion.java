 import java.util.Comparator;
public class Insertion {
    // This class should not be instantiated.
    private Insertion() { }

    // Rearrange the array in ascending order, using the natural order.
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    public static void SortWithSentinel(Comparable[] a) {
        int n = a.length;
        min = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] < a[min])
                min = i;
        }
        exch(a, 0, min);
        for (int i = 0; i < n; i++) {
            for (int j = i; less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    // Rearrange the subarray a[lo..hi] in ascending order, using the natural order.
    // public static void sort(Comparable[] a, int lo, int hi) {
    //     for (int i = lo; i < hi; i++) {
    //         for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
    //             exch(a, j, j - 1);
    //         }
    //     }
    //     assert isSorted(a, lo, hi);
    // }

    // Rearrange the array in ascending order, using a comparator.
    // public static void sort(Object[] a, Comparator comparator) {
    //     int n = a.length;
    //     for (int i = 0; i < n; i++) {
    //         for (int j = i; j > 0 && less(a[i], a[j-1], comparator); j--) {
    //             exch(a, j, j-1);
    //         }
    //         assert isSorted(a, 0, i, comparator);
    //     }
    //     assert isSorted(a, comparator);
    // }

    // Rearrange the subarray a[lo..hi) in ascending order, using a comparator.
    // public static void sort(Object[] a, int lo, int hi, Comparator comparator) {
    //     for (int i = lo; i < hi; i++) {
    //         for (int j = i; j > lo && less(a[i], a[j-1], comparator); j--) {
    //             exch(a, j, j-1);
    //         }
    //     }
    //     assert isSorted(a, lo, hi, comparator);
    // }

    // Return a permutation that gives the elements in a[] in ascending order.
    // do not change the original array a[]
    // Returns a permutation that gives the elements in the array in ascending order.
    // public static int[] indexSort(Comparable[] a) {
    //     int n = a.length;
    //     int[] index = new int[n];
    //     for (int i = 0; i < n; i++)
    //         index[i] = i;
    //     for (int i = 0; i < n; i++) {
    //         for (int j = i; j > 0 && less(a[index[j]], a[index[j-1]]); j--)
    //             exch(index, j, j-1);
    //     }
    //     return index;
    // }

    // Helper sorting functions.
    // is v < w?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // is v < w?
    // private static boolean less(Object v, Object w, Comparator comparator) {
    //     return comparator.compare(v, w) < 0;
    // }

    // exchange a[i] and a[j]
    // private static void exch(Object[] a, int i, int j) {
    //     Object swap = a[i];
    //     a[i] = a[j];
    //     a[j] = swap;
    // }

    // exchange a[i] and a[j] (for indirect sort)
    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    // Check if array is sorted - useful for debugging.
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }


    // private static boolean isSorted(Object[] a, Comparator comparator) {
    //     return isSorted(a, 0, a.length, comparator);
    // }

    // is the array a[lo..hi) sorted
    // private static boolean isSorted(Object[] a, int lo, int hi, Comparator comparator) {
    //     for (int i = lo + 1; i < hi; i++) {
    //         if (less(a[i], a[i-1], comparator))
    //             return false;
    //     }
    //     return true;
    // }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
    }

    // Reads in a sequence of strings from standard input; insertion sorts them;
    // and prints them to standard output in ascending order.
    public static void main(String[] args) {
        //String[] a = StdIn.readAllStrings();
        Integer[] a = new Integer[10];
        for (int i = 0; i < 10; i++)
            a[i] = 10 - i;
        Insertion.sort(a);
        Insertion.show(a);
    }
}





































