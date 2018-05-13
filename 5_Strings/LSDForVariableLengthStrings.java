// Develop an implementation of LSD string sort 
// that works for variable-length strings.
public class LSDForVariableLengthStrings {
    
    // do not instantiate
    private LSDForVariableLengthStrings() { }

    // find longest length string in string[] a.
    public static int findLongestLength(String[] a) {
        int longest = 0;
        for (int i = 0; i < a.length; ++i) {
            if (a[i].length() > longest) {
                longest = a[i].length();
            }
        }
        return longest;
    }

    // if d >= 0 && d < a[i].length(), return a[i].charAt(d);
    // else , return 0, which means least value to sort.
    public static int findCharAtInString(int i, int d, String[] a) {
        if (d < 0 || d >= a[i].length()) {
            return 0;
        }
        return a[i].charAt(d);
    }

    // Rearranges the array of variable-length strings.
    public static void sort(String[] a) {
        int n = a.length;
        int R = 256;    // extended ASCII alphabet size.
        String[] aux = new String[n];
        int w = findLongestLength(a);  // w is the length of longest string in a.
        for (int d = w - 1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[R + 1];
            for (int i = 0; i < n; ++i) {
                int c = findCharAtInString(i, d, a);
                count[c + 1]++;
            }

            // compute cumulates
            for (int r = 0; r < R; ++r) {
                count[r + 1] += count[r];
            }

            // move data
            for (int i = 0; i < n; ++i) {
                int c = findCharAtInString(i, d, a);
                aux[count[c]++] = a[i];
            }

            // copy back
            for (int i = 0; i < n; ++i) {
                a[i] = aux[i];
            }
        }
    }
    public static void main(String[] args) {

        String[] a = {"38A", "3TW723", "2IYEA938", "3CI34780720"};
        int n = a.length;
        // sort the strings
        sort(a);

        // prints results
        for (int i = 0; i < n; ++i) {
            System.out.println(a[i]);
        }

    }
}