package project4;

import edu.princeton.cs.algs4.*;

public class CertifyHeap {
    /**
     * @param a an array
     * @return {@code true} if {@code a[]} represents a max-heap, {@code false} otherwise
     */
    public static boolean isMaxHeap(Comparable[] a) {
        int n = a.length;

        // For each node 1 <= i <= n / 2, if a[i] is less than either of its children, return
        // false, meaning a[] does not represent a max-heap. If no such i exists, return true.
        for (int i = 1; i <= n / 2; i++) {
            int l = 2 * i, r = 2 * i + 1;
            if (l < n && (less(a[i], a[l]) || r < n && less(a[i], a[r]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param v a {@link Comparable} object
     * @param w a {@link Comparable} object
     * @return {@code true} if {@code v} is less than {@code w}, {@code false} otherwise
     */
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    /**
     * Unit tests the library. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(isMaxHeap(a));
    }
}
