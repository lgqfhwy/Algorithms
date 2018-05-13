public class Record implements Comparable<Record> {
    private String word;
    private int frequency;

    Record(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return String.format("%-10s%d", word, frequency);
    }

    public int compareTo(Record that) {
        if (this.frequency > that.frequency)    return 1;
        else if (this.frequency < that.frequency)   return -1;
        else return this.word.compareTo(that.word);
    }
    public static void main(String[] args) {
        
    }

}