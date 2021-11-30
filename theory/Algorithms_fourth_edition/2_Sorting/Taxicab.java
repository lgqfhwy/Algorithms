public class Taxicab implements Comparable<Taxicab> {
    private final long sum;
    private final int i;
    private final int j;

    public Taxicab(int i, int j) {
        this.sum = i * i * i + j * j * j;
        this.i = i;
        this.j = j;
    }

    public int compareTo(Taxicab that) {
        if (this.sum < that.sum)    return -1;
        if (this.sum > that.sum)    return 1;
        return 0;
    }

    public long sumNum() {
        return sum;
    }

    public String toString() {
        return sum + " = " + i + "^3" + " + " + j + "^3";
    }

    public static void main(String[] args) {
        int n = 100;
        MinPQ<Taxicab> pq = new MinPQ<Taxicab>();
        for (int i = 0; i <= n; i++) {
            pq.insert(new Taxicab(i, i));
        }
        while (!pq.isEmpty()) {
            Taxicab s = pq.delMin();
            if (pq.size() > 0 && s.sumNum() == pq.min().sumNum()) {
                StdOut.println(s);
                while (s.sumNum() == pq.min().sumNum()) {
                    StdOut.println(pq.delMin());
                }
            }
            if (s.j < n)
                pq.insert(new Taxicab(s.i, s.j + 1));
        }
    }
}