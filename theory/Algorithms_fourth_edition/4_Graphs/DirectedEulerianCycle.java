/**
 *  The {@code DirectedEulerianCycle} class represents a data type
 *  for finding an Eulerian cycle or path in a digraph.
 *  An Eulerian cycle is a cycle (not necessarily simple) that
 *  uses every edge in the digraph exactly once.
 *  This implementation uses a nonrecursive depth-first search.
 *  The constructor runs in O(E + V) time, and uses O(V) extra space, 
 *  where E is the number of edges and V the number of vertices
 *  All other methods take O(1) time.
 *  To compute Eulerian paths in digraphs, see {@link DirectedEulerianPath}.
 *  To compute Eulerian cycles and paths in undirected graphs, see
 *  {@link EulerianCycle} and {@link EulerianPath}.
 */
import java.util.Iterator;
public class DirectedEulerianCycle {

    private Stack<Integer> cycle = null;    // Eulerian cycle; null if no such cycle.

    // Computes an Eulerian cycle in the specified digraph, if one exists.
    public DirectedEulerianCycle(Digraph G) {

        // must have at least one edge
        if (G.E() == 0)
            return;
        // necessary condition: indegree(v) = outdegree(v) for each vertex v
        // (without this check, DFS might return a path instead of a cycle)
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) != G.indegree(v))
                return;
        }

        // create local view of adjacency lists, to iterate one vertex at a time.
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();
        
        // initialize stack with any non-isolated vertex.
        int s = nonIsolatedVertex(G);
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);

        // greedily add to putative cycle, depth-first search style.
        cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            // add vertex with no more leaving edges to cycle
            cycle.push(v);
        }

        // check if all edges have been used
        // (in case there are two or more vertex-disjoint Eulerian cycles)
        if (cycle.size() != G.E() + 1)
            cycle = null;
        assert certifySolution(G);
    }

    // Returns the sequence of vertices on an Eulerian cycle
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // Returns true if the digraph has an Eulerian cycle
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // returns any non-isolated vertex; -1 if no such vertex.
    private static int nonIsolatedVertex(Digraph G) {
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) > 0)
                return v;
        }
        return -1;
    }

    // The code below is solely for testing correctness of the data type.
    // Determines whether a digraph has an Eulerian cycle using necessary
    // and sufficient conditions (without computing the cycle itself):
    //      - at least one edge
    //      - indegree(v) = outdegree(v) for every vertex.
    //      - the graph is connected, when viewed as an undirected graph
    //      (ignoring isolated vertices)
    private static boolean hasEulerianCycle(Digraph G) {

        // Condition 0 : at least 1 edge
        if (G.E() == 0)
            return false;
        
        // Condition 1 : indegree(v) == outdegree(v) for every vertex
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) != G.indegree(v))
                return false;
        }

        // Condition 2 : graph is connected, ignoring isolated vertices
        Graph H = new Graph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v))
                H.addEdge(v, w);
        }

        // check that all non-isolated vertices are connected
        int s = nonIsolatedVertex(G);
        BreadthFirstPaths bfs = new BreadthFirstPaths(H, s);
        for (int v = 0; v < G.V(); v++) {
            if (H.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;
        }
        return true;
    }

    // check that solution is correct
    private boolean certifySolution(Digraph G) {

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
        return true;
    }

    private static void unitTest(Digraph G, String description) {
        StdOut.println(description);
        StdOut.println("--------------------------");
        StdOut.print(G);

        DirectedEulerianCycle euler = new DirectedEulerianCycle(G);

        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (int v : euler.cycle())
                StdOut.print(v + " ");
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }

    // Unit tests the DirectedEulerianCycle data type.
    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);

        // Eulerian cycle
        Digraph G1 = DigraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");

        // Eulerian path
        Digraph G2 = DigraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");

        // empty digraph
        Digraph G3 = new Digraph(V);
        unitTest(G3, "empty digraph");

        // self loop
        Digraph G4 = new Digraph(V);
        int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");

        // union of two disjoint cycles
        Digraph H1 = DigraphGenerator.eulerianCycle(V/2, E/2);
        Digraph H2 = DigraphGenerator.eulerianCycle(V - V/2, E - E/2);
        int[] perm = new int[V];
        for (int i = 0; i < V; i++)
            perm[i] = i;
        StdRandom.shuffle(perm);
        Digraph G5 = new Digraph(V);
        for (int v = 0; v < H1.V(); v++)
            for (int w : H1.adj(v))
                G5.addEdge(perm[v], perm[w]);
        for (int v = 0; v < H2.V(); v++)
            for (int w : H2.adj(v))
                G5.addEdge(perm[V/2 + v], perm[V/2 + w]);
        unitTest(G5, "Union of two disjoint cycles");

        // random digraph
        Digraph G6 = DigraphGenerator.simple(V, E);
        unitTest(G6, "simple digraph");

        // 4-vertex digraph
        Digraph G7 = new Digraph(new In("eulerianD.txt"));
        unitTest(G7, "4-vertex Eulerian digraph");
    }
}