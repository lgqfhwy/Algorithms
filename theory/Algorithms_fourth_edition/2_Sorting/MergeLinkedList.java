public class MergeLinkedList<Key extends Comparable<Key>> {
    // This class should not be instantiated.
    private MergeLinkedList() { }

    public static Node mergeSortLinkedList(Node headRef) {
        // Base case: length 0 or 1
        if ((headRef == null) || (headRef.next == null)) {
            return headRef;
        }

        Node secondRef = null;
        // Split into headRef and secondRef sublists.
        secondRef = headSecondSplit(headRef);

        // Recursively sort the sublists
        headRef = mergeSortLinkedList(headRef);
        secondRef = mergeSortLinkedList(secondRef);

        // merge the two sorted lists
        //return mergeLinkedListRecursively(headRef, secondRef);
        return mergeLinkedList(headRef, secondRef);
    }

    private static Node headSecondSplit(Node headRef) {
        // length < 2
        if (headRef == null || headRef.next == null) {
            return headRef;
        }
        else {
            Node fast = headRef.next;
            Node slow = headRef;
            while (fast != null) {
                fast = fast.next;
                if (fast != null) {
                    slow = slow.next;
                    fast = fast.next;
                }
            }
            Node tail = slow.next;
            slow.next = null;
            return tail;
        }
    }

    public static Node mergeLinkedListRecursively(Node headRef, Node secondRef) {
        Node result = null;
        if (headRef == null)
            return secondRef;
        else if (secondRef == null)
            return headRef;
        if (less(headRef.item, secondRef.item)) {
            result = headRef;
            result.next = mergeLinkedListRecursively(headRef.next, secondRef);
        }
        else {
            result = secondRef;
            result.next = mergeLinkedListRecursively(headRef, secondRef.next);
        }
        return result;
    }

    public static Node mergeLinkedList(Node headRef, Node secondRef) {
        Node first = null;
        Node result = null;
        if (headRef == null)
            return secondRef;
        else if (secondRef == null)
            return headRef;
        if (less(headRef.item, secondRef.item)) {
            first = headRef;
            headRef = headRef.next;
        }
        else {
            first = secondRef;
            secondRef = secondRef.next;
        }
        result = first;
        while (headRef != null && secondRef != null) {
            if (less(headRef.item, secondRef.item)) {
                result.next = headRef;
                headRef = headRef.next;
                result = result.next;
            }
            else {
                result.next = secondRef;
                secondRef = secondRef.next;
                result = result.next;
            }
        }
        if (headRef == null && secondRef != null) {
            result.next = secondRef;
        }
        if (secondRef == null && headRef != null) {
            result.next = headRef;
        }
        return first;
    }

    private static <Key> boolean less(Key i, Key j) {
        return ((Comparable)i).compareTo((Comparable)j) < 0;
    }

    public static <Key extends Comparable<Key>> Node insert(Node first, Key item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        return first;
    }

    public static void show(Node first) {
        for (Node p = first; p != null; p = p.next) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Integer a = 1;
        Node headRef = new Node(a);
        for (int i = 2; i < 9; i++)
            headRef = insert(headRef, i);
        show(headRef);
        headRef = mergeSortLinkedList(headRef);
        show(headRef);
    }
}