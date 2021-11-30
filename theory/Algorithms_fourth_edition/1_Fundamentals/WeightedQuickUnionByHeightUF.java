public class WeightedQuickUnionByHeightUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] height;     // height[i] = height of subtree rooted at i
    private int count;      // number of components

    // Initializes an empty union-find data structure with n sites 0 through n - 1.
    // Each site is initially in its own component.
    public WeightedQuickUnionByHeightUF(int n) {
        count = n;
        parent = new int[n];
        height = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            height[i] = 0;
        }
    }

    // Return the number of components.
    public int count() {
        return count;
    }

    // Return the component identifier for the component containing site.
    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    public int findWithPathCompression(int p) {
        validate(p);
        int root = p;
        while (root != parent[root])
            root = parent[root];
        while (p != root) {
            int temp = parent[p];
            parent[p] = root;
            p = temp;
        }
        return root;
    }

    // Validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    // Returns true if the two sites are in the same component.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Merge the component containing site p with the component
    // containig site q.
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return ;
        // Make smaller root point to larger one
        if (height[rootP] < height[rootQ]) {
            parent[rootP] = rootQ;
        }
        else if (height[rootP] > height[rootQ]){
            parent[rootQ] = rootP;
        }
        else {
            parent[rootP] = rootQ;
            height[rootQ]++; 
        }
        count--;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
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





































