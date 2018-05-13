// Perform specified number of trials of the following experiment:
// generate n random int values between 0 and m - 1 and count the
// number of distinct values generated.
// Probability theory says that the number of distinct values should
// be about m * (1 - e ^ (-alpha)), where alpha = n/m.

import java.util.Arrays;
public class Distinct {
    // return number of distinct entries in array a[]
    public static int distinct(Comparable[] a) {
        if (a.length == 0)      return 0;
        Arrays.sort(a);
        int distinct = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i - 1]) != 0)
                distinct++;
        }
        return distinct;
    }

    public static void main(String[] args) {
        // int m = Integer.parseInt(args[0]);
        // int n = Integer.parseInt(args[1]);
        // int trials = Integer.parseInt(args[2]);
        int m = 100;
        int n = 100;
        int trials = 1000;

        int[] distinct = new int[trials];
        for (int t = 0; t < trials; t++) {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = StdRandom.uniform(m);
            distinct[t] = distinct(a);
        }

        double empirical = StdStats.mean(distinct);
        double alpha = (double)n / m;
        double theoretical = m * (1 - Math.exp(-alpha));
        StdOut.printf("Theoretical = %.3f\n", theoretical);
        StdOut.printf("Empirical = %.3f\n", empirical);
    }
}