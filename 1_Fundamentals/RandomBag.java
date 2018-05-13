import java.util.Iterator;
public class RandomBag<Item> implements Iterable<Item> {
    private Item[] a = (Item[]) new Object[1];
    private int N = 0;

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) 
            temp[i] = a[i];
        a = temp;
    }

    public void add(Item item) {
        if (N == a.length)    resize(2 * a.length);
        a[N++] = item;
    }

    public  Item[] shuffle1(Item[] b) {
        for (int i = 0; i < N; i++) {
            int j = i + (int)(Math.random() * (N - 1 - i));
            Item temp = b[i];
            b[i] = b[j];
            b[j] = temp;
        }
        return b;
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = N;
        private Item[] h = shuffle1(a);

        //shuffle1(a);
        public boolean hasNext() {
            return i > 0;
        }
        public Item next() {
            //shuffle1(a);
            return a[--i];
        }

        public void remove() { }


    }

    public static void main(String[] args) {
        RandomBag<Integer> b = new RandomBag<Integer>();
        for (int i = 0; i < 10; i++)
            b.add(i);
        for (int i: b)
            StdOut.print(i + " ");
        StdOut.println();
    }




}






























