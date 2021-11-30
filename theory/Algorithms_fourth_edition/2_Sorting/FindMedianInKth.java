public class FindMedianInKth {

    public static double findMedianSortedArrays(int[] a, int[] b) {
        int len = a.length + b.length;
        if (len % 2 == 0)   return (findKth(a, b, len/2) + findKth(a, b, len/2 + 1)) / 2.0;
        else    return findKth(a, b, len/2 + 1);
    }
    // Find the kth smallest element in the union of two sorted arrays.
    public static int findKth(int a1[], int a2[], int k) {
        int size1 = a1.length, size2 = a2.length;
        int index1 = 0, index2 = 0, step = 0;
        while (index1 + index2 < k - 1) {
            step = (k - index1 - index2) / 2;
            int step1 = index1 + step;
            int step2 = index2 + step;
            if (size1 > step1 - 1 && 
               (size2 <= step2 - 1 || a1[step1 - 1] < a2[step2 - 1])) {
                index1 = step1;
            }
            else {
                index2 = step2;
            }
        }
        // The base case of (index1 + index2 == k - 1)
        if (size1 > index1 && (size2 <= index2 || a1[index1] < a2[index2])) {
            return a1[index1];
        }
        else {
            return a2[index2];
        }
    }

    public static int findKthRecursive(int a1[], int a2[], int index1, int index2, int k) {
        int size1 = a1.length, size2 = a2.length;
        // The base case of (index1 + index2 == k - 1)
        if (index1 + index2 == k - 1) {
            if (size1 > index1 && (size2 <= index2 || a1[index1] < a2[index2])) {
                return a1[index1];
            }
            else {
                return a2[index2];
            }
        }
        int step = (k - index1 - index2) / 2;
        int step1 = index1 + step;
        int step2 = index2 + step;
        if (size1 > step1 - 1 && (size2 <= step2 - 1 || a1[step1 - 1] < a2[step2 - 1])) {
            index1 = step1;
        }
        else {
            index2 = step2;
        }
        return findKthRecursive(a1, a2, index1, index2, k);
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 7, 8, 9};
        int[] b = {5, 6};
        double mid = findMedianSortedArrays(a, b);
        StdOut.println(mid);
    }
}