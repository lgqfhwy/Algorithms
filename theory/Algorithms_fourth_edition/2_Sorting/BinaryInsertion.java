public class BinaryInsertion {

    // This class should not be instantiated.
    private BinaryInsertion() { }

    // Rearrange the array in ascending order, using the natural order
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            // Binary search to determine index j at which to insert a[i]
            Comparable v = a[i];
            int lo = 0, hi = i;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (less(v, a[mid]))    hi = mid;
                else                    lo = mid + 1;
            }
            // Insertion sort with "half exchanges"
            // 1 3 5 7 9   4
            // (insert a[i] at index j and shift a[j], ,,, a[i-1] to right)
            for (int j = i; j > lo; --j)
                a[j] = a[j-1];
            a[lo] = v;
        }
        assert isSorted(a);
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // Check if array is sorted -- useful for debugging
    private static boolean isSorted(Comparable[] a) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i-1]))
                return false;
        }
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        BinaryInsertion.sort(a);
        show(a);
    }
}







































