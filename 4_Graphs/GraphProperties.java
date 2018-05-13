// The eccentricity of a vertex v is the the length of the shortest path from that vertex 
// to the furthest vertex from v. The diameter of a graph is the maximum eccentricity of 
// any vertex. The radius of a graph is the smallest eccentricity of any vertex. 
// A center is a vertex whose eccentricity is the radius.
public class GraphProperties {

    private int[] eccentricity;
    private int diameterVertex;
    private int radiusVertex;
    private int shortCircleLength;
    private Queue<Integer> circlePath;

    // constructor (exception if G not connected)
    public GraphProperties(Graph G) {
        eccentricity = new int[G.V()];
        boolean connected = false;
        int maximum = Integer.MIN_VALUE;
        int minimum = Integer.MAX_VALUE;
        shortCircleLength = Integer.MAX_VALUE;
        for (int i = 0; i < G.V(); i++) {
            BreadthFirstPaths bfs = new BreadthFirstPaths(G, i);
            int length = Integer.MIN_VALUE;
            if (bfs.shortLengthCircle() < shortCircleLength) {
                shortCircleLength = bfs.shortLengthCircle();
                circlePath = new Queue<Integer>();
                for (int m : bfs.shortCirclePath())
                    circlePath.enqueue(m);
            }
            for (int v = 0; v < G.V(); v++) {
                if (bfs.hasPathTo(v)) {
                    connected = true;
                    int k = bfs.distTo(v);
                    if (k > length)
                        length = k;
                }
            }
            if (!connected) {
                throw new IllegalArgumentException("G not connected");
            }
            eccentricity[i] = length;
            if (length > maximum) {
                diameterVertex = i;
            }
            if (length < minimum) {
                radiusVertex = i;
            }
        }
    }

    // eccentricity of v
    public int eccentricity(int v) {
        return eccentricity[diameterVertex];
    }

    // diameter of G
    public int diameter() {
        return eccentricity[radiusVertex];
    }

    // a center of G
    public int center() {
        return radiusVertex;
    }

    public int shortCircleLength() {
        return shortCircleLength;
    }

    public Iterable<Integer> circlePath() {
        return circlePath;
    }

    // The girth of a graph is the length of its shortest cycle. If a graph
    // is acyclic, then its girth is infinite. 

    public static void main(String[] args) {
        String vertex = args[0];
        int v = Integer.parseInt(vertex);
        String edge = args[1];
        int e = Integer.parseInt(edge);
        Graph graph = new Graph(v);
        Graph.readGraph(graph, v, e);
        StdOut.println(graph);
        GraphProperties graphPro = new GraphProperties(graph);
        for (int i = 0; i < v; i++)
            StdOut.print(graphPro.eccentricity(i) + " ");
        StdOut.println();
        StdOut.println(graphPro.diameter());
        StdOut.println(graphPro.center());
        System.out.println("The shortest circle is " + graphPro.shortCircleLength());
        for (int k : graphPro.circlePath())
            System.out.print(k + " ");
        System.out.println();
    }

}