public class Josephus {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int start = Integer.parseInt(args[1]);
        int m = Integer.parseInt(args[2]);
        ResizingArrayQueue<Integer> q = new ResizingArrayQueue<Integer>();
        for (int i = 0; i < n; i++)
            q.enqueue((start + i) % n);
        for (int i = 0; i < n; i++) {
            for (int k = 1; k < m; k++) {
                //StdOut.println("Hello");
                int j = q.dequeue();
                q.enqueue(j);
            }
            StdOut.print(q.dequeue() + " ");
        }
        StdOut.println();
    }
}