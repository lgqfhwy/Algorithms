// Interval 1D data type. Write three static comparators for Interval1D.java, one that 
// compares intervals by their left endpoint, one that compares intervals by their
// right endpoint, and one that compares intervals by their length.

import java.util.Arrays;
import java.util.Comparator;

// The Interval1D class represents a one-dimensional interval.
// The interval is closed -- it contains both endpoints.
// Intervals are immutable: their values cannot be changed after they are created.
// The class Interval1D includes methods for checking whether an interval
// contains a point and determining  whether two intervals intersect.

public class Interval1D {
    
    // Compares two intervals by max endpoint.
    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = new MinEndpointComparator();
    // Compares two intervals by max endpoint.
    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = new MaxEndpointComparator();
    // Compares two intervals by length.
    public static final Comparator<Interval1D> LENGTH_ORDER = new LengthComparator();

    private final double min;
    private final double max;

    // Initializes a closed interval [min, max].
    public Interval1D(double min, double max) {
        if (Double.isInfinite(min) || Double.isInfinite(max))
            throw new IllegalArgumentException("Endpoint must be finite");
        if (Double.isNaN(min) || Double.isNaN(max))
            throw new IllegalArgumentException("Endpoint cannot be NaN");
        
        // convert -0.0 to +0.0
        if (min == 0.0)    min = 0.0;
        if (max == 0.0)    max = 0.0;

        if (min <= max) {
            this.min = min;
            this.max = max;
        }
        else throw new IllegalArgumentException("Illegal interval");
    }

    // Returns the left endpoint of this interval.
    @Deprecated
    public double left() {
        return min;
    }

    // Returns the right endpoint of this interval.
    @Deprecated
    public double right() {
        return max;
    }

    // Returns the min endpoint of this interval.
    public double min() {
        return min;
    }

    // Returns the max endpoint of this interval.
    public double max() {
        return max;
    }

    // Returns true if this interval intersects the argument interval.
    // false otherwise.
    public boolean intersects(Interval1D that) {
        if (this.max < that.min)    return false;
        if (that.max < this.min)    return false;
        return true;
    }

    // Returns true if this interval contains the value x;
    // false otherwise.
    public boolean contains(double x) {
        return (min <= x) && (x <= max);
    }

    // Returns the length of this interval.
    public double length() {
        return max - min;
    }

    // Returns a string representation of this interval in the form [min, max].
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    // Compares this transaction to the other interval
    // Return true if this interval equals the other intrval;
    // false otherwise.
    public boolean equals(Object other) {
        if (other == this)     return true;
        if (other == null)     return false;
        if (other.getClass() != this.getClass())    return false;
        Interval1D that = (Interval1D) other;
        return this.min == that.min && this.max == that.max;
    }

    // Returns an integer hash code for this interval.
    public int hashCode() {
        int hash1 = ((Double) min).hashCode();
        int hash2 = ((Double) max).hashCode();
        return 31 * hash1 + hash2;
    }

    // ascending order of min endpoint, breaking ties by max endpoint.
    private static class MinEndpointComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            if      (a.min < b.min)     return -1;
            else if (a.min > b.min)     return +1;
            else if (a.max < b.max)     return -1;
            else if (a.max > b.max)     return +1;
            else                        return 0;
        }
    }

    // ascending order of max endpoint, breaking ties by min endpoint
    private static class MaxEndpointComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            if      (a.max < b.max)     return -1;
            else if (a.max > b.max)     return +1;
            else if (a.min < b.min)     return -1;
            else if (a.min > b.min)     return +1;
            else                        return 0;
        }
    }

    // ascending order of length
    private static class LengthComparator implements Comparator<Interval1D> {
        public int compare(Interval1D a, Interval1D b) {
            double alen = a.length();
            double blen = b.length();
            if      (alen < blen)       return -1;
            else if (alen > blen)       return +1;
            else                        return 0;
        }
    }

    // Unit tests the Interval1D data type.
    public static void main(String[] args) {
        Interval1D[] intervals = new Interval1D[4];
        intervals[0] = new Interval1D(15.0, 33.0);
        intervals[1] = new Interval1D(45.0, 60.0);
        intervals[2] = new Interval1D(20.0, 70.0);
        intervals[3] = new Interval1D(46.0, 55.0);

        StdOut.println("Unsorted");
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by min endpoint");
        Arrays.sort(intervals, Interval1D.MIN_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by max endpoint");
        Arrays.sort(intervals, Interval1D.MAX_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();

        StdOut.println("Sort by length");
        Arrays.sort(intervals, Interval1D.LENGTH_ORDER);
        for (int i = 0; i < intervals.length; i++)
            StdOut.println(intervals[i]);
        StdOut.println();
    }

}