/**
 *  The {@code TransitiveClosure} class represents a data type for 
 *  computing the transitive closure of a digraph.
 *  This implementation runs depth-first search from each vertex.
 *  The constructor takes time proportional to V (V + E)
 *  (in the worst case) and uses space proportional to V<sup>2</sup>,
 *  where V is the number of vertices and E is the number of edges.
 */
public class TransitiveClosure {

    private DirectedDFS[] tc;   // tc[v] = reachable from v.

    // Computes the transitive closure of the digraph G.
    public TransitiveClosure(Digraph G) {
        tc = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            tc[v] = new DirectedDFS(G, v);
    }

    // Is there a directed path from vertex v to vertex w in the digraph ?
    public boolean reachable(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return tc[v].marked(w);
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = tc.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        TransitiveClosure tc = new TransitiveClosure(G);

        // print header
        StdOut.print("     ");
        for (int v = 0; v < G.V(); v++)
            StdOut.printf("%3d", v);
        StdOut.println();
        StdOut.println("--------------------------------------------");

        // print transitive closure
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (tc.reachable(v, w)) StdOut.printf("  T");
                else                    StdOut.printf("   ");
            }
            StdOut.println();
        }
    }
}