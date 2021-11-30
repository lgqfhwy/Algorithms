public class LocalMinArray {
    // This algorithm only works if edges of the array count as local minima
    // if they are smaller than the adjacent element.
    public static int findlocalmin(int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            int mid = (i + j) / 2;
            if (mid == 0 && a[mid] < a[mid + 1]) {
                return mid;
            }
            if (mid == a.length - 1 && a[mid] < a[mid - 1]) {
                return mid;
            }
            if (a[mid] < a[mid - 1] && a[mid] < a[mid + 1]) {
                return mid;
            }
            else if (a[mid] > a[mid - 1])
                j = mid - 1;
            else 
                i = mid + 1;
        }
        return -1;
    }
    public static void main(String[] args) {
        int a[] = {1, 2, 3, 4, 5, 6};
        int k = findlocalmin(a);
        StdOut.println(k);
    }
}