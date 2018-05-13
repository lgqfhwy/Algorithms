// Sort files by name. Write a program FileSorted.java that takes the name of a 
// directory as a command line input and print out all of the files in the current
// directoy, sorted by filename.
// Hint: use the java.io.File data type.

import java.io.File;
import java.util.Arrays;

public class FileSorter {
    public static void main(String[] args) {
        File directory = new File(args[0]);     // root directory
        if (!directory.exists()) {
            StdOut.println(args[0] + " does not exist");
            return;
        }
        if (!directory.isDirectory()) {
            StdOut.println(args[0] + " is not a directory");
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            StdOut.println("could not read files");
            return;
        }
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++)
            StdOut.println(files[i].getName());
    }   
}