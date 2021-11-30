public class NewBinarySearch {
    public static int SearchWithFib(int[] a, int key){
        if (a.length < 1)
            return -1;
        if (a.length == 1) {
            if (a[0] == key)
                return 0;
            else
                return -1;
        }
        int N = a.length - 1;
        int a1 = 0;
        int a2 = 1;
        int a3 = 1;
        int cnt = 3;
        //0, 1, 1, 2, 3, 5, 
        while (a3 < N) {
            a1 = a2;
            a2 = a3;
            a3 = a1 + a2;
            cnt++;
        }
        a1 = 0;
        a2 = 1;
        a3 = 1;
        int[] fib = new int[cnt];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < cnt; i++) {
            a3 = a1 + a2;
            fib[i] = a3;
            a1 = a2;
            a2 = a3;
        }
        int k = cnt - 1;
        int start = 0;
        int end = a.length - 1;
        //0 1 2 3 4 5 6 7 8
        //0 1 1 2 3 5 8 13
        while (k > 0) {
            while ((fib[k-1] + start) > end)
                k--;          
            if (a[fib[k-1] + start] == key)
                return fib[k-1] + start;
            else if (a[fib[k-1] + start] > key) {
                end = fib[k-1];
                k = k - 1;
            }
            else {
                start = fib[k-1] + start;
                k = k - 2;
            }
        }
        return -1;
        
    }
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = SearchWithFib(a, 3);
        StdOut.println(index);
    }
}





























