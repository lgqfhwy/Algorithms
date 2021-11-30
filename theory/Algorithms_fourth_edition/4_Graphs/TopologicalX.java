/**
 *  The {@code TopologicalX} class represents a data type for 
 *  determining a topological order of a directed acyclic graph (DAG).
 *  Recall, a digraph has a topological order if and only if it is a DAG.
 *  The hasOrder operation determines whether the digraph has
 *  a topological order, and if so, the order operation returns one.
 *  This implementation uses a nonrecursive, queue-based algorithm.
 *  The constructor takes time proportional to V + E (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the hasOrder and rank operations takes constant time;
 *  the order operation takes time proportional to V.
 *  See {@link DirectedCycle}, {@link DirectedCycleX}, and
 *  {@link EdgeWeightedDirectedCycle} to compute a
 *  directed cycle if the digraph is not a DAG.
 *  See {@link Topological} for a recursive version that uses depth-first search.
 */
public class TopologicalX {

    private Queue<Integer> order;   // vertices in topological order
    private int[] ranks;    // ranks[v] = order where vertex v appears in order

    // Determines whether the digraph G has a topological order, if so, 
    // finds such a topological order.
    public TopologicalX(Digraph G) {
        int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            indegree[v] = G.indegree(v);
        }
        // initialize
        ranks = new int[G.V()];
        order = new Queue<Integer>();
        int count = 0;

        // initialize queue to contain all vertices with indegree = 0.
        Queue<Integer> queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) {
                queue.enqueue(v);
            }
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            order.enqueue(v);
            ranks[v] = count++;
            for (int w : G.adj(v)) {
                indegree[w]--;
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        // there is a directed cycle in subgraph of vertices with indegree >= 1
        if (count != G.V()) {
            order = null;
        }
        assert check(G);
    }

    // Determines whether the edge-weighted digraph G has a topological order and, 
    // if so, finds such a topological order.
    public TopologicalX(EdgeWeightedDigraph G) {

        // indegrees of remaining vertices
        int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            indegree[v] = G.indegree(v);
        }
        // initialize 
        ranks = new int[G.V()];
        order = new Queue<Integer>();
        int count = 0;

        // Initialize queue to contain all vertices with indegree = 0.
        Queue<Integer> queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) {
                queue.enqueue(v);
            }
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            order.enqueue(v);
            ranks[v] = count++;
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                indegree[w]--;
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        // there is a directed cycle in subgraph of vertices with indegree >= 1.
        if (count != G.V()) {
            order = null;
        }
        assert check(G);
    }

    // Returns a topological order if the digraph has a topological order,
    // and null otherwise.
    public Iterable<Integer> order() {
        return order;
    }

    // Does the digraph have a topological order ?
    public boolean hasOrder() {
        return order != null;
    }

    // The rank of vertex v in the topological order;
    // -1 if the digraph is not a DAG.
    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) {
            return ranks[v];
        }
        else {
            return -1;
        }
    }

    // certify that digraph is acyclic
    private boolean check(Digraph G) {

        // digraph is acyclic
        if (hasOrder()) {
            // check that ranks are a permutation of 0 to V-1
            boolean[] found = new boolean[G.V()];
            for (int i = 0; i < G.V(); i++) {
                found[rank(i)] = true;
            }
            for (int i = 0; i < G.V(); i++) {
                if (!found[i]) {
                    System.err.println("No vertex with rank " + i);
                    return false;
                }
            }

            // check that ranks provide a valid topological order
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    if (rank(v) > rank(w)) {
                        System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n",
                                          v, w, v, rank(v), w, rank(w));
                        return false;
                    }
                }
            }

            // check that order() is consistent with rank()
            int r = 0;
            for (int v : order()) {
                if (rank(v) != r) {
                    System.err.println("order() and rank() inconsistent");
                    return false;
                }
                r++;
            }
        }
        return true;
    }
    // certify that digraph is acyclic
    private boolean check(EdgeWeightedDigraph G) {

        // digraph is acyclic
        if (hasOrder()) {
            // check that ranks are a permutation of 0 to V-1
            boolean[] found = new boolean[G.V()];
            for (int i = 0; i < G.V(); i++) {
                found[rank(i)] = true;
            }
            for (int i = 0; i < G.V(); i++) {
                if (!found[i]) {
                    System.err.println("No vertex with rank " + i);
                    return false;
                }
            }

            // check that ranks provide a valid topological order
            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    if (rank(v) > rank(w)) {
                        System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n",
                                          v, w, v, rank(v), w, rank(w));
                        return false;
                    }
                }
            }

            // check that order() is consistent with rank()
            int r = 0;
            for (int v : order()) {
                if (rank(v) != r) {
                    System.err.println("order() and rank() inconsistent");
                    return false;
                }
                r++;
            }
        }
        return true;
    }
    // throw an IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = ranks.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    public static void main(String[] args) {
        
    }
}