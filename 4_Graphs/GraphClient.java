// Typical graph-processing code.
public class GraphClient {

    // compute the degree of v
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w : G.adj(v))
            degree++;
        return degree;
    }

    // String representation of the graph's adjacency lists
    // (instance method in Graph)
    public String toString() {
        String s = G.V() + " vertices, " + G.E() + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w: this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    // maximum degree
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > max)
                max = G.degree(v);
        }
        return max;
    }

    // average degree
    public static int avgDegree(Graph G) {
        // each edge incident on two vertices
        return 2 * G.E() / G.V();
    }

    // number of self-loops
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w)
                    count++;
            }
        }
        return count / 2;   // self loop appears in adjacency list twice.
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);

        StdOut.println("vertex of maximum degree = " + maxDegree(G));
        StdOut.println("average degree           = " + avgDegree(G));
        StdOut.println("number of self loops     = " + numberOfSelfLoops(G));
    }
}