import java.util.Arrays;

public class TwoSumFasterBest {
    public static int count(int[] a) {
        Arrays.sort(a);
        int i = 0;
        int j = a.length - 1;
        int cnt = 0;
        while (i < j) {
            int value = a[i] + a[j];
            if (value == 0) {
                cnt++;
                j--;
            }
            else if (value > 0) {
                j--;
            }
            else
                i++;
        }
        return cnt;
    }
    public static void main(String[] args) {
        
    }
}