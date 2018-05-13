import java.util.Arrays;
public class ClosetPair {
    public static void difference(double[] a) {
        Arrays.sort(a);
        int N = a.length;
        double diff = Math.abs(a[1] - a[0]);
        int index = 1;
        for (int i = 2; i < N; i++) {
            if (Math.abs(a[i] - a[i - 1]) < diff) {
                diff = a[i] - a[i - 1];
                index = i;
            }
        }
        StdOut.println(diff + " " + a[index] + " " + a[index - 1]);
    }
    public static void main(String[] args) {
        
    }
}