public class Counter implements Comparable<Counter> {
    private final String name;  // counter name
    private int count = 0;      // current value

    // Initializes a new counter starting at 0, with the given id.
    public Counter(String id) {
        name = id;
    }

    // Increments the counter by 1.
    public void increment() {
        count++;
    }

    // Returns the current value of this counter.
    public int tally() {
        return count;
    }

    // Returns a string representation of this counter.
    public String toString() {
        return count + " " + name;
    }

    // Compares this counter to the specified counter.
    @Override
    public int compareTo(Counter that) {
        if          (this.count < that.count)   return -1;
        else if     (this.count > that.count)   return +1;
        else                                    return 0;
    }

    // Reads two command-line integers n and trials; create n counters;
    // increments trials counters at random; and prints results.
    public static void main(String[] args) {
        //int n = Integer.parseInt(args[0]);
        //int trials = Integer.parseInt(args[1]);
        int n = 10;
        int trials = 100;

        // create n counters
        Counter[] hits = new Counter[n];
        for (int i = 0; i < n; i++) {
            hits[i] = new Counter("counter" + i);
        }
        // increment trials counters at random
        for (int t = 0; t < trials; t++) {
            hits[StdRandom.uniform(n)].increment();
        }
        // print results
        for (int i = 0; i < n; i++) {
            StdOut.println(hits[i]);
        }
    }
}