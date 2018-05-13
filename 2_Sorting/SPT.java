import java.util.Arrays;
public class SPT {
    public static void main(String[] args) {
        //int n = StdIn.readInt();
        int n = 4;
        Job[] jobs = new Job[n];
        jobs[0] = new Job("Jack", 4);
        jobs[1] = new Job("John", 6);
        jobs[2] = new Job("Mary", 1);
        jobs[3] = new Job("David", 3);
        // for (int i = 0; i < n; i++) {
        //     String name = StdIn.readString();
        //     double time = StdIn.readDouble();
        //     jobs[i] = new Job(name, time);
        // }

        // sort jobs in ascending order of processing time.
        Arrays.sort(jobs);

        // print out schedule
        for (int i = 0; i < n; i++)
            StdOut.println(jobs[i]);
    }
}