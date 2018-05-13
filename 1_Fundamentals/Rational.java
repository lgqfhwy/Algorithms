public class Rational {
    private final long num;
    private final long den;

    public Rational(long numerator, long denominator) {
        if (Euclid(numerator, denominator) != 1)
            throw new IllegalArgumentException("Invalid rational");
        num = numerator;
        den = denominator;
    }

    public long Euclid(long p, long q) {
        if (q == 0)    return p;
        long r = p % q;
        return Euclid(q, r);
    }

    public Rational plus(Rational b) {
        long m = b.den * den;
        long n = b.den * num + b.num * den;
        long factor = Euclid(n, m);
        return new Rational(n / factor, m / factor);
    }

    public Rational minus(Rational b) {
        assert (b.den < Long.MAX_VALUE && b.num < Long.MAX_VALUE):
            "overflow";
        long m = b.den * den;
        long n = num * b.den - b.num * den;
        long factor = Euclid(n, m);
        return new Rational(n / factor, m / factor);
    }

    public Rational times(Rational b) {
        long m = b.den * den;
        long n = num * b.num;
        long factor = Euclid(n, m);
        return new Rational(n / factor, m / factor);
    }

    public Rational divides(Rational b) {
        long m = den * b.num;
        long n = num * b.den;
        long factor = Euclid(n, m);
        return new Rational(n / factor, m / factor);
    }

    public boolean equals(Rational that) {
        if (num == that.num && den == that.den)    
            return true;
        return false;
    }

    public String toString() {
        return String.format("%d / %d", num, den);
    }

    public static void main(String[] args) {
        Rational a = new Rational(2, 3);
        Rational b = new Rational(5, 6);
        System.out.println(a.plus(b));
        System.out.println(a.minus(b));
        System.out.println(a.times(b));
        System.out.println(a.divides(b));
        System.out.println(a.equals(b));
    }
}


































