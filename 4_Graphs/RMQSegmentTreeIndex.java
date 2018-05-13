// Returns the index of the minimum value.
public class RMQSegmentTreeIndex {
    
    int st[];   // array of store index of segment tree
    int data[]; // array of store data

    private int minVal(int x, int y) {
        //System.out.println("x = " + x + ", y = " + y);
        if (x == -1)
            return y;
        if (y == -1)
            return x;
        return (data[x] < data[y]) ? x : y;
    }

    private int getMid(int s, int e) {
        return s + (e - s) / 2;
    }

    public RMQSegmentTreeIndex(int arr[], int n) {
        int x = (int)(Math.ceil(Math.log(n) / Math.log(2)));
        int max_size = 2 * (int)Math.pow(2, x) - 1;
        st = new int[max_size];
        data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = arr[i];
        }
        constructSTUtil(0, n - 1, 0);
    }

    private int constructSTUtil(int ss, int se, int si) {
        if (ss == se) {
            st[si] = ss;
            return ss;
        }
        int mid = getMid(ss, se);
        st[si] = minVal(constructSTUtil(ss, mid, si * 2 + 1),
                 constructSTUtil(mid + 1, se, si * 2 + 2));
        return st[si];
    }

    public int RMQIndex(int qs, int qe) {
        if (qs < 0 || qe > data.length - 1 || qs > qe) {
            System.out.println("Invalid Input");
            return -1;
        }
        return RMQUtilIndex(0, data.length - 1, qs, qe, 0);
    }


    public int RMQUtilIndex(int ss, int se, int qs, int qe, int index) {
        //System.out.println("ss = " + ss + ", se = " + se + ", index = " + index);
        if (qs <= ss && qe >= se) {
            return st[index];
        }
        if (se < qs || ss > qe) {
            return -1;
        }
        int mid = getMid(ss, se);
        return minVal(RMQUtilIndex(ss, mid, qs, qe, 2 * index + 1),
            RMQUtilIndex(mid + 1, se, qs, qe, 2 * index + 2));
    }
    
    public static void main(String[] args) {
        int arr[] = {1, 3, 2, 7, 9, 11};
        int n = arr.length;
        RMQSegmentTreeIndex rmq = new RMQSegmentTreeIndex(arr, n);
        int qs = 1;
        int qe = 4;
        System.out.println("Minimum of values int range [" + qs + ", " + qe + "] is = "
            + rmq.RMQIndex(qs, qe));
    }


}