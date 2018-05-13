public class Fibonacci {
    public static long F(int N) {
        if (N == 0)    return 0;
        if (N == 1)    return 1;
        return F(N - 1) + F(N - 2);
    }
    public static long F2(int N) {
        if (N == 0)    return 0;
        if (N == 1)    return 1;
        long a1 = 0;
        long a2 = 1;
        long a3 = 0;
        int i = 0;
        while (i < N) {
            a3 = a1 + a2;
            a1 = a2;
            a2 = a3;
            i++;
        }
        return a3;
    }
    public static long F3(long[] b, int N) {
        if (N == 0)    return 0;
        if (N == 1)    return 1;
        if (b[N] == 0) {
            b[N] = F3(b, N - 1) + F3(b, N - 2);
            return b[N];
        }
        return b[N];

    }
    public static void main(String[] args) {
        long[] b = new long[101];
        b[1] = 0;
        for (int N = 0; N < 100; N++) {
            System.out.println(N + " " + F3(b, N));
        }
    }
}