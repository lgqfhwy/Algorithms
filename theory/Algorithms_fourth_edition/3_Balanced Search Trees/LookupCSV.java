// Reads in a set of key-value pairs from a two-column CSV file specified
// on the command line; then, reads in keys from standard input and prints
// out corresponding values.
// The LookupCSV class provides a data-friven client for reading in a
// key-value pairs from a file; then, printing the values corresponding to the
// keys found on standard input. Both keys and values are strings.
// The fields to serve as the key and value are taken as command-line arguments.
public class LookupCSV {

    // Do not instantiate.
    private LookupCSV() { }

    public static void main(String[] args) {
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);

        // symbol table
        ST<String, String> st = new ST<String, String>();
        // read in the data from csv file
        In in = new In(args[0]);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String val = tokens[valField];
            st.put(key, val);
        }

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (st.contains(s))
                StdOut.println(st.get(s));
            else 
                StdOut.println("Not found");
        }
    }
}