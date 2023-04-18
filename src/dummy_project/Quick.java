package dummy_project;

import edu.princeton.cs.algs4.*;

/**
 * Quick sort algorithm
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Quick {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    /**
     * Partitions an array {@code a} into {@code a[lo..j - 1], a[j], a[j + 1..hi]}
     * @param a the array
     * @param lo the lower end of the initial partition
     * @param hi the higher end of the initial partition
     * @return the index of the item which splits the partitions
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            // Scan right, scan left, check for scan complete, and exchange
            while (less(v, a[--j])) {
                if (j == lo)
                    break;
            }
            while (less(a[++i], v)) {
                if (i == hi)
                    break;
            }
            if (i >= j)
                break;
            exchange(a, i, j);
        }
        exchange(a, lo, j);
        return j;
    }

    private static boolean less(Comparable v, Comparable w) { return v.compareTo(w) < 0; }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * Prints an array on a single line
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

    private static class Quick3way {
        private static void sort(Comparable[] a, int lo, int hi) {
            if (hi <= lo) return;
            int lt = lo, i = lo + 1, gt = hi;
            Comparable v = a[lo];
            while (i <= gt) {
                int cmp = a[i].compareTo(v);
                if (cmp < 0)
                    exchange(a, lt++, i++);
                else if (cmp > 0)
                    exchange(a, i, gt--);
                else
                    i++;
            }
            // Now a[lo..lt - 1] < v = a[lt..gt] < a[gt + 1..hi]
            sort(a, lo, lt - 1);
            sort(a, gt + 1, hi);
        }
    }
}
