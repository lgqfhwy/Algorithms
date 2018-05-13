// Queue-based topological sort. Develop a topological sort implementation that 
// maintains a vertex-indexed array that keeps track of the indegree of each vertex. 
// Initialize the array and a queue of sources in a single pass through all the edges, 
// as in Exercise 4.2.7. Then, perform the following operations until the source queue is empty:
// ■ Remove a source from the queue and label it.
// ■ Decrement the entries in the indegree array corresponding to the destination
//   vertex of each of the removed vertex’s edges.
// ■ If decrementing any entry causes it to become 0, insert the corresponding vertex 
//   onto the source queue.
public class TopologicalBasedQueue {
    Queue<Integer> sourceQueue;
    int[] indegree;
    Queue<Integer> order;

    public TopologicalBasedQueue(Digraph G) {
        indegree = new int[G.V()];
        sourceQueue = new Queue<Integer>();
        order = new Queue<Integer>();
        for (int i = 0; i < G.V(); i++) {
            indegree[i] = 0;
        }
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                indegree[w]++;
            }
        }
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0) {
                sourceQueue.enqueue(v);
                indegree[v] = -1;
                buildOrder(G);
            }
        }
    }

    private void buildOrder(Digraph G) {
        while (!sourceQueue.isEmpty()) {
            int v = sourceQueue.dequeue();
            order.enqueue(v);
            for (int w : G.adj(v)) {
                if (indegree[w] != -1) {
                    indegree[w]--;
                    if (indegree[w] == 0) {
                        sourceQueue.enqueue(w);
                        indegree[w] = -1;
                    }
                }
            }
        }
    }

    // Returns a topological order if the digraph has a topological order.
    public Iterable<Integer> order() {
        return order;
    }

    // Does the digraph have a topological order ?
    public boolean hasOrder() {
        if (order == null || order.size() != indegree.length) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(6);
        G.addEdge(0, 1);
        G.addEdge(0, 5);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        TopologicalBasedQueue topological = new TopologicalBasedQueue(G);
        if (topological.hasOrder()) {
            for (int w : topological.order()) {
                System.out.print(w + " ");
            }
            System.out.println();
        }
    }
}