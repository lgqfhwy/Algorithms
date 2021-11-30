// Computes shortest paths in an edge-weighted acyclic digraph.
/**
 *  The {@code AcyclicSP} class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted directed acyclic
 *  graphs (DAGs). The edge weights can be positive, negative, or zero.
 *  This implementation uses a topological-sort based algorithm.
 *  The constructor takes time proportional to V + E,
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the {@code distTo()} and {@code hasPathTo()} methods take
 *  constant time and the {@code pathTo()} method takes time proportional to the
 *  number of edges in the shortest path returned.
 */
public class AcyclicSP {

    private double[] distTo;    // distTo[v] = distance of shortest s->v path.
    private DirectedEdge[] edgeTo;  // edgeTo[v] = last edge on shortest s->v path.

    // Computes a shortest paths tree from s to every other vertex in
    // the directed acyclic graph G.
    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // visit vertices in topological order.
        Topological topological = new Topological(G);
        if (!topological.hasOrder()) {
            throw new IllegalArgumentException("Digraph is not acyclic.");
        }
        for (int v : topological.order()) {
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    // relax edge e
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    // Returns the length of a shortest path from the source vertex s to vertex v.
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    // Is there a path from the source vertex s to vertex v ?
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
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

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the AcyclicSP data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // find shortest path from s to each other vertex in DAG.
        AcyclicSP sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                System.out.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (DirectedEdge e : sp.pathTo(v)) {
                    System.out.print(e + "  ");
                }
                System.out.println();
            }
            else {
                System.out.printf("%d to %d   no path\n", s, v);
            }
        }
    }
}
