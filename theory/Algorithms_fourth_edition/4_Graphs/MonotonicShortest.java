// Monotonic shortest path. Given a weighted digraph, find a monotonic shortest path 
// from s to every other vertex. A path is monotonic if the weight of every edge on 
// the path is either strictly increasing or strictly decreasing. The path should 
// be simple (no repeated vertices). Hint : Relax edges in ascending order and find 
// a best path; then relax edges in descending order and find a best path.
public class MonotonicShortest {

    private double[] previousIncreaseDis;   // previousIncreaseDis[v] = the distance of edge to v in increase path.
    private double[] increaseDisTo;     // increaseDisTo[v] = distance of shortest s->v increase path 
    private double[] decreaseDisTo;     // decreaseDisTo[v] = distance of shortest s->v decrease path
    private double[] previousDecreaseDis;   // previousDecreaseDis[v] = the distance of edge to v in decrease path.
    private DirectedEdge[] increaseEdgeTo;  // increaseEdgeTo[v] = last edge on increase s->v path
    private DirectedEdge[] decreaseEdgeTo;  // decreaseEdgeTo[v] = last edge on decrease s->v path
    private boolean[] onIncreaseQueue;  // onIncreaseQueue[v] = is v currently on the increase queue.
    private boolean[] onDecreaseQueue;  // onDecreaseQueue[v] = is v currently on the decrease queue.
    private Queue<Integer> increaseQueue;   // queue if vertex to relax in increase path.
    private Queue<Integer> decreaseQueue;   // queue if vertex to relax in decrease path.

    public MonotonicShortest(EdgeWeightedDigraph G, int s) {
        previousDecreaseDis = new double[G.V()];
        increaseDisTo = new double[G.V()];
        previousIncreaseDis = new double[G.V()];
        decreaseDisTo = new double[G.V()];
        increaseEdgeTo = new DirectedEdge[G.V()];
        decreaseEdgeTo = new DirectedEdge[G.V()];
        onIncreaseQueue = new boolean[G.V()];
        onDecreaseQueue = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            increaseDisTo[v] = Double.POSITIVE_INFINITY;
            decreaseDisTo[v] = Double.POSITIVE_INFINITY;
        }
        increaseDisTo[s] = 0.0;
        decreaseDisTo[s] = 0.0;
        previousDecreaseDis[s] = Double.POSITIVE_INFINITY;
        previousIncreaseDis[s] = 0.0;

        increaseQueue = new Queue<Integer>();
        decreaseQueue = new Queue<Integer>();
        increaseQueue.enqueue(s);
        decreaseQueue.enqueue(s);
        onIncreaseQueue[s] = true;
        onDecreaseQueue[s] = true;

        while (!increaseQueue.isEmpty()) {
            int v = increaseQueue.dequeue();
            onIncreaseQueue[v] = false;
            increaseRelax(G, v);
        }

        while (!decreaseQueue.isEmpty()) {
            int v = decreaseQueue.dequeue();
            onDecreaseQueue[v] = false;
            decreaseRelax(G, v);
        }
    }

    private void increaseRelax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if ((increaseDisTo[w] > increaseDisTo[v] + e.weight()) && e.weight() > previousIncreaseDis[v]) {
                increaseDisTo[w] = increaseDisTo[v] + e.weight();
                increaseEdgeTo[w] = e;
                previousIncreaseDis[w] = e.weight();
                if (!onIncreaseQueue[w]) {
                    increaseQueue.enqueue(w);
                    onIncreaseQueue[w] = true;
                }
            }
        }
    }

    private void decreaseRelax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if ((decreaseDisTo[w] > decreaseDisTo[v] + e.weight()) && e.weight() < previousDecreaseDis[v]) {
                decreaseDisTo[w] = decreaseDisTo[v] + e.weight();
                previousDecreaseDis[w] = e.weight();
                decreaseEdgeTo[w] = e;
                if (!onDecreaseQueue[w]) {
                    decreaseQueue.enqueue(w);
                    onDecreaseQueue[w] = true;
                }
            }
        }
    }

    // Is there a strictly increase path from the source s to vertex v ?
    public boolean hasIncreasePathTo(int v) {
        validateVertex(v);
        return increaseDisTo[v] < Double.POSITIVE_INFINITY;
    }

    // Is there a strictly decrease path from the source s to vertex v ?
    public boolean hasDecreasePathTo(int v) {
        validateVertex(v);
        return decreaseDisTo[v] < Double.POSITIVE_INFINITY;
    }

    // Returns a strictly increase shortest path from the source s to vertex v ?
    public Iterable<DirectedEdge> increasePathTo(int v) {
        validateVertex(v);
        if (!hasIncreasePathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> increasePath = new Stack<DirectedEdge>();
        for (DirectedEdge e = increaseEdgeTo[v]; e != null; e = increaseEdgeTo[e.from()]) {
            increasePath.push(e);
        }
        return increasePath;
    }

    // Returns a strictly decrease shorest path from the source s to vertex v ?
    public Iterable<DirectedEdge> decreasePathTo(int v) {
        validateVertex(v);
        if (!hasDecreasePathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> decreasePath = new Stack<DirectedEdge>();
        for (DirectedEdge e = decreaseEdgeTo[v]; e != null; e = decreaseEdgeTo[e.from()]) {
            decreasePath.push(e);
        }
        return decreasePath;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = increaseDisTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
        G.addEdge(new DirectedEdge(0, 1, 1));
        G.addEdge(new DirectedEdge(1, 2, 2));
        G.addEdge(new DirectedEdge(2, 3, 3));
        G.addEdge(new DirectedEdge(3, 4, 4));
        G.addEdge(new DirectedEdge(0, 5, 4));
        G.addEdge(new DirectedEdge(5, 6, 3));
        G.addEdge(new DirectedEdge(6, 7, 2));
        G.addEdge(new DirectedEdge(7, 4, 1));

        int s = 0;
        MonotonicShortest ms = new MonotonicShortest(G, s);
        if (ms.hasIncreasePathTo(4)) {
            StdOut.println("Increase Path:");
            for (DirectedEdge e : ms.increasePathTo(4)) {
                StdOut.print(e + " ");
            }
        }
        StdOut.println();
        if (ms.hasDecreasePathTo(4)) {
            StdOut.println("Decrease Path:");
            for (DirectedEdge e : ms.decreasePathTo(4)) {
                StdOut.print(e + " ");
            }
        }
    }


}