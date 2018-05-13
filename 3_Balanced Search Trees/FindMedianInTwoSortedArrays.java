public class FindMedianInTwoSortedArrays {

    private FindMedianInTwoSortedArrays() { }

    public static double findMedianSortedArrays(int[] a, int[] b) {
        int len = a.length + b.length;
        if (len % 2 == 0)   return (findKth(a, 0, b, 0, len/2) + findKth(a, 0, b, 0, len/2 + 1)) / 2.0;
        else    return findKth(a, 0, b, 0, len/2 + 1);
    }

    // find kth number of two sorted array
    public static int findKth(int[] a, int aStart, int[] b, int bStart, int k) {
        if (aStart >= a.length)   return b[bStart + k - 1];
        else if (bStart >= b.length)  return a[aStart + k - 1];
        if (k == 1)     return Math.min(a[aStart], b[bStart]);
        int aKey = (aStart + k / 2 - 1 < a.length)  //  if one array is too short
                    ? a[aStart + k / 2 - 1] : Integer.MAX_VALUE;    // trick;
        int bKey = (bStart + k / 2 - 1 < b.length)  // if one array is too short
                    ? b[bStart + k / 2 - 1] : Integer.MAX_VALUE;
        
        if (aKey < bKey) {
            return findKth(a, aStart + k / 2, b, bStart, k - k / 2);
        }
        else {
            return findKth(a, aStart, b, bStart + k / 2, k - k / 2);
        }
    }
    public static void main(String[] args) {
        ;
    }
}