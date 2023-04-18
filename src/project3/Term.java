package project3;

import java.util.*;
import edu.princeton.cs.algs4.*;

public class Term implements Comparable<Term> {
    /**
     * Query string
     */
    private final String query;
    /**
     * Query weight
     */
    private final long weight;

    /**
     * Constructs a term given the associated query string, having weight 0
     * @param query the query string
     */
    public Term(String query) {
        // corner case
        if (query == null)
            throw new NullPointerException("query is null");
        this.query = query;
        this.weight = 0;
    }

    /**
     * Constructs a term given the associated query string and weight
     * @param query the query string
     * @param weight the weight
     */
    public Term(String query, long weight) {
        // corner cases
        if (query == null)
            throw new NullPointerException("query is null");
        if (weight < 0)
            throw new IllegalArgumentException("Illegal weight");
        this.query = query;
        this.weight = weight;
    }

    /**
     * @return the string representation of this term
     */
    public String toString() { return weight + "\t" + query; }

    /**
     * Compares two terms by query
     * @param other the object to be compared
     * @return {@code -1} if this query string is smaller lexicographically, {@code 1} if this query string is larger,
     * {@code 0} otherwise
     */
    public int compareTo(Term other) { return this.query.compareTo(other.query); }

    /**
     * @return a comparator for comparing two terms in reverse order of their weights
     */
    public static Comparator<Term> byReverseWeightOrder() { return new ReverseWeightOrder(); }

    /**
     * @param r the length of the prefixes to compare
     * @return a comparator for comparing two terms by their prefixes of length {@code r}
     */
    public static Comparator<Term> byPrefixOrder(int r) {
        // corner case
        if (r < 0)
            throw new IllegalArgumentException("Illegal r");
        return new PrefixOrder(r);
    }

    /**
     * A class that is a reverse-weight comparator
     */
    private static class ReverseWeightOrder implements Comparator<Term> {
        /**
         * Compares terms by weights in reverse order
         * @param v the first object to be compared
         * @param w the second object to be compared
         * @return {@code -1} if {@code v} has a larger weight, {@code 1} if {@code w} has a larger weight,
         * {@code 0} otherwise
         */
        public int compare(Term v, Term w) { return Long.compare(w.weight, v.weight); }
    }

    /**
     * A class that is a prefix-order comparator
     */
    private static class PrefixOrder implements Comparator<Term> {
        /**
         * Prefix length
         */
        private final int r;

        /**
         * Constructs a new prefix order given the prefix length
         * @param r the prefix length
         */
        PrefixOrder(int r) { this.r = r; }

        /**
         * Compares terms by their prefixes of length {@code r}
         * @param v the first object to be compared
         * @param w the second object to be compared
         * @return {@code -1} if {@code v}'s prefix is smaller lexicographically, {@code 1} if {@code w}'s prefix is
         * smaller, {@code 0} otherwise
         */
        public int compare(Term v, Term w) {
            String a = v.query.substring(0, Math.min(r, v.query.length()));
            String b = w.query.substring(0, Math.min(r, w.query.length()));
            return a.compareTo(b);
        }
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++)
            StdOut.println(terms[i]);
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++)
            StdOut.println(terms[i]);
    }
}
