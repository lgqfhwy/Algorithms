/**
 *  The {@code DepthFirstOrder} class represents a data type for 
 *  determining depth-first search ordering of the vertices in a digraph
 *  or edge-weighted digraph, including preorder, postorder, and reverse postorder.
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to V + E
 *  (in the worst case),
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the preorder, postorder, and reverse postorder
 *  operation takes take time proportional to V.
 */
public class DepthFirstOrder {
    private boolean[] marked;   // marked[v] = hs v been marked in dfs ?
    private int[] pre;  // pre[v] = preorder umber of v
    private int[] post; // post[v] = postorder number of v
    private Queue<Integer> preorder;    // vertices in preorder
    private Queue<Integer> postorder;   // vertices in postorder
    private int preCounter;    // counter or preorder numbering
    private int postCounter;   // counter for postorder numbering
    
    // Determines a depth-first order for the digraph G.
    public DepthFirstOrder(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
        assert check();
    }

    // Determines a depth-first order for the edge-weighted digraph G.
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v);
        }
    }

    // run DFS in digraph G from vertex v and compute preorer / postorder
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w);
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }

    // run DFS in edge-weighed digraph G from vertex v and compute preorder / postorder.
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue(v);
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w])
                dfs(G, w);
        }
        postorder.enqueue(v);
        post[v] = postCounter++;
    }

    // Returns the preorder number of vertex v.
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    // Return the postorder number of vertex v.
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    // Returns the vertices in postorder.
    public Iterable<Integer> post() {
        return postorder;
    }

    // Returns the vertices in preorder.
    public Iterable<Integer> pre() {
        return preorder;
    }

    // Retruns the vertices in reverse postorder.
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }

    // check that pre() and post() are consistent with pre(v) and post(v)
    private boolean check() {
        // check that post(v) is consistent with post()
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                StdOut.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }
        return true;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the DepthFirstOrder data type.
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   v  pre post");
        StdOut.println("--------------");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }

        StdOut.print("Preorder:  ");
        for (int v : dfs.pre()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Postorder: ");
        for (int v : dfs.post()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Reverse postorder: ");
        for (int v : dfs.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}