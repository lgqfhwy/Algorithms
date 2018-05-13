// The UF class represents a union-find data type (also known as the 
// disjoint-sets data type).
// It supports the union and find operations, along with a connected 
// operation for determining whether two sites are in the same component
// and a count operation that returns the total number of components.
public class UF {
    private int[] parent;   // parent[i] = parsent of i
    private byte[] rank;    // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;      // number of components

    // Initializes as an empty union-find data structure with n sites 0 through n - 1.
    // Each site is initially in its own component.
    public UF(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Returns the component identifier for the component containing site.
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];  // path compression by halving
            p = parent[p];
        }
        return p;
    }

    // Return the number of components
    public int count() {
        return count;
    }

    // Return true if the two sites are in the same component.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Merges the component containing site p with the component
    // containing site q.
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return ;
        // Make root of smaller rank point to root of larger rank.
        if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        }
        else if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        }
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    // Reads in an integer n and a sequence of pairs of integers between
    // 0 and n - 1 from standard input, where each integer in the pair 
    // represents some site;
    // if the sites are in different components, merge the two components
    // and print the pair to standard output.
    public static void main(String[] args) {
        int n = StdIn.readInt();
        UF uf = new UF(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q))
                continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}




































