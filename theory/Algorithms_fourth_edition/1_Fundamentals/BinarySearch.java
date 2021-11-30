import java.util.Arrays;
public class BinarySearch {
    // public static int rank(int key, int[] a) {
    //     // Array must be sorted.
    //     int lo = 0;
    //     int hi = a.length - 1;
    //     //System.out.printf("%d %d %d\n", lo, hi, depth);
    //     while (lo <= hi) {
    //         // key is in a[lo..hi] or not in present.
    //         int mid = lo + (hi - lo) / 2;
    //         if (key < a[mid])   hi = mid - 1;
    //         else if (key > a[mid])  lo = mid + 1;
    //         else                    return mid;
    //     }
    //     return -1;
    // }
    public static int rank(int key, int[] a) {
        //int depth = 0;
        return rank(key, a, 0, a.length - 1);
    }

    public static int rank(int key, int[] a, int lo, int hi) {
        if (lo > hi)    return -1;
        int mid = lo + (hi - lo) / 2;
        //System.out.printf("%d %d %d\n", lo, hi, depth);
        if (key < a[mid])   return rank(key, a, lo, mid - 1);
        else if (key > a[mid])  return rank(key, a, mid + 1, hi);
        else {
            int j = mid;
            while (j - 1 >= 0 && a[j - 1] == a[mid])
                j--;
            return mid;
        }
    }
    // public static int count(int key, int[] a) {
    //     return count(key, a, 0, a.length - 1);
    // }
    // public static int count(int key, int[] a, int lo, int hi) {
    //     if (lo > hi)    return -1;
    //     int mid = lo + (hi - lo) / 2;
    //     if (key < a[mid])   return count(key, a, lo, mid - 1);
    //     else if (key > a[mid])  return count(key, a, mid + 1, hi);
    //     else {
    //         int i = 1;
    //         int j = mid + 1;
    //         while(a[j] == a[mid]) {
    //             j++;
    //             i++;
    //         }
    //         j = mid - 1;
    //         while(a[j] == a[mid]) {
    //             j--;
    //             i++;
    //         }
    //         return i;
    //     }
    // }
    public static int[] noDupes(int[] a) {
        Arrays.sort(a);
        int noDupCount = 0;
        for (int i = 0; i < a.length; i++) {
            if (i == 0 || a[i] != a[i - 1]) {
                noDupCount++;
            }
        }
        int[] a2 = new int[noDupCount];
        for (int i = 0, j = 0; i < a.length; i++) {
            if (i == 0 || a[i] != a[i - 1]) {
                a2[j++] = a[i];
            }
        }
        return a2;
    }

    public static void main(String[] args) {
        // int[] whilelist = In.readInts(args[0]);
        // //int[] a2 = noDupes(whilelist);
        // Arrays.sort(whilelist);
        // for (int i = 0; i < whilelist.length; i++) {
        //     System.out.print(whilelist[i] + " ");
        // }
        // System.out.println();
        // while (!StdIn.isEmpty()) {
        //     // Read key, print if not in whilelist.
        //     int key = StdIn.readInt();
        //     int result1 = rank(key, whilelist);
        //     int result2 = count(key, whilelist);
        //     if (result1 < 0) {
        //         StdOut.println(key);
        //     }
        //     else {
        //         StdOut.println(result1 + " " + result2);
        //     }

        // }
    }
}