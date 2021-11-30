// Write a program KendallTau.java that computes the Kendall tau distance
// between two permutations in linearithmic time.
public class KendallTau {

    // return Kendall tau distance between two permutatuins
    public static long distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;
        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;
        int[] bnew = new int[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];
        return Inversions.count(bnew);
    }

    // return a random permutation of size n
    public static int[] permutation(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i;
        StdRandom.shuffle(a);
        return a;
    }

    public static void main(String[] args) {

        // two random permutation of size n
        //int n = Integer.parseInt(args[0]);
        //int[] a = KendallTau.permutation(n);
        //int[] b = KendallTau.permutation(n);
        int n = 5;
        int[] a = {1, 2, 3, 4, 5, 0};
        int[] b = {3, 4, 1, 2 ,5, 0};
        // print initial permutation
        for (int i = 0; i < n; i++)
            StdOut.println(a[i] + " " + b[i]);
        StdOut.println();
        StdOut.println("inversions = " + distance(a, b));
    }
}