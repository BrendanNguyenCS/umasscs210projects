package project2;

import edu.princeton.cs.algs4.*;

public class Sample {
    /**
     * Entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // standard input
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];
        // corner case
        if (!(mode.equals("+") || mode.equals("-")))
            throw new IllegalArgumentException("Illegal mode");
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<>();
        for (int i = lo; i <= hi; i++)                  // populate queue with all integers between lo and hi
            q.enqueue(i);
        if (mode.equals("+")) {                         // with replacement
            for (int i = 0; i < k; i++) {
                int sample = q.sample();                // take random sample of queue
                StdOut.println(sample);
            }
        } else {                                        // without replacement
            for (int i = 0; i < k; i++) {
                int sample = q.dequeue();               // dequeue sample from queue
                StdOut.println(sample);
            }
        }
    }
}
