import java.util.Iterator;
import java.util.NoSuchElementException;
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q;     // queue elements
    private int n = 0;    // number of elements on queue
    private int first = 0;    // index of first element of queue
    private int last = 0;     // index of next available slot


    // Initialize an empty queue.
    public ResizingArrayQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    // Is this queue empty?
    // Return true if this queue is empty, false othersize.
    public boolean isEmpty() {
        return n == 0;
    }

    // Return the number of items in this queue.
    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    // Adds the item to this queue.
    // @param item the item to end
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array.
        // double size of array if necessaary
        if (n == q.length)    resize(2 * q.length);    
        q[last++] = item;                           // add item
        if (last == q.length)    last = 0;          // wrap-arround
        n++;
    }

    // Removes and returns the item on this queue that was least recently added.
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;        // to avoid loitering
        n--;
        first++;
        if (first == q.length)    first = 0;
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4)    resize(q.length / 2);
        return item;
    }

    public Item randomDequeFirst() {
        // Random remove and return the item on this queue that was least 
        // recently added.
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int j = (int)(Math.random() * (n - 1));
        j = (j + first) % q.length;
        Item temp = q[first];
        q[first] = q[j];
        q[j] = temp;
        Item item = q[first];
        q[first] = null;
        first++;
        n--;
        if (first == q.length)    first = 0;
        if (n > 0 && n == q.length / 4)    resize(q.length / 2);
        return item;
    }

    public Item randomDequeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int j = (int)(Math.random() * (n - 1));
        j = (j + first) % q.length;
        Item temp = q[last - 1];
        q[last - 1] = q[j];
        q[j] = temp;
        Item item = q[last - 1];
        q[last] = null;
        last--;
        n--;
        if (last == -1)    last = q.length - 1;
        if (n > 0 && n == q.length / 4)    resize(q.length / 2);
        return item;
    }

    // Return the item least recently added to this queue.
    public Item peek() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        return q[first];
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");
        int j = (int)(Math.random() * (n - 1));
        j = (j + first) % q.length;
        return q[j];
    }

    public Item[] shuffle(Item[] m) {
        Item[] b = (Item[]) new Object[n];
        for (int i = 0; i < n; i++) {
            b[i] = m[(first + i) % q.length];
            //StdOut.print("##" + b[i] + "  ");
        }
        for (int i = 0; i < n; i++) {
            int j = (int)(Math.random() * (n - 1 - i));
            j = (j + i + first) % q.length;
            Item temp = b[(first + i) % q.length];
            b[(first + i) % q.length] = b[j];
            b[j] = temp;
        }
        return b;
    }

    // Returns an iterator that iterates over the items  in this queue in FIFO order.
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // An iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] b = shuffle(q);
        public boolean hasNext() {
            return i < n;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = b[i];
            i++;
            return item;
        }
    }

    // Unit tests the data code.
    public static void main(String[] args) {
        //int a = Integer.parseInt(args[0]);
        // ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        // int count = 0;
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
            // if (!item.equals("-"))
            //     queue.enqueue(item);
            // else if (!queue.isEmpty())
            //     StdOut.print(queue.dequeue() + " ");
        //     queue.enqueue(item);
        //     count++;
        //     if (count == a) {
        //         StdOut.println(item);
        //     }
        // }
        //StdOut.println("(" + queue.size() + " left on queue)");
        ResizingArrayQueue<Integer> queue = new ResizingArrayQueue<Integer>();
        for (int i = 0; i < 13; i++)
            queue.enqueue(i);
        // for (int i = 0; i < 13; i++)
        //     StdOut.print(q.randomDequeFirst() + " ");
        // StdOut.println();
        // for (int i = 0; i < 13; i++)
        //     q.enqueue(i);
        // for (int i = 0; i < 13; i++)
        //     StdOut.print(q.randomDequeLast() + " ");
        // StdOut.println();
        for (int i : queue) {
            StdOut.print(i + "  ");
        }
        StdOut.println();

    }
}


















































