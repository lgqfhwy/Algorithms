public class PeakFinding {
    // Find a local minimum or maximum in a terrain by sampling.
    // 1D Peak Finding, Given an array A[0..n-1], A[i] is a peak if it is
    // not smaller than its neighbor(s): A[i - 1] <= A[i] >= A[i + 1];
    // where we imagine: A[-1] = A[n] = -MAXVALUE
    // Goal: Find any peak.

    // "Brute Force" Algorithm: test all elements for peakyness.
    //         for i in range(n):
    //              if A[i - 1] <= A[i] >= A[i + 1]:
    //                  return i

    // Look at any element A[i] and its neighbors A[i - 1] & A[i + 1],
    // if peak, return i; 
    // otherwise: locally rising on some side.
    //       Must be a peak in that direction.
    //       So can throw away rest of array, leaving A[:i] or A[i+1:]   

    public static int peakOneDimension(int[] a, int i, int j) {
        int m = i + (j - i) / 2;
        if (m == 0 || m == 1) {
            return m;
        }
        else if (a[m] >= a[m - 1] && a[m] <= a[m + 1]) {
            return m;
        }
        else if (a[m] < a[m - 1]) {
            return peakOneDimension(a, i, m - 1);
        }
        else {
            return peakOneDimension(a, m + 1, j);
        }
    }                
}