public class SmartDate {
    private final int month;
    private final int day;
    private final int year;

    public SmartDate(int m, int d, int y) {
        month = m; day = d; year = y;
    }

    public String dayOfTheWeek() {
        int year1 = year % 100;
        int w = year1 + year1 / 4 + 20 / 4 - 20 * 2 + 26 * (month + 1) / 10 + day - 1;
        w = w % 7;
        String s = "Sunday";
        if (w == 0)    s = "Sunday";
        else if (w == 1)    s = "Monday";
        else if (w == 2)    s = "Tuesday";
        else if (w == 3)    s = "Wednesday";
        else if (w == 4)    s = "Thursday";
        else if (w == 5)    s = "Friday";
        else    s = "Saturday";

        return s;
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int d = Integer.parseInt(args[1]);
        int y = Integer.parseInt(args[2]);
        SmartDate date = new SmartDate(m, d, y);
        String s = date.dayOfTheWeek();
        System.out.println(s);
    }
}
























