// Alphabets. Some applications involve strings taken from a restricted alphabet. 
// In such applications, it often makes sense to use an Alphabet.java class with 
// the following API.

// A data type for alphabets, for use with string-processing code
// that must convert between an alphabet of size R and the integers
// 0 through R-1.
// Warning: supports only the basic multilingual plane (BMP), i.e,
//          Unicode characters between U+0000 and U+FFFF.

public class Alphabet {

    // The binary alphabet {0, 1}
    public static final Alphabet BINARY = new Alphabet("01");

    // The octal alphabet {0, 1, 2, 3, 4, 5, 6, 7}.
    public static final Alphabet OCTAL = new Alphabet("01234567");

    // The decimal alphabet {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}.
    public static final Alphabet DECIMAL = new Alphabet("0123456789");

    // The hexadecimal alphabet {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F}
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");

    // The DNA alphabet {A, C, T, G}.
    public static final Alphabet DNA = new Alphabet("ACGT");

    // The lowercase alphabet { a, b, c, ..., z }.
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");

    // The uppercase alphabet { A, B, C, ..., Z }.
    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    // The protein alphabet { A, C, D, E, F, G, H, I, K, L, M, N, P, Q, R, S, T, V, W, Y }.
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");

    // The base-64 alphabet (64 characters).
    public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

    // The ASCII alphabet (0-127).
    public static final Alphabet ASCII = new Alphabet(128);

    // The extended ASCII alphabet (0-255).
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);

    // The Unicode 16 alphabet (0-65,535).
    public static final Alphabet UNICODE16      = new Alphabet(65536);

    private char[] alphabet;    // the characters in the alphabet
    private int[] inverse;  // indices
    private final int R;    // the radix of the alphabet

    // Initializes a new alphabet from the given set of characters.
    public Alphabet(String alpha) {

        // check that alphabet contains no duplicate chars
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < alpha.length(); i++) {
            char c = alpha.charAt(i);
            if (unicode[c]) {
                throw new IllegalArgumentException("Illegal alphabet: repeated character = " + "'c'");
            }
            unicode[c] = true;
        }
        alphabet = alpha.toCharArray();
        R = alpha.length();
        inverse = new int[Character.MAX_VALUE];
        for (int i = 0; i < inverse.length; i++) {
            inverse[i] = -1;
        }
        // can't use char since R can be as big as 65536
        for (int c = 0; c < R; c++) {
            inverse[alphabet[c]] = c;
        }
    }

    // Initializes a new alphabet using characters 0 through R-1.
    private Alphabet(int radix) {
        this.R = radix;
        alphabet = new char[R];
        inverse = new int[R];
        // can't use char since R can be as big as 65536.
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char)i;
        }
        for (int i = 0; i < R; i++) {
            inverse[i] = i;
        }
    }

    // Initialize a new alphabet using characters 0 through 255.
    public Alphabet() {
        this(256);
    }

    // Returns true if the argument is a character in this alphabet.
    public boolean contains(char c) {
        return inverse[c] != -1;
    }

    // Returns the number of characters in this alphabet (the radix).
    @Deprecated
    public int R() {
        return R;
    }

    // Returns the number of characters in this alphabet (the radix).
    public int radix() {
        return R;
    }

    // Returns the binary logarithm of the number of characters in this alphabet.
    public int lgR() {
        int lgR = 0;
        for (int t = R - 1; t >= 1; t /= 2) {
            lgR++;
        }
        return lgR;
    }

    // Returns the index corresponding to the argument character.
    public int toIndex(char c) {
        if (c >= inverse.length || inverse[c] == -1) {
            throw new IllegalArgumentException("Character " + c + " not in alphabet");
        }
        return inverse[c];
    }

    // Returns the indices corresponding to the argument characters.
    public int[] toIndices(String s) {
        char[] source = s.toCharArray();
        int[] target = new int[s.length()];
        for (int i = 0; i < source.length; i++) {
            target[i] = toIndex(source[i]);
        }
        return target;
    }

    // Returns the character corresponging to the argument index.
    public char toChar(int index) {
        if (index < 0 || index >= R) {
            throw new IllegalArgumentException("index must be between 0 and " + R + ": " + index);
        }
        return alphabet[index];
    }

    // Returns the characters corresponging to the argument indices.
    public String toChars(int[] indices) {
        StringBuilder s = new StringBuilder(indices.length);
        for (int i = 0; i < indices.length; i++) {
            s.append(toChar(indices[i]));
        }
        return s.toString();
    }

    // Unit tests teh Alphabet data type.
    public static void main(String[] args) {
        int[]  encoded1 = Alphabet.BASE64.toIndices("NowIsTheTimeForAllGoodMen");
        String decoded1 = Alphabet.BASE64.toChars(encoded1);
        System.out.println(decoded1);
 
        int[]  encoded2 = Alphabet.DNA.toIndices("AACGAACGGTTTACCCCG");
        String decoded2 = Alphabet.DNA.toChars(encoded2);
        System.out.println(decoded2);

        int[]  encoded3 = Alphabet.DECIMAL.toIndices("01234567890123456789");
        String decoded3 = Alphabet.DECIMAL.toChars(encoded3);
        System.out.println(decoded3);
    }
}