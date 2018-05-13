public class Merge {
    // This class should not be instantiated.
    private Merge() { }

    // stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    a[k] = aux[j++];
            else if (j > hi)    a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else    a[k] = aux[i++];
        }
        // postcondition: a[lo .. hi] is sorted.
        assert isSorted(a, lo, hi);
    }

    // Faster merge. Implement a version of merge() that copies the second half of a[] to aux[] in
    // decreasing order and then does the merge back to a[]. This change allows you to remove the code
    // to test that exch of the halves has been exhausted from the inner loop.
    // Note: the resulting sort is not stable.
    private static void fastermerge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int i = lo; i <= mid; i++)
            aux[i] = a[i];
        for (int j = mid + 1; j <= hi; j++)
            aux[j] = a[hi - j + mid + 1];
        int i = lo, j = hi;
        for (int k = lo; k <= hi; k++) {
            if (less(aux[j], aux[i]))
                a[k] = aux[j--];
            else
                a[k] = aux[i++];
        }

    }

    // mergesort a[lo .. hi] using auxiliary array aux[lo .. hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo)   return ;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    // Rearrange the array in ascending order, using the natural order.
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
        assert isSorted(a);
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // Check if array is sorted - useful for debugging
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    // Index mergesort
    // stably merge a[lo .. mid] with a[mid+1 .. hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, int[] index, int[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = 0; k <= hi; k++) {
            aux[k] = index[k];
        }

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)    index[k] = aux[j++];
            else if (j > hi)     index[k] = aux[i++];
            else if (less(a[aux[j]], a[aux[i]]))    index[k] = aux[j++];
            else    index[k] = aux[i++];
        }
    }

    // Returns a permutation that gives the elements in the array in ascending order.
    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++)
            index[i] = i;

        int[] aux = new int[n];
        sort(a, index, aux, 0, n - 1);
        return index;
    }

    // mergesort a[lo .. hi] using auxiliary array aux[lo .. hi]
    private static void sort(Comparable[] a, int[] index, int[] aux, int lo, int hi) {
        if (hi <= lo) return ;
        int mid = lo + (hi - lo) / 2;
        sort(a, index, aux, lo, mid);
        sort(a, index, aux, mid + 1, hi);
        merge(a, index, aux, lo, mid, hi);
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Merge.sort(a);
        show(a);
    }
}







































