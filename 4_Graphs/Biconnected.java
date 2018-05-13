// Biconnectivity: An articulation vertex (or cut vertex) is a vertex 
// whose removal increases the number of connected components. A graph 
// is biconnected if it has no articulation vertices. Biconnected.java 
// uses depth-first search to find the bridges and articulation vertices. 
// It takes time proportional to V + E in the worst case.
// Identify articulation points and print them out.
// This can be used to decompose a graph into biconnected components.
// Runs in O(E + V) time.
public class Biconnected {

    private int[] low;
    private int[] pre;
    private int cnt;
    private boolean[] articulation;
    private boolean[] componentMarked;
    private int countComponent;

    public Biconnected(Graph G) {
        low = new int[G.V()];
        pre = new int[G.V()];
        articulation = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            low[v] = -1;
        for (int v = 0; v < G.V(); v++)
            pre[v] = -1;

        for (int v = 0; v < G.V(); v++) {
            if (pre[v] == -1)
                dfs(G, v, v);
        }
        componentPrintEdge(G);
    }

    private void dfs(Graph G, int u, int v) {
        int children = 0;
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : G.adj(v)) {
            if (pre[w] == -1) {
                System.out.println("** " + v + " " + w + " " + children);
                children++;
                System.out.println("** " + v + " " + w + " " + children);
                dfs(G, v, w);

                // update low number 
                low[v] = Math.min(low[v], low[w]);

                // non-root of DFS is an articulation point if low[w] >= pre[v]
                if (low[w] >= pre[v] && u != v)
                    articulation[v] = true;
            }
            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
        }
        // root of DFS is an articulation point if it has more than 1 child
        System.out.println("## " + u + " " + v + " " + children);
        if (u == v && children > 1)
            articulation[v] = true;
    }

    // is vertex v an articulation point ?
    public boolean isArticulation(int v) {
        return articulation[v];
    }

    // Biconnected components. Modify Biconnected to print out the edges that constitute 
    // each biconnected component. Hint: each bridge is its own biconnected component; 
    // to compute the other biconnected components, mark each articulation point as visited, 
    // and then run DFS, keeping track of the edges discovered from each DFS start point.
    public void componentPrintEdge(Graph G) {
        componentMarked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            componentMarked[i] = articulation[i];
        }
        for (int v = 0; v < G.V(); v++) {
            if (!componentMarked[v]) {
                dfsPrintEdge(G, v, v);
                countComponent++;
            }
        }
    }

    private void dfsPrintEdge(Graph G, int u, int v) {
        componentMarked[v] = true;
        for (int w : G.adj(v)) {
            if (!componentMarked[w]) {
                System.out.println("Edge " + countComponent + " : " + v + " " + w);
                dfsPrintEdge(G, v, w);
            }
            else if (w != u && articulation[w]) {
                System.out.println("Edge " + countComponent + " : " + v + " " + w);
            }
        }
    }

    // test client
    public static void main(String[] args) {
        // int V = Integer.parseInt(args[0]);
        // int E = Integer.parseInt(args[1]);
        // Graph G = GraphGenerator.simple(V, E);
        Graph G = new Graph(4);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 2);
        G.addEdge(0, 3);
        StdOut.println(G);

        Biconnected bic = new Biconnected(G);

        // print out articulation points
        StdOut.println();
        StdOut.println("Articulation points");
        StdOut.println("-------------------");
        for (int v = 0; v < G.V(); v++) {
            if (bic.isArticulation(v))
                StdOut.println("Hello" + v);
        }
    }
}
