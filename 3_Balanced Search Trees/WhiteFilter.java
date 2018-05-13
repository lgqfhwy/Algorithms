// Read in a whitelist of words from a file. Then read in a list of
// words from standard input and print out all those words that
// are in the first file.
// The WhiteFilter class provides a client for reading in a whitelist
// of words from a file; then, reading in a sequence of words from standard
// input, printing out each word that appears in the file.
// It is useful as a test client for various symbol table implementations.
public class WhiteFilter {

    // Do not instantiate
    private WhiteFilter() { }

    public static void main(String[] args) {
        SET<String> set = new SET<String>();
        // read in strings and add to set
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String word = in.readString();
            set.add(word);
        }

        // read in string from standard input, printing out all exceptions
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (set.contains(word))
                StdOut.println(word);
        }
    }
}