// Parses simple arithmetic expressions of the form z = x and
// z = x + y, where x and y can be symbolic variables or real
// numbers. Uses a symbol table to store the mapping between
// variable names and their values.
public class Interpreter {
    public static void main(String[] args) {
        ST<String, Double> st = new ST<String, Double>();
        // read in one line at a time and parse.
        String line;
        In stdin = new In();
        System.out.print(">> ");
        while ((line = stdin.readLine()) != null) {
            String[] tokens = line.split("\\s");

            // singe variable - just print out its value
            if (tokens.length == 1) {
                String zvar = tokens[0];
                System.out.println(zvar + " := " + st.get(zvar));
            }

            // z = x
            else if (tokens.length == 3) {
                String zvar = tokens[0];
                String eq = tokens[1];
                String xvar = tokens[2];
                if (!eq.equals(":="))   throw new RuntimeException("Illegal assignment");
                double x;
                if (st.contains(xvar))  x = st.get(xvar);
                else                    x = Double.parseDouble(xvar);
                st.put(zvar, x);
                System.out.println(zvar + " := " + st.get(zvar));
            }

            // z = function x
            else if (tokens.length == 4) {
                String zvar = tokens[0];
                String eq = tokens[1];
                String func = tokens[2];
                String xvar = tokens[3];
                if (!eq.equals(":="))   throw new RuntimeException("Illegal assignment");

                double x;
                if (st.contains(xvar))  x = st.get(xvar);
                else                    x = Double.parseDouble(xvar);

                if (func.equals("sin"))
                    st.put(zvar, Math.sin(x));
                else if (func.equals("cos"))
                    st.put(zvar, Math.cos(x));
                else if (func.equals("sqrt"))
                    st.put(zvar, Math.sqrt(x));
                else if (func.equals("-"))
                    st.put(zvar, -x);
                else 
                    throw new RuntimeException("Illegal function");
                System.out.println(zvar + " := " + st.get(zvar));
            }

            // z = x + y
            else if (tokens.length == 5) {
                String zvar = tokens[0];
                String eq = tokens[1];
                String xvar = tokens[2];
                String op = tokens[3];
                String yvar = tokens[4];
                if (!eq.equals(":="))
                    throw new RuntimeException("Illegal assignment");
                
                double x;
                if (st.contains(xvar))
                    x = st.get(xvar);
                else 
                    x = Double.parseDouble(xvar);
                
                double y;
                if (st.contains(yvar))
                    y = st.get(yvar);
                else
                    y = Double.parseDouble(yvar);
                
                if (op.equals("+"))
                    st.put(zvar, x + y);
                else if (op.equals("-"))
                    st.put(zvar, x - y);
                else if (op.equals("+"))
                    st.put(zvar, x + y);
                else if (op.equals("*"))
                    st.put(zvar, x * y);
                else if (op.equals("/"))
                    st.put(zvar, Math.pow(x, y));
                else
                    throw new RuntimeException("Illegal operator");
                System.out.println(zvar + " = " + st.get(zvar));
            }

            else 
                throw new RuntimeException("Illegal expression");
            System.out.println();
            System.out.print(">> ");
        }
    }
}