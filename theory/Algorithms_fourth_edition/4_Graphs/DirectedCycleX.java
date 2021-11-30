// Find a directed cycle in a digraph, using a nonrecursive, queue-based
// algorithm. Runs in O(E + V) time.

/**
 *  The {@code DirectedCycleX} class represents a data type for 
 *  determining whether a digraph has a directed cycle.
 *  The hasCycle operation determines whether the digraph has
 *  a directed cycle and, and of so, the cycle operation returns one.
 *  This implementation uses a nonrecursive, queue-based algorithm.
 *  The constructor takes time proportional to V + E (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the hasCycle operation takes constant time;
 *  the cycle operation takes time proportional to the length of the cycle.
 *  See {@link DirectedCycle} for a recursive version that uses depth-first search.
 *  See {@link Topological} or {@link TopologicalX} to compute a topological order
 *  when the digraph is acyclic.
 */
public class DirectedCycleX {

    private Stack<Integer> cycle;   // the directed cycle; null if digraph is acyclic.

    public DirectedCycleX(Digraph G) {
        // indegree of remaining vertices
        int[] indegree = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            indegree[v] = G.indegree(v);
        }

        // initialize queue to contain all vertices with indegree = 0.
        Queue<Integer> queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) {
                queue.enqueue(v);
            }
        }

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                indegree[w]--;
                if (indegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }

        // there is a directed cycle in subgraph of vertices with indegree >= 1.
        int[] edgeTo = new int[G.V()];
        int root = -1;  // any vertex with indegree >= -1.
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) {
                continue;
            }
            else {
                root = v;
            }
            for (int w : G.adj(v)) {
                if (indegree[w] > 0) {
                    edgeTo[w] = v;
                }
            }
        }

        if (root != -1) {
            // find any vertex on cycle
            boolean[] visited = new boolean[G.V()];
            while (!visited[root]) {
                visited[root] = true;
                root = edgeTo[root];
            }
            // extract cycle
            cycle = new Stack<Integer>();
            int v = root;
            do {
                cycle.push(v);
                v = edgeTo[v];
            } while (v != root);
            assert check();
        }
    }

    // Returns a directed cycle if the digraph has a directed cycle, and null otherwise.
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // Does the digraph have a directed cycle ?
    public boolean hasCycle() {
        return cycle != null;
    }

    // certify that digraph has a directed cycle if it reports one.
    private boolean check() {
        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) {
                    first = v;
                }
                last = v;
            }
            if (first != last) {
                System.out.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // create random DAG with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);
        Digraph G = DigraphGenerator.dag(V, E);

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            G.addEdge(v, w);
        }

        StdOut.println(G);


        DirectedCycleX finder = new DirectedCycleX(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }

        else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }
}