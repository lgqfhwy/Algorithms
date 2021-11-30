// Dijkstra's algorithm. Computes the shortest path tree.
// Assumes all weights are nonnegative.

/**
 *  The {@code DijkstraSP} class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to E log V,
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the {@code distTo()} and {@code hasPathTo()} methods take
 *  constant time and the {@code pathTo()} method takes time proportional to the
 *  number of edges in the shortest path returned.
 */
public class DijkstraSP {

    private double[] distTo;    // distTo[v] = distance of shortest s->v path.
    private DirectedEdge[] edgeTo;  // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;  // priority queue of vertices.
    private DirectedEdge[] edgeToSecond;    // edgeTo[v] = last edge on second shortedt s->v path
    boolean hasSeondPath;

    // Computes a shortest-paths tree from the source vertex s to every other
    // vertex in the edge-weighted digraph G.
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                throw new IllegalArgumentException("edge " + e + " has negative weight");
            }
        }
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        edgeToSecond = new DirectedEdge[G.V()];
        hasSeondPath = false;
        validateVertex(s);

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // relax vertices in order of distance from s.
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeToSecond[w] = e;
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]);
            }
            else {
                pq.insert(w, distTo[w]);
            }
        }
        else if (distTo[w] == distTo[v] + e.weight()) {
            edgeToSecond[w] = e;
            hasSeondPath = true;
        }
    }

    // Returns the length of a shortest path from the source vertex s to vertex v.
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    // Returns true if there is a path from the source vertex s to vertex  v.
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // Returns true if there is a second path from the source vertex s to vertex v.
    public boolean hasSecondPathTo(int v) {
        validateVertex(v);
        return (edgeToSecond[v] != null) && hasSeondPath;
    }

    // Returns a shortest path from the source vertex s to vertex v.
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    // Returns a second shortest path from the source vertex s to vertex v.
    public Iterable<DirectedEdge> secondPathTo(int v) {
        validateVertex(v);
        if (!hasSecondPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> secondPath = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeToSecond[v]; e != null; e = edgeToSecond[e.from()]) {
            secondPath.push(e);
        }
        return secondPath;
    }

    // check optimality conditions:
    // (1) for all edges e : distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (2) fro all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(EdgeWeightedDigraph G, int s) {

        // check that edges weights are nonnegative
        for (DirectedEdge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) {
                continue;
            }
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) {
                continue;
            }
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) {
                return false;
            }
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the DijkstraSP data type.
    public static void main(String[] args) {
        // In in = new In(args[0]);
        // EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        // int s = Integer.parseInt(args[1]);

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(6);
        G.addEdge(new DirectedEdge(0, 1, 2));
        G.addEdge(new DirectedEdge(1, 2, 3));
        G.addEdge(new DirectedEdge(2, 3, 8));
        G.addEdge(new DirectedEdge(0, 4, 1));
        G.addEdge(new DirectedEdge(4, 5, 2));
        G.addEdge(new DirectedEdge(5, 3, 10));
        int s = 0;

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
        StdOut.println("Print second Path:");
        // print second path
        if (sp.hasSecondPathTo(3)) {
            for (DirectedEdge e : sp.secondPathTo(3)) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }

    }
}