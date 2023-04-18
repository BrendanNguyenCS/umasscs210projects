package dummy_project;

import edu.princeton.cs.algs4.*;

/**
 * Various merge sort algorithms
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Merge {
    private static Comparable[] aux;

    /**
     * Abstract in-place merge
     */
    private static void inplaceMerge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        if (hi + 1 - lo >= 0)
            System.arraycopy(a, lo, aux, lo, hi + 1 - lo);
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    /**
     * Top down merge sorting
     * <P>
     * Serves as the caller for the private sorting handler.
     */
    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        topdownSort(a, 0, a.length - 1);
    }

    private static void topdownSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        topdownSort(a, lo, mid);
        topdownSort(a, mid + 1, hi);
        inplaceMerge(a, lo, mid, hi);
    }

    /**
     * Bottom up merge sort
     */
    public static void bottomupSort(Comparable[] a) {
        // Do lg n passes of pairwise merges
        int n = a.length;
        aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len + len)
                inplaceMerge(a, lo, lo + len - 1,
                        Math.min(lo + len + len - 1, n - 1));
        }
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
}
