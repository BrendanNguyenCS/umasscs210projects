package dummy_project;

import edu.princeton.cs.algs4.*;

/**
 * Selection sort algorithm
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Selection {
    /**
     * Sort an array into increasing order
     * @param a the array
     */
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            // Exchange a[i] with smallest entry in a[i], ..., a[n-1]
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            exchange(a, i, min);
        }
    }

    private static boolean less(Comparable v, Comparable w) { return v.compareTo(w) < 0; }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        // Print the array, on a single line
        for (Comparable c : a)
            StdOut.print(c + " ");
        StdOut.println();
    }

    /**
     * Tests whether an array's entries are in order
     * @param a
     * @return
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if(less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // Read strings from standard input, sort them, and print
        String[] a = StdIn.readAllStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
