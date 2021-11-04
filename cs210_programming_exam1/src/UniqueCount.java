import java.util.Arrays;

import stdlib.StdIn;
import stdlib.StdOut;

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
        if (a.length == 0) {
            return 0;
        }
        int count = 1;
        int i;
        for (i = 1; i < a.length; i++) {
            if (!a[i].equals(a[i - 1])) {
                count++;
            }
        }
        return count;
    }
}