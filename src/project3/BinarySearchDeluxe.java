package project3;

import java.util.*;
import edu.princeton.cs.algs4.*;

public class BinarySearchDeluxe {
    /**
     * Returns the index of the first key in {@code a} that equals the search {@code key}, or {@code -1}, according
     * to the order induced by the comparator {@code c}
     * @param a the array of keys to search through
     * @param key the key to find
     * @param c the comparator
     * @return the {@link Key} of first index hit of the search
     * @throws NullPointerException if any of the inputs are {@code null}
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // corner case
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }

        // invalid or empty array length
        if (a.length == 0) {
            return -1;
        }

        int lo = 0;                                     // first index of a
        int hi = a.length - 1;                          // last index of a
        int mid;
        int index = -1;                                 // placeholder index
        while (lo <= hi) {
            mid = (hi - lo) / 2 + lo;                   // set mid index
            if (c.compare(key, a[mid]) > 0) {           // if key is higher than mid, check right of current mid
                lo = mid + 1;
            } else if (c.compare(key, a[mid]) < 0) {    // if key is lower than mid, check left of current mid
                hi = mid - 1;
            } else {                                    // if key is equal to mid
                if (mid == 0 || c.compare(key, a[mid - 1]) > 0) {
                    index = mid;
                    break;
                } else {                                // position left is equal or greater than key
                    hi = mid - 1;
                }
            }
        }
        return index;
    }

    /**
     * Returns the index of the last key in {@code a} that equals the search {@code key}, or {@code -1}, according to
     * the order induced by the comparator {@code c}
     * @param a the array of keys to search through
     * @param key the key to find
     * @param c the comparator
     * @return the {@link Key} of the last index hit of the search
     * @throws NullPointerException if any of the inputs are {@code null}
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // corner case
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }

        // invalid or empty array
        if (a.length == 0) {
            return -1;
        }

        int lo = 0;                                     // first index of a
        int hi = a.length - 1;                          // last index of a
        int mid;
        int index = -1;                                 // placeholder index
        while (lo <= hi) {
            mid = (hi - lo) / 2 + lo;                   // set mid index
            if (c.compare(key, a[mid]) > 0) {           // if key is higher than mid, check right of current mid
                lo = mid + 1;
            } else if (c.compare(key, a[mid]) < 0) {    // if key is lower than mid, check left of current mid
                hi = mid - 1;
            } else {                                    // if key is equal to mid
                if (mid == a.length - 1 || c.compare(key, a[mid + 1]) < 0) {
                    index = mid;
                    break;
                }  else {                               // position right is equal or less than key
                    lo = mid + 1;
                }
            }
        }
        return index;
    }

    /**
     * Unit tests the library. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
