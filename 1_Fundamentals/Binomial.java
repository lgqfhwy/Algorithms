public class Binomial {
    public static double binomial(int N, int k, double p) {
        if((N == 0) || (k < 0))    return 1.0;
        return (1.0 - p) * binomial(N - 1, k) + p * binomial(N - 1, k - 1);
    }
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        double c = binomial(a, b, 0.5);
        System.out.println(c);
    }   
}
