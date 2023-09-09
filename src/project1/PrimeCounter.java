package project1;

import edu.princeton.cs.algs4.*;

public class PrimeCounter {
    /**
     * Entry point. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(primes(n));
    }

    /**
     * For each {@code 2 <= i <= x/i}, if {@code x} is divisible by {@code i}, then {@code x} is not a prime. If no such
     * {@code i} exists, then {@code x} is a prime.
     * @param x a number
     * @return {@code true} if x is prime, {@code false} otherwise
     */
    private static boolean isPrime(int x) {
        for (int i = 2; i <= x/i; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * For each {@code 2 <= i <= n}, use {@link #isPrime(int) isPrime} to test if {@code i} is prime. Increments
     * a count if so.
     * @param n a number
     * @return the number of primes <= n
     */
    private static int primes(int n) {
        int primes = 0;
        for (int i = 2; i <= n; i++) {
            primes += isPrime(i) ? 1 : 0;
        }
        return primes;
    }
}
