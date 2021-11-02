/**
 * Various merge sort algorithms
 */

import stdlib.StdIn;
import stdlib.StdOut;

public class Merge {
    private static Comparable[] aux;

    // Abstract in-place merge
    private static void inplaceMerge(Comparable[] a, int lo, int mid, int hi) {
        // Merge a[lo...hi] with a[mid + 1...hi]
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    // Top down merge sorting
    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        topdownSort(a, 0, a.length - 1);
    }

    private static void topdownSort(Comparable[] a, int lo, int hi) {
        // Sort a[lo..hi]
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        topdownSort(a, lo, mid);
        topdownSort(a, mid + 1, hi);
        inplaceMerge(a, lo, mid, hi);
    }

    // Bottom up merge sort
    public static void bottomupSort(Comparable[] a) {
        // Do lg n passes of pairwise merges
        int n = a.length;
        aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len + len) {
                inplaceMerge(a, lo, lo + len - 1, Math.min(lo + len + len - 1, n - 1));
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
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
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
