//  Develop an EdgeWeightedGraph implementation for dense graphs that uses an adjacency-matrix 
//  (two-dimensional array of weights) representation. Disallow parallel edges.

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixEdgeWeightedGraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private double[][] adj;

    // Initializes an empty edge-weighted graph with vertices and 0 edges.
    public AdjMatrixEdgeWeightedGraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        this.adj = new double[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                adj[i][j] = 0;
            }
        }
    }

    // Initializes a random edge-weighted digraph with V vertices and E edges.
    public AdjMatrixEdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0)
            throw new IllegalArgumentException("Number of edges in a graph must be nonegative");
        if (E > V * (V - 1))
            throw new IllegalArgumentException("Number of edges too big");
        
        while (this.E != E) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) {
                continue;
            }
            double weight = 0.01 * StdRandom.uniform(100);
            addEdge(v, w, weight);
        }
    }

    // Initializes an edge-weighted graph from the specified input stream.
    // The format is the number of vertices V, followed by the number of edges E,
    // followed by E pairs of vertices and edge weights, with each entry separated 
    // by whiterspace
    public AdjMatrixEdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges must be nonnegative");
        }
        if (E > V * (V - 1))
            throw new IllegalArgumentException("Number of edges too big");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            validateVertex(v);
            validateVertex(w);
            double weight = in.readDouble();
            addEdge(v, w, weight);
        }
    }

    // Returns the number of vertices in this edge-weighted graph.
    public int V() {
        return V;
    }

    // Returns the number of edges in this edge-weighted graph.
    public int E() {
        return E;
    }

    // Throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Adds the graph edge v-w-weight 
    public void addEdge(int v, int w, double weight) {
        if (!(adj[v][w] != 0)) {
            E++;
        }
        adj[v][w] = weight;
        adj[w][v] = weight;
    }

    // does the graph contain the edge v-w ?
    public boolean contains(int v, int w) {
        return adj[v][w] != 0;
    }

    // return list of neighbors of v
    public Iterable<Integer> adj(int v) {
        return new AdjIterator(v);
    }

    // support class AdjIterator over graph vertices
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        private int v;
        private int w = 0;

        AdjIterator(int v) {
            this.v = v;
        }

        public Iterator<Integer> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < V) {
                if (adj[v][w] != 0)
                    return true;
                w++;
            }
            return false;
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return w++;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // String representation of Graph - takes quadratic time.
    public String toStirng() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj(v)) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    // test client
    public static void main(String[] args) {
        //int V = Integer.parseInt(args[0]);
        //int E = Integer.parseInt(args[1]);
        int V = 10;
        int E = 10;
        AdjMatrixGraph G = new AdjMatrixGraph(V, E);
        StdOut.println(G);

        System.out.println("#############");
        for (int v : G.adj(1)) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

}