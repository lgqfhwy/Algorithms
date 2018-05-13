/**
 *  The {@code Cycle} class represents a data type for 
 *  determining whether an undirected graph has a cycle.
 *  The has Cycle operation determines whether the graph has
 *  a cycle and, if so, the cycle operation returns one.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E
 *  (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the hasCycle operation takes constant time;
 *  the cycle operation takes time proportional
 *  to the length of the cycle.
 */
public class Cycle {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    // Determines whether the undirected graph has a cycle and,
    // if so, finds such a cycle.
    public Cycle(Graph G) {
        if (hasSelfLoop(G))
            return;
        if (hasParallelEdges(G))
            return;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, -1, v);
        }
    }

    // does this graph have a self loop ?
    // side effect: initialize cycle to be self loop
    private boolean hasSelfLoop(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    // does this graph have two parallel edges ?
    // side effect: initialize cycle to be two parallel edges
    private boolean hasParallelEdges(Graph G) {
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            // check for parallel edges incident to v
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }
            // reset so marked[v] = false for all v
            for (int w : G.adj(v)) {
                marked[w] = false;
            }
        }
        return false;
    }

    // Returns true if the graph G has a cycle.
    public boolean hasCycle() {
        return cycle != null;
    }

    // Returns a cycle in the graph G.
    public Iterable<Integer> cycle() {
        return cycle;
    }

    private void dfs(Graph G, int u, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            // short circuit if cycle already found
            if (cycle != null)
                return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, v, w);
            }
            // check for cycle (but disregard reverse of edge leading to v)
            else if (w != u) {
                Stack<Integer> cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) 
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
    }

    // Unit tests the Cycle data type.
    public static void main(String[] args) {
        // In in = new In(args[0]);
        // Graph G = new Graph(in);
        // Cycle finder = new Cycle(G);
        // if (finder.hasCycle()) {
        //     for (int v : finder.cycle())
        //         StdOut.print(v + " ");
        //     StdOut.println();
        // }
        // else 
        //     StdOut.println("Graph is acyclic");
        String vertex = args[0];
        int v = Integer.parseInt(vertex);
        String edge = args[1];
        int e = Integer.parseInt(edge);
        Graph graph = new Graph(v);
        Graph.readGraph(graph, v, e);
        StdOut.println(graph);

        Cycle finder = new Cycle(graph);
        if (finder.hasCycle()) {
            for (int k : finder.cycle())
                StdOut.print(k + " ");
            StdOut.println();
        }
        else 
            StdOut.println("Graph is acyclic");
    }
}