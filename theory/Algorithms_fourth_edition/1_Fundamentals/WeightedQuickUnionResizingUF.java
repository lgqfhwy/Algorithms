public class WeightedQuickUnionResizingUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of sites in subtree rooted at i
    private int count;      // number of components

    // Initializes an empty union-find data structure with n sites 0 through n - 1.
    // Each site is initially in its own component.
    public WeightedQuickUnionResizingUF() {
        int n = 10;
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void resize(int capacity) {
        int[] tempparent = new int[capacity];
        int[] tempsize = new int[capacity];
        for (int i = 0; i < parent.length; i++) {
            tempparent[i] = parent[i];
            tempsize[i] = size[i];
        }

        for (int i = parent.length; i < tempparent.length; i++) {
            tempparent[i] = i;
            tempsize[i] = 1;
        }
        count += capacity - parent.length;
        parent = tempparent;
        size = tempsize;
    }

    // Return the number of components.
    public int count() {
        return count;
    }

    // Return the component identifier for the component containing site.
    public int find(int p) {
        //validate(p);
        if (p >= parent.length)
            return p;
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
        int maxnum = Math.max(p, q);
        if (maxnum >= parent.length) {
            resize(maxnum + 1);
        }
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return ;
        // Make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public static void main(String[] args) {
        WeightedQuickUnionResizingUF uf = new WeightedQuickUnionResizingUF();
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





































