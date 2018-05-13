public class DequeTwoStacks<Item> {
    private Stack<Item> p;
    private Stack<Item> q;
    private int N;

    public DequeTwoStacks() {
        p = new Stack<Item>();
        q = new Stack<Item>();
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void pushleft(Item item) {
        p.push(item);
        N++;
    }

    public void pushright(Item item) {
        q.push(item);
        N++;
    }

    public Item popleft() {
        if (!p.isEmpty())
            Item item = p.pop();
        else {
            while (!q.isEmpty())
                p.push(q.pop());
            Item item = p.pop();
        }
        N--;
        return item;
    }

    public Item popright() {
        if (q.isEmpty())
            Item item = q.pop();
        else {
            while (p.isEmpty())
                q.push(p.pop());
            Item item = q.pop();
        }
        N--;
        return item;
    }

    public static void main(String[] args) {
        DequeTwoStacks<String> q = new DequeTwoStacks<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                //q.pushleft(item);
                q.pushright(item);
            }
            else if (!q.isEmpty()) {
                //StdOut.print(q.popLeft() + " ");
                StdOut.print(q.popright() + " ");
            }
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }


}




































