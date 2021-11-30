import java.util.Arrays;

public class Grade implements Comparable<Grade> {
    private String grade;

    public Grade(String grade) {
        this.grade = grade;
        if (gpa() < 0.0) throw new IllegalArgumentException("Illegal grade");
    }

    public double gpa() {
        if (grade.equals("A"))  return 4.00;
        if (grade.equals("B"))  return 3.00;
        if (grade.equals("C"))  return 2.00;
        if (grade.equals("D"))  return 1.00;
        if (grade.equals("F"))  return 0.00;
        if (grade.equals("B+")) return 3.33;
        if (grade.equals("C+")) return 2.33;
        if (grade.equals("A-")) return 3.67;
        if (grade.equals("B-")) return 2.67;
        if (grade.equals("C-")) return 1.67;
        return -1.0;
    }

    public int compareTo(Grade that) {
        return Double.compare(this.gpa(), that.gpa());
    }

    // use %-2s flag to left justify
    public String toString() {
        return String.format("%-2s %3.2f", grade, gpa());
    }


    // test client
    public static void main(String[] args) {
        Grade[] grades = new Grade[8];
        grades[0] = new Grade("B+");
        grades[1] = new Grade("A");
        grades[2] = new Grade("C+");
        grades[3] = new Grade("B-");
        grades[4] = new Grade("A-");
        grades[5] = new Grade("D");
        grades[6] = new Grade("F");
        grades[7] = new Grade("A-");

 
        StdOut.println("Unsorted");
        for (int i = 0; i < grades.length; i++)
            StdOut.println(grades[i]);
        StdOut.println();
        
        StdOut.println("Sorted");
        Arrays.sort(grades);
        for (int i = 0; i < grades.length; i++)
            StdOut.println(grades[i]);
    }

}
