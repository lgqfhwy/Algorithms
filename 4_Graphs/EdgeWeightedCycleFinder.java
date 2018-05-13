// Adapt the DirectedCycle to use the EdgeweightedDigraph APIs
// of this section, thus implementing EdgeWeightedCycleFinder classes.
public class EdgeWeightedCycleFinder {
    private boolean[] marked;   // marked[v] has vertex v been marked ?
    private DirectedEdge[] edgeTo;  // edgeTo[v] = e previous edge on path to v
    private boolean[] onStack;  // onStack[v] = is vertex on the stack?
    private Stack<DirectedEdge> cycle;   // directed cycle (or null is no such cycle)

    // Determines whether the EdgeWeightedDigraph G has a directed cycle and, 
    // if so, finds such a cycle.
    public EdgeWeightedCycleFinder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && cycle == null) {
                dfs(G, v);
            }
        }
    }

    // check that algorithm computes either the topological order or
    // finds a directed cycle.
    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            // short circuit directed cycle find
            if (cycle != null) {
                return;
            }
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<DirectedEdge>();
                DirectedEdge x = e;
                for (x = e; x.from() != w; x = edgeTo[x.from()]) {
                    cycle.push(x);
                }
                cycle.push(x);
                return;
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        
    }



}