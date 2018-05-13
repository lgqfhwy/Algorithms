import java.util.Iterator;
public class Queue<Item> implements Iterable<Item> {
    private Node first;    // link to least recently added node.
    private Node last;     // link to most recently added node
    private int N;         // number of items on the queue

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;
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

    public void catenable(Queue<Item> q) {
        while (!q.isEmpty()) {
            Item item = q.dequeue();
            enqueue(item);
        }
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
        // Create a queue and enqueue/dequeue strings.
        // Queue<String> q = new Queue<String>();
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     if (!item.equals("-"))
        //         q.enqueue(item);
        //     else if (!q.isEmpty())
        //         StdOut.print(q.dequeue() + " ");
        // }
        // StdOut.println("(" + q.size() + " left on queue)");
        // int i = 5;
        // double a = i;
        // System.out.println(a);
        // String[] nameArray = {"ABCDEFG"};
        // for (String name: nameArray) {
        //     System.out.println(name);
        // }
    }
}



























