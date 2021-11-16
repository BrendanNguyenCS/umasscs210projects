import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<String>();
        // continue until no more values entered
        while (!StdIn.isEmpty()) {
            // entered string
            String w = StdIn.readString();
            // value is less than first string in deque
            if (!d.isEmpty() && less(w, d.peekFirst())) {
                d.addFirst(w);
            // value is greater than last string in deque
            } else if (!d.isEmpty() && less(d.peekLast(), w)) {
                d.addLast(w);
            } else {
                LinkedStack<String> s = new LinkedStack<String>();      // temp stack
                // add all values that are less than value to temp stack
                while(!d.isEmpty() && less(d.peekFirst(), w)) {
                    String temp = d.removeFirst();
                    s.push(temp);
                }
                // add value to deque
                d.addFirst(w);
                // add all values from temp stack back into deque while emptying temp stack
                while(!s.isEmpty()) {
                    String temp = s.pop();
                    d.addFirst(temp);
                }
            }
        }
        // print out each value of deque on its own line
        for (String s : d) {
            StdOut.println(s);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
