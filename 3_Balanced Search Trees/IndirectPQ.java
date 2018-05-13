// Indirext priority queue implementation with java's TreeSet and
// HashMap. It assumes the priorities are integers and the values 
// are strings, although it is easily modifiable for Comparable
// priorities and Object values.
// All operations are efficient, but it could be improved by using
// a binary heap instead of the red-black tree.

import java.util.HashMap;
import java.util.TreeSet;

public class IndirectPQ {

    private TreeSet<Element> pq = new TreeSet<Element>();
    private HashMap<String, Element> st = new HashMap<String, Element>();

    private class Element implements Comparable<Element> {
        private String key;
        private int priority;

        public Element(String key, int priority) {
            this.key = key;
            this.priority = priority;
        }

        public int compareTo(Element that) {
            if (this.priority != that.priority)
                return this.priority - that.priority;
            return this.key.compareTo(that.key);
        }

        public boolean equals(Object other) {
            if (other == this)  return true;
            if (other == null)  return false;
            if (other.getClass() != this.getClass())    return false;
            Element that = (Element) other;
            return (this.priority == that.priority) && (this.key.equals(that.key));
        }
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }

    public int size() {
        return pq.size();
    }

    // Insert a key with a given priority (changing the priority 
    // if the key is present).
    public void put(String key, int priority) {
        delete(key);
        Element element = new Element(key, priority);
        st.put(key, element);
        pq.add(element);
    }

    // does the key exist?
    public boolean exists(String key) {
        return st.get(key) != null;
    }

    // delete key
    public void delete(String key) {
        Element element = st.get(key);
        if (element != null) {
            pq.remove(element);
            st.remove(key);
        }
    }

    // return the priority of a given key
    public int get(String key) {
        Element element = st.get(key);
        return element.priority;
    }

    // return minimum priority, error if empty.
    public int min() {
        Element min = pq.first();
        return min.priority;
    }

    // return maximum priority, error if empty.
    public int max() {
        Element max = pq.last();
        return max.priority;
    }

    // delete and return the minimum value, error if empty.
    public String delMin() {
        Element min = pq.first();
        pq.remove(min);
        st.remove(min.key);
        return min.key;
    }

    // delete and return the maximum value, error if empty.
    public String delMax() {
        Element max = pq.last();
        pq.remove(max);
        st.remove(max.key);
        return max.key;
    }

    // test client.
    public static void main(String[] args) {
        IndirectPQ pq = new IndirectPQ();
        pq.put("test", 31);
        pq.put("is", 55);
        pq.put("this", 25);
        pq.put("not", 65);
        pq.put("a", 36);
        pq.put("this", 61);    // changes the key
        pq.delete("not");
        while (!pq.isEmpty()) {
            StdOut.println(pq.delMax());
        }
    }
}