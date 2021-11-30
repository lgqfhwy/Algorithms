public class ShuffleProgram {
    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            // Exchange a[i] with random element in a[i..N-1]
            int r = i + StdRandom.uniform(N-i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        int N = Integer.parseInt(args[1]);
        int[] deck = new int[M];
        int[][] a = new int[M][M];
        for (int i = 0;i < M;i++) {
            for(int j = 0; j < M; j++) {
                a[i][j] = 0;
            }
        }
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                deck[j] = j;
            }
            shuffle(deck);
            for (int j = 0; j < M; j++) {
                int card_at_j = deck[j];
                a[card_at_j][j]++;
            }
        }
        for (int b = 0; b < M; b++) {
            for (int c = 0; c < M; c++) {
                System.out.print(" " + a[b][c]);
                //System.out.println();
            }
            System.out.println();
        }
    }
}

































