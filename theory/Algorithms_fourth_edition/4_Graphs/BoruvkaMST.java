// Boruvka's algorithm. Develop an implementation BoruvkaMST.java of Boruvka's algorithm: 
// Build an MST by adding edges to a growing forest of trees, as in Kruskal's algorithm, 
// but in stages. At each stage, find the minimum-weight edge that connects each tree to 
// a different one, then add all such edges to the MST. Assume that the edge weights are 
// all different, to avoid cycles. Hint: Maintain in a vertex-indexed array to identify 
// the edge that connects each component to its nearest neighbor, and use the union-find data structure.
// Remark. There are a most log V phases since number of trees decreases by at least a factor 
// of 2 in each phase. Attractive because it is efficient and can be run in parallel.

/**
 *  The {@code BoruvkaMST} class represents a data type for computing a
 *  minimum spanning tree in an edge-weighted graph.
 *  The edge weights can be positive, zero, or negative and need not
 *  be distinct. If the graph is not connected, it computes a minimum
 *  spanning forest, which is the union of minimum spanning trees
 *  in each connected component. The {@code weight()} method returns the 
 *  weight of a minimum spanning tree and the {@code edges()} method returns its edges.
 *  This implementation uses Boruvka's algorithm and the union-find data type.
 *  The constructor takes time proportional to E log V
 *  and extra space (not including the graph) proportional to V,
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the {@code weight()} method takes constant time
 *  and the {@code edges()} method takes time proportional to V.
 */
public class BoruvkaMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Bag<Edge> mst = new Bag<Edge>();    // edges in MST
    private double weight;    // weight of MST.

    // Compute a minimum spanning tree (or forest) of an edge-weighted graph.
    public BoruvkaMST(EdgeWeightedGraph G) {
        UF uf = new UF(G.V());
        // repeat at most logV times or until we have V-1 edges.
        for (int t = 1; t < G.V() && mst.size() < G.V() - 1; t = t + t) {
            // foreach tree in forest, find closest edge
            // if edge weights are equal, ties are broken in favour of first edge in G.edges()
            Edge[] closest = new Edge[G.V()];
            for (Edge e : G.edges()) {
                int v = e.either(), w = e.other(v);
                int i = uf.find(v), j = uf.find(w);
                if (i == j) {
                    continue;   // same tree
                }
                if (closest[i] == null || less(e, closest[i])) {
                    closest[i] = e;
                }
                if (closest[j] == null || less(e, closest[j])) {
                    closest[j] = e;
                }
            }

            // add newly discovered edges to MST.
            for (int i = 0; i < G.V(); i++) {
                Edge e = closest[i];
                if (e != null) {
                    int v = e.either(), w = e.other(v);
                    // don't add the same edge twice
                    if (!uf.connected(v, w)) {
                        mst.add(e);
                        weight += e.weight();
                        uf.union(v, w);
                    }
                }
            }
        }
        // check optimality conditions
        assert check(G);
    }

    // Returns the edges in a minimum spanning tree (or forest).
    public Iterable<Edge> edges() {
        return mst;
    }

    // Returns the sum of the edge weights in a minimum spanning tree (or forest).
    public double weight() {
        return weight;
    }

    // is the weight of edge e strictly less than than of edge f ?
    private static boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }

    // check optimality conditions (takes time proportional to E V log V)
       private boolean check(EdgeWeightedGraph G) {

        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either(), w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {

            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either(), y = f.other(x);
                if (f != e) uf.union(x, y);
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either(), y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }

    // Unit tests the BoruvkaMST data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        BoruvkaMST mst = new BoruvkaMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}