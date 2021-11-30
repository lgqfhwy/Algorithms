// Reads in a sequence of words from standard input, sort them in
// reverse order of lettering in each word, and prints them to standard
// output. Rhyming words appear next to one another.

import java.util.Arrays;

public class Rhymer {
     
    private static String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        return new String(sb);
    }

    public static void main(String[] args) {
        String[] strings = StdIn.readAllStrings();
        // reverse order of letters in each word.
        for (int i = 0; i < strings.length; i++) {
            strings[i] = reverse(strings[i]);
        }
        // sort the words
        Arrays.sort(strings);
        // reverse order of letters in each word
        for (int i = 0; i < strings.length; i++) {
            strings[i] = reverse(strings[i]);
        }
        // print words
        for (int i = 0; i < strings.length; i++) {
            StdOut.println(strings[i]);
        }
    }
}