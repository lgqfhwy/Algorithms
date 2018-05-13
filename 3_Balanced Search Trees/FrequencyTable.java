// Read in a list of words from standard input and print
// out each word and the number of times it appears.
public class FrequencyTable<Key extends Comparable<Key>> {

    private ST<Key, Integer> st = new ST<Key, Integer>();

    // add 1 to the number of times key appears.
    public void hit(Key key) {
        if (st.contains(key)) 
            st.put(key, st.get(key) + 1);
        else
            st.put(key, 1);
    }

    // return the number of times the key appears.
    public int count(Key key) {
        if (st.contains(key))   return st.get(key);
        else                    return 0;
    }

    // print all the keys to standard output.
    public void show() {
        for (Key key : st.keys()) 
            StdOut.println(st.get(key) + " " + key);
    }

    public static void main(String[] args) {
        FrequencyTable<String> freq = new FrequencyTable<String>();
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            freq.hit(key);
        }
        freq.show();
    }
}