// Two-sum. Given an array of N integers, design a linearithmic algorithm to find a pair of 
// integers whose sum is closest to zero.
// Solution: sort by absolute value--the best pair is now adjacent.
public class TwoSumNearly{

    // Rearranges the array in ascending order by absolute value.
    public static void sortAbsoluteValue(int[] a) {
        StdRandom.shuffle(a);
        sortAbsoluteValue(a, 0, a.length - 1);
    }

    // quicksort the subarray from a[lo] to a[hi].
    private static void sortAbsoluteValue(int[] a, int lo, int hi) {
        if (hi <= lo)   return;
        int j = partition(a, lo, hi);
        sortAbsoluteValue(a, lo, j - 1);
        sortAbsoluteValue(a, j + 1, hi);
    }

    // partition the subarray a[lo..hi] so that the absolute value of 
    // a[lo..j-1] <= a[j] <= a[j+1..hi] and return the index j.
    private static int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = abs(a[lo]);
        while (true) {
            // find item on lo to swap.
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            // find item on hi to swap.
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            // check if pointers cross
            if (i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static int abs(int i) {
        if (i < 0) {
            i = -i;
        }
        return i;
    }

    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean less(int i, int j) {
        return abs(i) < abs(j);
    }

    public static void show(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void findSumTok(int[] a, int k) {
        sortAbsoluteValue(a);
        show(a);
        int sum = abs(a[0] + a[1] - k);
        int si = 0, sj = 1;
        int diff = 0;
        for (int i = 0; i < a.length - 1; i++) {
            diff = abs(a[i] + a[i + 1] - k);
            if (diff < sum) {
                sum = diff;
                si = i;
                sj = i + 1;
            }
        }
        System.out.println("The sum of " +  a[si] + " and " + a[sj] + " is " + (a[si] + a[sj]));
    }

    public static void main(String[] args) {
        //int[] a = {9, 8, -1, 6, 19, -3, -8};
        int[] a = {1, 0, -9, -2, 11, -11, -16};
        //show(a);
        findSumTok(a, 0);
    }
}