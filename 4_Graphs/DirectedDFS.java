/**
 *  The {@code DirectedDFS} class represents a data type for 
 *  determining the vertices reachable from a given source vertex s
 *  (or set of source vertices) in a digraph. For versions that find the paths,
 *  see {@link DepthFirstDirectedPaths} and {@link BreadthFirstDirectedPaths}.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E
 *  (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 */
public class DirectedDFS {

    private boolean[] marked;   // marked[v] = true if v is reachable
                                // from source (or source)
    private int count;          // number of vertices reachable from s

    // Computes the vertices in digraph G that are reachable from the source vertex s.
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    // Computes the vertices in digraph G that are connected to any of the 
    // source vertices sources.
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        validateVertex(sources);
        for (int v : sources) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    private void dfs(Digraph G, int v) {
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
    }

    // Is there a directed path from the source vertex (or any of the source
    // vertices) and vertex v ?
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    // Returns the number of vertices reachable from the source vertex
    // (or source vertices).
    public int count() {
        return count;
    }

    // thorw new IllegalArgumentException unless code 0 <= v < V.
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the DirectedDFS data type.
    public static void main(String[] args) {
        // read in digraph from command-line argument
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        // read in sources from command-line arguments
        Bag<Integer> sources = new Bag<Integer>();
        for (int i = 1; i < args.length; i++) {
            int s = Integer.parseInt(args[i]);
            sources.add(s);
        }

        // multiple-source reachability
        DirectedDFS dfs = new DirectedDFS(G, sources);

        // print out vertices reachable from sources
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v)) StdOut.print(v + " ");
        }
        StdOut.println();
    }

}