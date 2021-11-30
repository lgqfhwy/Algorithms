import java.io.File;
public class Directory {
    public static void main(String[] args) {
        Queue<File> queue = new Queue<File>();
        File root = new File(args[0]);    // root directory
        if (!root.exists()) {
            StdOut.println(args[0] + " does not exist");
            return ;
        }

        queue.enqueue(root);
        while (!queue.isEmpty()) {
            File x = queue.dequeue();
            if (!x.isDirectory()) {
                StdOut.println(x.length() + ":\t" + x);
            }
            else {
                File[] files = x.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++)
                        queue.enqueue(files[i]);
                }
            }
        }
    }
}



































