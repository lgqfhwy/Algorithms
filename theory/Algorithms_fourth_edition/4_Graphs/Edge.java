/**
 *  The {@code Edge} class represents a weighted edge in an 
 *  {@link EdgeWeightedGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    // Initializes an edge between vertices v and w of the given weight.
    public Edge(int v, int w, double weight) {
        if (v < 0)
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (w < 0)
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    // Returns the weight of this edge.
    public double weight() {
        return weight;
    }

    // Returns either endpoint of this edge.
    public int either() {
        return v;
    }

    // Returns the endpoint of this edge that is different from the given vertex.
    public int other(int vertex) {
        if (vertex == v)    
            return w;
        else if (vertex == w)   
            return v;
        else 
            throw new IllegalArgumentException("Illegal endpoint");
    }

    // Compares two edges by weight.
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    // Returns a string representation of this edge.
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    // Unit tests the Edge data type.
    public static void main(String[] args) {
        Edge e = new Edge(12, 34, 5.67);
        System.out.println(e);
    }
}