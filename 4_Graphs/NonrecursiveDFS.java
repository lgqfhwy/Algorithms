// Nonrecursive depth-first search. Write a program NonrecursiveDFS.java that implements 
// depth-first search with an explicit stack instead of recursion.
/**
 *  The {@code NonrecursiveDFS} class represents a data type for finding
 *  the vertices connected to a source vertex s in the undirected
 *  graph.
 *  This implementation uses a nonrecursive version of depth-first search
 *  with an explicit stack.
 *  The constructor takes time proportional to V + E,
 *  where V is the number of vertices and E is the number of edges.
 *  It uses extra space (not including the graph) proportional to V.
 */
import java.util.Iterator;
public class NonrecursiveDFS {

    private boolean[] marked;   // marked[v] = is there an s-v path ?

    // computes the vertices connected to the source vertex s in the graph G.
    public NonrecursiveDFS(Graph G, int s) {
        marked = new boolean[G.V()];

        validateVertex(s);

        // to be able to iterate over each adjacency list, keeping track of which 
        // vertex in each adjacency list needs to be explored next.
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        // depth-first search using an explicit stack.
        Stack<Integer> stack = new Stack<Integer>();
        marked[s] = true;
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked[w]) {
                    // discovered vertex w for the first time.
                    marked[w] = true;
                    // edgeTo[w] = v;
                    stack.push(w);
                }
            }
            else {
                stack.pop();
            }
        }
    }

    // Here is an alternate implementation suggested by Bin Jiang in the early 1990s. 
    // The only extra memory is for a stack of vertices but that stack must support 
    // arbitrary deletion (or at least the movement of an arbitrary item to the top of the stack).
    private void dfs(Graph G, int s) {
        SuperStack<Integer> stack = new SuperStack<Integer>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (!marked[v]) {
                marked[v] = true;
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        if (!marked[w]) {
                            if (stack.contains(w))
                                stack.delete(w);
                            stack.push(w);
                        }
                    }
                }
            }
            else {
                // v's adjacency list is exhausted
                stack.pop();
            }
        }
    }

    // Here is yet another implementation. It is, perhaps, the simplest nonrecursive implementation, 
    // but it uses space proportional to E + V in the worst case (because more than one copy of a 
    // vertex can be on the stack) and it explores the vertices adjacent to v in the reverse order 
    // of the standard recursive DFS. Also, an edgeTo[v] entry may be updated more than once, so it 
    // may not be suitable for backtracking applications.
    private void dfs(Graph G, int s) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!marked[v]) {
                marked[v] = true;
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        stack.push(w);
                    }
                }
            }
        }
    }


    // Is vertex v connected to the source vertex s ?
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    // throw an IllegalArgumentException unless code 0 <= v < V.
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the NonrecursiveDFS data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        NonrecursiveDFS dfs = new NonrecursiveDFS(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.marked(v))
                StdOut.print(v + " ");
        }
        StdOut.println();
    }
}