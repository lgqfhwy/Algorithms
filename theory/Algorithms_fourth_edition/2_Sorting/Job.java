public class Job implements Comparable<Job> {
    private String name;
    private double time;

    Job(String name, double time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%-10s%.2f", name, time);
    }

    public int compareTo(Job that) {
        if (this.time > that.time)  return 1;
        else if (this.time < that.time)  return -1;
        else return this.name.compareTo(that.name);
    }

    public static void main(String[] args) {
        
    }
}