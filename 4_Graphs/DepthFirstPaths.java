// Run depth first search on an undirected graph.
// Runs in O(E + V) time.
/**
 *  The {@code DepthFirstPaths} class represents a data type for finding
 *  paths from a source vertex s to every other vertex in an undirected graph.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E,
 *  where V is the number of vertices and E is the number of edges.
 *  It uses extra space (not including the graph) proportional to V.
 */
public class DepthFirstPaths {
    private boolean[] marked;   // marked[v] = is there an s-v path ?
    private int[] edgeTo;       // edgeTo[v] = last edge on s-v path
    private final int s;        // source vertex

    // Computes a path between s and every other vertex in graph G.
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    // depth first search uses an explicit stack instead of the function call stack.
    private void bfsWithStack(Graph G, int v) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(v);

        while (!stack.isEmpty()) {
            int m = stack.pop();
            if (!marked[v]) {
                marked[v] = true;
                for (int w : G.adj(m)) {
                    if (!marked[w]) {
                        edgeTo[w] = m;
                        stack.push(w);
                    }
                }
            }
        }
    }

    // Is there a path between the source vertex s and vertex v ?
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    // Returns a path between the source vertex s and vertex v, or null
    // if no such path.
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    // throw new IllegalArgumentException unless { 0 <= v < V }
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the DepthFirstPaths data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d: ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x);
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d: not connected\n", s, v);
            }
        }
    }
}