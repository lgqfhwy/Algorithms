// Bitonic shortest path. Given a digraph, find a bitonic shortest path from s to every 
// other vertex (if one exists). A path is bitonic if there is an intermediate vertex v 
// such that the edges on the path from s to v are strictly increasing and the edges on 
// the path from v to t are strictly decreasing. The path should be simple (no repeated vertices).

// relax the edges once in increasing order and once in decreasing order.

public class BitonicShortestPath {

    private double[] distTo;    // distTo[v] = distance of shortest s->v path.
    private DirectedEdge[] edgeTo;  // edgeTo[v] = last edge on shortest s->v path.
    private double[] previousWeight;    // previousWeight[v] = the weight of edge to v.
    private boolean[] onQueue;  // onQueue[v] = is v currently on the queue.
    private Queue<Integer> queue;   // queue of vertices to relax
    private boolean[] hasBitonic;   // is s->v bitonic shortest path exist ?
    private boolean[] marked;   // marked increase.

    public BitonicShortestPath(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        previousWeight = new double[G.V()];
        onQueue = new boolean[G.V()];
        queue = new Queue<Integer>();
        hasBitonic = new boolean[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            previousWeight[v] = 0.0;
        }
        distTo[s] = 0.0;

        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            increaseRelax(G, v);
        }

        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            decreaseRelax(G, v);
        }
    }

    // increase relax v and put other endpoints on queue if changed.
    private void increaseRelax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if ((distTo[w] > distTo[v] + e.weight()) && (e.weight() > previousWeight[v])) {
                //StdOut.println("w = " + w);
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                previousWeight[w] = e.weight();
                marked[w] = true;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
        }
    }

    // decrease relax v and put other endpoints on queue if changed.
    private void decreaseRelax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if ((distTo[w] > distTo[v] + e.weight()) && (e.weight() < previousWeight[v])) {
                //StdOut.println("w = " + w);
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                previousWeight[w] = e.weight();
                hasBitonic[w] = true;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            else if (marked[w]) {
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
        }
    }

    // Is there a bitonic path from the source s to vertex v ?
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return hasBitonic[v];
    }

    // Returns the length of a shortest path from the source s to vertex v.
    public double distTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            throw new UnsupportedOperationException("Don't have bitonic shortest path.");
        }
        return distTo[v];
    }

    // Returns a shortest path from the source s to vertex v.
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            throw new UnsupportedOperationException("Don't have bitonic shortest path.");
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(10);
        G.addEdge(new DirectedEdge(0, 1, 2));
        G.addEdge(new DirectedEdge(1, 2, 4));
        G.addEdge(new DirectedEdge(2, 3, 6));
        G.addEdge(new DirectedEdge(3, 4, 8));
        G.addEdge(new DirectedEdge(4, 5, 7));
        G.addEdge(new DirectedEdge(5, 6, 5));

        G.addEdge(new DirectedEdge(0, 7, 1));
        G.addEdge(new DirectedEdge(7, 8, 3));
        G.addEdge(new DirectedEdge(8, 9, 5));

        int s = 0;
        BitonicShortestPath bs = new BitonicShortestPath(G, s);
        if (bs.hasPathTo(6)) {
            StdOut.println("Bitonic shortest path to 6:");
            for (DirectedEdge e : bs.pathTo(6)) {
                StdOut.print(e + "  ");
            }
        }
        StdOut.println();
        if (bs.hasPathTo(9)) {
            StdOut.println("Bitonic shortest path: to 9");
            for (DirectedEdge e : bs.pathTo(9)) {
                StdOut.print(e + "  ");
            }
        }
    }

}
