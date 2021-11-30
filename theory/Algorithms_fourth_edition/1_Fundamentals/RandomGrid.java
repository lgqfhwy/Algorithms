public class RandomGrid {

    private class Node {
        int p;
        int q;
        public Node(int i, int j) {
            p = i;
            q = j;
        }
    }
    public RandomBag<Node> generate(int N) {
        RandomBag<Node> bag = new RandomBag<Node>();
        int k = 0;
        while (k < N) {
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            if (i != j) {
                Node t = new Node(i, j);
                bag.add(t);
                k++;
            }
        }
        return bag;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RandomGrid r = new RandomGrid();
        RandomBag<Node> bag = r.generate(N);
        for (Node x : bag) {
            StdOut.println(x.p + "  " + x.q);
        }
    }
}





























