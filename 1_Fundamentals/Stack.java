import java.util.Iterator;
import java.util.NoSuchElementException;
public class Stack<Item> implements Iterable<Item> {
    private Node first;    // top of stack (most recently added node)
    private int N;         // number of items
    private int counter;    // counts the number of push() and pop() operations.
                            // when creating an iterator, store this value as an
                            // Iterator instance variable.Before each call to 
    // hasNext() and next(), check that this value has not changed since construction
    // of the iterator; if it has, throw the exception.

    private class Node {
        // nested class to define nodes
        Item item;
        Node next;
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
        counter++;
        N++;
    }

    public Item pop() {
        // Remove item from top of stack.
        Item item = first.item;
        first = first.next;
        N--;
        counter++;
        return item;
    }

    public Item peek() {
        Item item = first.item;
        return item;
    }

    public void seem() {
        for (Node p = first; p != null; p = p.next)
            StdOut.print(p.item + " ");
        //StdOut.println();
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;
        private int state = counter;
        public boolean hasNext() {
            if (state != counter)
                throw new NoSuchElementException();
            return current != null;
        }

        public void remove() { }

        public Item next() {
            if(state != counter)
                throw new NoSuchElementException();
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
        // Create a stack and push/pop strings as directed on StdIn.
        // Stack<String> s = new Stack<String>();
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     if (!item.equals("-"))
        //         s.push(item);
        //     else if (!s.isEmpty())
        //         StdOut.print(s.pop() + " ");
        // }
        // StdOut.println("(" + s.size() + " left on stack)");
        Stack<Integer> s = new Stack<Integer>();
        //boolean isfalse = false;
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     if (item.equals("[") || item.equals("(") || item.equals("{"))
        //         s.push(item);
        //     else if (item.equals("]") || item.equals(")") || item.equals("}")) {
        //         if (!s.isEmpty()) {
        //             String thing = s.pop();
        //             if (item.equals("]") && thing.equals("[")) { }
        //             else if (item.equals(")") && thing.equals("(")) { }
        //             else if (item.equals("}") && thing.equals("{")) { }
        //             else {
        //                 System.out.println("false");
        //                 isfalse = true;
        //             }
        //         }
        //         else  {
        //             System.out.println("false");
        //             isfalse = true;
        //         }
        //     }
        // }
        // while (!s.isEmpty()) {
        //     String item = s.pop();
        //     if (item.equals("[") || item.equals("(") || item.equals("{")) {
        //         System.out.println("false");
        //         isfalse = true;
        //     }
        // }
        // if (!isfalse) {
        //     System.out.println("true");
        // }
        // while (!StdIn.isEmpty()) {
        //     String item = StdIn.readString();
        //     s.push(item);
        // }
        // Stack<String> s1 = copy(s);
        // while (!s1.isEmpty()) {
        //     System.out.println(s1.pop());
        // }
        for (int i = 0; i < 10; i++)
            s.push(i);
        for (int i : s)
            StdOut.print(i + " ");
    }
}























