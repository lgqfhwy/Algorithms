public class QuickUnionUF {
    private int[] parent;   // parent[i] = parent of i.
    private int count;      // number of components

    //Initializes an empty union-find data structure with n sites
    // 0 through n - 1. Each site is initially in its own component.
    public QuickUnionUF(int n) {
        parent = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // Returns the number of components.
    public int count() {
        return count;
    }

    // Returns the components identifier for the component containing site p.
    public int find(int p) {
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    // find with path compression
    public int findWithPathCompression(int p) {
        int root = p;
        while (root != id[root])
            root = id[root];
        while (p != root) {
            int temp = id[p];
            id[p] = root;
            p = temp;
        }
        return root;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    // Returns true if the two sites are in the same component.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Merges the component containing site p with the 
    // component containing site q.
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return ;
        parent[rootP] = rootQ;
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(n);
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

































