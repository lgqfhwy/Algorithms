public class ToInfix {
    public static void main(String[] args) {
         Stack<String> s = new Stack<String>();
         while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals(")"))
                s.push(item);
            else  {
                String thing1 = s.pop() + " " + ")";
                thing1 = s.pop() + " " + thing1;
                thing1 = s.pop() + " " + thing1;
                thing1 = "(" + " " + thing1;
                s.push(thing1);

            }
         }
         System.out.println(s.pop());
         //System.out.println("hello");
    }
}