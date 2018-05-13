/**
 *  The {@code SymbolDigraph} class represents a digraph, where the
 *  vertex names are arbitrary strings.
 *  By providing mappings between string vertex names and integers,
 *  it serves as a wrapper around the
 *  {@link Digraph} data type, which assumes the vertex names are integers
 *  between 0 and V - 1.
 *  It also supports initializing a symbol digraph from a file.
 *  This implementation uses an {@link ST} to map from strings to integers,
 *  an array to map from integers to strings, and a {@link Digraph} to store
 *  the underlying graph.
 *  The indexOf and contains operations take time 
 *  proportional to log V, where V is the number of vertices.
 *  The nameOf operation takes constant time.
 */
public class SymbolDigraph {

    private ST<String, Integer> st;    // string -> index
    private String[] keys;  // index -> string
    private Digraph graph;  // the underlying digraph

    // Initializes a digraph from a file using the specified delimiter.
    // Each line in the file contains the name of vertex, followed by a
    // list of the names of the vertices adjacent to that vertex, separated by the delimiter.
    public SymbolDigraph(String filename, String delimiter) {
        st = new ST<String, Integer>();
        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }

        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys())
            keys[st.get(name)] = name;
        
        // second pass builds the digraph by connecting first vertex on each
        // line to all others
        graph = new Digraph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                graph.addEdge(v, w);
            }
        }
    }

    // Does the digraph contain the vertex named s ?
    public boolean contains(String s) {
        return st.contains(s);
    }

    // Returns the integer associated with the vertex named s.
    @Deprecated
    public int index(String s) {
        return st.get(s);
    }

    // Returns the integer associated with the vertex named s.
    public int indexOf(String s) {
        return st.get(s);
    }

    // Returns the name of the vertex associated with the integer v.
    @Deprecated
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    // Returns the name of the vertex associated with the integer v.
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    // Returns the digraph associated with the symbol graoh. It is the client's responsibility
    // not to mutate the digraph.
    @Deprecated
    public Digraph G() {
        return graph;
    }

    // Returns the digraph associated with the symbol graph. It is the client's responsibility
    // not to mutate the digraph.
    public Digraph digraph() {
        return graph;
    }

    // throw new IllegalArgumentException unless 0 <= v < V.
    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // Unit tests the SymbolDigraph data type.
    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Digraph graph = sg.digraph();
        while (!StdIn.isEmpty()) {
            String t = StdIn.readLine();
            for (int v : graph.adj(sg.index(t))) {
                StdOut.println("   " + sg.name(v));
            }
        }
    }


}