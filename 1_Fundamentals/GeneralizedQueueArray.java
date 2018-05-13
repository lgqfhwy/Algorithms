import java.util.NoSuchElementException;

public class GeneralizedQueueArray<Item> {
    private Item[] q;
    private int n;
    private int first;
    private int last;

    public GeneralizedQueueArray() {
        q = (Item[]) new Object[2];
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

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) 
            temp[i] = q[(first + i) % q.length];
        q = temp;
        first = 0;
        last = n;
    }

    public void insert(Item x) {
        if (n == q.length)    resize(2 * q.length);
        q[last++] = x;
        if (last == q.length)
            last = 0;
        n++;
    }

    public Item delete(int k) {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = q[(first + k - 1) % q.length];
        for (int i = k - 1; i > 0; i--) {
            q[(first + i) % q.length] = q[(first + i - 1) % q.length];
        }
        q[first] = null;
        first++;
        if (first == q.length)
            first = 0;
        n--;
        if (n > 0 && n == q.length / 4)    resize(q.length / 2);
        return item;
    }

    public static void main(String[] args) {
        GeneralizedQueueArray<String> q = new GeneralizedQueueArray<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                q.insert(item);
            else if (!q.isEmpty())
                StdOut.print(q.delete(2) + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}



































