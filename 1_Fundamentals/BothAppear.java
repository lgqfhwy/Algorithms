public class BothAppear {
    public static void findboth(int[] a, int[] b) {
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                i++;
            else if (a[i] > b[j])
                j++;
            else  {
                StdOut.print(a[i] + " ");
                i++;
                j++;
            }
        }
    }
    public static void main(String[] args) {
        int[] a = {2, 5, 6, 7, 8, 9, 10, 34};
        int[] b = {1, 3, 4, 5, 6, 7};
        findboth(a, b);
    }
}