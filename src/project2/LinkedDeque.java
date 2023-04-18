package project2;

import java.util.*;
import edu.princeton.cs.algs4.*;

/**
 * A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
 * list as the underlying data structure.
 * @param <Item> a generic type
 */
public class LinkedDeque<Item> implements Iterable<Item> {
    /**
     * Size of the deque
     */
    private int n;
    /**
     * The first item in the deque
     */
    private Node first;
    /**
     * The last item in the deque
     */
    private Node last;

    /**
     * Constructs an empty deque
     */
    public LinkedDeque() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    /**
     * @return {@code true} if this deque is empty, {@code false} otherwise.
     */
    public boolean isEmpty() { return n == 0; }

    /**
     * @return the number of items in this deque
     */
    public int size() { return n; }

    /**
     * Adds an item to the front of this deque
     * @param item the item to be added
     */
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException("item is null");
        Node newFirst = new Node();
        newFirst.item = item;
        if (isEmpty()) {
            last = newFirst;                    // last will be the same value as first
            first = newFirst;
        } else {
            newFirst.next = first;              // new Node's next is the old first Node
            first.prev = newFirst;              // the old first Node's previous is the new first
            first = newFirst;                   // set first to new Node
        }
        n++;
    }

    /**
     * Adds an item to the back of this deque
     * @param item the item to be added
     */
    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException("item is null");
        Node newLast = new Node();
        newLast.item = item;
        if (isEmpty()) {
            last = newLast;                     // last will be the same value as first
            first = newLast;
        } else {
            newLast.prev = last;                // new last Node's previous is the old last Node
            last.next = newLast;                // the old last Node's next will be the new Node
            last = newLast;                     // set last to new Node
        }
        n++;
    }

    /**
     * @return the item at the front of this deque
     */
    public Item peekFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        return first.item;
    }

    /**
     * Removes and returns the item at the front of this deque
     * @return the item at the front of this deque that is removed
     */
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        if (n == 1) {                           // Removing a Node in a queue with only 1 Node results in an empty queue
            first = null;
            last = null;
        } else {
            first = first.next;                 // set new first to the current first's next Node
            first.prev = null;                  // set the new first's previous to null
        }
        n--;
        return item;
    }

    /**
     * @return the item at the back of this deque
     */
    public Item peekLast() {
        if (this.isEmpty())
            throw new NoSuchElementException("Deque is empty");
        return last.item;
    }

    /**
     * Removes and returns the item at the back of this deque
     * @return the item at the back of this deque that is removed
     */
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        if (n == 1) {                           // Removing a Node in a queue with only 1 Node results in an empty queue
            first = null;
            last = null;
        } else {
            last = last.prev;                   // set new last to the current last's previous Node
            last.next = null;                   // set new last's next Node to null
        }
        n--;
        return item;
    }

    /**
     * @return an iterator to iterate over the items in this deque from front to back
     */
    public Iterator<Item> iterator() { return new DequeIterator(); }

    /**
     * @return the string representation of this deque
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    /**
     * An iterator for the deque
     */
    private class DequeIterator implements Iterator<Item> {
        /**
         * Reference to the current node in the iterator
         */
        private Node current;

        /**
         * Constructs an iterator
         */
        public DequeIterator() { current = first; }

        /**
         * @return {@code true} if there are more items to iterate, {@code false} otherwise
         */
        public boolean hasNext() { return current != null; }

        /**
         * @return the next item
         */
        public Item next() {
            if (isEmpty())
                throw new NoSuchElementException("Iterator is exhausted");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * A data type to represent a doubly-linked list. Each node in the list stores a generic item
     * and references to the enxt and previous nodes in the list
     */
    private class Node {
        /**
         * The item
         */
        private Item item;
        /**
         * The next node
         */
        private Node next;
        /**
         * The previous node
         */
        private Node prev;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniformInt(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--)
            deque.addFirst(quote.charAt(i));
        for (int i = 0; i < quote.substring(r).length(); i++)
            deque.addLast(quote.charAt(r + i));
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque)
            StdOut.print(c);
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniformDouble();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s))
                deque.removeFirst();
            else
                deque.removeLast();
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}
