public class KendallDistance {
    private static long counter = 0;

    public static long distance(int[] a, int[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException("Array dimensions disagree");
        int N = a.length;
        int[] aIndex = new int[N];
        for (int i = 0; i < N; i++)
            aIndex[a[i]] = i;
        int[] bIndex = new int[N];
        for (int i = 0; i < N; i++)
            bIndex[i] = aIndex[b[i]];
        return mergeCount(bIndex);
    }

    public static long insertionCount(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && a[j] < a[j - 1]; j--) {
                int temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
                counter++;
            }
        }
        return counter;
    }

    public static long mergeCount(int[] a) {
        int[] aux = new int[a.length];
        mergeSort(a, aux, 0, a.length - 1);
        return counter;
    }

    private static void mergeSort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo)
            return ;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, aux, lo, mid);
        mergeSort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    a[k] = aux[j++];
            else if (j > hi)    a[k] = aux[i++];
            else if (aux[j] < aux[i])  {
                a[k] = aux[j++];
                counter += mid - i + 1;
            } 
            else    a[k] = aux[i++];
        }
    }
    public static void main(String[] args) {
        int[] a = {0, 3, 1, 6, 2, 5, 4};
        int[] b = {1, 0, 3, 6, 4, 2, 5};
        for (int i = 0; i < a.length; i++)
            System.out.println(a[i] + " " + b[i]);
        System.out.println("Inversions: " + distance(a, b));
    }
}


































