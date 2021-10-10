import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        // standard input
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];

        // corner case
        if (!(mode.equals("+") || mode.equals("-"))) {
            throw new IllegalArgumentException("Illegal mode");
        }

        // queue declaration and initialization
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        for (int i = lo; i <= hi; i++) {
            // populate queue with all integers between lo and hi
            q.enqueue(i);
        }

        if (mode.equals("+")) {  // with replacement
            for (int i = 0; i < k; i++) {
                // take random sample of queue
                int sample = q.sample();
                // print out sample value
                StdOut.println(sample);
            }
        } else {  // without replacement
            for (int i = 0; i < k; i++) {
                // dequeue sample from queue
                int sample = q.dequeue();
                // print out sample value
                StdOut.println(sample);
            }
        }
    }
}
