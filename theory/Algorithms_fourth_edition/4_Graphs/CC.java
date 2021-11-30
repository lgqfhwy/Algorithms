/**
 *  The {@code CC} class represents a data type for 
 *  determining the connected components in an undirected graph.
 *  The id operation determines in which connected component
 *  a given vertex lies; the connected operation
 *  determines whether two vertices are in the same connected component;
 *  the count operation determines the number of connected
 *  components; and the size operation determines the number
 *  of vertices in the connect component containing a given vertex.
 *  The component identifier of a connected component is one of the
 *  vertices in the connected component: two vertices have the same component
 *  identifier if and only if they are in the same connected component.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E
 *  (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the id, count, connected,
 *  and size operations take constant time.
 */
public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked ?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components

    // Computes the connected components of the undirected graph G.
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    // Counputes the connected components of the edge-weighted graph G
    public CC(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    // depth-first search for a graph
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    // depth-first search for an EdgeWeightedGraph
    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (!marked[w])
                dfs(G, w);
        }
    }

    // Returns the component id of the connected component containing vertex v.
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    // Returns the number of vertices in the connected component containing vertex v.
    public int count() {
        return count;
    }

    // Returns true if vertices v and w are in the same connected
    // component.
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    // Returns true if vertices v and w are in the same
    // connected component.
    @Deprecated
    public boolean areConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) 
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
    
    // Unit tests the CC data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);

        // number of connected components
        int m = cc.count();
        StdOut.println(m + " components");

        // compute list of vertices in each connected component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }
        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}