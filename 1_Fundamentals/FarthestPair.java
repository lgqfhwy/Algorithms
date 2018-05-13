public class FarthestPair{
    public static void farthet(double[] a) {
        double max = a[0];
        int maxi = 0;
        double min = a[0];
        int mini = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                maxi = i;
            }
            if (a[i] < min) {
                min = a[i];
                mini = i;
            }
        }
        StdOut.println("Farthest Pair is " + max + " " + min);
    }
    public static void main(String[] args) {
        
    }
}