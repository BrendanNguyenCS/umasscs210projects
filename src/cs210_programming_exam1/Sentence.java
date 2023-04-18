package cs210_programming_exam1;

import edu.princeton.cs.algs4.*;
import java.util.*;

/**
 * This comparable, iterable data type that represents a sentence, which is a sequence of words
 */
public class Sentence implements Comparable<Sentence>, Iterable<String> {
    /**
     * The sentence
     */
    private final String s;
    /**
     * The words in the sentence
     */
    private final String[] words;

    /**
     * Constructor
     * @param s the sentence string to split
     */
    public Sentence(String s) {
        this.s = s;
        // split string by words and assign to instance variable
        words = s.split("\\s+");
    }

    /**
     * @return the number of words in this sentence
     */
    public int wordCount() { return words.length; }

    /**
     * Compares two {@link Sentence}s
     * @param other the other {@link Sentence} object
     * @return {@code true} if they are the same, {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;
        if (other == this)
            return true;
        String[] a = this.words;
        String[] b = ((Sentence) other).words;
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i]))
                return false;
        }
        return true;
    }

    /**
     * @return the string representation of this {@link Sentence} object
     */
    public String toString() { return wordCount() + ":" + s; }

    /**
     * A comparison of this and other {@link Sentence} based on their lengths (i.e. character counts)
     * @param other the object to be compared
     * @return the difference in character lengths
     */
    public int compareTo(Sentence other) { return this.s.length() - other.s.length(); }

    /**
     * @return the comparator for comparing {@link Sentence}s based on their word count
     */
    public static Comparator<Sentence> wordCountOrder() { return new WordCountOrder(); }

    /**
     * @return the iterator for iterating over this sentence in reverse order
     */
    public Iterator<String> iterator() { return new ReverseIterator(); }

    /**
     * A comparator class for comparing {@link Sentence}s based on their word counts
     */
    private static class WordCountOrder implements Comparator<Sentence> {
        // Returns a comparison of sentences s1 and s2 based on their word count.
        public int compare(Sentence s1, Sentence s2) { return s1.wordCount() - s2.wordCount(); }
    }

    /**
     * An iterator class for iterating over a sentence in reverse order
     */
    private class ReverseIterator implements Iterator<String> {
        /**
         * The index of the current letter
         */
        private int i;

        /**
         * Constructor (Sets i to the last element of the {@code words} array)
         */
        public ReverseIterator() { i = wordCount() - 1; }

        /**
         * @return {@code true} if there are more words in the sentence, {@code false} otherwise
         */
        public boolean hasNext() { return i >= 0; }

        /**
         * @return the next word in the sentence
         */
        public String next() {
            String item = words[i];
            i--;
            return item;
        }
    }

    /**
     * Unit tests the data type [DO NOT EDIT].
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Sentence s1 = new Sentence("abc def ghi jkl mno");
        Sentence s2 = new Sentence("abcdefg hijklmn opqrst");
        Sentence s3 = new Sentence("abc def ghi jkl mno");
        StdOut.println("s1 = " + s1);
        StdOut.println("s2 = " + s2);
        StdOut.println("s1.equals(s2) = " + s1.equals(s2));
        StdOut.println("s2.equals(s3) = " + s2.equals(s3));
        StdOut.println("s1.compareTo(s2) = " + s1.compareTo(s2));
        StdOut.println("WordCountOrder::compare(s1, s2) = " +
                Sentence.wordCountOrder().compare(s1, s2));
        StdOut.print("reverse(s3): ");
        for (String word : s3)
            StdOut.print(word + " ");
        StdOut.println();
    }
}