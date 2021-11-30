// FileIndex.java takes a sequence of file names as command-line arguments 
// and builds a symbol table associating every keyword with a SET of file 
// names where the keyword can be found. Then, it takes keyword queries 
// from standard input.
// The FileIndex class provides a client for indexing a set of files, specified
// as command-line arguments. It takes queries from standard input and print exch
// file that contains the given query.
import java.io.File;
public class FileIndex {

    // Do not instantiate.
    private FileIndex() { }

    public static void main(String[] args) {
        // key = word, value = set of files containing that word
        ST<String, SET<File>> st = new ST<String, SET<File>>();
        // create inverted index of all files.
        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println(" " + filename);
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word))
                    st.put(word, new SET<File>());
                    SET<File> set = st.get(word);
                    set.add(file);
            }
        }
        // read queries from standard input, one per line.
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println(" " + file.getName());
                }
            }
        }
    }
}
