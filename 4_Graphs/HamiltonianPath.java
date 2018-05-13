// Hamiltonian path in DAGs. Given a DAG, design a linear-time algorithm to determine 
// whether there is a directed path that visits each vertex exactly once.
// Answer: Compute a topological sort and check if there is an edge between each consecutive 
// pair of vertices in the topological order.
public class HamiltonianPath {
    Topological topological;
    Queue<Integer> directedPath;
    boolean isPath;

    public HamiltonianPath(Digraph G) {
        topological = new Topological(G);
        isPath = topological.hasOrder();
        directedPath = new Queue<Integer>();
        if (isPath) {
            for (int v : topological.order()) {
                directedPath.enqueue(v);
            }
        }
    }

    public boolean hasExist() {
        return isPath;
    }

    public Iterable<Integer> orderPath() {
        return directedPath;
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        HamiltonianPath h = new HamiltonianPath(G);
        if (h.hasExist()) {
            for (int v : h.orderPath()) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }

}