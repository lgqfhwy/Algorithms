import java.util.Arrays;

public class ThreeSumFasterBest {
    public static int count(int[] a) {
        Arrays.sort(a);
        int cnt = 0;
        for (int i = 0; i < a.length - 3; i++) {
            int j = i + 1;
            int k = a.length - 1;
            while (j < k) {
                int value = a[i] + a[j] + a[k];
                if (value == 0) {
                    cnt++;
                    k--;
                }
                else if (value > 0) {
                    k--;
                }
                else
                    j++;
            }
        }
        return cnt;
    }
    public static void main(String[] args) {
        
    }
}