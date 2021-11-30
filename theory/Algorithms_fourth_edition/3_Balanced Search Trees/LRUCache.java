import edu.princeton.cs.algs4.StdOut;

// LRU cache. Create a data structure that supports the following operations: 
// ac- cess and remove. The access operation inserts the item onto the data 
// structure if it’s not already present. The remove operation deletes and 
// returns the item that was least recently accessed. Hint : Maintain the items 
// in order of access in a doubly linked list, along with pointers to the first 
// and last nodes. Use a symbol table with keys = items, values = location in 
// linked list. When you access an element, delete it from the linked list and 
// reinsert it at the beginning. When you remove an element, delete it from the 
// end and remove it from the symbol table.
public class LRUCache<Item extends Comparable<Item>> {
    private Node first;
    private ST<Item, Node> st;

    private class Node {
        Object item;
        Node front;
        Node back;

        public Node(Object item, Node front, Node back) {
            this.item = item;
            this.front = front;
            this.back = back;
        }
    }

    // Initializes the class
    public LRUCache() {
        st = new ST<Item, Node>();
    }

    public int size() {
        return st.size();
    }

    public boolean isEmpty() {
        return st.isEmpty();
    }

    // access. The access operation inserts the item onto
    // the data structure if its's not already present.
    public void access(Item item) {
        st.delete(item);
        first = new Node(item, null, first);
        st.put(item, first);
    }

    // remove. The remove operation deletes and returns the item
    // that was least recently accessed.
    public Item remove() {
        Item item = (Item)first.item;
        Node x = st.get(item);
        if (x != null && first == x) {
            first = first.back;
            if (first != null) {
                first.front = null;
            }
        }
        else {
            x.front.back = x.back;
            x.back.front = x.front;
        }
        st.delete(item);
        return item;
    }
    public static void main(String[] args) {
        LRUCache<String> st = new LRUCache<String>();
        st.access("Hello");
        st.access("hi");
        st.access("jack");
        StdOut.println(st.remove());
        StdOut.println(st.remove());
        st.access("koo");
        while (!st.isEmpty()) {
            StdOut.println(st.remove());
        }
    }
}