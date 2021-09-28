import dsa.LinkedStack;
import stdlib.StdOut;

// A data type to represent a text editor buffer.
public class Buffer {
    protected LinkedStack<Character> left;  // chars left of cursor
    protected LinkedStack<Character> right; // chars right of cursor

    // Creates an empty buffer.
    public Buffer() {
        ...
    }

    // Inserts c at the cursor position.
    public void insert(char c) {
        ...
    }

    // Deletes and returns the character immediately ahead of the cursor.
    public char delete() {
        ...
    }

    // Moves the cursor k positions to the left.
    public void left(int k) {
        ...
    }

    // Moves the cursor k positions to the right.
    public void right(int k) {
        ...
    }

    // Returns the number of characters in this buffer.
    public int size() {
        ...
    }

    // Returns a string representation of the buffer with the "|" character (not part of the buffer)
    // at the cursor position.
    public String toString() {
        ...

        // Push chars from left into a temporary stack.
        ...

        // Append chars from temporary stack to sb.
        ...

        // Append "|" to sb.
        ...

        // Append chars from right to sb.
        ...

        // Return the string from sb.
        ...
    }

    // Unit tests the data type (DO NOT EDIT).
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
