public class Buffer<Item> {
    private Stack<Item> p;
    private Stack<Item> q;
    private int N;

    public Buffer() {
        p = new Stack<Item>();
        q = new Stack<Item>();
        N = 0;
    }

    public void insert(Item c) {
        p.push(c);
        N++;
    }

    public Item delete() {
        N--;
        return p.pop();
    }

    public void left(int k) {
        if (p.size() < k)
            k = p.size();
        for (int i = k; i > 0; i--) {
            Item item = p.pop();
            q.push(item);
        }
    }

    public void right(int k) {
        if (q.size() < k)
            k = q.size();
        for (int i = k; i > 0; i--) {
            Item item = q.pop();
            p.push(item);
        }
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void seem() {
        p.seem();
        q.seem();
    }

    public static void main(String[] args) {
        Buffer<Character> b = new Buffer<Character>();
        while (!StdIn.isEmpty()) {
            Character c = StdIn.readChar();
            if (!c.equals('-')) {
                b.insert(c);
                //b.seem();

            }
            else if (!b.isEmpty()) {
                b.left(2);
                StdOut.print(b.delete() + "##");
                b.seem();
            }

        }
        StdOut.println("(" + b.size() + " left on buffer)");
    }


}




































































