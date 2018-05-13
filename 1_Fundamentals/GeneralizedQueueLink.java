import java.util.NoSuchElementException;

public class GeneralizedQueueLink<Item> {
    private int n = 0;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(Item x) {
        Node oldlast = last;
        last = new Node();
        last.item = x;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        n++;
    }

    public Item delete(int k) {
        if (k == 1) {
            Item item = first.item;
            first = first.next;
            n--;
            return item;
        }
        int i = 1;
        Node point1 = first;
        Node point2 = first;
        while (i < k) {
            if (i != 1)
                point1 = point1.next;
            i++;
            point2 = point2.next;
            if (point2 == null)
                break;
        }
        if (i < k) {
            throw new NoSuchElementException();
        }
        else {
            Item item = point2.item;
            point1.next = point2.next;
            n--;
            return item;
        }

    }

    public static void main(String[] args) {
        GeneralizedQueueLink<String> q = new GeneralizedQueueLink<String>();
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











































