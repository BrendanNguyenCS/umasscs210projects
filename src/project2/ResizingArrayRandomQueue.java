package project2;

import java.util.*;
import edu.princeton.cs.algs4.*;

/**
 * A data type to represent a random queue, implemented using a resizing array as the underlying data structure
 * @param <T> a generic type
 */
public class ResizingArrayRandomQueue<T> implements Iterable<T> {
    /**
     * Array to store items in the queue
     */
    private T[] q;
    /**
     * Size of the queue
     */
    private int n;

    /**
     * Constructs an empty random queue
     */
    public ResizingArrayRandomQueue() {
        q = (T[]) new Object[2];
        n = 0;
    }

    /**
     * @return {@code true} if this queue is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Getter for {@link #n}
     * @return the number of items in this queue
     */
    public int size() {
        return n;
    }

    /**
     * Adds an item to the end of this queue
     * @param item the item to be added
     * @throws NullPointerException if the item is {@code null}
     */
    public void enqueue(T item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n] = item;
        n++;
    }

    /**
     * @return a random item from this queue
     * @throws NoSuchElementException if the queue is empty
     * @apiNote the deprecated {@link StdRandom#uniform(int) uniform()} method has been replaced
     */
    public T sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniformInt(n);
        return q[r];
    }

    /**
     * Removes and returns a random item form this queue
     * @return the item that is removed
     * @throws NoSuchElementException if the queue is empty
     * @apiNote the deprecated {@link StdRandom#uniform(int) uniform()} method has been replaced
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniformInt(n);
        T item = q[r];                          // temp variable for the random sample
        q[r] = q[n-1];                          // set the random sample element to the last element of the queue
        q[n-1] = null;                          // set the last element of the queue to null
        n--;
        if (n > 0 && n == q.length / 4) {       // check for resize
            resize(q.length / 2);
        }
        return item;
    }

    /**
     * @return an independent iterator to iterate over the items in this queue in random order
     */
    public Iterator<T> iterator() {
        return new RandomQueueIterator();
    }

    /**
     * @return the string representation of this queue
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    /**
     * An iterator, doesn't implement {@link #remove()} since it's optional
     */
    private class RandomQueueIterator implements Iterator<T> {
        /**
         * Array to store the items of {@link #q}
         */
        private final T[] items;
        /**
         * Index of the current item
         */
        private int current;

        /**
         * Constructs an iterator
         */
        public RandomQueueIterator() {
            items = (T[]) new Object[n];
            System.arraycopy(q, 0, items, 0, n);
            StdRandom.shuffle(items);
            current = 0;
        }

        /**
         * @return {@code true} if there are more items to iterate, {@code false} otherwise
         */
        public boolean hasNext() {
            return current < n;
        }

        /**
         * @return the next item
         * @throws NoSuchElementException if the iterator has already reached the final item
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            T item = items[current];
            current++;
            return item;
        }
    }

    /**
     * Resizes the underlying array.
     * @param max the new array length
     */
    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     * @apiNote the deprecated {@link StdRandom#uniform(int) uniform()} method has been replaced
     */
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<>();
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniformInt(10000);
            q.enqueue(r);
            sum += r;
        }
        int iterSumQ = 0;
        for (int x : q) {
            iterSumQ += x;
        }
        int dequeSumQ = 0;
        while (q.size() > 0) {
            dequeSumQ += q.dequeue();
        }
        StdOut.println("sum       = " + sum);
        StdOut.println("iterSumQ  = " + iterSumQ);
        StdOut.println("dequeSumQ = " + dequeSumQ);
        StdOut.println("iterSumQ + dequeSumQ == 2 * sum? " + (iterSumQ + dequeSumQ == 2 * sum));
    }
}
