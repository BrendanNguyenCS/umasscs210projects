package cs210_programming_exam1;

import edu.princeton.cs.algs4.*;
import java.util.Comparator;
import java.util.Iterator;

// This comparable, iterable data type that represents a sentence, which is a sequence of words.
public class Sentence implements Comparable<Sentence>, Iterable<String> {
    private final String s;       // the sentence
    private final String[] words; // words in the sentence

    // Constructs a cs210_programming_exam1.Sentence object from the sentence s.
    public Sentence(String s) {
        this.s = s;
        // split string by words and assign to instance variable
        words = s.split("\\s+");
    }

    // Returns the number of words in this sentence.
    public int wordCount() {
        return words.length;
    }

    // Returns true if this sentence is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        // temps for comparison
        String[] a = this.words;
        String[] b = ((Sentence) other).words;
        // loop through both arrays
        for (int i = 0; i < a.length; i++) {
            // if any of them aren't equal, return false
            if (!a[i].equals(b[i])) {
                return false;
            }
        }

        /* Another way to do the above
         *
         * cs210_programming_exam1.Sentence a = words;
         * cs210_programming_exam1.Sentence b = (cs210_programming_exam1.Sentence) other;
         * for loop through 0 <= i < a.length
         * if(!a.words[i].equals(b.words[i])) return false
         */

        // both should be the same if this is reached
        return true;
    }

    // Returns a string representation of this sentence.
    public String toString() {
        return wordCount() + ":" + s;
    }

    // Returns a comparison of this and other sentence based on their lengths (ie, character
    // counts).
    public int compareTo(Sentence other) {
        return this.s.length() - other.s.length();
    }

    // Returns a comparator for comparing sentences based on their word count.
    public static Comparator<Sentence> wordCountOrder() {
        return new WordCountOrder();
    }

    // Returns an iterator for iterating over this sentence in reverse order.
    public Iterator<String> iterator() {
        return new ReverseIterator();
    }

    // A comparator for comparing sentences based on their word counts.
    private static class WordCountOrder implements Comparator<Sentence> {
        // Returns a comparison of sentences s1 and s2 based on their word count.
        public int compare(Sentence s1, Sentence s2) {
            return s1.wordCount() - s2.wordCount();
        }
    }

    // An iterator for iterating over a sentence in reverse order.
    private class ReverseIterator implements Iterator<String> {
        private int i; // index of current letter

        // Constructs an iterator.
        public ReverseIterator() {
            // set i to the last element of words array
            i = wordCount() - 1;
        }

        // Returns true if there are more words in the sentence, and false otherwise.
        public boolean hasNext() {
            return i >= 0;              // as long as the index is in bounds
        }

        // Returns the next word in the sentence.
        public String next() {
            String item = words[i];     // temp for current item value
            i--;                        // move to next item
            return item;                // return temp
        }
    }

    // Unit tests the data type [DO NOT EDIT].
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
        for (String word : s3) {
            StdOut.print(word + " ");
        }
        StdOut.println();
    }
}