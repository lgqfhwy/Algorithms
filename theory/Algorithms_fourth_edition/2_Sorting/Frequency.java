// Write a program Frequency.java that reads strings from standard input and prints the
// number of times each string occurs, in descending order of frequency.
import java.util.Arrays;
public class Frequency {
    public static void main(String[] args) {

        // read in and sort the input strings.
        //String[] a = StdIn.readAllStrings();
        String[] a = {"list", "love", "you", "hard", "life", "life", "abc"};
        int n = a.length;
        Arrays.sort(a);

        // create an array of distinct strings and their frequencies
        Record[] records = new Record[n];
        String word = a[0];
        int freq = 1;
        int m = 0;
        for (int i = 1; i < n; i++) {
            if (!a[i].equals(word)) {
                records[m++] = new Record(word, freq);
                word = a[i];
                freq = 0;
            }
            freq++;
        }
        records[m++] = new Record(word, freq);

        // sort by frequency and print results
        Arrays.sort(records, 0, m);
        for (int i = m - 1; i >= 0; i--)
            StdOut.println(records[i]);
    }
}