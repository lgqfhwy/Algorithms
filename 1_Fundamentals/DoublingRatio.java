public class DoublingRatio {
    public static double timeTrial(int N) {
        int MAX = 1000000;
        double[] a = new double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(-MAX, MAX);
        Stopwatch timer = new Stopwatch();
        //int cnt = ThreeSumFast.count(a);
        //ClosetPair.difference(a);
        //int cnt = TwoSumFast.count(a);
        FarthestPair.farthet(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        double prev = timeTrial(125);
        for (int N = 250; N < 100000; N += N) {
            double time = timeTrial(N);
            StdOut.printf("%6d %7.1f ", N, time);
            StdOut.printf("%5.1f\n", time/prev);
            prev = time;
        }
    }
}