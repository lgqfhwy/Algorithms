import java.util.Arrays;
public class StaticsSETofInts {
    private int[] a;
    public StaticsSETofInts(int[] keys) {
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i];    //defensive copy
        Arrays.sort(a);
    }
    public boolean contains(int key) {
        return rank(key) != -1;
    }
    public int rank(int key) {
        // Binary search.
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // key is in a[lo..hi] or not present
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid])   hi = mid - 1;
            else if (key > a[mid])   lo = mid + 1;
            else    return mid;
        }
        return -1;
    }

    public int howMany(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid])   hi = mid - 1;
            else if (key > a[mid])   lo = mid + 1;
            else  {
                int count = 1;
                int j = mid;
                while (j - 1 >= 0 && a[j - 1] == a[mid]) {
                    j--;
                    count++;
                }
                return count;
            }
        }
        return -1;
    }
}





