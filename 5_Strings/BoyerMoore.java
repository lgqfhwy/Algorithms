// Reads in two strings, the pattern and the input text, and
// searches for the pattern in the input text using the
// bad-character rule part of the Boyer-Moore algorithm.
// (does not implement the strong good suffix rule)

/**
 *  The {@code BoyerMoore} class finds the first occurrence of a pattern string
 *  in a text string.
 *  This implementation uses the Boyer-Moore algorithm (with the bad-character
 *  rule, but not the strong good suffix rule).
 */
public class BoyerMoore {
    private final int R;    // the radix.
    private int[] right;    // the bad-character skip array.

    private char[] pattern;     // store the pattern as a character array.
    private String pat;    // or as a string.

    // Preprocesses the pattern string
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < pat.length(); j++) {
            right[pat.charAt(j)] = j;
        }
    }

    // Preprocesses the pattern string.
    public BoyerMoore(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++) {
            this.pattern[j] = pattern[j];
        }
        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < pattern.length; j++) {
            right[pattern[j]] = j;
        }
    }

    // Returns the index of the first occurrence of the pattern string
    // in the next string.
    public int search(String txt) {
        int m = pat.length();
        int n = txt.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i;   // found/
            }
        }
        return n;   // not found.
    }

    // Returns the index of the first occurrence of the pattern string
    // in the text string.
    public int search(char[] text) {
        int m = pattern.length;
        int n = text.length;
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pattern[j] != text[i + j]) {
                    skip = Math.max(1, j - right[text[i + j]]);
                    break;
                }
            }
            if (skip == 0) {
                return i;   // found.
            }
        }
        return n;   // not found.
    }

    public static void main(String[] args) {
        
    }
}