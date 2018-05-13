import java.util.Arrays;

public class TwoSumFaster {
    public static int findboth(int[] a, int[] b) {
        int cnt = 0;
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                i++;
            else if (a[i] > b[j])
                j++;
            else {
                cnt++;
                i++;
                j++;
            }
        }
        return cnt;
    }
    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int cnt = 0;
        int length = 0;
        for (int i = 0; i < N; i++) {
            if (a[i] > 0) {
                break;
            }
            length++;
        }
        if (length == 0 || length == N)
            return 0;
        int[] a1 = new int[length];
        int[] a2 = new int[N - length];
        for (int i = 0; i < length; i++)
            a1[i] = -a[i];
        for (int i = length; i < N; i++)
            a2[i - length] = a[i];
        cnt = findboth(a1, a2);
        return cnt;
    }
    public static void main(String[] args) {
        
    }
}






























