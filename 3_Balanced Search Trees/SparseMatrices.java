import edu.princeton.cs.algs4.ST;

// Sparse matrices. Develop an API and an implementation for 
// sparse 2D matrices. Support matrix addition and matrix 
// multiplication. Include constructors for row and column vectors.
public class SparseMatrices {
    private int d;  // dimension
    private ST<Integer, ST<Integer, Double>> st;    // the matrices, represented by index(row)-ST pairs.

    public SparseMatrices(int d) {
        this.d = d;
        this.st = new ST<Integer, ST<Integer, Double>>();
    }

    // Sets the ith row and jth column of this matrix to the specified value.
    public void put(int i, int j, double value) {
        if (i < 0 || i >= d || j < 0 || j >= d)    throw new IllegalArgumentException("Illegal index");
        if (value == 0.0)   delete(i, j);
        else {
            if (!st.contains(i)) {
                ST<Integer, Double> row = new ST<Integer, Double>();
                st.put(i, row);
            }
            ST<Integer, Double> row = st.get(i);
            row.put(j, value);
        }
    }

    // Returns true if matrix contains value which is at ith row and jth column.
    public boolean contains(int i, int j) {
        if (st.contains(i)) {
            ST<Integer, Double> row = st.get(i);
            if (row.contains(j))
                return true;
            return false;
        }
        return false;
    }

    // Returns the ith row and jth column of this matrix.
    public double get(int i, int j) {
        if (i < 0 || i >= d || j < 0 || j >= d)    throw new IllegalArgumentException("Illegal index");
        if (st.contains(i)) {
            ST<Integer, Double> row = st.get(i);
            if (row.contains(j))
                return row.get(j);
            return 0.0;
        }
        return 0.0;
    }

    public void delete(int i, int j) {
        if (i < 0 || i >= d || j < 0 || j >= d)    throw new IllegalArgumentException("Illegal index");
        if (!contains(i, j)) {
            return;
        }
        ST<Integer, Double> row = st.get(i);
        row.delete(j);
    }

    // Returns the number of nonzero entries in this matrix.
    public int nnz() {
        int sum = 0;
        for (int i = 0; i < d; i++) {
            sum += st.get(i).size();
        }
        return sum;
    }

    // Returns the dimension of this matrix.
    @Deprecated
    public int size() {
        return d;
    }

    // Returns the dimension of this matrix.
    public int dimension() {
        return d;
    }

    // matrix addition
    public SparseMatrices addition(SparseMatrices that) {
        if (this.d != that.d)   throw new IllegalArgumentException("Matrix dimension disagree");
        SparseMatrices c = new SparseMatrices(d);
        for (int i : this.st.keys()) {
            for (int j : this.st.get(i).keys()) {
                c.put(i, j, get(i, j));
            }
        }
        for (int i : that.st.keys()) {
            if (!c.st.contains(i)) {
                c.st.put(i, new ST<Integer, Double>());
            }
            for (int j : that.st.get(i).keys()) {
                c.put(i, j, that.get(i, j) + c.get(i, j));
            }
        }
        StdOut.println("#####");
        StdOut.println(toString());
        return c;
    }

    // matrix multiplication
    public SparseMatrices multiplication(SparseMatrices that) {
        if (this.d != that.d)   throw new IllegalArgumentException("Matrix dimension disagree");
        SparseMatrices c = new SparseMatrices(d);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                double sum = 0.0;
                for (int k = 0; k < d; k++) {
                    StdOut.println(this.get(i, k) + ", " + that.get(k, j));
                    sum += (this.get(i, k) * that.get(k, j));
                }
                c.put(i, j, sum);
            }
        }
        return c;
    }

    // Returns a string representation of this matrix.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : st.keys()) {
            for (int j : st.get(i).keys()) {
                s.append("(" + i + ", " + j + ", " + get(i, j) + ") ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        SparseMatrices a = new SparseMatrices(2);
        SparseMatrices b = new SparseMatrices(2);
        a.put(1, 0, 0.5);
        a.put(1, 1, 0.5);
        b.put(1, 0, 0.5);
        b.put(1, 1, 0.6);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a addition b = " + "\n" + a.addition(b));
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a multiplication b = " + "\n" + a.multiplication(b));
    }



}