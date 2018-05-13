// We have an array arr[0 . . . n-1]. We should be able to
// 1 Find the sum of elements from index l to r where 0 <= l <= r <= n-1
// 2 Change value of a specified element of the array to a new value x. 
//   We need to do arr[i] = x where 0 <= i <= n-1.
// Representation of Segment trees
// 1. Leaf Nodes are the elements of the input array.
// 2. Each internal node represents some merging of the leaf nodes. 
//    The merging may be different for different problems. 
//    For this problem, merging is sum of leaves under a node.

// An array representation of tree is used to represent Segment Trees. 
// For each node at index i, the left child is at index 2*i+1, 
// right child at 2*i+2 and the parent is at [(i - 1)/2].
public class SegmentTree {

    int st[];   // The array that stores segment tree nodes.

    // Constructor to construct segment tree from given array. This 
    // constructor allocates memeory for segment tree and calls 
    // constructSTUtil() to fill the allocated memory.
    public SegmentTree(int arr[], int n) {
        // Allocate memory for segment tree.
        // Height of segment tree.
        int x = (int)(Math.ceil(Math.log(n) / Math.log(2)));

        // Maximum size of segment tree
        int max_size = 2 * (int)Math.pow(2, x) - 1;

        st = new int[max_size];    // Memory allocation
        constructSTUtil(arr, 0, n - 1, 0);
    }

    // Autility function to get the middle index from corner indexes.
    int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    // A recursive function that constructs segment tree for array[ss, ee].
    // si is index of current node in segment tree st.
    int constructSTUtil(int arr[], int ss, int se, int si) {
        // if there is one element in array, store it in current node of
        // segment tree and return
        if (ss == se) {
            st[si] = arr[ss];
            return arr[ss];
        }
        // If there are more than one elements, then recur for left and 
        // right subtree and store the sum of values in this node.
        int mid = getMid(ss, se);
        st[si] = constructSTUtil(arr, ss, mid, si * 2 + 1) + 
                 constructSTUtil(arr, mid + 1, se, si * 2 + 2);
        return st[si];
    }

    // A recursive function to get the sum of values in given range of
    // the array. The following are parameters for this function.
    // st -> Pointer to segment tree
    // si -> Index of current node in the segment tree. Initially 0 is
    //       passed as root is always at index 0.
    // ss & se -> Starting and ending indexes of the segment represented
    //            by current node.
    // qs & qe -> Starting and ending indexes of query range.
    int getSumUtil(int ss, int se, int qs, int qe, int si) {
        // If segment of this node is a part of given range, then return
        // the sum of the segment.
        if (qs <= ss && qe >= se) {
            return st[si];
        }
        // If segment of this node is outside the given range.
        if (se < qs || ss > qe) {
            return 0;
        }
        // If a part of this segment overlaps with the given range
        int mid = getMid(ss, se);
        return getSumUtil(ss, mid, qs, qe, 2 * si + 1) +
               getSumUtil(mid + 1, se, qs, qe, 2 * si + 2);
    }

    // A recursive function to update the nodes which have the given 
    // index in their range. The following are parameters st, si, ss
    // and se are same as getSumUtil().
    // i -> index of the element to be updated. This index is in input
    //      array.
    // diff -> Value to be added to all node which have i in range.
    void updateValueUtil(int ss, int se, int i, int diff, int si) {
        // Base Case: If the input index lies outside the range of
        // this segment.
        if (i < ss || i > se) {
            return;
        }
        // If the input index is in range of this node, then update the
        // value of the node and its children.
        st[si] = st[si] + diff;
        if (se != ss) {
            int mid = getMid(ss, se);
            updateValueUtil(ss, mid, i, diff, 2 * si + 1);
            updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
        }
    }
    // The function to update a value in input array and segment tree.
    // It uses updateValueUtil() to update the value in segment tree.
    void updateValue(int arr[], int n, int i, int new_val) {
        // Check for erroneous input index
        if (i < 0 || i > n - 1) {
            System.out.println("Invalid Input");
            return;
        }

        // Get the difference between new value and old value
        int diff = new_val - arr[i];

        // Update the value in array.
        arr[i] = new_val;

        // Update the value of nodes in segment tree.
        updateValueUtil(0, n -1, i, diff, 0);
    }

    // Return sum of elements in range from index qs (query start) to
    // qe (query end). It mainly uses getSumUtil().
    int getSum(int n, int qs, int qe) {
        // check for erroneous input values
        if (qs < 0 || qe > n - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return getSumUtil(0, n - 1, qs, qe, 0);
    }

    public static void main(String[] args) {
        int arr[] = {1, 3, 5, 7, 9, 11};
        int n = arr.length;

        // Build segment tree from given array
        SegmentTree tree = new SegmentTree(arr, n);

        // Print sum of values in array from index 1 to 3.
        System.out.println("Sum of values in given range = " + tree.getSum(n, 1, 3));

        // Update: set arr[1] = 10 and update corresponding segment tree nodes.
        tree.updateValue(arr, n, 1, 10);

        // Find sum after the value is updated
        System.out.println("Updated sum of values in given range = " + tree.getSum(n, 1, 3));
    }

}