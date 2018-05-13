public class InversionsSelf {
    public static int count(int[] a) {
        int countNum = 0;
        int[] aux = new int[a.length];
        countNum = mergeSortCount(a, aux, 0, a.length - 1, countNum);
        return countNum;
    }

    public static int mergeSortCount(int[] a, int[] aux, int lo, int hi, int countNum) {
        if (lo >= hi)    return 0;
        int mid = lo + (hi - lo) / 2;
        countNum += mergeSortCount(a, aux, lo, mid, countNum);
        countNum += mergeSortCount(a, aux, mid + 1, hi, countNum);
        countNum += merge(a, aux, lo, mid, hi, countNum);
        return countNum;
    }

    public static int merge(int[] a, int[] aux, int lo, int mid, int hi, int countNum) {
        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)    a[k] = aux[j++];
            else if (j > hi)     a[k] = aux[i++];
            else if (a[i] <= a[j])   a[k] = a[i++];
            else  {
                a[k] = a[j++];
                countNum += (mid - i + 2);
            }  
        }
        return countNum;
    }
    public static void main(String[] args) {
        
    }
}