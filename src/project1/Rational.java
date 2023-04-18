package project1;

import edu.princeton.cs.algs4.*;

public class Rational {
    /**
     * Numerator
     */
    private final long x;
    /**
     * Denominator
     */
    private final long y;

    /**
     * Constructs a rational number whose numerator is {@code x} and denominator is 1
     */
    public Rational(long x) {
        this.x = x;
        this.y = 1;
    }

    /**
     * Constructs a rational number given its numerator {@code x} and denominator {@code y}
     */
    public Rational(long x, long y) {
        this.x = x / gcd(x, y);
        this.y = y / gcd(x, y);
    }

    /**
     * The sum of rationals {@code a/b} and {@code c/d} is the rational {@code (ad+bc)/bd}.
     * @param other another {@link Rational} number
     * @return the sum of this rational number and {@code other}
     */
    public Rational add(Rational other) {
        return new Rational(x * other.y + y * other.x, y * other.y);
    }

    /**
     * The product of rationals {@code a/b} and {@code c/d} is the rational {@code ac/bd}.
     * @param other another {@link Rational} number
     * @return the product of this rational number and {@code other}
     */
    public Rational multiply(Rational other) {
        return new Rational(x * other.x, y * other.y);
    }

    /**
     * Rationals {@code a/b} and {@code c/d} are equal iff {@code a == c} and {@code b == d}.
     * @param other another {@link Rational} number
     * @return {@code true} if this rational number is equal to {@code other}, {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == null || (other.getClass() != this.getClass()))
            return false;
        if (other == this)
            return true;
        Rational a = this, b = (Rational) other;
        return a.x == b.x && a.y == b.y;
    }

    /**
     * @return the string representation of this rational number
     */
    public String toString() {
        long a = x, b = y;
        if (a == 0 || b == 1)
            return String.valueOf(a);
        if (b < 0) {
            a *= -1;
            b *= -1;
        }
        return a + "/" + b;
    }

    /**
     * @param p a number
     * @param q a number
     * @return the greatest common divisor, computed using Euclid's algorithm
     */
    private static long gcd(long p, long q) { return q == 0 ? p : gcd(q, p % q); }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Rational total = new Rational(0);
        Rational term = new Rational(1);
        for (int i = 1; i <= n; i++) {
            total = total.add(term);
            term = term.multiply(new Rational(1, 2));
        }
        Rational expected = new Rational((long) Math.pow(2, n) - 1, (long) Math.pow(2, n - 1));
        StdOut.printf("a           = 1 + 1/2 + 1/4 + ... + 1/2^%d = %s\n", n, total);
        StdOut.printf("b           = (2^%d - 1) / 2^(%d - 1) = %s\n", n, n, expected);
        StdOut.printf("a.equals(b) = %b\n", total.equals(expected));
    }
}
