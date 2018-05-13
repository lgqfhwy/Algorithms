public class MoveToFront<Item> {
    private int N;
    private Node first;
    private Node last;

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

    public int find(Item item) {
        // Only apply for String
        int num = 0;
        for (Node p = first; p != null; p = p.next) {
            if (p.item.equals(item))
                return num;
            num++;
        }
        return -1;
    }

    public Item delete(int num) {
        if (num == 0) {
            Item item = first.item;
            first = first.next;
            N--;
            return item;
        }
        else {
            Node p = first;
            for (int i = 0; i < num - 1; i++)
                p = p.next;
            Item item = p.next.item;
            p.next = p.next.next;
            N--;
            return item;
        }
    }

    public void insert(Item item) {
        // Insert the item at the front of the list.
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty())
            last = first;
        N++;
    }

    public void seem() {
        for (Node p = first; p != null; p = p.next)
            System.out.print(p.item + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        MoveToFront<String> q = new MoveToFront<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            int j = q.find(item);
            if (j == -1) {
                q.insert(item);
            }
            else {
                q.delete(j);
                q.insert(item);
            }
            q.seem();
        }
    }
}







































