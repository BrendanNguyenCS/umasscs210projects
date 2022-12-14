package cs210_programming_exam1;

import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class UniqueCount {
    // Entry point [DO NOT EDIT].
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(uniqueCount(a));
    }

    // Returns the number of unique strings in a.
    private static int uniqueCount(String[] a) {
        // sort a
        Arrays.sort(a);

        // if array length is 0, return to prevent exception in following loop
        if (a.length == 0) {
            return 0;
        }

        int count = 1; // counter for unique words in array

        // loop through array starting at index 1
        for (int i = 1; i < a.length; i++) {
            // check equality of current index value with previous index value
            if (!a[i].equals(a[i - 1])) {
                // increase counter if true
                count++;
            }
        }
        return count;
    }
}