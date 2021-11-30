// Feel-good interval. Given an array of N nonegative integers (representing a person's emotional value
// on each day), the happiness in an interval is the sum of the values in that interval multiplied by the
// smallest integer in that interval. Design an O(NlogN) divide-and-comquer algorithm to find the happiest
// interval.
// Solution. Here's a mergesort style solution.
//      Divide the elements in the middle: a[l..m-1], a[m], a[m+1..r]
//      Recursively compute the optimal interval entirely in the left half
//      Recursively compute the optimal interval entirely in the right half
//      Compute the optimal interval containing a[m]
//      Return the best of the three intervals
// The key step for efficiency is computing the optimal interval containing 
// a[m] in linear time. Here's a greedy solution: If the optimal interval 
// containing a[m] contains one element, it is simply a[m]. If it contains 
// more than one element, it must contain the larger of a[m-1] and a[m+1], 
// so add this to the interval. Repeat, etc. Return the best interval of 
// any size constructed by this process.
public class FeelGoodInterval {
    private FeelGoodInterval() {}

    public static int computeOptimalInterval(int[] a) {
        int bestInterval = computeOptimalInterval(a, 0, a.length - 1);
        return bestInterval;
    }

    public static int computeOptimalInterval(int[] a, int lo, int hi) {
        if (hi <= lo)   return computeBest(a, lo);
        int mid = lo + (hi - lo) / 2;
        int leftInterval = computeOptimalInterval(a, lo, mid - 1);
        int rightInterval = computeOptimalInterval(a, mid + 1, hi);
        int midInterval = computeBest(a, mid);
        return max(leftInterval, midInterval, rightInterval);
    }

    public static int computeBest(int[] a, int mid) {
        int lo = 0;
        int hi = a.length - 1;
        int i = mid - 1, j = mid + 1;
        int sum = a[mid];
        while (i >= lo && less(a, mid, i)) {
            sum += a[i];
            i--;
        }
        while (j <= hi && less(a, mid, j)) {
            sum += a[j];
            j++;
        }
        System.out.printf("a[i] = %d, a[j] = %d, a[mid] = %d, sum = %d\n", a[i + 1], a[j - 1], a[mid], sum);
        return sum * a[mid];
    }

    public static boolean less(int[] a, int i, int j) {
        return a[i] <= a[j];
    }

    public static int max(int leftInterval, int midInterval, int rightInterval) {
        int maxNum = leftInterval;
        if (maxNum < midInterval)
            maxNum = midInterval;
        if (maxNum < rightInterval)
            maxNum = rightInterval;
        return maxNum;
    }

    public static void main(String[] args) {
        //int[] a = {10, 12, 3, 4, 5, 6, 1, 8, 9};
        int[] a  = {9, 8, 1, 2, 3, 4, 5, 6, 7};
        int best = computeOptimalInterval(a);
        System.out.println(best);
    }

}