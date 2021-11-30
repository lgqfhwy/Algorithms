/**
 *  The {@code Topological} class represents a data type for 
 *  determining a topological order of a directed acyclic graph (DAG).
 *  Recall, a digraph has a topological order if and only if it is a DAG.
 *  The hasOrder operation determines whether the digraph has
 *  a topological order, and if so, the order operation returns one.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the hasOrder and rank operations takes constant time;
 *  the order operation takes time proportional to V.
 *  See {@link DirectedCycle}, {@link DirectedCycleX}, and
 *  {@link EdgeWeightedDirectedCycle} to compute a
 *  directed cycle if the digraph is not a DAG.
 *  See {@link TopologicalX} for a nonrecursive queue-based algorithm
 *  to compute a topological order of a DAG.
 */
public class Topological {

    private Iterable<Integer> order;    // topological order.
    private int[] rank;     // rank[v] = position of vertex v in topological order.

    // Determines whether the digraph G has a topological order and, if so,
    // finds such a topological order.
    public Topological(Digraph G) {
        DirectedCycle finder = new DirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v : order)
                rank[v] = i++;
        }
    }

    // Determines whether the edge-weighted digraph G has a topological order
    // and, if so, finds such an order.
    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    // Returns a topological order if the digraph has a topological order.
    public Iterable<Integer> order() {
        return order;
    }

    // Does the digraph have a topological order ?
    public boolean hasOrder() {
        return order != null;
    }

    // Does the digraph have a topological order ?
    @Deprecated
    public boolean isDAG() {
        return hasOrder();
    }

    // The rank of vertex v in the topological order;
    // -1 if the digraph is not a DAG.
    public int rank(int v) {
        validateVertex(v);
        if (hasOrder())
            return rank[v];
        else
            return -1;
    }

    // Write a method that checks whether or not a given permutation of a DAG’s vertices is 
    // a topological order of that DAG.
    public static boolean checkTopologicalOrder(Digraph G, Iterable<Integer> order) {
        Stack<Integer> check = new Stack<Integer>();
        for (int w : order)
            check.push(w);
        boolean isOrder = true;
        boolean[] marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            marked[v] = false;
        while (!check.isEmpty()) {
            int v = check.pop();
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w])
                    isOrder = false;
            }
            if (!isOrder)
                break;
        }
        return isOrder;
    } 

    // throw new IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the Topological data type.
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Topological topological = new Topological(sg.digraph());
        for (int v : topological.order())
            StdOut.println(sg.nameOf(v));
    }
}