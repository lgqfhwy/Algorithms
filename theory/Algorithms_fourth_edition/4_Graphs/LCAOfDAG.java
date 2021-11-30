// LCA of a DAG. Given a DAG and two vertices v and w, find the lowest common 
// ancestor (LCA) of v and w. The LCA of v and w is an ancestor of v and w 
// that has no descendants that are also ancestors of v and w. Computing the 
// LCA is useful in multiple inheritance in programming languages, analysis 
// of genealogical data (find degree of inbreeding in a pedigree graph), and 
// other applications. Hint : Define the height of a vertex v in a DAG to be 
// the length of the longest path from a root to v. Among vertices that are 
// ancestors of both v and w, the one with the greatest height is an LCA of v and w.
public class LCAOfDAG {

    int[] vertexSequence;   // the Euler tour of the graph vertex.
    int[] depthSequence;    // the depth sequence corresponding to the vertex.
    int num;
    RMQSegmentTreeIndex rmq;
    boolean[] marked; 

    public LCAOfDAG(Digraph G, int source) {
        int n = G.E() * 2 + 2;
        vertexSequence = new int[n];
        depthSequence = new int[n];
        int depth = 0;
        num = 0;
        int count = 0;
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++) {
            marked[i] = false;
        }
        num = buildSequence(G, source, depth, count);
        //System.out.println("&&num = " + num);
        rmq = new RMQSegmentTreeIndex(depthSequence, num);
    }

    private int buildSequence(Digraph G, int source, int depth, int count) {
        for (int w : G.adj(source)) {
            //System.out.println(w + ", num = " + num);
            if (!marked[w]) {
                marked[w] = true;
                vertexSequence[count] = source;
                //System.out.println("##" + source);
                depthSequence[count] = depth;
                vertexSequence[count + 1] = w;
                //System.out.println("%%" + w);
                depthSequence[count + 1] = depth + 1;
                count+= 2;
                count = buildSequence(G, w, depth + 1, count);
            }
        }
        //System.out.println("**** count = " + count);
        return count;
    }

    public int LCAQuery(int i, int j) {
        // System.out.println("num = " + num);
        // for (int y = 0; y < num; y++) {
        //     System.out.println("Hello");
        //     System.out.print(vertexSequence[y] + " ");
        // }
        // System.out.println();
        int k, h;
        for (k = 0; k < num; k++) {
            if (vertexSequence[k] == i) {
                break;
            }
        }
        for (h = 0; h < num; h++) {
            if (vertexSequence[h] == j) {
                break;
            }
        }
        if (k != num && h != num) {
            int n;
            if (k > h) {
                n = rmq.RMQIndex(h, k);
            }
            else {
                n = rmq.RMQIndex(k, h);
            }
            //System.out.println("k = " + k + ", h = " + h);
            return vertexSequence[n];
        }
        else {
            System.out.println("Index not in array");
            return -1;
        }
    }
    public static void main(String[] args) {
        Digraph G = new Digraph(8);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(0, 3);
        G.addEdge(2, 4);
        G.addEdge(2, 5);
        G.addEdge(3, 6);
        G.addEdge(5, 7);
        LCAOfDAG lca = new LCAOfDAG(G, 0);
        System.out.println(lca.LCAQuery(4, 7));
    }
}