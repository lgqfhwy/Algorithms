public class Matrix {
    public static double dot(double[] x, double[] y) {
        double sum = 0.0;
        for(int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }
    public static double[][] mult(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++)
                    c[i][j] += a[i][k] * b[k][j];
            }
        }
        return c;
    }
    public static double[][] transpose(double[][] a) {
        double[][] t = new double[a[0].length][a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                t[i][j] = a[j][i];
            }
        }
        return t;
    }
    public static double[] mult(double[][] a, double[] x) {
        double[] t = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            for (int k = 0; k < a[0].length; k++) {
                t[i] += a[i][k] * x[k];
            }
        }
        return t;
    }
    public static double[] mult(double[] y, double[][] a) {
        double[] t = new double[a[0].length];
        for (int i = 0; i < a[0].length; i++) {
            for (int k = 0; k < y.length; k++) {
                t[i] += y[k] * a[i][k];
            }
        }
        return t;
    }
    public static void main(String[] args) {
        double[] x = new double[10];
        double[] y = new double[10];
        for (int i = 0; i < 10; i++) {
            x[i] = 1;
            y[i] = i;
        }
        double t = dot(x, y);
        System.out.println(t);
    }
}








































