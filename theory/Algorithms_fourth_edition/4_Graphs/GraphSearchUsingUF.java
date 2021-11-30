// Develop an implementation for the search API
// that uses UF.
public class GraphSearchUsingUF {

    private UF uf;
    private boolean[] marked;   // marked[v] = is there an s-v path ?
    private int count;


    public GraphSearchUsingUF(Graph G, int s) {
        uf = new UF(G.V());
        marked = new boolean[G.V()];
        count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (uf.connected(w, v))
                    continue;
                uf.union(w, v);
            }
        }
        for (int i = 0; i < G.V(); i++) {
            if (i != s && uf.connected(i, s)) {
                count++;
                marked[i] = true;
            }
        }
        marked[s] = true;
    }

    // is v connected to s ?
    public boolean marked(int v) {
        return marked[v];
    }

    // how many vertices are connected to s ?
    public int count() {
        return count;
    }

    public static void main(String[] args) {
        
    }

}