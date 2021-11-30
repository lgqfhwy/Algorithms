import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
    private Node left;    // link to least recently added node.
    private Node right;     // link to most recently added node.
    private int N;         // number of items on the queue

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return left == null;
    }

    public int size() {
        return N;
    }

    public void pushLeft(Item item) {
        // Add an item to the left end
        Node oldleft = left;
        left = new Node();
        left.item = item;
        left.next = oldleft;
        if (isEmpty())
            right = left;
        N++;
    }

    public void pushRight(Item item) {
        // Add an item to the right end
        Node oldright = right;
        right = new Node();
        right.item = item;
        right.next = null;
        if (isEmpty())
            left = right;
        else
            oldright.next = right;
        N++;
    }

    public Item popLeft() {
        // remove an item from the left end
        Item item = left.item;
        left = left.next;
        N--;
        return item;
    }

    public Item popRight() {
        // remove an item from the right end
        Item item = right.item;
        if (size() == 1) {
            left = null;
            right = null;
        }
        else {
            Node point = left;
            while (point.next.next != null) 
                point = point.next;
            right = point;
        }
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = left;
        public boolean hasNext() {
            return current != null;
        }
        public void remove() { }
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                //q.pushLeft(item);
                q.pushRight(item);
            }
            else if (!q.isEmpty()) {
                //StdOut.print(q.popLeft() + " ");
                StdOut.print(q.popRight() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }

}













































