public class Whitelist {
    public static void main(String[] args) {
        int[] w = In.readInts(args[0]);
        StaticsSETofInts set = new StaticsSETofInts(w);
        while (!StdIn.isEmpty()) {
            // Read key, print if not in whitelist.
            int key = StdIn.readInt();
            if (set.rank(key) == -1)
                StdOut.println(key);
        }
    }
}