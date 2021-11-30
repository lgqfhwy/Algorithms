public class QueueCircularLinklist<Item> {
    private Node last;
    private int N;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return N == 0;    
    }

    public int size() {
        return N;
    } 

    public void enqueue(Item item) {
        // Add item to the end of the list
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.next = last;
        }
        if(!isEmpty()) {
            Node first = oldlast.next;
            oldlast.next = last;
            last.next = first;
        }
        N++;
    }

    public Item dequeue() {
        // Remove item from the beginning of the list.
        Item item = last.next.item;
        last.next = last.next.next;
        N--;
        return item;
    }

    public static void main(String[] args) {
        // Create a queue and enqueue/dequeue strings.
        Queue<String> q = new Queue<String>();
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








































