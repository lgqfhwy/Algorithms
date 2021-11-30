 public class fragment {
    public static String mystery(String s) {
        int N = s.length();
        if (N <= 1)    return s;
        String a = s.substring(0, N/2);
        String b = s.substring(N/2, N);
        return mystery(b) + mystery(a);
    }
    public static void main(String[] args) {
        // String string1 = "hello";
        // String string2 = string1;
        // string1 = "world";
        // StdOut.println(string1);
        // StdOut.println(string2);
        //String s = "Hello World";
        // String m = "Hello";
        // s.toUpperCase();
        // StdOut.println(s);
        // s = s.substring(6, 11);
        // StdOut.println(s);
        // int t = s.indexOf(m.charAt(0), 0);
        // System.out.println(t);
        //System.out.println(s.substring(s.length() - 2, s.length()));
        int a = 3;
        long b = a;
        System.out.println(b);
    }
 }