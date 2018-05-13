// Case insensitive order. Write a code fragment to read in a sequence of strings
// and sort them in ascending order, ignoring case.

import java.util.Arrays;
import java.util.Comparator;

public class StringOrder {
    // A comparator for comparing strings in ascending order, ignoring case.
    public static final Comparator<String> CASE_INSENSITIVE_ORDER = new CaseInsensitiveOrder();

    private static class CaseInsensitiveOrder implements Comparator<String> {
        public int compare(String a, String b) {
            //return a.toLowerCase().compareTo(b.toLowerCase());
            //return b.compareToIgnoreCase(a);  // in descending order.
            return a.compareToIgnoreCase(b);
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        String[] a = new String[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdIn.readString();
        }
        Arrays.sort(a, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < N; i++) {
            StdOut.println(a[i]);
        }
    }
}