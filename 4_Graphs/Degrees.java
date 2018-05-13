// The indegree of a vertex in a digraph is the number of directed edges that point to that vertex. 
// The outdegree of a vertex in a digraph is the number of directed edges that emanate from that vertex. 
// No vertex is reachable from a vertex of outdegree 0, which is called a sink; a vertex of indegree 0, 
// which is called a source, is not reachable from any other vertex. A digraph where self-loops are 
// allowed and every vertex has outdegree 1 is called a map (a function from the set of integers 
// from 0 to V–1 onto itself). 
public class Degrees {

    private int[] indegree;     // indegree of v.
    private int[] outdegree;    // outgree of v.
    private Bag<Integer> sources;   // a vertex of indegree of 0.
    private Bag<Integer> sinks;     // a vertex of outgree of 0.
    private boolean isMap;      // every vertex has outdegree 1.

    public Degrees(Digraph G) {
        indegree = new int[G.V()];
        outdegree = new int[G.V()];
        sources = new Bag<Integer>();
        sinks = new Bag<Integer>();
        isMap = true;

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                indegree[w]++;
            }
            outdegree[v] = G.outdegree(v);
            if (outdegree[v] == 0)
                sinks.add(v);
            if (outdegree[v] != 1)
                isMap = false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (indegree[v] == 0)
                sources.add(v);
        }
    }

    // indegree of v
    public int indegree(int v) {
        return indegree[v];
    }

    // outdegree of v
    public int outdegree(int v) {
        return outdegree[v];
    }

    // sources
    public Iterable<Integer> sources() {
        return sources;
    }

    // sinks
    public Iterable<Integer> sinks() {
        return sinks;
    }

    // Is G a map ?
    public boolean isMap() {
        return isMap;
    }

    public static void main(String[] args) {
        
    }
}