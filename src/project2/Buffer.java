package project2;

import edu.princeton.cs.algs4.*;

/**
 * A data type to represent a text editor buffer
 */
public class Buffer {
    /**
     * Characters to the left of the cursor
     */
    protected LinkedStack<Character> left;
    /**
     * Characters to the right of the cursor
     */
    protected LinkedStack<Character> right;

    /**
     * Creates an empty buffer
     */
    public Buffer() {
        left = new LinkedStack<Character>();
        right = new LinkedStack<Character>();
    }

    /**
     * Inserts {@code c} at the cursor position
     */
    public void insert(char c) {
        left.push(c);
    }

    /**
     * Deletes and returns the character immediately ahead of the cursor
     */
    public char delete() {
        return right.pop();
    }

    /**
     * Moves the cursor {@code k} positions to the left
     */
    public void left(int k) {
        for (int i = 0; i < k; i++) {
            char c = left.pop();
            right.push(c);
        }
    }

    /**
     * Moves the cursor {@code k} positions to the right
     */
    public void right(int k) {
        for (int i = 0; i < k; i++) {
            char c = right.pop();
            left.push(c);
        }
    }

    /**
     * @return the number of characters in this buffer
     */
    public int size() {
        return left.size() + right.size();
    }

    /**
     * @return the string representation of the buffer with the "{@code |}" character (not part of the buffer) at the cursor position
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LinkedStack<Character> ts = new LinkedStack<>();    // Push chars from left into a temporary stack.
        for (char c : left) {
            ts.push(c);
        }
        for (char c : ts) {                                 // Append chars from temporary stack to sb.
            sb.append(c);
        }
        sb.append("|");                                     // Append "|" to sb.
        for (char c : right) {                              // Append chars from right to sb.
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Unit tests the data type [DO NOT EDIT].
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Buffer buf = new Buffer();
        String s = "There is grandeur in this view of life, with its several powers, having been " +
                "originally breathed into a few forms or into one; and that, whilst this planet " +
                "has gone cycling on according to the fixed law of gravity, from so simple a " +
                "beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        buf.left(buf.size());
        buf.right(97);
        s = "by the Creator ";
        for (int i = 0; i < s.length(); i++) {
            buf.insert(s.charAt(i));
        }
        buf.right(228);
        buf.delete();
        buf.insert('-');
        buf.insert('-');
        buf.left(342);
        StdOut.println(buf);
    }
}
