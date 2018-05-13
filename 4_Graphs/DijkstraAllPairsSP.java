/**
 *  The {@code DijkstraAllPairsSP} class represents a data type for solving the
 *  all-pairs shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  This implementation runs Dijkstra's algorithm from each vertex.
 *  The constructor takes time proportional to V (E log V)
 *  and uses space proprtional to V^2,
 *  where V is the number of vertices and E is the number of edges.
 *  Afterwards, the {@code dist()} and {@code hasPath()} methods take
 *  constant time and the {@code path()} method takes time proportional to the
 *  number of edges in the shortest path returned.
 */
public class DijkstraAllPairsSP {

    private DijkstraSP[] all;

    // Computes a shortest paths tree from each vertex to every other vertex in
    // the edge-weighted digraph G.
    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++) {
            all[v] = new DijkstraSP(G, v);
        }
    }

    // Returns a shortest path from vertex s to vertex t.
    public Iterable<DirectedEdge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return all[s].pathTo(t);
    }

    // Is there a path from the vertex s to vertex t ?
    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return dist(s, t) < Double.POSITIVE_INFINITY;
    }

    // Returns the length of a shortest path from vertex s to vertex t.
    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return all[s].distTo(t);
    }

    // throw an IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = all.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public static void main(String[] args) {
        
    }
}