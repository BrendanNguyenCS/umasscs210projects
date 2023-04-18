package project2;

import java.util.Iterator;
import edu.princeton.cs.algs4.*;

/**
 * An immutable data type to systematically iterate over binary strings of length n
 */
public class BinaryStrings implements Iterable<String> {
    /**
     * Need all binary strings of length n
     */
    private final int n;

    /**
     * Constructs a BinaryStrings object given the length of binary strings needed
     */
    public BinaryStrings(int n) { this.n = n; }

    /**
     * @return an iterator to iterate over binary strings of length n
     */
    public Iterator<String> iterator() { return new BinaryStringsIterator(); }

    /**
     * Binary strings iterator
     */
    private class BinaryStringsIterator implements Iterator<String> {
        /**
         * Number of binary strings returned so far
         */
        private int count;
        /**
         * Current number in decimal
         */
        private int p;

        /**
         * Constructs an iterator
         */
        public BinaryStringsIterator() {
            this.count = 0;
            this.p = 0;
        }

        /**
         * @return {@code true} if there are anymore binary strings to be iterated, {@code false} otherwise
         */
        public boolean hasNext() { return count <= Math.pow(2, n) - 1; }

        /**
         * @return the next binary string
         */
        public String next() {
            String s = binary(p++);
            count++;
            return s;
        }

        /**
         * @return the n-bit binary representation of {@code x}
         */
        private String binary(int x) {
            StringBuilder s = new StringBuilder(Integer.toBinaryString(x));
            int padding = n - s.length();
            for (int i = 1; i <= padding; i++)
                s.insert(0, "0");
            return s.toString();
        }
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (String s : new BinaryStrings(n))
            StdOut.println(s);
    }
}
