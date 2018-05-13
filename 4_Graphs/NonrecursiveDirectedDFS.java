// Run nonrecurisve depth-first search on an directed graph.
// Runs in O(E + V) time.
// Explores the vertices in exactly the same order as DirectedDFS.java.
/**
 *  The {@code NonrecursiveDirectedDFS} class represents a data type for finding
 *  the vertices reachable from a source vertex s in the digraph.
 *  This implementation uses a nonrecursive version of depth-first search
 *  with an explicit stack.
 *  The constructor takes time proportional to V + E,
 *  where V is the number of vertices and E is the number of edges.
 *  It uses extra space (not including the digraph) proportional to V.
 */
import java.util.Iterator;
public class NonrecursiveDirectedDFS {

    private boolean[] marked;   // marked[v] = is there an s->v path ?

    // Computes the vertices reachable from the source vertex s in the digraph G.
    public NonrecursiveDirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);

        // to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next.
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = G.adj(v).iterator();
        }

        // depth-first search using an explicit stack.
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            }
            else {
                stack.pop();
            }
        }
    }

    // Is vertex v reachable from the source vertex s ?
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    // Unit tests the NonrecursiveDirectedDFS data type
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        int s = Integer.parseInt(args[1]);
        NonrecursiveDirectedDFS dfs = new NonrecursiveDirectedDFS(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v))
                StdOut.print(v + " ");
        }
        StdOut.println();
    }
}