package project2;

import edu.princeton.cs.algs4.*;

public class MinMax {
    /**
     * @param first the first node in the linked list
     * @return the minimum value in the given linked list
     */
    public static int min(Node first) {
        int min = Integer.MAX_VALUE;
        for (Node x = first; x != null; x = x.next) {
            if (x.item < min)
                min = x.item;
        }
        return min;
    }

    /**
     * @param first the first node in the linked list
     * @return the maximum value in the given linked list
     */
    public static int max(Node first) {
        int max = Integer.MIN_VALUE;
        for (Node x = first; x != null; x = x.next) {
            if (x.item > max)
                max = x.item;
        }
        return max;
    }

    /**
     * A data type to represent a linked list. Each node in the list stores an integer item and a
     * reference to the next node in the list
     */
    protected static class Node {
        /**
         * The item
         */
        protected int item;
        /**
         * The next node
         */
        protected Node next;
    }

    /**
     * Unit tests the library. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int[] items = new int[1000];
        for (int i = 0; i < 1000; i++)
            items[i] = StdRandom.uniformInt(-10000, 10000);
        Node first = null;
        for (int item : items) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }
        StdOut.println("min(first) == StdStats.min(items)? " + (min(first) == StdStats.min(items)));
        StdOut.println("max(first) == StdStats.max(items)? " + (max(first) == StdStats.max(items)));
    }
}
