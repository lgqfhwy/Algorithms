// Floyd-Warshall algorithm. FloydWarshall.java implements the Floyd-Warshall algorithm 
// for the all-pairs shortest path problem. It takes time proportional to V^3 and space 
// proportional to V^2. It uses AdjMatrixEdgeWeightedDigraph.java.

// Floyd-Warshall all-pairs shortest path algorithm.
// Should check for negative cycles during triple loop; otherwise
// intermediate numbers can get exponentially large.
// Reference: "The Floyd-Warshall algorithm on graphs with negative cycles"
// by Stefan Hougardy

/**
 *  The {@code FloydWarshall} class represents a data type for solving the
 *  all-pairs shortest paths problem in edge-weighted digraphs with
 *  no negative cycles.
 *  The edge weights can be positive, negative, or zero.
 *  This class finds either a shortest path between every pair of vertices
 *  or a negative cycle.
 *  This implementation uses the Floyd-Warshall algorithm.
 *  The constructor takes time proportional to V^3 in the
 *  worst case, where V is the number of vertices.
 *  Afterwards, the {@code dist()}, {@code hasPath()}, and {@code hasNegativeCycle()}
 *  methods take constant time; the {@code path()} and {@code negativeCycle()}
 *  method takes time proportional to the number of edges returned.
 */
public class FloydWarshall {

    private boolean hasNegativeCycle;   // is there a negative cycle ?
    private double[][] distTo;  // distTo[v][w] = length of shortest v->w path.
    private DirectedEdge[][] edgeTo;    // edgeTo[v][w] = last edge on shortest v->w path.

    // Computes a shortest paths tree from each vertex to every other vertex in 
    // the edge-weighted digraph G. If no such shortest path exists for same pair
    // vertices, it computes a negative cycle.
    public FloydWarshall(AdjMatrixEdgeWeightedDigraph G) {
        int V = G.V();
        distTo = new double[V][V];
        edgeTo = new DirectedEdge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // in case of self-loops
            if (distTo[v][v] >= 0.0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floy-Warshall update
        for (int i = 0; i < V; i++) {
            // compute shortest paths using only 0, 1, ,,, i as intermediate vertices
            for (int v = 0; v < V; v++) {
                if (edgeTo[v][i] == null) {
                    continue;   // optimization
                }
                for (int w = 0; w < V; w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if (distTo[v][v] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
        assert check(G);
    }

    // Is there a negative cycle ?
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    // Returns a negative cycle, or null if there is no such cycle.
    public Iterable<DirectedEdge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            // negative cycle in v's predecessor graph.
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; w++) {
                    if (edgeTo[v][w] != null) {
                        spt.addEdge(edgeTo[v][w]);
                    }
                }
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    // Is there a path from the vertex s to vertex t ?
    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return distTo[s][t] < Double.POSITIVE_INFINITY;
    }

    // Returns the length of a shortest path from vertex s to vertex t.
    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return distTo[s][t];
    }

    // Returns a shortest path from vertex s to vertex t.
    public Iterable<DirectedEdge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!hasPath(s, t)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.from()]) {
            path.push(e);
        }
        return path;
    }

    // check optimality conditions
    private boolean check(AdjMatrixEdgeWeightedDigraph G) {
        // no negative cycle
        if (!hasNegativeCycle()) {
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    for (int i = 0; i < G.V(); i++) {
                        if (distTo[i][w] > distTo[i][v] + e.weight()) {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the FloydWarshall data type
    public static void main(String[] args) {
        // random graph with V vertices and E edges, parallel edges allowed
        //int V = Integer.parseInt(args[0]);
        //int E = Integer.parseInt(args[1]);
        int V = 10;
        int E = 15;
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * (StdRandom.uniform() - 0.15)) / 100.0;
            if (v == w) G.addEdge(new DirectedEdge(v, w, Math.abs(weight)));
            else G.addEdge(new DirectedEdge(v, w, weight));
        }

        StdOut.println(G);

        // run Floyd-Warshall algorithm
        FloydWarshall spt = new FloydWarshall(G);

        // print all-pairs shortest path distances
        StdOut.printf("  ");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%6d ", v);
        }
        StdOut.println();
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); w++) {
                if (spt.hasPath(v, w)) StdOut.printf("%6.2f ", spt.dist(v, w));
                else StdOut.printf("  Inf ");
            }
            StdOut.println();
        }

        // print negative cycle
        if (spt.hasNegativeCycle()) {
            StdOut.println("Negative cost cycle:");
            for (DirectedEdge e : spt.negativeCycle())
                StdOut.println(e);
            StdOut.println();
        }

        // print all-pairs shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                for (int w = 0; w < G.V(); w++) {
                    if (spt.hasPath(v, w)) {
                        StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (DirectedEdge e : spt.path(v, w))
                            StdOut.print(e + "  ");
                        StdOut.println();
                    }
                    else {
                        StdOut.printf("%d to %d no path\n", v, w);
                    }
                }
            }
        }
    }
 }