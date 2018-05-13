import java.util.Arrays;
import java.util.Comparator;

// The Transaction class is an immutable data type to encapsulate a
// commercial transaction with a customer name, date, and amount.

public class Transaction implements Comparable<Transaction>{
    private final String who;   // customer
    private final Date when;    // date
    private final double amount;    // amount

    // Initialize a new transaction from the given arguments
    public Transaction(String who, Date when, double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    // Initializes a new transaction by parsing a string of the form NAME DATE AMOUNT.
    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");
        who = a[0];
        when = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
    }

    // Return the date of this transaction
    public Date when() {
        return when;
    }

    // Return the amount of this transaction
    public double amount() {
        return amount;
    }

    // Returns a string representation of this transaction
    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }
    /*
    // Compares two transactions by amount.
    public int compareTo(Transaction that) {
        return Double.compare(this.amount, that.amount);
    }
    */
    public int compareTo(Transaction that) {
        if (this.amount > that.amount)  return +1;
        if (this.amount < that.amount)  return -1;
        return 0;
    }



    // Compares this transcation to the specified object.
    @Override
    public boolean equals(Object other) {
        if (other == this)   return true;
        if (other == null)   return false;
        if (other.getClass() != this.getClass())    return false;
        Transaction that = (Transaction) other;
        return (this.amount == this.amount) && (this.who.equals(that.who))
                                            && (this.when.equals(that.when));
    }

    // Returns a hash code for this transaction.
    public int hashCode() {
        System.out.println("Hello");
        int hash = 1;
        hash = 31 * hash + who.hashCode();
        System.out.println("##" + who.hashCode());
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
    }

    public static Transaction[] readTransactions() {
        Queue<Transaction> q = new Queue<Transaction>();
        while(!StdIn.isEmpty()) {
            String[] fields = new String[3];
            fields[0] = StdIn.readString();
            fields[1] = StdIn.readString();
            fields[2] = StdIn.readString();
            String who = fields[0];
            Date when = new Date(fields[1]);
            double amount = Double.parseDouble(fields[2]);
            Transaction thing = new Transaction(who, when, amount);
            q.enqueue(thing);
        }
        int n = q.size();
        Transaction[] a = new Transaction[n];
        for (int i = 0; i < n; i++) {
            a[i] = q.dequeue();
        }
        return a;
    }

    public static void main(String[] args) {
        // Transaction a = new Transaction("Turing 6/17/1990 644.08");
        // System.out.println(a.hashCode());
        Transaction[] a = readTransactions();
        Shell.sort(a);
        for (Transaction t : a)
            StdOut.println(t);
    }


}



































