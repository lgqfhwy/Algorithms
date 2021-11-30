/******************************************************************************
 *  Immutable data type for US phone numbers, but with a broken
 *  covariant implementation of equals(). By implementing equals()
 *  with the signature
 *
 *       public boolean equals(CovariantPhoneNumber that)
 *
 *  we do not override the equals() method inherited from Object.
 *  This causes unexpected behavior when used with java.util.HashSet.
 *
 *  DO NOT USE THIS CLASS
 *
 ******************************************************************************/
import java.util.HashSet;
public final class CovariantPhoneNumber {
    private final int area;    // area code (3 digits)
    private final int exch;    // exchange (3 digits)
    private final int ext;     // extension (4 digits)

    public CovariantPhoneNumber(int area, int exch, int ext) {
        this.area = area;
        this.exch = exch;
        this.ext = ext;
    }

    // covariant equals - don't do this
    public boolean equals(CovariantPhoneNumber that) {
        return (this.area == that.area) && (this.exch == that.exch) && (this.ext == that.ext);
    }

    // satisfies the hashCode contract
    public int hashCode() {
        return 10007 * (area + 1009 * exch) + ext;
    }

    // 0 for padding with leading 0s.
    public String toString() {
        return String.format("(%03d) %03d-%04d", area, exch, ext);
    }

    public static void main(String[] args) {
        CovariantPhoneNumber a = new CovariantPhoneNumber(609, 258, 4455);
        CovariantPhoneNumber b = new CovariantPhoneNumber(609, 876, 5309);
        CovariantPhoneNumber c = new CovariantPhoneNumber(609, 203, 5309);
        CovariantPhoneNumber d = new CovariantPhoneNumber(215, 876, 5309);
        CovariantPhoneNumber e = new CovariantPhoneNumber(609, 876, 5309);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("c = " + c);
        StdOut.println("d = " + d);
        StdOut.println("e = " + e);
        
        // show broken behavior when you use covariant equals with a Set
        HashSet<CovariantPhoneNumber> set = new HashSet<CovariantPhoneNumber>();
        set.add(a);
        set.add(b);
        set.add(c);
        StdOut.println("Added a, b, and c");
        StdOut.println("contains a:  " + set.contains(a));
        StdOut.println("contains b:  " + set.contains(b));
        StdOut.println("contains c:  " + set.contains(c));
        StdOut.println("contains d:  " + set.contains(d));
        StdOut.println("***contains e:  " + set.contains(e));  // not in set, but it should be!
        StdOut.println("b == e:      " + (b == e));
        StdOut.println("b.equals(e): " + (b.equals(e)));
    }
}