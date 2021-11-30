import edu.princeton.cs.algs4.StdIn;

// Inverted concordance. Write a program InvertedConcordance that 
// takes a concordance on standard input and puts the original 
// string on standard output stream.
public class InvertedConcordance {
    public static void main(String[] args) {
        int CONTEXT = 5;
        In in = new In(args[0]);
        String[] words = in.readAllStrings();
        ST<String, SET<Integer>> st = new ST<String, SET<Integer>>();

        // build up concordance
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if (!st.contains(s)) {
                st.put(s, new SET<Integer>());
            }
            SET<Integer> set = st.get(s);
            set.add(i);
        }
        StdOut.println("Finished building concordance");

        // process queries
        while (StdIn.hasNextLine()) {
            StdOut.println("Hello");
            String line = StdIn.readLine();
            String[] query = line.split(" ");
            for (int i = 0; i < query.length; i++) {
                if (st.contains(query[i])) {
                    StdOut.print(query[i]);
                }
                else  {
                    String str = query[i].substring(1, query[i].length() - 1);
                    if (st.contains(str)) {
                        StdOut.print(str);
                    }
                }
                if (i != query.length - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
}