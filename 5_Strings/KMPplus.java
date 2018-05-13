// Knuth-Morris-Pratt algorithm over UNICODE alphabet.
public class KMPplus {

    private String pattern;
    private int[] next;

    // create Knuth-Morris-Pratt NFA from pattern.
    public KMPplus(String pattern) {
        this.pattern = pattern;
        int m = pattern.length();
        next = new int[m];
        int j = -1;
        for (int i = 0; i < m; i++) {
            if (i == 0) {
                next[i] = -1;
            } else if (pattern.charAt(i) != pattern.charAt(j)) {
                next[i] = j;
            } else {
                next[i] = next[j];
            }
            while (j >= 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
        }
        for (int i = 0; i < m; i++) {
            System.out.println("next[" + i + "] = " + next[i]);
        }
    }

    // return offset of first occurrence of text in pattern (or n if no match)
    // simulate the NFA to find match.
    public int search(String text) {
        int m = pattern.length();
        int n = text.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
            System.out.println("j = " + j + "  i = " + i);
        }
        if (j == m) {
            return i - m;
        }
        return n;
    }

    // Add to our brute-force implementation of substring search a count() method to
    // count occurrences and a searchAll() method to print all occurrences.
    public int count(String text) {
        int m = pattern.length();
        int n = text.length();
        int countNum = 0;
        int i, j;
        for (i = 0, j = 0; i < n; i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
            if (j == m) {
                countNum++;
                j = 0;
            }
        }
        return countNum;
    }



    public static void main(String[] args) {
        String pattern = "ababc";
        String text = "abababcababc";
        int m = pattern.length();
        int n = text.length();
        // substring search
        KMPplus kmp = new KMPplus(pattern);
        int offset = kmp.search(text);
        // print results
        System.out.println("m = " + m + ", n = " + n);
        System.out.println("text:   " + text);
        System.out.print("pattern:");
        for (int i = 0; i < offset; i++) {
            System.out.print(" ");
        }
        System.out.println(pattern);
        System.out.println(kmp.count(text));
    }
}