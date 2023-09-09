package project3;

import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class Autocomplete {
    /**
     * Array of terms
     */
    private final Term[] terms;

    /**
     * Constructs an autocomplete data structure from an array of terms
     * @param terms the terms to calculate data
     * @throws NullPointerException if the terms are {@code null}
     */
    public Autocomplete(Term[] terms) {
        // corner case
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }

        // defensive copy of parameter
        this.terms = new Term[terms.length];
        System.arraycopy(terms, 0, this.terms, 0, terms.length);

        // lexicographic sort
        Arrays.sort(this.terms);
    }

    /**
     * @param prefix the prefix to find
     * @return all terms that start with prefix, in descending order of their weights
     * @throws NullPointerException if the prefix is {@code null}
     */
    public Term[] allMatches(String prefix) {
        // corner case
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        // first and last instance of prefix
        int i = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix),
                Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix),
                Term.byPrefixOrder(prefix.length()));

        // number of matching terms
        int n = numberOfMatches(prefix);
        // array to hold results
        Term[] matches = new Term[n];

        // if results are found, copy matches from terms into matches
        if (i != -1 && j != -1) {
            System.arraycopy(terms, i, matches, 0, n);
        }
        // sort matches by reverse weight
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    /**
     * @param prefix the prefix to find
     * @return the number of terms that start with prefix
     * @throws NullPointerException if the prefix is {@code null}
     */
    public int numberOfMatches(String prefix) {
        // corner case
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }

        Term search = new Term(prefix);
        int i = BinarySearchDeluxe.firstIndexOf(terms, search, Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(terms, search, Term.byPrefixOrder(prefix.length()));
        return j - i + 1;
    }

    /**
     * Unit tests the data types. [DO NOT EDIT]
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
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
