public class RelativelyPrime{
    public static int gcd(int p, int q) {
        if (q == 0)    return p;
        int r = p % q;
        return gcd(q, r);
    }
    public static void main(String[] args) {
        int N = 4;
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(gcd(i, j) == 1)
                    a[i][j] = true;
                else
                    a[i][j] = false;
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.println(a[i][j]);
            }
        }
    }

}