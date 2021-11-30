// Perfect balance. Write  a program Perfectbalance.java that inserts a set of keys into an
// initially empty BST such that the tree produced is equivalent to binary search, in the sense
// that the sequence of compares done in the search for any key in the BST is the same as the
// sequence of compares used by binary search for the same set of keys.
// Hint: put the median at the root and recursively build the left and right subtree.
import java.util.Arrays;
public class PerfectBalance {
    // precondition: a[] has no duplicates
    public static void perfect(BST bst, String[] a) {
        Arrays.sort(a);
        perfect(bst, a, 0, a.length - 1);
        StdOut.println();
    }

    // precondition: a[lo..hi] is sorted
    private static void perfect(BST bst, String[] a, int lo, int hi) {
        if (hi < lo)    return;
        int mid = lo + (hi - lo) / 2;
        bst.put(a[mid], mid);
        StdOut.println(a[mid] + " ");
        perfect(bst, a, lo, mid - 1);
        perfect(bst, a, mid + 1, hi);
    }

    public static void main(String[] args) {
        String[] str = {"word", "else", "people", "life", "live", "happy"};
        BST<String, Integer> bst = new BST<String, Integer>();
        perfect(bst, str);
    }
}