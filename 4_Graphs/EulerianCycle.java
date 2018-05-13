/**
 *  The {@code EulerianCycle} class represents a data type
 *  for finding an Eulerian cycle or path in a graph.
 *  An Eulerian cycle is a cycle (not necessarily simple) that
 *  uses every edge in the graph exactly once.
 *  This implementation uses a nonrecursive depth-first search.
 *  The constructor runs in O(E + V) time, and uses O(E + V) extra space, 
 *  where E is the number of edges and V the number of vertices
 *  All other methods take O(1) time.
 *  To compute Eulerian paths in graphs, see {@link EulerianPath}.
 *  To compute Eulerian cycles and paths in digraphs, see
 *  {@link DirectedEulerianCycle} and {@link DirectedEulerianPath}.
 */
public class EulerianCycle {

    private Stack<Integer> cycle = new Stack<Integer>();    // Eulerian cycle; null if no such cycle.

    // an undirected edge, with a field to indicate whether the edge has already been used
    private static class Edge {
        private final int v;
        private final int w;
        private boolean isUsed;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
            isUsed = false;
        }

        // returns the other vertex of the edge
        public int other(int vertex) {
            if (vertex == v)
                return w;
            else if (vertex == w)
                return v;
            else
                throw new IllegalArgumentException("Illegal endpoint");
        }
    }

    // Computes an Eulerian cycle in the specified graph, if one exists.
    public EulerianCycle(Graph G) {

        // must have at least one edge.
        if (G.E() == 0)
            return;
        
        // necessary condition: all vertices have even degree.
        // (this test is needed or it might find an Eulerian path instead of cycle)
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0)
                return;
        }

        // create local view of adjacency lists, to iterate one vertex at a time
        // the helper Edge data type is used to avoid exploring both copies of an edge v-w.
        Queue<Edge>[] adj = (Queue<Edge>[])new Queue[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = new Queue<Edge>();
        
        for (int v = 0; v < G.V(); v++) {
            int selfLoops = 0;
            for (int w : G.adj(v)) {
                // careful with self loops
                if (v == w) {
                    if (selfLoops % 2 == 0) {
                        Edge e = new Edge(v, w);
                        adj[v].enqueue(e);
                        adj[w].enqueue(e);
                    }
                    selfLoops++;
                }
                else if (v < w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }

        // initialize stack with any non-isolated vertex.
        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);

        // greedily search through edges in iterative DFS style.
        cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (!adj[v].isEmpty()) {
                Edge edge = adj[v].dequeue();
                if (edge.isUsed)
                    continue;
                edge.isUsed = true;
                stack.push(v);
                v = edge.other(v);
            }
            // push vertex with no more leaving edges to cycle
            cycle.push(v);
        }

        // check if all edges are used.
        if (cycle.size() != G.E() + 1)
            cycle = null;
        
        assert certifySolution(G);
    }

    // Returns the sequence of vertices on an Eulerian cycle.
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // Returns true if the graph has an Eulerian cycle.
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // Returns any non-isolated vertex; -1 if no such vertex.
    private static int nonIsolatedVertex(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0)
                return v;
        }
        return -1;
    }

    // The code below is solely for testing correctness of the data type.
    // Determines whether a graph has an Eulerian cycle using necessary
    // and sufficient conditions (without computing the cycle itself):
    //  - at least one edge
    //  - degree(v) is even for every vertex v.
    //  - the graph is connected (ignoring isolated vertices)
    private static boolean hasEulerianCycle(Graph G) {

        // Condition 0: at least 1 edge.
        if (G.E() == 0)
            return false;
        
        // Condition 1: degree(v) is even for every vertex.
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0)
                return false;
        }

        // Condition 2: graph is connected, ignoring isolated vertices.
        int s = nonIsolatedVertex(G);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;
        }

        return true;
    }

    // check that solution is correct
    private boolean certifySolution(Graph G) {

        // internal consistency check
        if (hasEulerianCycle() == (cycle() == null))
            return false;
        
        // hasEulerianCycle() returns correct value
        if (hasEulerianCycle() != hasEulerianCycle(G))
            return false;

        // nothing else to check if no Eulerian cycle
        if (cycle == null)
            return true;
        
        // check that cycle() uses correct number of edges
        if (cycle.size() != G.E() + 1)
            return false;

        // check that first and last vertices in cycle() are the same
        int first = -1, last = -1;
        for (int v : cycle()) {
            if (first == -1)
                first = v;
            last = v;
        }
        if (first != last)
            return false;
        return true;
    }

    private static void unitTest(Graph G, String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);

        EulerianCycle euler = new EulerianCycle(G);

        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }

    // Unit tests the EulerianCycle data type.
    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);

        // Eulerian cycle
        Graph G1 = GraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        Graph G2 = GraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // empty graph
        Graph G3 = new Graph(V);
        unitTest(G3, "empty graph");

        // self loop
        Graph G4 = new Graph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // union of two disjoint cycles
        Graph H1 = GraphGenerator.eulerianCycle(V/2, E/2);
        Graph H2 = GraphGenerator.eulerianCycle(V - V/2, E - E/2);
        int[] perm = new int[V];
        for (int i = 0; i < V; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        Graph G5 = new Graph(V);
        for (int v = 0; v < H1.V(); v++)
            for (int w : H1.adj(v))
                G5.addEdge(perm[v], perm[w]);
        for (int v = 0; v < H2.V(); v++)
            for (int w : H2.adj(v))
                G5.addEdge(perm[V/2 + v], perm[V/2 + w]);
        unitTest(G5, "Union of two disjoint cycles");

        // random digraph
        Graph G6 = GraphGenerator.simple(V, E);
        unitTest(G6, "simple graph");
    }
}