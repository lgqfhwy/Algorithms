public class SortTime {

    public static double time(String alg, Double[] a) {
        Stopwatch sw = new Stopwatch();
        if (alg.equals("Insertion"))    Insertion.sort(a);
        // else if (alg.equals("InsertionX"))  InsertionX.sort(a);
        // else if (alg.equals("BinaryInsertion")) BinaryInsertion.sort(a);
        else if (alg.equals("Selection"))   Selection.sort(a);
        // else if (alg.equals("Bubble"))  Bubble.sort(a);
        // else if (alg.equals("Shell"))   Shell.sort(a);
        // else if (alg.equals("Merge"))   Merge.sort(a);
        // else if (alg.equals("MergeX"))  MergeX.sort(a);
        // else if (alg.equals("MergeBU")) MergeBU.sort(a);
        // else if (alg.equals("Quick"))   Quick.sort(a);
        // else if (alg.equals("Quick3way"))   Quick3way.sort(a);
        // else if (alg.equals("QuickX"))   QuickX.sort(a);
        // else if (alg.equals("Heap"))    Heap.sort(a);
        // else if (alg.equals("System"))  Arrays.sort(a);
        else 
            throw new IllegalArgumentException("Invalid algorithm: " + alg);
        return sw.elapsedTime();
    }

    // Use alg to sort trials random arrays of length n.
    public static double timeSortedInput(String alg, int n, int trials) {
        double total = 0.0;
        Double[] a = new Double[n];
        // Perform one experiment (generate and sort an array).
        for (int t = 0; t < trials; t++) {
            for (int i = 0; i < n; i++)
                a[i] = 1.0 * i;
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        double time1, time2;
        int n = 1000;
        int trials = 1000;
        String alg1 = "Selection";
        String alg2 = "Insertion";
        time1 = timeSortedInput(alg1, n, trials);
        time2 = timeSortedInput(alg2, n, trials);
        StdOut.printf("For %d random Doubles\n %s is", n, alg1);
        StdOut.printf(" %f times faster than %s\n", time2/(time1), alg2);
        //StdOut.println(time1 + "  " + time2);
    }
}














































