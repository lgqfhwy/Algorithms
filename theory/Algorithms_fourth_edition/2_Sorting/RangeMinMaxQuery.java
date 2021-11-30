import java.util.Comparator;
public class RangeMinMaxQuery<Key> {
    private Key[][] MinSum;
    private Key[][] MaxSum;
    private int length1;    // the row of the array
    private int length2;    // the column of the array, the number of the init array


    public RangeMinMaxQuery(Key[] init) {
        int j = 1;
        while (j < init.length)
            j *= 2;
        length1 = j;
        length2 = init.length;
        MinSum = (Key[][]) new Object[length1 + 1][length2 + 1];
        MaxSum = (Key[][]) new Object[length1 + 1][length2 + 1];
        for (int i = 1; i <= length2; i++) {
            MinSum[i][0] = init[i - 1];
            MaxSum[i][0] = init[i - 1];
        }
        ComputeMinMax();
    }

    public void ComputeMinMax() {
        for (int j = 1; j <= length1; j++) {
            for (int i = 1; i <= length2; i++) {
                int t = (int)Math.pow(2, j - 1);
                if (i + t <= length2) {
                    MinSum[i][j] = min(MinSum[i][j - 1], MinSum[i + t][j - 1]);
                    MaxSum[i][j] = max(MaxSum[i][j - 1], MaxSum[i + t][j - 1]);
                    StdOut.printf("MaxSum[%d][%d] = ", i, j);
                    StdOut.println(MaxSum[i][j]);
                }
            }
        }
    }

    public Key queryMin(int a, int b) {
        StdOut.println("b - a + 1 = " + (b - a + 1));
        int k = (int)(Math.log(b - a + 1) / Math.log(2));
        int h = (int)Math.pow(2, k);
        return min(MinSum[a][k], MinSum[b - h + 1][k]);
    }

    public Key queryMax(int a, int b) {
        StdOut.println("b - a + 1 = " + (b - a + 1));
        int k = (int)(Math.log(b - a + 1) / Math.log(2));
        int h = (int)Math.pow(2, k);
        StdOut.println("k = " + k + " h = " + h);
        StdOut.println("a = " + a + " k = " + k + " b - h + 1 = " + (b - h + 1) + " k = " + k);
        return max(MaxSum[a][k], MaxSum[b - h + 1][k]); 
    }

    private Key min(Key i, Key j) {
        if (i == null || j == null) {
            if (i == null && j == null)
                return null;
            else if (i == null)
                return j;
            else
                return i;
        }
        Key num = i;
        if (less(j, i))
            num = j;
        return num;
    }

    private Key max(Key i, Key j) {
        Key num = i;
        if (less(i, j))
            num = j;
        return num;
    }

    private boolean less(Key i, Key j) {
        if (i == null || j == null) {
            if (i == null && j == null)
                return false;
            else if (i == null)
                return true;
            else
                return false;
        }
        return ((Comparable<Key>)i).compareTo(j) < 0;
    }

    public void showMin() {
        for (int i = 1; i < length1; i++) {
            for (int j = 1; j < length2; j++) {
                StdOut.print(MinSum[i][j] + " ");
            }
            StdOut.println();
        }
    }

    public void showMax() {
        for (int i = 1; i < length1; i++) {
            for (int j = 1; j < length2; j++) {
                StdOut.print(MaxSum[i][j] + " ");
            }
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        Integer[] a = {3, 2, 8, 11, 10, 9};
        RangeMinMaxQuery<Integer> pq = new RangeMinMaxQuery<Integer>(a);
        //pq.showMin();
        StdOut.println(pq.queryMax(1, 6));
    }
}














































