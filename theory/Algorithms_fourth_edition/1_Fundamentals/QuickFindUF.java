public class QuickFindUF {
    private int[] id;   // access to component id(site indexed)
    private int count;  // number of components.

    // Initialize an empty union-find data structure with n sites
    // 0 through n - 1. Each site is initially in its own component.
    public QuickFindUF(int N) {
        // Initialize component id array.
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    // Return the number of components.
    public int count() {
        return count;
    }

    // Return true if the two sites are in the same component.
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    // Return the component identifier for the component containing site.
    public int find(int p) {
        return id[p];
    }

    // validate that p is a valid index.
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public void union(int p, int q) {
        validate(p);
        validate(q);
        // Put p and q into the same component.
        int pID = find(p);
        int qID = find(q);
        // Nothing to do if p and q are already in 
        // the same component.
        if (pID == qID)    return ;
        // Rename p's component to q's name.
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID)
                id[i] = qID;
        }
        count--;
    }


    // Reads in a sequence of integers (betwwen 0 and n-1) from standard input,
    // where each integer represents some site;
    // if the sites are in different compoents, merge the two compoents and 
    // print the pair to standard output. 
    public static void main(String[] args) {
        // Solve dynamic connectivity problem on StdIn.
        int N = StdIn.readInt();    // Read number of sites.
        QuickFindUF uf = new QuickFindUF(N);          // Initialize N components.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();    // Read pair to connect.
            if (uf.connected(p, q))
                continue;               // Ignore if connected.
            uf.union(p, q);         // Combine components.
            //StdOut.println(p + " " + q);    // and print connection.
            for (int i = 0; i < N; i++) {
                StdOut.printf("id[%d] = %d  ", i, uf.id[i]);
            }
            StdOut.println();
        }
        StdOut.println(uf.count() + " components");
    }
}

































