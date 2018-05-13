public class OrderedLinkedListMaxPQ<Key extends Comparable<Key>> {
    private Node first = null;
    private int n = 0;

    private class Node {
        Key item;
        Node next;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public void insert(Key x) {
        n++;
        if (first == null) {
            first = new Node();
            first.item = x;
            first.next = null;
        }
        else if (less(first.item, x)) {
            Node olderfirst = first;
            first = new Node();
            first.item = x;
            first.next = olderfirst;
        }
        else {
            Node p1 = first;
            Node p2 = first.next;
            while (p2 != null && less(x, p2.item)) {
                p2 = p2.next;
                p1 = p1.next;
            }
            Node point = new Node();
            point.item = x;
            point.next = p2;
            p1.next = point;
        }
    }

    public Key delMax() {
        Key item = first.item;
        Node olderfirst = first;
        first = first.next;
        olderfirst = null;
        n--;
        return item;
    }

    public boolean less(Key i, Key j) {
        return i.compareTo(j) < 0;
    }

    public void show() {
        for (Node p = first; p != null; p = p.next) {
            StdOut.println(p.item);
        }
    }

    public static void main(String[] args) {
        OrderedLinkedListMaxPQ<String> pq = new OrderedLinkedListMaxPQ<String>();
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        while (!pq.isEmpty()) {
            StdOut.println(pq.delMax());
        }
    }
}



































