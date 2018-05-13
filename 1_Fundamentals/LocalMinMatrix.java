public class LocalMinMatrix {

    public static void findlocalmin(int[][] a, int lefti, int leftj, int righti, int rightj) {
        int mini = lefti;
        int minj = leftj;
        int midi = (lefti + righti) / 2;
        int midj = (leftj + rightj) / 2;
        for (int j = leftj; j < rightj; j++) {
            if (a[lefti][j] < a[mini][minj]) {
                mini = lefti;
                minj = j;
            }
            if (a[midi][j] < a[mini][minj]) {
                mini = midi;
                minj = j;
            }
            if (a[righti][j] < a[mini][minj]) {
                mini = righti;
                minj = j;
            }
        }
        for (int i = lefti; i < righti; i++) {
            if (a[i][leftj] < a[mini][minj]) {
                mini = i;
                minj = leftj;
            }
            if (a[i][midj] < a[mini][minj]) {
                mini = i;
                minj = midj;
            }
            if (a[i][rightj] < a[mini][minj]) {
                mini = i;
                minj = rightj;
            }
        }
        if ((mini == lefti && minj == leftj) || (mini == lefti && minj == midj) 
            || (mini == lefti && minj == rightj) || (mini == midi && minj == leftj)
            || (mini == midi && minj == midj) || (mini == midi && mini == rightj)
            || (mini == righti && minj == leftj) || (mini == righti && minj == midj)
            || (mini == righti && minj == rightj)) {
            StdOut.println("Minmum i = " + mini + " j = " + minj);
            //return a[mini][minj];
        }
        else if (mini == lefti) {
            if (a[mini][minj] < a[mini + 1][minj]) {
                StdOut.println("Minmum i = " + mini + " j = " + minj);
                //return a[mini][minj];
            }
            else if (minj < midj){
                findlocalmin(a, lefti, leftj, midi, midj);
            }
            else
                findlocalmin(a, lefti, midj, midi, rightj);
        }
        else if (mini == midi) {
            if (a[mini][minj] < a[mini + 1][minj] && a[mini][minj] < a[mini - 1][minj])
                StdOut.println("Minmum i = " + mini + " j = " + minj);
            else if (a[mini + 1][minj] > a[mini - 1][minj]) {
                if (minj < midj)
                    findlocalmin(a, lefti, leftj, midi, midj);
                else
                    findlocalmin(a, lefti, midj, midi, leftj);
            }
            else {
                if (minj < midj)
                    findlocalmin(a, midi, leftj, lefti, midj);
                else
                    findlocalmin(a, midi, midj, righti, rightj);
            }
        }
        else if (mini == righti) {
            if (a[mini][minj] < a[mini - 1][minj])
                StdOut.println("Minmum i = " + mini + " j = " + minj);
            else if (minj < midj)
                findlocalmin(a, midi, leftj, righti, midj);
            else
                findlocalmin(a, midi, midj, righti, rightj);
        }
        else if (minj == leftj) {
            if (a[midi][midj] < a[midi][midj + 1])
                StdOut.println("Minmum i = " + mini + " j = " + minj);
            else if (mini < midi)
                findlocalmin(a, lefti, leftj, midi, midj);
            else 
                findlocalmin(a, lefti, midi, righti, midj);
        }
        else if (minj == midj) {
            if (a[mini][minj] < a[midi][midj - 1] && a[mini][minj] < a[midi][midj + 1])
                StdOut.println("Minmum i = " + mini + " j = " + minj);
            else if (a[midi][midj] > a[midi][midj - 1]) {
                if (mini < midi) {
                    findlocalmin(a, lefti, leftj, midi, midj);
                }
                else 
                    findlocalmin(a, midi, lefti, righti, midj);
            }
            else {
                if (mini < midi) {
                    findlocalmin(a, lefti, midj, midi, rightj);
                }
                else
                    findlocalmin(a, midi, midj, righti, rightj);
                
            }
        }
        else {
            if (a[mini][minj] < a[mini][minj - 1]) {
                StdOut.println("Minmum i = " + mini + " j = " + minj);
            }
            else if (mini < midi)
                findlocalmin(a, lefti, midj, midi, rightj);
            else
                findlocalmin(a, midi, midj, righti, rightj);
        }
    }
    public static void main(String[] args) {
        int[][] a = { {10, 10, 10, 10, 10, 10, 10, 10, 10},
                      {10, 9, 3, 5, 2, 4, 9, 8, 10},
                      {10, 7, 2, 5, 1, 4, 0, 3, 10},
                      {10, 9, 8, 9, 3, 2, 4, 8, 10},
                      {10, 7, 6, 3, 1, 3, 2, 3, 10},
                      {10, 9, 0, 6, 0, 4, 6, 4, 10},
                      {10, 8, 9, 8, 0, 5, 3, 0, 10},
                      {10, 2, 1, 2, 1, 1, 1, 1, 10},
                      {10, 10, 10, 10, 01, 10, 10, 10, 10}
                    };
        findlocalmin(a, 0, 0, a.length - 1, a[1].length - 1);
    }
}































