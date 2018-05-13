import java.util.Arrays;
public class GeneralKSum {

    // Two sum: Given an array of N integers, design a algorithm to find a pair of 
    // integers whose sum is closest to zero.
    // The first way to solve two sum : sort by absolute value--the best pair is now adjacent.
    // The Second approach, which is a more general way, to solve two sum : sort by value in ascending order, using two index i , which point to
    // the start of the array, and j, which point to the end of the array. 
    // when a[i] + a[j] < k, i++; a[i] + a[j] > k, j--; a[i] + a[j] = k, the end.

    public static void generalTwoSum(int[] a, int k) {
        Arrays.sort(a);
        int n = a.length;
        int i = 0;
        int j = n - 1;
        while (i < j) {
            if (a[i] + a[j] == k) {
                System.out.println(a[i] + " + " + a[j] + " = " + (a[i] + a[j]));
                return;
            }
            else if (a[i] + a[j] < k) {
                i++;
            }
            else {
                j--;
            }
        }
    }

    public static void generalThreeSum(int[] a, int k) {
        Arrays.sort(a);
        int n = a.length;

        for (int h = 0; h < n - 2; h++) {
            int i = h + 1;
            int j = n - 1;
            while (i < j) {
                if (a[i] + a[j] + a[h] == k) {
                    System.out.println(a[i] + " + " + a[j] + " + " + a[h] + 
                                        " = " + (a[i] + a[j] + a[h]));
                    return;
                }
                else if (a[i] + a[j] + a[h] < k){
                    i++;
                }
                else {
                    j--;
                }
            }
        }
        System.out.println("Have no subarrays sum to " + k);
    }



    public static void main(String[] args) {
        int[] a = {-25, -10, -7, -3, 2, 4, 8, 10};
        generalThreeSum(a, 0);
    }
}