// Compute transitive closure of a digraph G and support
// reachability queries.
public class WarshallTC {

    private boolean[][] tc;    // tc[v][w] = true iff path from v to w.

    public WarshallTC(AdjMatrixDigraph G) {
        // initialize tc[][]
        int V = G.V();
        tc = new boolean[V][V];
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                tc[v][w] = true;
            }
            tc[v][v] = true;
        }
        // Warshall's algorithm
        for (int i = 0; i < V; i++) {
            for (int v = 0; v < V; v++) {
                if (!tc[v][i])
                    continue;
                for (int w = 0; w < V; w++) {
                    if (tc[v][i] && tc[i][w])
                        tc[v][w] = true;
                }
            }
        }
    }

    // is there a directed path from v to w ?
    public boolean hasPath(int v, int w) {
        return tc[v][w];
    }

    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        AdjMatrixDigraph G = new AdjMatrixDigraph(V, E);
        StdOut.println(G);
        WarshallTC tc = new WarshallTC(G);

        // print header
        StdOut.println("Transitive closure");
        StdOut.println("-----------------------------------");
        StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            StdOut.printf("%3d", v);
        StdOut.println();

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.hasPath(v, w)) StdOut.printf("  x");
                else                  StdOut.printf("   ");
            }
            StdOut.println();
        }
    }
}