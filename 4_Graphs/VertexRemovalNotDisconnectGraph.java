// Prove that every connected graph has a vertex whose removal 
// (including all adjacent edges) will not disconnect the graph, 
// and write a DFS method that finds such a vertex. 
// Hint : Consider a vertex whose adjacent vertices are all marked.
import java.util.ArrayList;
public class VertexRemovalNotDisconnectGraph {

    public static void main(String[] args) {
        String vertex = args[0];
        int v = Integer.parseInt(vertex);
        String edge = args[1];
        int e = Integer.parseInt(edge);
        Graph graph = new Graph(v);
        Graph.readGraph(graph, v, e);
        StdOut.println(graph);
        ArrayList<Integer> list = new ArrayList<Integer>(v);
        boolean exist = false;
        for (int i = 0; i < v; i++)
            list.add(i);
        while (!list.isEmpty()) {
            int item = list.remove(0);
            DepthFirstSearch search = new DepthFirstSearch(graph, item);
            boolean allMarked = true;
            for (int k = 0; k < v; v++) {
                for (int w : graph.adj(k)) {
                    if (!search.marked(w)) {
                        allMarked = false;
                        break;
                    }
                    else {
                        if (list.contains(w)) {
                            int h = list.indexOf(w);
                            list.remove(h);
                        }
                    }
                }
                if (allMarked) {
                    StdOut.println("vertex " + k + " whose adjacent vertices are all marked.");
                    exist = true;
                    break;
                }
            }
            if (exist)
                break;
        }
        if (!exist)
            StdOut.println("Not exist");
    }
}