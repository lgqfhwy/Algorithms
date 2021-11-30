// Queue sort. Implement MSD string sorting using queues, as follows: 
// Keep one queue for each bin. On a first pass through the items to 
// be sorted, insert each item into the appropriate queue, according 
// to its leading character value. Then, sort the sublists and stitch 
// together all the queues to make a sorted whole. Note that this method 
// does not involve keeping the count[] arrays within the recursive method.
import java.util.LinkedList;
import java.util.Queue;
public class MSDWithQueue {
    private static final int CUTOFF = 15;   // cutoff to insertion sort
    private static final int R = 256;   // extended ASCII alphabet size.

    // do not instantiate
    private MSDWithQueue() { }

    // Rearranges the array extended ASCII strings in ascending order.
    public static void sort(String[] a) {
        int n = a.length;
        Queue<String>[] auxBin = new LinkedList[R + 2];
    }

    public static void main(String[] args) {
        
    }
}
