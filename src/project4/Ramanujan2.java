package project4;

import edu.princeton.cs.algs4.*;

public class Ramanujan2 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        MinPQ<Pair> pq = new MinPQ<>();
        for (int i = 1; cubed(i) < n; i++) {
            pq.insert(new Pair(i, i + 1));
        }

        Pair prev, curr = null;
        while (!pq.isEmpty()) {
            prev = curr;
            curr = pq.delMin();
            if (prev != null && prev.sumOfCubes == curr.sumOfCubes && prev.sumOfCubes <= n) {
                String s = "%d = %d^3 + %d^3 = %d^3 + %d^3\n";
                StdOut.printf(s, prev.sumOfCubes, prev.i, prev.j, curr.i, curr.j);
            }

            if (cubed(curr.j) < n) {
                pq.insert(new Pair(curr.i, curr.j + 1));
            }
        }
    }

    // Returns an integer cubed (or to the 3rd power)
    public static int cubed(int x) {
        return x * x * x;
    }

    // A data type that encapsulates a pair of numbers (i, j) and the sum of their cubes.
    private static class Pair implements Comparable<Pair> {
        private final int i;          // first number in the pair
        private final int j;          // second number in the pair
        private final int sumOfCubes; // i^3 + j^3

        // Constructs a pair (i, j).
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        // Returns a comparison of pairs this and other based on their sum-of-cubes values.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }
}
