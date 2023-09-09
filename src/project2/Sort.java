package project2;

import edu.princeton.cs.algs4.*;

public class Sort {
    /**
     * Entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<>();
        while (!StdIn.isEmpty()) {                                  // continue until no more values entered
            String w = StdIn.readString();                          // entered string
            if (!d.isEmpty() && less(w, d.peekFirst())) {           // value is less than first string in deque
                d.addFirst(w);
            } else if (!d.isEmpty() && less(d.peekLast(), w)) {     // value is greater than last string in deque
                d.addLast(w);
            } else {
                LinkedStack<String> s = new LinkedStack<>();        // temp stack
                while(!d.isEmpty() && less(d.peekFirst(), w)) {     // add all values less than value to temp stack
                    String temp = d.removeFirst();
                    s.push(temp);
                }
                d.addFirst(w);                                      // add value to deque
                while(!s.isEmpty()) {
                    String temp = s.pop();                          // empty tech stack
                    d.addFirst(temp);                               // add to deque from tech stack
                }
            }
        }
        for (String s : d) {
            StdOut.println(s);
        }
    }

    /**
     * @param v a string
     * @param w another string
     * @return {@code true} if v is less than w according to their lexicographic order, and {@code false} otherwise.
     */
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
