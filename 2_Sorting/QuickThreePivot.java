public class QuickThreePivot {
    // This class should not be instantiated
    private QuickThreePivot() { }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        if (n == 2) {
            if (less(a[hi], a[lo])) {
                exch(a, lo, hi);
            }
        }
        if (n < 2) {
            return ;
        }
        else {
            order(a, lo, lo+1, hi);
            int a1 = lo + 2;
            int b1 = lo + 2;
            int c1 = hi - 1;
            int d1 = hi - 1;
            int p = lo;
            int q = lo + 1;
            int r = hi;
            while (b1 <= c1) {
                while (less(a[b1], a[q]) && b1 <= c1) {
                    if (less(a[b1], a[p])) {
                        exch(a, a1, b1);
                        a1++;
                    }
                    b1++;
                }
                while (less(a[q], a[c1]) && b1 <= c1) {
                    if (less(a[r], a[c1])) {
                        exch(a, c1, d1);
                        d1--;
                    }
                    c1--;
                }
                if (b1 <= c1) {
                    if (less(a[r], a[b1])) {
                        if (less(a[c1], a[p])) {
                            exch(a, a1, b1);
                            exch(a, a1, c1);
                            a1++;
                        }
                        else {
                            exch(a, b1, c1);
                        }
                        exch(a, c1, d1);
                        b1++; c1--; d1--;
                    }
                    else {
                        if (less(a[c1], a[p])) {
                            exch(a, a1, b1);
                            exch(a, a1, c1);
                            a1++;
                        }
                        else {
                            exch(a, b1, c1);
                        }
                        b1++; c1--;
                    }
                }
            }
            a1--; b1--; c1++; d1++;
            exch(a, lo+1, a1);
            exch(a, a1, b1);
            a1--;
            exch(a, lo, a1);
            exch(a, hi, d1);
            sort(a, lo, a1);
            sort(a, a1+1, b1);
            sort(a, b1+1, d1);
            sort(a, d1+1, hi);
        }
        
    }
    private static void order(Comparable[] a, int lo, int mid, int hi) {
        if (less(a[mid], a[lo])) {
            exch(a, lo, mid);
        }
        if (less(a[hi], a[lo])) {
            exch(a, lo, hi);
        }
        if (less(a[hi], a[mid])) {
            exch(a, mid, hi);
        }
    }

    // Helper sorting functions.
    // is v < w?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // does v == w ?
    private static boolean eq(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // Check if array is sorted -- useful for debugging.
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
    public static void main(String[] args) {
        Integer[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        QuickThreePivot.sort(a);
        show(a);
    }
}
















































