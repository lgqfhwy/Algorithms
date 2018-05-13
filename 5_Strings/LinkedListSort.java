// Linked-list sort. Develop a sort implementation that takes a linked 
// list of nodes with String key values as argument and rearranges the 
// nodes so that they appear in sorted order (returning a link to the 
// node with the smallest key). Use 3-way string quicksort.
public class LinkedListSort {

    public static class Node {
        private String key;
        private Node next;
        private Node pre;
        public Node(String key) {
            this.key = key;
            next = null;
            pre = null;
        }
        public Node(String key, Node next, Node pre) {
            this.key = key;
            this.next = next;
            this.pre = pre;
        }
    }

    private LinkedListSort() { }

    public static Node push(Node head, String data) {
        Node newNode = new Node(data, head, null);
        head.pre = newNode;
        return newNode;
    }

    public static void printList(Node head) {
        Node t = head;
        while (t != null) {
            System.out.print(t.key + " ");
            t = t.next;
        }
        System.out.println();
    }

    public static Node getTail(Node cur) {
        while (cur != null && cur.next != null) {
            cur = cur.next;
        }
        return cur;
    }

    public static Node sort(Node head) {
        head = sort(head, getTail(head), 0);
        //System.out.println("That6:");
        //assert isSorted(head);
        return head;
    }

    // Return the dth character of s, -1 if d = length of s
    private static int charAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) {
            return -1;
        }
        return s.charAt(d);
    }

    // 3-way string quicksort starting at dth character
    private static Node sort(Node head, Node tail, int d) {
        if (head == null || tail == null || head == tail) {
            return head;
        }
        Node lt = head, gt = tail;
        int v = charAt(head.key, d);
        Node i = head.next;
        //System.out.println("This:");
        while (i.pre != gt) {
            //printList(head);
            //System.out.println("i.key = " + i.key + " gt.key = " + gt.key);            
            int t = charAt(i.key, d);
            if (t < v) {
                exch(lt, i);
                lt = lt.next;
                i = i.next;
            } else if (t > v) {
                exch(i, gt);
                gt = gt.pre;
            } else {
                i = i.next;
            }
        }
        //System.out.println("That:");
        //System.out.println("lala " + lt.key + "  " + gt.key);
        if (head != lt)
            head = sort(head, lt.pre, d);
        //System.out.println("That2:");
        if (v >= 0) {
            if (lt != gt)
                sort(lt, gt, d + 1);
        }
        if (gt != tail)
            sort(gt.next, tail, d);
        return head;
    }

    private static void exch(Node i, Node j) {
        String temp = i.key;
        i.key = j.key;
        j.key = temp;
    }

    private static boolean isSorted(Node head) {
        for (Node t = head.next; t != null; t = t.next) {
            if (t.key.compareTo(t.pre.key) < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Node head = new Node("b");
        head = push(head, "a");
        head = push(head, "f");
        head = push(head, "b");
        head = push(head, "g");
        head = push(head, "c");
        printList(head);
        head = sort(head);
        //System.out.println("That7:");
        printList(head);
        //System.out.println("hi");
    }

}