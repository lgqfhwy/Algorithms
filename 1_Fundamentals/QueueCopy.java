import java.util.Iterator;
public class QueueCopy<Item> implements Iterable<Item> {
    private Node first;    // link to least recently added node.
    private Node last;     // link to most recently added node
    private int N;         // number of items on the queue

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;

        Node() { }

        //Recursive solution
        // Node(Node x) {
        //     item = x.item;
        //     if (x.next != null)
        //         next = new Node(x.next);
        // }

        // Nonrecursive solution
        Node(Node x) {
            this.item = x.item;
            this.next = x.next;
        }
    }



    public QueueCopy() {
        N = 0;
        first = null;
        last = null;
    }

    // Recursive solution
    // public QueueCopy(QueueCopy<Item> q) {
    //     first = new Node(q.first);
    // }

    // Nonrecursive solution
    public QueueCopy(QueueCopy<Item> q) {
        N = q.size();
        if (q.first != null) {
            first = new Node(q.first);
            for (Node x = first; x.next != null; x = x.next)
                x.next = new Node(x.next);
        }
    }

    public boolean isEmpty() {
        return first == null;       // Or: N == 0.
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

    public  Node reverseIterative(Node x) {
        Node first = x;
        Node reverse = null;
        while (first != null) {
            Node second = first.next;
            first.next = reverse;
            reverse = first;
            first = second;
        }
        return reverse;
    }

    public  Node reverseRecursive(Node first) {
        if (first == null)    return null;
        if (first.next == null)    return first;
        Node second = first.next;
        Node rest = reverseRecursive(second);
        second.next = first;
        first.next = null;
        return rest;
    }

    public static void main(String[] args) {
        QueueCopy<Integer> q = new QueueCopy<Integer>();
        for (int i = 0; i < 10; i++)
            q.enqueue(i);
        QueueCopy<Integer> r = new QueueCopy<Integer>(q);
        StdOut.println(r.size());
        for (int i = 0; i < 10; i++)
            StdOut.print(r.dequeue() + " ");
        StdOut.println();
        StdOut.println(q.size());
        StdOut.println();
    }
}




































































