// An edge-weighted digraph, implemented using an adjacency matrix.
// Parallel edges are disallowed; self-loops are allowed.

/**
 *  The {@code AdjMatrixEdgeWeightedDigraph} class represents a edge-weighted
 *  digraph of vertices named 0 through V - 1, where each
 *  directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *  It supports the following two primary operations: add a directed edge
 *  to the digraph and iterate over all of edges incident from a given vertex.
 *  It also provides
 *  methods for returning the number of vertices V and the number
 *  of edges E. Parallel edges are disallowed; self-loops are permitted.
 *  This implementation uses an adjacency-matrix representation.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident from a given vertex, which takes
 *  time proportional to V.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixEdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private DirectedEdge[][] adj;

    // Initializes an empty-weighted digraph with V vertices and 0 edges.
    public AdjMatrixEdgeWeightedDigraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        this.adj = new DirectedEdge[V][V];
    }

    // Initializes a random edge-weighted digraph with V vertices and E edges.
    public AdjMatrixEdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("number of edges must be nonnegative");
        }
        if (E > V * V) {
            throw new IllegalArgumentException("too many edges");
        }
        // can be inefficient
        while (this.E != E) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    // Returns the number of vertices in the edge-weighted digraph.
    public int V() {
        return V;
    }

    // Returns the number of edges in the edge-weighted digraph.
    public int E() {
        return E;
    }

    // Adds the directed edge e to the edge-weighted digraph (if there
    // is not already an edge with the same endpoints).
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        if (adj[v][w] == null) {
            E++;
            adj[v][w] = e;
        }
    }

    // Returns the directed edges incident from vertex v.
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return new AdjIterator(v);
    }

    // support iteration over graph vertices
    private class AdjIterator implements Iterator<DirectedEdge>, Iterable<DirectedEdge> {
        private int v;
        private int w = 0;

        public AdjIterator(int v) {
            this.v = v;
        }

        public Iterator<DirectedEdge> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < V) {
                if (adj[v][w] != null) {
                    return true;
                }
                w++;
            }
            return false;
        }

        public DirectedEdge next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return adj[v][w++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Returns a string representation of the edge-weighted digraph. This method 
    // takes time proportional to V^2.
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the AdjMatrixEdgeWeightedDograph data type.
    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(V, E);
        StdOut.println(G);
    }
}