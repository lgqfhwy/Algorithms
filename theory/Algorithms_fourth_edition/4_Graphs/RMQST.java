// As in the solution above, answering queries in constant time will be achieved by pre-computing 
// results. However, the array will store precomputed min queries for all and only the ranges whose 
// size is a power of two. There are Θ(log n) such queries for each start position i, so the size 
// of the dynamic programming table B is Θ(n log n). Each element B[i, j] holds the index of the 
// minimum of the range A[i…i+2^j-1]. The table is filled with the indices of minima using the 
// recurrence：
// If A[B[i, j-1]] ≤ A[B[i+2j-1, j-1]], then B[i, j] = B[i, j-1];
// else, B[i, j] = B[i+2j-1, j-1].
// A query RMQA(l,r) can now be answered by splitting it into two separate queries: 
// one is the pre-computed query with range from l to the highest boundary smaller than r. 
// The other is the query with the same range that has r as its right boundary. 
// The overall result can be obtained in constant time because these two queries can be 
// answered in constant time and the only thing left to do is to choose the smaller of the two results.
public class RMQST {

    int min[][];    // Each element min[i][j] holds the index of the minimum of the range A[i...i + 2 ^ j - 1].

    public RMQST(int A[], int n) {
        int k = (int)(Math.log(n) / Math.log(2) + 2);
        min = new int[n][k];
        // initialize min[][] for the intervals with length 1
        for (int i = 0; i < n; i++) {
            min[i][0] = i;
        }
        // compute values from smaller to big intervals
        for (int j = 1; 1 << j <= n; j++) {
            for (int i = 0; i + (1 << j) - 1 < n; i++) {
                if (A[min[i][j - 1]] < A[min[i + (1 << (j - 1))][j - 1]]) {
                    min[i][j] = min[i][j - 1];
                }
                else {
                    min[i][j] = min[i + (1 << (j - 1))][j - 1];
                }
            }
        }
    }

    public int query(int i, int j, int A[]) {
        int k = (int)(Math.log(j - i + 1));
        if (A[min[i][k]] <= A[min[j - (int)(Math.pow(2, k)) + 1][k]]) {
            return min[i][k];
        }
        else {
            return min[j - (int)(Math.pow(2, k)) + 1][k];
        }
    }

    public static void main(String[] args) {
        int A[] = {1, 2, 3, 4, 5, 6, 7};
        RMQST rmq = new RMQST(A, A.length);
        System.out.println(rmq.query(1, 2, A));
    }
}