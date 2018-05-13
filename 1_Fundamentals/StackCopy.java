import java.util.Iterator;
public class StackCopy<Item> implements Iterable<Item> {
    private Node first;    // top of stack (most recently added node)
    private int N;         // number of items

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;

        Node() {}
        // Recursive solution
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

    public StackCopy() {
        first = null;
        N = 0;
    }

    // Recursive solution
    // public StackCopy(StackCopy<Item> s) {
    //     N = s.size();
    //     first = new Node(s.first);
    // }

    // Nonrecursive solution
    public StackCopy(StackCopy<Item> s) {
        N = s.size();
        if (s.first != null) {
            first = new Node(s.first);
            for (Node x = first; x.next != null; x = x.next) 
                x.next = new Node(x.next);
        }
    }

    public boolean isEmpty() {
        return first == null;   // Or: N == 0.
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        // Add item to top of stack.
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item pop() {
        // Remove item from top of stack.
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public Item peek() {
        Item item = first.item;
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

    public static Stack<String> copy(Stack<String> s) {
        // Take a stack of strings as a argument and returns a 
        // copy of the stack.
        Stack<String> s1 = new Stack<String>();
        Stack<String> s2 = new Stack<String>();
        while (!s.isEmpty()) {
            String item = s.pop();
            s1.push(item);
        }
        while (!s1.isEmpty()) {
            String item = s1.pop();
            s2.push(item);
        }
        return s2;
    }

    public static void main(String[] args) {
        StackCopy<Integer> s = new StackCopy<Integer>();
        for (int i = 0; i < 10; i++)
            s.push(i);
        StackCopy<Integer> r = new StackCopy<Integer>(s);
        for (int i = 0; i < 10; i++)
            StdOut.print(r.pop() + " ");
        StdOut.println();
        StdOut.println(s.size());
    }
}























