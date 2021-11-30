/**
 *  The {@code DepthFirstDirectedPaths} class represents a data type for finding
 *  directed paths from a source vertex s to every
 *  other vertex in the digraph.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E,
 *  where V is the number of vertices and E is the number of edges.
 *  It uses extra space (not including the graph) proportional to V.
 */
public class DepthFirstDirectedPaths {
    private boolean[] marked;   // marked[v] = true if v is reachable from s.
    private int[] edgeTo;   // edgeTo[v] = last edge on path from s to v.
    private final int s;    // source vertex.

    // Computes a directed path from s to every other vertex in digraph G.
    public DepthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    // Is there a directed path from the source vertex s to vertex v ?
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    // Returns a directed path from the source vertex s to vertex v, or null
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

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the DepthFirstDirectedPaths data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        int s = Integer.parseInt(args[1]);
        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d: ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s)
                        StdOut.print(x);
                    else
                        StdOut.print("-" + x);
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d: not connected\n", s, v);
            }
        }
    }
}