/**
 *  The {@code DirectedEdge} class represents a weighted edge in an 
 *  {@link EdgeWeightedDigraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the directed edge and
 *  the weight.
 */
public class DirectedEdge {

    private final int v;
    private final int w;
    private final double weight;

    // Initializes a directed edge from vertex v to vertex w with
    // the given weight.
    public DirectedEdge(int v, int w, double weight) {
        if (v < 0)
            throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (w < 0)
            throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    // Returns the head vertex of the directed edge.
    public int from() {
        return v;
    }

    // Returns the tail vertex of the directed edge.
    public int to() {
        return w;
    }

    // Returns the weight of the directed edge.
    public double weight() {
        return weight;
    }

    // Returns a string representation of the directed edge.
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }

    // Unit tests the DirectedEdge data type.
    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 34, 5.67);
        StdOut.println();
    }
}