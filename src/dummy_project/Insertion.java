package dummy_project;

import edu.princeton.cs.algs4.*;

/**
 * Insertion sort algorithm
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Insertion {
    /**
     * Sorts an array in increasing order
     * @param a an array of type {@link Comparable}
     */
    public static void sort(Comparable[] a) {
        int n = a.length;
        for(int i = 1; i < n; i++) {
            // Insert a[i] among a[i - 1], a[i - 2], a[i - 3] ....
            for(int j = i; j > 0 && less(a[j], a[j - 1]); j--)
                exchange(a, j, j - 1);
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * Switches items of an array
     * @param a the array
     * @param i an index
     * @param j an index
     */
    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * Prints an array in a single line
     * @param a the array
     */
    private static void show(Comparable[] a) {
        for (Comparable c : a)
            StdOut.print(c + " ");
        StdOut.println();
    }

    /**
     * Tests whether an array's entries are in order
     * @param a the array
     * @return {@code true} if the array is sorted, {@code false} otherwise
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
