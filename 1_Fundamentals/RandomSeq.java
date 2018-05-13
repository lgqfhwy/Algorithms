public class RandomSeq {
    public static void main(String[] args) {
        // Print N random values in (lo, hi)
        int N = Integer.parseInt(args[0]);
        double lo = Double.parseDouble(args[1]);
        double hi = Double.parseDouble(args[2]);
        for (int i = 0; i < N; i++) {
            double x = StdRandom.uniform(lo, hi);
            StdOut.printf("%5.2f\n", x);
        }
    }
}