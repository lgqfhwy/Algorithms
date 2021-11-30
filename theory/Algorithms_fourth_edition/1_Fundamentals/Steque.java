import java.util.Iterator;
public class Steque<Item> implements Iterable<Item> {
    private Node first;    // link to least recently added node.
    private Node last;     // link to most recently added node.
    private int N;         // number of items on the queue

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        // Add item to the end of the list.
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        N++;
    }

    public void push(Item item) {
        // Add item to the beginning of the list.
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item pop() {
        // Remove item from beginning of the list.
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Item dequeue() {
        // Remove item from the beginning of the list.
        Item item = first.item;
        first = first.next;
        if (isEmpty())
            last = null;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
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
        Steque<String> q = new Steque<String>();
        while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                //q.enqueue(item);
                q.push(item);
            }
            else if (!q.isEmpty()) {
                StdOut.print(q.pop() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }


}



























