package cs210_programming_exam1;

import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class UniqueCount {
    /**
     * Entry point [DO NOT EDIT].
     * @param args
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(uniqueCount(a));
    }

    /**
     * @param a an array of strings
     * @return the number of unique strings in {@code a}
     */
    private static int uniqueCount(String[] a) {
        Arrays.sort(a);
        if (a.length == 0) {
            return 0;
        }
        int count = 1;
        for (int i = 1; i < a.length; i++) {
            if (!a[i].equals(a[i - 1])) {
                count++;
            }
        }
        return count;
    }
}