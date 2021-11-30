public class Exercises1_1 {
    public static int lg(int N) {
        int n = 1;
        int i = 0;
        while (n <= N / 2.0) {
            n *= 2;
            i++;
        }
        return i;

    }
    public static int[] histogrm(int[] a, int M) {
        int[] b = new int[M];
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= M) {
                continue;
            }
            b[a[i]]++;
        }
        return b;
    }
    public static String exR1(int n) {
        if (n <= 0)  return "";
        return exR1(n - 3) + n + exR1(n - 2) + n;
    }
    public static int mystery1(int a, int b) {
        if (b == 0)    return 0;
        if (b % 2 == 0)    return mystery1(a + a, b / 2);
        return mystery1(a + a, b / 2) + a;
    }
    public static int mystery2(int a, int b) {
        if (b == 0)    return 1;
        if (b % 2 == 0)    return mystery2(a * a, b / 2);
        return mystery2(a * a, b / 2) * a;
    }
    public static double ln(int N) {
        if(N == 1)  return 0;
        return Math.log(N) + ln(N - 1);
    }
    public static void main(String[] args) {
        //1.1.1
        // double a = (0 + 15) / 2;
        // System.out.println(a);

        // 1.1.3
        // int a = Integer.parseInt(args[0]);
        // int b = Integer.parseInt(args[1]);
        // int c = Integer.parseInt(args[2]);
        // if (a == b && b == c) {
        //     System.out.println("Equal");
        // }
        // else {
        //     System.out.println("Not equal");
        // }

        //1.1.5
        // double x = Double.parseDouble(args[0]);
        // double y = Double.parseDouble(args[1]);
        // System.out.println(x >= 0 && x <= 1 && y >= 0 && y <= 1);

        //1.1.6
        // int f = 0;
        // int g = 1;
        // for (int i = 0; i <= 15; i++) {
        //     StdOut.println(f);
        //     f = f + g;
        //     g = f - g;
        // }
        // double t = 9.0;
        // while (Math.abs(t - 9.0/t) > 0.001)
        //     t = (9.0/t + t) / 2.0;
        // StdOut.printf("%.5f\n", t);
        // int sum = 0;
        // for (int i = 1; i < 1000; i++) {
        //     for (int j = 0; j < i; j++) {
        //         sum++;
        //     }
        // }
        // StdOut.println(sum);
        // int N = 10;
        // int sum = 0;
        // for (int i = 1; i < 1000; i *= 2) {
        //     for (int j = 0; j < N; j++) {
        //         sum++;
        //     }
        // }
        // StdOut.println(sum);
        // System.out.println('b');
        // System.out.println('b' + 'c');
        // System.out.println("bcd" + ' ' + 'c');
        // System.out.println((char)('a' + 4));
        // int N = Integer.parseInt(args[0]);
        // System.out.println(Integer.toBinaryString(N));
        // String s = "";
        // for (int n = N; n > 0; n /= 2) {
        //     s = (n % 2) + s;
        // }
        // System.out.println(s);
        // int[] a = new int[10];
        // for (int i = 0; i < 10; i++) {
        //     a[i] = i * i;
        // }
        // int[] a = new int[10];
        // for (int i = 0; i < 10; i++) {
        //     a[i] = 9 - i;
        // }
        // for (int i = 0; i < 10; i++) {
        //     a[i] = a[a[i]];
        // }
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(i);
        // }
        //ystem.out.println(lg(1025));
        // int[] a = new int[10];
        // for (int i = 0; i < 10; i++) {
        //     a[i] = i;
        // }
        // int[] b;
        // b = histogrm(a, 10);
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(b[i]);
        // }
        //System.out.println(exR1(6));
        // System.out.println(mystery(2, 25));
        // System.out.println(mystery(3, 11));
        //System.out.println(mystery2(2, 10));
        System.out.println(ln(10));
    }
}






























