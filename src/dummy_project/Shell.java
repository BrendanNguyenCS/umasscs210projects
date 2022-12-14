/**
 * dummy_project.Shell sort algorithm
 */
package dummy_project;

import edu.princeton.cs.algs4.*;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Shell {
    public static void sort(Comparable[] a) {
        // Sort a[] into increasing order
        int n = a.length;
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;        // 1, 4, 13, 40, 121, 364, 1093, ...
        while (h >= 1) {
            // h-sort the array
            for (int i = h; i < n; i++) {
                // Insert a[i] among a[i - h], a[i - 2 * h], a[i - 3 * h], ...
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
                h = h / 3;
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        // Print the array, on a single line
        for (Comparable c : a) {
            StdOut.print(c + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a) {
        // Test whether the array entries are in order
        for (int i = 1; i < a.length; i++) {
            if(less(a[i], a[i - 1])) return false;
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
