package project2;

import java.util.Iterator;
import edu.princeton.cs.algs4.*;

/**
 * An immutable data type to systematically iterate over the first n primes
 */
public class Primes implements Iterable<Integer> {
    /**
     * Number of primes to iterate over
     */
    private final int n;

    /**
     * Constructs a {@link Primes} object given the number of primes needed
     * @param n the number of primes to be calculated
     */
    public Primes(int n) {
        this.n = n;
    }

    /**
     * @return an iterator to iterate over the first {@link #n} primes
     */
    public Iterator<Integer> iterator() {
        return new PrimesIterator();
    }

    /**
     * {@link Primes} iterator
     */
    private class PrimesIterator implements Iterator<Integer> {
        /**
         * Number of primes returned so far
         */
        private int count;
        /**
         * Current prime
         */
        private int p;

        /**
         * Constructs an iterator
         */
        public PrimesIterator() {
            this.count = 0;
            this.p = 2;
        }

        /**
         * @return {@code true} if there are more primes to be iterated, {@code false} otherwise
         */
        public boolean hasNext() {
            return count < n;
        }

        /**
         * @return the next prime
         */
        public Integer next() {
            count++;
            while (!isPrime(p)) {
                p++;
            }
            return p++;
        }

        /**
         * @param x a number
         * @return {@code true} if {@code x} is a prime number, {@code false} otherwise
         */
        private boolean isPrime(int x) {
            for (int i = 2; i <= x / i; i++) {
                if (x % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i : new Primes(n)) {
            StdOut.println(i);
        }
    }
}
