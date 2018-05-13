// Reads in two strings, the pattern and the input text, and
// searches for the pattern in the input text using the
// Las Vegas version of the Rabin-Karp algorithm.

/**
 *  The {@code RabinKarp} class finds the first occurrence of a pattern string
 *  in a text string.
 */
import java.math.BigInteger;
import java.util.Random;

public class RabinKarp {
    private String pat;    //the pattern, needed only for Las Vegas
    private long patHash;   // pattern hash value
    private int m;    //  pattern length;
    private long q; // a large prime, small enough to avoid long overflow
    private int R;  // radix
    private long RM;    // R^(M - 1) % Q.

    // Preprocesses the pattern string
    public RabinKarp(char[] pattern, int R) {
        this.pat = String.valueOf(pattern);
        this.R = R;
        throw new UnsupportedOperationException("Operation not supported yet");
    }

    // Preprocesses the pattern string
    public RabinKarp(String pat) {
        this.pat = pat;    // save pattern (needed only for Las Vegas)
        //R = 256;
        R = 10;
        m = pat.length();
        q = longRandomPrime();
        //q = 997;

        // precompute R^(m - 1) % q for use in removing leading digit
        RM = 1;
        for (int i = 1; i <= m - 1; i++) {
            RM = (R * RM) % q;
        }
        //System.out.println("RM = " + RM);
        patHash = hash(pat, m);
        //System.out.println("patHash = " + patHash);
    }

    // Compute hash for key[0..m-1]
    private long hash(String key, int m) {
        long h = 0;
        for (int j = 0; j < m; j++) {
            h = (R * h + key.charAt(j)) % q;
            //System.out.println("h = " + h + "  " + key.charAt(j));
        }
        return h;
    }

    // Las Veges version: does pat[] match txt[i..i-m+1]?
    private boolean check(String txt, int i) {
        for (int j = 0; j < m; j++) {
            if (pat.charAt(j) != txt.charAt(i + j)) {
                return false;
            }
        }
        return true;
    }

    // Returns the index of the first occurrence of the pattern string
    // in the text string.
    public int search(String txt) {
        int n = txt.length();
        if (n < m) {
            return n;
        }
        long txtHash = hash(txt, m);
        // check for match at offset 0
        if ((patHash == txtHash) && check(txt, 0)) {
            return 0;
        }
        // check for hash match; if hash match, check for exact match
        for (int i = m; i < n; i++) {
            // Remove leading digit, add trailing digit, check for match.
            txtHash = (txtHash + q - RM * txt.charAt(i - m) % q) % q;
            txtHash = (txtHash * R + txt.charAt(i)) % q;

            // match
            int offset = i - m + 1;
            if ((patHash == txtHash) && check(txt, offset)) {
                return offset;
            }
        }
        // no match
        return n;
    }

    // a random 31-bit prime
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    public static void main(String[] args) {
        String pat = "26535";
        String txt = "3141592653589793";

        RabinKarp searcher = new RabinKarp(pat);
        int offset = searcher.search(txt);

        // print results
        System.out.println("text:    " + txt);

        // from brute force search method 1
        System.out.print("pattern: ");
        for (int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}