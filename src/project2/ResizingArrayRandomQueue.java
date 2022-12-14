package project2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;

// A data type to represent a random queue, implemented using a resizing array as the underlying
// data structure.
public class ResizingArrayRandomQueue<T> implements Iterable<T> {
    private T[] q; // array to store items in the queue
    private int n; // size of the queue

    // Constructs an empty random queue.
    @SuppressWarnings("unchecked")
    public ResizingArrayRandomQueue() {
        q = (T[]) new Object[2];
        n = 0;
    }

    // Returns true if this queue is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of items in this queue.
    public int size() {
        return n;
    }

    // Adds item to the end of this queue.
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

    // Returns a random item from this queue.
    public T sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniformInt(n);
        return q[r];
    }

    // Removes and returns a random item from this queue.
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniformInt(n);
        // temp variable for the random sample
        T item = q[r];
        // set the random sample element to the last element of the queue
        q[r] = q[n-1];
        // set the last element of the queue to null
        q[n-1] = null;
        // decrement n
        n--;

        // check for resize
        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }

        return item;
    }

    // Returns an independent iterator to iterate over the items in this queue in random order.
    public Iterator<T> iterator() {
        return new RandomQueueIterator();
    }

    // Returns a string representation of this queue.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<T> {
        private final T[] items; // array to store the items of q
        private int current; // index of current item

        // Constructs an iterator.
        @SuppressWarnings("unchecked")
        public RandomQueueIterator() {
            items = (T[]) new Object[n];
            // copy q's elements into items
            System.arraycopy(q, 0, items, 0, n);
            // shuffle items
            StdRandom.shuffle(items);
            current = 0;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current < n;
        }

        // Returns the next item.
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            T item = items[current];
            current++;
            return item;
        }
    }

    // Resizes the underlying array.
    @SuppressWarnings("unchecked")
    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Unit tests the data type. [DO NOT EDIT]
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
