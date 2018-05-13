import java.util.Iterator;
import java.util.NoSuchElementException;

public class ResizingArrayDeque<Item> implements Iterable<Item> {
    private Item[] q;   // queue of number
    private int n;      // number of elements on queue
    private int left;
    private int right;

    // Initialize an empty queue.
    public ResizingArrayDeque() {
        q = (Item[]) new Object[3];
        n = 0;
        left = 1;
        right = 1;
    }

    // Is this queue empty?
    // Return true if this queue is empty, false othersize.
    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        int start = (capacity - n) / 2;
        for (int i = 0; i < n; i++) {
            temp[start + i] = q[(left + i + 1) % q.length];
        }
        q = temp;
        left = (capacity - n) / 2 - 1;
        right = (capacity + n) / 2;
    }

    public void pushLeft(Item item) {
        // Add an item to the left end
        if (n == q.length - 2)    resize(2 * q.length + 2);
        q[left] = item;
        if (left == right)
            right++;
        left--;
        if (left < 0) 
            left = q.length + left;
        n++;
    }

    public void pushRight(Item item) {
        // Add an item to the right end
        if (n == q.length - 2)    resize(2 * q.length + 2);
        q[right] = item;
        if (left == right)
            left--;
        right++;
        if (right == q.length)
            right = 0;
        n++;
        // StdOut.println("Right = " + right);
        // StdOut.println("left = " + left);
        // for (int i = 0; i < n; i++) {
        //     StdOut.print(q[(left + i + 1) % q.length] + "  ");
        // }
    }

    public Item popLeft() {
        // remove an item from the left end
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = q[(left + 1) % q.length];
        q[left] = null;
        n--;
        left = (left + 1) % q.length;
        if (n > 0 && n == q.length / 4)
            resize(q.length / 2 + 2);
        return item;
    }

    public Item popRight() {
        // remove an item from the right end
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int temp = (right - 1 + q.length) % q.length;
        // StdOut.println("right = " + right);
        // StdOut.println("temp = " + temp);
        Item item = q[temp];
        q[right] = null;
        n--;
        right = temp;
        // for (int i = 0; i < n; i++) {
        //     StdOut.print(q[(left + i + 1) % q.length] + "  ");
        // }
        if (n > 0 && n == q.length / 4)
            resize(q.length / 2 + 2);
        return item;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() {
            return i < n;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = q[(left + i + 1) % q.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        ResizingArrayDeque<String> q = new ResizingArrayDeque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                q.pushLeft(item);
                //q.pushRight(item);
            }
            else if (!q.isEmpty()) {
                StdOut.print(q.popLeft() + " ");
                //StdOut.print(q.popRight() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}












































