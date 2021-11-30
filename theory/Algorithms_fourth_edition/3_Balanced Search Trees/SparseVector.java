// Sparse vectors and matrices. Program SparseVector.java implements a sparse 
// vector using a symbol table of index-value pairs. Memory is proportional 
// to the number of nonzeros. The operations set and get take O(1) time (on average); 
// taking the dot product of two vectors takes time proportional to the number of 
// nonzero entries in both.
/**
 *  The {@code SparseVector} class represents a d-dimensional mathematical vector.
 *  Vectors are mutable: their values can be changed after they are created.
 *  It includes methods for addition, subtraction,
 *  dot product, scalar product, unit vector, and Euclidean norm.
 *  The implementation is a symbol table of indices and values for which the vector
 *  coordinates are nonzero. This makes it efficient when most of the vector coordindates
 *  are zero.
 */
public class SparseVector {
    private int d;  // dimension
    private ST<Integer, Double> st;    // the vector, represented by index-value pairs

    // Initializes a d-dimensional zero vector.
    public SparseVector(int d) {
        this.d = d;
        this.st = new ST<Integer, Double>();
    }

    // Sets the ith coordinate of this vector to the specified value.
    public void put(int i, double value) {
        if (i < 0 || i >= d)    throw new IllegalArgumentException("Illegal index");
        if (value == 0.0)   st.delete(i);
        else                st.put(i, value);
    }

    // Returns the ith coordinate of this vector.
    public double get(int i) {
        if (i < 0 || i >= d)    throw new IllegalArgumentException("Illegal index");
        if (st.contains(i))     return st.get(i);
        else                    return 0.0;
    }

    public void delete(int i) {
        if (i < 0 || i >= d)    throw new IllegalArgumentException("Illegal index");
        st.delete(i);
    }

    // Returns the number of nonzero entries in this vector.
    public int nnz() {
        return st.size();
    }

    // Returns the dimension of this vector.
    @Deprecated
    public int size() {
        return d;
    }

    // Returns the dimension of this vector.
    public int dimension() {
        return d;
    }

    // Returns the inner product of this vector with the specified vector.
    public double dot(SparseVector that) {
        if (this.d != that.d)   throw new IllegalArgumentException("Vector lengths disagree");
        double sum = 0.0;

        // iterate over the vector with the fewest nonzeros
        if (this.st.size() < that.st.size()) {
            for (int i : this.st.keys()) {
                if (that.st.contains(i))
                    sum += this.get(i) * that.get(i);
            }
        }
        else {
            for (int i : that.st.keys()) {
                if (this.st.contains(i))
                    sum += this.get(i) * that.get(i);
            }
        }
        return sum;
    }

    // Returns the inner product of this vector with the specified array.
    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keys())
            sum += that[i] * this.get(i);
        return sum;
    }

    // Returns the magnitude of this vector.
    // This is also known as the L2 norm or the Euclidean norm.
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    // Returns the Euclidean norm of this vector.
    @Deprecated
    public double norm() {
        return Math.sqrt(this.dot(this));
    }

    // Returns the scalar-vector product of this vector with the specified scalar.
    public SparseVector scale(double alpha) {
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys())
            c.put(i, alpha * this.get(i));
        return c;
    }

    // Returns the sum of this vector and the specified vector.
    public SparseVector plus(SparseVector that) {
        if (this.d != that.d)   throw new IllegalArgumentException("Vector lengths disagree");
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys())
            c.put(i, this.get(i));     // c = this
        for (int i : that.st.keys())
            c.put(i, that.get(i) + c.get(i));   // c = c + this
        return c;
    }

    // Returns a string representation of this vector.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : st.keys()) {
            s.append("(" + i + ", " + st.get(i) + ") ");
        }
        return s.toString();
    }

    // Unit tests the SparseVector data type.
    public static void main(String[] args) {
        SparseVector a = new SparseVector(10);
        SparseVector b = new SparseVector(10);
        a.put(3, 0.50);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.00);
        b.put(3, 0.60);
        b.put(4, 0.90);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a dot b = " + a.dot(b));
        StdOut.println("a + b   = " + a.plus(b));
    }


}

