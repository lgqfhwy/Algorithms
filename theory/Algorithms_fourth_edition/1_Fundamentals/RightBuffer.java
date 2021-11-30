
public class RightBuffer<Item> {
    private Item[] q;
    private int n;
    private int first;
    private int last;

    public RightBuffer(int size) {
        q = (Item[]) new Object[size];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (n < q.length) {
            q[last++] = item;
            if (last == q.length)    last = 0;
            n++;
        }
    }

    public Item dequeue() {
        if (!isEmpty()) {
            Item item = q[first];
            q[first] = null;
            first++;
            if (first == q.length)    first = 0;
            n--;
            return item;
        }
        return null;
    }

    public static void main(String[] args) {
        RightBuffer<String> q = new RightBuffer<String>(5);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                q.enqueue(item);
            else if (!q.isEmpty())
                StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}






































