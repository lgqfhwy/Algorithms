public class StringCircular {
    public static void main(String[] args) {
        // String s = args[0];
        // String t = args[1];
        String s = "ACTGACG";
        String t = "TGACGAC";
        int index = 0;
        int length1 = s.length();
        while (s.indexOf(t.charAt(0), index) != -1) {
            int m = s.indexOf(t.charAt(0), index);
            String temp = s.substring(m, length1) + s.substring(0, m);
            System.out.println(temp);
            if (t.equals(temp))   {
                System.out.println("Yes");
                return ;
            }
            index = m + 1;
            System.out.println(index);
        }
        System.out.println("No");
    }
}