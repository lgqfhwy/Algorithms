import edu.princeton.cs.algs4.StdOut;

public class Inversions {
    // do not instantiate
    private Inversions() { }

    // merge and count
    private static long merge(int[] a, int[] aux, int lo, int mid, int hi) {
        long inversions = 0;
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    a[k] = aux[j++];
            else if (j > hi)    a[k] = aux[i++];
            else if (aux[j] < aux[i]) {
                a[k] = aux[j++];
                inversions += (mid - i + 1);
            }
            else    a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo .. hi]
    // side effect b[lo .. hi] is rearranged in ascending order
    private static long count(int[] a, int[] aux, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo)   return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, aux, lo, mid);
        inversions += count(a, aux, mid + 1, hi);
        inversions += merge(a, aux, lo, mid, hi);
        //assert inversions == brute(a, lo, hi);
        return inversions;
    }

    // Return the number of inversions in the integer array.
    // The argument array is not modified.
    public static long count(int[] a) {
        int[] b = new int[a.length];
        int[] aux = new int[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
        long inversions = count(a, aux, 0, a.length - 1);
        return inversions;
    }

    // merge and count (Comparable version)
    private static <Key extends Comparable<Key>> long merge(Key[] a, Key[] aux, int lo, int mid, int hi) {
        long inversions = 0;
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        // merge back to a[]
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    a[k] = aux[j++];
            else if (j > hi)   a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  {
                a[k] = aux[j++];
                inversions += (mid - i + 1);
            }
            else a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo .. hi]
    // side effect b[lo .. hi] is rearranged in ascending order.
    private static <Key extends Comparable<Key>> long count(Key[] a, Key[] b, Key[] aux, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo)   return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, aux, lo, mid);
        inversions += count(a, b, aux, mid+1, hi);
        inversions += merge(b, aux, lo, mid, hi);
        assert inversions == brute(a, lo, hi);
        return inversions;
    }

    // Returns the number of inversions in the comparable array.
    // The argument array is not modified.
    public static <Key extends Comparable<Key>> long count(Key[] a) {
        StdOut.println("Hello!");
        Key[] b = a.clone();
        Key[] aux = a.clone();
        long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }

    // is v < w ?
    private static <Key extends Comparable<Key>> boolean less(Key v, Key w) {
        return v.compareTo(w) < 0;
    }

    // count number of inversions in a[lo .. hi] via brute force (for debugging only)
    private static <Key extends Comparable<Key>> long brute(Key[] a, int lo, int hi) {
        long inversions = 0;
        for (int i = lo; i <= hi; i++) {
            for (int j = i + 1; j <= hi; j++) {
                if (less(a[j], a[i]))
                    inversions++;
            }
        }
        return inversions;
    }

    // Reads in a sequence of intergers from standard input and prints
    // the number of inversions
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        int n = a.length;
        Integer[] b = new Integer[n];
        for (int i = 0; i < n; i++)
            b[i] = a[i];
        StdOut.println(Inversions.count(a));
        StdOut.println(Inversions.count(b));
    }


}

































