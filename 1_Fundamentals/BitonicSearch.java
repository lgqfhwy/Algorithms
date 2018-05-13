public class BitonicSearch {
    // find the index of the maximum in a bitonic subarray.
    public static int max(int[] a, int lo, int hi) {
        if (lo == hi)   return hi;
        int mid = (lo + hi) / 2;
        if (a[mid] < a[mid + 1])    return max(a, mid + 1, hi);
        else if (a[mid] >  a[mid + 1])  return max(a, lo, mid);
        else    return mid;
    }
    public static int binarysearch(int[] a, int key, boolean reverse) {
        // if reverse is false, we consider a as increase
        // otherwise a is decrease.
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (a[mid] == key)
                return mid;
            else if (a[mid] > key) {
                if (!reverse)
                    hi = mid - 1;
                else
                    lo = mid + 1;
            }
            else {
                if (!reverse)
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }
        return -1;
    }
    public static int find(int[] a, int key) {
        int maxi = max(a, 0, a.length - 1);
        int n1 = binarysearch(a, key, false);
        int n2 = binarysearch(a, key, true);
        if (n1 != -1)
            return n1;
        else if (n2 != -1)
            return n2;
        else 
            return -1;
    }
    public static void main(String[] args) {
        int[] a = {0, 8, 14, 19, 22, 20, 11, 9, 1, -8};
        int index = find(a, -8);
        StdOut.println(index);
    }
}

































