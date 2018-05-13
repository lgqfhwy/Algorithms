import java.util.NoSuchElementException;
public class LinkedList<Item> {
    private Node first;    // link to least recently added node.
    private Node last;     // link to most recently added node.
    private int N;         // number of items on the queue

    private class Node {
        // Nested class to define nodes
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;    // Or: N == 0.
    }

    public int size() {
        return N;
    }

    public void addOnFirst(Item item) {
        // Add item to the first of the list.
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    public Item removeOnFirst() {
        // Remove item from first of the list.
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    public void addOnLast(Item item) {
        // Add item to the last of the list.
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

    public Item removeOnLast() {
        // Remove item from last of the list.
        Node point = first;
        while(point.next.next != null)
            point = point.next;
        Item item = last.item;
        last = point;
        last.next = null;
        return item;
    }

    public Item delete(int k) {
        // Delete the kth element in a linked list, if it exists.
        int num = 1;
        Node point1 = first;
        Node point2 = first;
        while (num < k) {
            if (num > 1)
                point1 = point1.next;
            point2 = point2.next;
            //System.out.println(point2.item);
            if(point2 == null)
                break;
            num++;
        }
        if (num < k) {
            throw new NoSuchElementException("Does't exist this element.");
        }
        else {
            Item item = point2.item;
            point1.next = point2.next;
            return item;
        }
    }

    public boolean find(Item key) {
        // Only apply for string.
        for (Node x = first; x != null; x = x.next) {
            //System.out.println("##" + x.item + "##" + key + "##");
            if (x.item.equals(key))
                return true;
        }
        return false;
    }

    public void remove(Item key) {
        // Only apply for string
        // Remove all of the nodes in the list that have key 
        // as its item field.
        Node pre = first;
        for (Node x = first; x != null; x = x.next) {
            if (x.item.equals(key)) {
                if (x == first) {
                    first = first.next;
                }
                else {
                    pre.next = x.next;
                }
            }
            if (x != first) {
                pre = pre.next;
            }
        }
    }

    public int max() {
        // Return the value of the maximum key in the list.
        // Assume that all keys are positive integers, and return
        // 0 if the list is empty.
        if (first == null) 
            return 0;
        int max = first.item;
        for (Node x = first; x != null; x = x.next) {
            if (x.item > max) {
                max = x.item;
            }
        }
        return max;
    }


    public Node removeAfter(Node x) {
        for (Node m = first; m != null; m = m.next) {
            if (m == x) {
                Node t = m.next;
                m.next = t.next;
                return t;
            }
        }
        return null;
    }

    public void insertAfter(Node x) {
        if (x == null or first == null)
            return null;
        else {
            x.next = first.next;
            first.next = x;
        }
    }

    public static void max(Node first) {
        // Return the value of the maximum key in the list.
        // Assume that all keys are positive integers, and return
        // 0 if the list is empty.
        if (first == null) 
            return 0;
        int max = first.item;
        for (Node x = first; x != null; x = x.next) {
            if (x.item > max) {
                max = x.item;
            }
        }
        return max;
    }

    public static int max(int a, int b) {
        if (a > b)
            return a;
        else 
            return b;
    }

    public static void recursiveMax(Node first) {
        if (first == null) 
            return 0;
        else {
            return max(first.item, recursiveMax(first.next));
        }
    }

    public static void main(String[] args) {
        //int k = Integer.parseInt(args[0]);
        LinkedList<String> link = new LinkedList<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            link.addOnLast(item);
        }
        // String item = link.delete(k);
        // System.out.print(item);
        // System.out.println();
        String thing = "e";
        System.out.println(link.find(thing));
        //System.out.println(thing == "e");
    } 
}









































