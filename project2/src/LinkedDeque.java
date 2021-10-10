import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
// list as the underlying data structure.
public class LinkedDeque<Item> implements Iterable<Item> {
    private int n; // size of the deque
    private Node first; // the first item in the deque
    private Node last; // the last item in the deque

    // Constructs an empty deque.
    public LinkedDeque() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    // Returns true if this deque is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of items in this deque.
    public int size() {
        return n;
    }

    // Adds item to the front of this deque.
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        Node newFirst = new Node();
        newFirst.item = item;
        if (isEmpty()) {
            // both first and last will be the same value since it will be the only element in
            // the queue
            last = newFirst;
            first = newFirst;
        } else {
            // new Node's next is the old first Node
            newFirst.next = first;
            // the old first Node's previous is the new first
            first.prev = newFirst;
            // set first to new Node
            first = newFirst;
        }
        // Increment n by 1
        n++;
    }

    // Adds item to the back of this deque.
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        Node newLast = new Node();
        newLast.item = item;
        if (isEmpty()) {
            // both first and last will be the same value since it will be the only element in
            // the queue
            last = newLast;
            first = newLast;
        } else {
            // new last Node's previous is the old last Node
            newLast.prev = last;
            // the old last Node's next will be the new Node
            last.next = newLast;
            // set last to new Node
            last = newLast;
        }

        // Increment n
        n++;
    }

    // Returns the item at the front of this deque.
    public Item peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return first.item;
    }

    // Removes and returns the item at the front of this deque.
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        if (n == 1) {
            // Removing a Node in a queue with only 1 Node will result in an empty queue
            first = null;
            last = null;
        } else {
            // set new first to the current first's next Node
            first = first.next;
            // set the new first's previous to null
            first.prev = null;
        }

        // decrement n
        n--;
        return item;
    }

    // Returns the item at the back of this deque.
    public Item peekLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return last.item;
    }

    // Removes and returns the item at the back of this deque.
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        // checking to see if there is more than 1 item in the deque
        if (n == 1) {
            // Removing a Node in a queue with only 1 Node will result in an empty queue
            first = null;
            last = null;
        } else {
            // set new last to the current last's previous Node
            last = last.prev;
            // set new last's next Node to null
            last.next = null;
        }

        // decrement n
        n--;
        return item;
    }

    // Returns an iterator to iterate over the items in this deque from front to back.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Returns a string representation of this deque.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // A deque iterator.
    private class DequeIterator implements Iterator<Item> {
        private Node current; // reference to the current node in the iterator

        // Constructs an iterator.
        public DequeIterator() {
            current = first;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current != null;
        }

        // Returns the next item.
        public Item next() {
            if (isEmpty()) {
                throw new NoSuchElementException("Iterator is exhausted");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // A data type to represent a doubly-linked list. Each node in the list stores a generic item
    // and references to the next and previous nodes in the list.
    private class Node {
        private Item item;  // the item
        private Node next;  // the next node
        private Node prev;  // the previous node
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}
