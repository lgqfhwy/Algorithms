// Range minimum query
// In computer science, a range minimum query (RMQ) solves the problem of finding the minimal value 
// in a sub-array of an array of comparable objects. Range minimum queries have several use cases in 
// computer science such as the lowest common ancestor problem or the longest common prefix problem (LCP).

// We have an array arr[0 . . . n-1]. We should be able to efficiently find the minimum value 
// from index qs (query start) to qe (query end) where 0 <= qs <= qe <= n-1.

// Segment tree can be used to do preprocessing and query in moderate time. 
// With segment tree, preprocessing time is O(n) and time to for range minimum 
// query is O(Logn). The extra space required is O(n) to store the segment tree.

// Representation of Segment trees
// 1. Leaf Nodes are the elements of the input array.
// 2. Each internal node represents minimum of all leaves under it.

// An array representation of tree is used to represent Segment Trees. 
// For each node at index i, the left child is at index 2*i+1, right child 
// at 2*i+2 and the parent is at [(i - 1) / 2].
public class RMQSegmentTree {

    int st[];   // array to store segment tree

    // A utility function to get minimum of two numbers.
    private int minVal(int x, int y) {
        return (x < y) ? x : y;
    }

    // A utility function to get the middle index from corner indexes.
    private int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    // Constructor to construct segment tree from given array. This 
    // constructor allocates memeory for segment tree and calls 
    // constructSTUtil() to fill the allocated memory.
    public RMQSegmentTree(int arr[], int n) {
        // Allocate memory for segment tree.
        // Height of segment tree.
        int x = (int)(Math.ceil(Math.log(n) / Math.log(2)));

        // Maximum size of segment tree
        int max_size = 2 * (int)Math.pow(2, x) - 1;

        st = new int[max_size];    // Memory allocation
        constructSTUtil(arr, 0, n - 1, 0);
    }

    // A recursive function that constructs Segment Tree for
    // array[ss..se]. si is index of current node in segment tree st.
    private int constructSTUtil(int arr[], int ss, int se, int si) {
        // If there is one element in array, store it in current node of
        // segment tree and return.
        //System.out.println("ss = " + ss + " se = " + se);
        if (ss == se) {
            st[si] = arr[ss];
            return arr[ss];
        }

        // If there are more than one elements, then recur for left and right
        // subtrees and store the minimum of two values in this node.
        int mid = getMid(ss, se);
        st[si] = minVal(constructSTUtil(arr, ss, mid, si * 2 + 1), 
            constructSTUtil(arr, mid + 1, se, si * 2 + 2));
        return st[si];
    }

    // Return minimum of elements in range from index qs (query start) to
    // qe (query end). It mainly uses RMQUtil().
    public int RMQ(int n, int qs, int qe) {
        // check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return RMQUtil(0, n - 1, qs, qe, 0);
    }


    // A recursive function to get the minimum value in a given range
    // of array indexs. The following are parameters for this function.
    // st -> Pointer to segment tree.
    // index -> Index of current node in the segment tree. Initially 
    //          0 is passed as root is always at index 0.
    // ss & se -> Starting and ending indexes of the segment represented 
    //            by current node.
    // qs & qe -> Starting and ending indexes of query range.
    public int RMQUtil(int ss, int se, int qs, int qe, int index) {
        // If segment of this node is a part of given range, then
        // return the min of the segment.
        if (qs <= ss && qe >= se) {
            return st[index];
        }
        // If segment of this node is outside the given range.
        if (se < qs || ss > qe) {
            return Integer.MAX_VALUE;
        }
        // If a part of this segment overlaps with the given range
        int mid = getMid(ss, se);
        return minVal(RMQUtil(ss, mid, qs, qe, 2 * index + 1), 
            RMQUtil(mid + 1, se, qs, qe, 2 * index + 2));
    }


    public static void main(String[] args) {
        int arr[] = {1, 3, 2, 7, 9, 11};
        int n = arr.length;
        RMQSegmentTree tree = new RMQSegmentTree(arr, n);
        int qs = 1;
        int qe = 4;
        System.out.println("Minimum of values in range [" + qs + ", " + qe
            + "] is = " + tree.RMQ(n, qs, qe));
    }


}