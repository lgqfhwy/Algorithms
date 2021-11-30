public class ErdosRenyi {
    public static int count(int n) {
        int edges = 0;
        UF uf = new UF(n);
        while (uf.count() > 1) {
            int i = StdRandom.uniform(n);
            int j = StdRandom.uniform(n);
            uf.union(i, j);
            edges++;
        }
        return edges;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);   // number of vertices
        int trials = Integer.parseInt(args[1]);   // number of trials.
        int[] edges = new int[trials];

        // repeat the experiment trials times
        for (int t = 0; t < trials; t++) {
            edges[t] = count(n);
        }

        // report statistics
        StdOut.println("1/2 n ln n = " + 0.5 * n * Math.log(n));
        //StdOut.println("mean       = " + StdStats.mean(edges));
        //StdOut.println("stddev.    = " + StdStats.stddev(edges));
    }
}