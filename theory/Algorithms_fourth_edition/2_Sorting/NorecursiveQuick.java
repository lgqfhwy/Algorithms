public class NorecursiveQuick {
    // This class should not be instantiated
    private NorecursiveQuick() { }


    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        Stack<subarray> s = new Stack<subarray>();
        subarray fringe = new subarray();
        fringe.lo = 0;
        fringe.hi = a.length - 1;
        s.push(fringe);
        while (!s.isEmpty()) {
            subarray item = s.pop();
            int lo = item.lo;
            int hi = item.hi;
            int i = lo;
            int j = hi + 1;
            Comparable v = a[lo];
            while (true) {
                while (less(a[++i], v)) {
                    if (i == hi)
                        break;
                }
                while (less(v, a[--j])) {
                    if (j == lo)
                        break;
                }
                if (i >= j)
                    break;
                exch(a, i, j);
            }
            exch(a, lo, j);
            if (j-1-lo > hi-1-j) {
                pushSubArray(s, lo, j-1);
                pushSubArray(s, j+1, hi);
            }
            else {
                pushSubArray(s, j+1, hi);
                pushSubArray(s, lo, j-1);
            }
        }
    }
    private static void pushSubArray(Stack<subarray> s, int lo, int hi) {
        if (lo < hi) {
            subarray sub = new subarray();
            sub.lo = lo;
            sub.hi = hi;
            s.push(sub);
        }
    }
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
    public static void main(String[] args) {
        Integer[] a = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        NorecursiveQuick quick = new NorecursiveQuick();
        quick.sort(a);
        show(a);
    }
}

















































