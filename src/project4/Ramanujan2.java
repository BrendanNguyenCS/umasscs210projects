package project4;

import edu.princeton.cs.algs4.*;

public class Ramanujan2 {
    /**
     * Entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        MinPQ<Pair> pq = new MinPQ<>();
        for (int i = 1; cubed(i) < n; i++)
            pq.insert(new Pair(i, i + 1));

        Pair prev, curr = null;
        while (!pq.isEmpty()) {
            prev = curr;
            curr = pq.delMin();
            if (prev != null && prev.sumOfCubes == curr.sumOfCubes && prev.sumOfCubes <= n) {
                String s = "%d = %d^3 + %d^3 = %d^3 + %d^3\n";
                StdOut.printf(s, prev.sumOfCubes, prev.i, prev.j, curr.i, curr.j);
            }
            if (cubed(curr.j) < n)
                pq.insert(new Pair(curr.i, curr.j + 1));
        }
    }

    /**
     * @param x a number
     * @return an integer cubed (or to the 3rd power)
     */
    public static int cubed(int x) { return x * x * x; }

    /**
     * A data type that encapsulates a pair of numbers ({@code i}, {@code j}) and the sum of their cubes
     */
    private static class Pair implements Comparable<Pair> {
        /**
         * The first number in the pair
         */
        private final int i;
        /**
         * The second number in the pair
         */
        private final int j;
        /**
         * The sum of their cubes ({@code i}^3 + {@code j}^3)
         */
        private final int sumOfCubes;

        /**
         * Constructs a pair ({@code i}, {@code j})
         */
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        /**
         * Compares two pairs of numbers based on their sum-of-cube values
         * @param other the object to be compared
         * @return the difference in the sum-of-cubes of the pairs ({@code this} - {@code other})
         */
        public int compareTo(Pair other) { return sumOfCubes - other.sumOfCubes; }
    }
}
