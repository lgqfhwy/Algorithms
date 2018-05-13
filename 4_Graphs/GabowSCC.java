// Compute the strongly-connected components of a digraph using 
// Gabow's algorithm (aka Cheriyan-Mehlhorn algorithm).
// Runs in O(E + V) time.
/**
 *  The {@code GabowSCC} class represents a data type for 
 *  determining the strong components in a digraph.
 *  The id operation determines in which strong component
 *  a given vertex lies; the areStronglyConnected operation
 *  determines whether two vertices are in the same strong component;
 *  and the count operation determines the number of strong components.
 *  The component identifier of a component is one of the
 *  vertices in the strong component: two vertices have the same component
 *  identifier if and only if they are in the same strong component.
 *  This implementation uses the Gabow's algorithm.
 *  The constructor takes time proportional to V + E (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the id, count, and areStronglyConnected
 *  operations take constant time.
 *  For alternate implementations of the same API, see
 *  {@link KosarajuSharirSCC} and {@link TarjanSCC}.
 */
public class GabowSCC {

    private boolean[] marked;   // marked[v] = has v been visited?
    private int[] id;   // id[v] = id of strong component containing v.
    private int[] preorder;    // preorder[v] = preorder of v.
    private int pre;    // preorder number counter
    private int count;  // number of strongly-connected components
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    // Computes the strong components of the digraph G.
    public GabowSCC(Digraph G) {
        marked = new boolean[G.V()];
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();
        id = new int[G.V()];
        preorder = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            id[v] = -1;
        }
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        preorder[v] = pre++;
        stack1.push(v);
        stack2.push(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            } else if (id[w] == -1) {
                while (preorder[stack2.peek()] > preorder[w]) {
                    stack2.pop();
                }
            }
        }

        // found strong component containing v
        if (stack2.peek() == v) {
            stack2.pop();
            int w;
            do {
                w = stack1.pop();
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    // Returns the numebr of strong components.
    public int count() {
        return count;
    }

     //Are vertices v and w in the same strong component ?
    public boolean stronglyConnected(int v, int w) {
        validateVerex(v);
        validateVerex(w);
        return id[v] == id[w];
    }

    // Returns the component id of the strong component containing vertex v.
    public int id(int v) {
        validateVerex(v);
        return id[v];
    }

    // does the id[] array contain the strongly connected components ?
    private boolean check(Digraph G) {
        TransitiveClosure tc = new TransitiveClosure(G);
        for (int v = 0; v < G.V(); v++) {
            for (int w = 0; w < G.V(); w++) {
                if (stronglyConnected(v, w) != (tc.reachable(v, w) && tc.reachable(w, v))) {
                    return false;
                }
            }
        }
        return true;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVerex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    public static void main(String[] args) {
        
    }
}
