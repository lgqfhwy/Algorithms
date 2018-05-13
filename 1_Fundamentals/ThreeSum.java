public class ThreeSum {
    public static int count(int[] a) {
        // Count triples that sum to 0.
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    long m = a[i] + a[j];
                    if (m == -a[k])
                        cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] a = In.readInts(args[0]);
        StdOut.println(count(a));
    }
}