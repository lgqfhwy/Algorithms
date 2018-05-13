// Reads in two strings, the pattern and the input text, and
// searches for the pattern in the input text using brute force.
public class Brute {

    // String versions
    
    // return offset of first match or n is no match.
    public static int search1(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i;   // found at offset i.
            }
        }
        return n;   // not found.
    }

    // return offset of first match or N if no match
    public static int search2(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (txt.charAt(i) == pat.charAt(j)) {
                j++;
            } else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) {
            return i - m;   // found
        } else {
            return n;   // not found
        }
    }

    // char[] array versions

    // return offset of first match or n if no match
    public static int search1(char[] pattern, char[] text) {
        int m = pattern.length;
        int n = text.length;

        for (int i = 0; i <= n - m; i ++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text[i + j] != pattern[j]) {
                    break;
                }
            }
            if (j == m) {
                return i;   // found at offset i.
            }
        }
        return n;   // not found
    }

    // return offset of first match or n if no match
    public static int search2(char[] pattern, char[] text) {
        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (text[i] == pattern[j]) {
                j++;
            } else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) {
            return i - m;   // found;
        } else {
            return n;   // not found;
        }
    }

    // Add to our brute-force implementation of substring search a count() method to
    // count occurrences and a searchAll() method to print all occurrences.
    public static int count(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int countNum = 0;
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                countNum++;
            }
        }
        return countNum;
    }

    public static void searchAll(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                System.out.println("i = " + i);
            }
        }
    }

    // Takes a pattern string and an input string as command-line arguments;
    // searches for the pattern string in the text string; and prints
    // the first occurrence of the pattern string in the text string.
    public static void main(String[] args) {
        //String pat = args[0];
        //String txt = args[1];
        String pat = "ababc";
        String txt = "abcababcababc";
        char[] pattern = pat.toCharArray();
        char[] text    = txt.toCharArray();

        int offset1a = search1(pat, txt);
        int offset2a = search2(pat, txt);
        int offset1b = search1(pattern, text);
        int offset2b = search2(pattern, text);

        // print results
        System.out.println("text:    " + txt);

        // from brute force search method 1a
        System.out.print("pattern: ");
        for (int i = 0; i < offset1a; i++)
            System.out.print(" ");
        System.out.println(pat);

        // from brute force search method 2a
        System.out.print("pattern: ");
        for (int i = 0; i < offset2a; i++)
            System.out.print(" ");
        System.out.println(pat);

        // from brute force search method 1b
        System.out.print("pattern: ");
        for (int i = 0; i < offset1b; i++)
            System.out.print(" ");
        System.out.println(pat);

        // from brute force search method 2b
        System.out.print("pattern: ");
        for (int i = 0; i < offset2b; i++)
            System.out.print(" ");
        System.out.println(pat);

        System.out.println(count(pat, txt));
    }
}