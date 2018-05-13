public class UnorderedLinkedListMaxPQ<Key extends Comparable<Key>> {
    private Node first = null;
    private int n = 0;

    private class Node {
        Key item;
        Node next;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public void insert(Key x) {
        if (first == null) {
            first = new Node();
            first.item = x;
            first.next = null;
        }
        else {
            Node olderfirst = first;
            first = new Node();
            first.item = x;
            first.next = olderfirst;
        }
        n++;
    }

    public Key delMax() {
        Key max = first.item;
        int count = 0;
        int point = 0;
        for (Node p = first; p != null; p = p.next) {
            if (less(max, p.item)) {
                max = p.item;
                point = count;
            }
            count++;
        }
        //StdOut.println(point);
        if (point == 0) {
            Node olderfirst = first;
            first = first.next;
            Key item = olderfirst.item;
            olderfirst = null;
            n--;
            return item;
        }
        else {
            Node p1 = first;
            Node p2 = first.next;
            while (point > 1) {
                p2 = p2.next;
                p1 = p1.next;
                point--;
            }
            p1.next = p2.next;
            Key item = p2.item;
            p2 = null;
            n--;
            return item;
        }
    }

    private boolean less(Key i, Key j) {
        return i.compareTo(j) < 0;
    }

    public void show() {
        for (Node p = first; p != null; p = p.next) {
            StdOut.println(p.item);
        }
    }
    public static void main(String[] args) {
        UnorderedLinkedListMaxPQ<String> pq = new UnorderedLinkedListMaxPQ<String>();
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("test");
        //pq.show();
        // test a is this
        while (!pq.isEmpty()) {
            StdOut.println(pq.delMax());
        }
    }

}



































