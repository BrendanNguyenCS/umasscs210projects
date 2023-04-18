package project5;

import edu.princeton.cs.algs4.*;

@SuppressWarnings({"unchecked"})
public class ArrayST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    /**
     * Keys in the symbol table
     */
    private Key[] keys;
    /**
     * The corresponding values
     */
    private Value[] values;
    /**
     * The number of key-value pairs
     */
    private int n;

    /**
     * Constructs an empty symbol table
     */
    public ArrayST() {
        keys = (Key[]) new Object[2];
        values = (Value[]) new Object[2];
        n = 0;
    }

    /**
     * @return {@code true} if this symbol table is empty, {@code false} otherwise.
     */
    public boolean isEmpty() { return n == 0; }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return n; }

    /**
     * Inserts the key-value pair into this symbol table
     * @param key the key
     * @param value the value
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        if (value == null)
            throw new IllegalArgumentException("value is null");
        // duplicates
        for (int i = 0; i < n; i++) {
            if (keys[i].equals(key)) {
                values[i] = value;
                return;
            }
        }
        // increase size if needed
        if (n >= values.length)
            resize(2 * n);
        keys[n] = key;
        values[n++] = value;
    }

    /**
     * @param key the key
     * @return the value associated with {@code key} in this symbol table, or {@code null}
     */
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        for (int i = 0; i < n; i++) {
            if (keys[i].equals(key))
                return values[i];
        }
        return null;
    }

    /**
     * @param key the key
     * @return {@code true} if this symbol table contains key, {@code false} otherwise
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        return get(key) != null;
    }

    /**
     * Deletes key and the associated value from thsi symbol table
     * @param key the key
     */
    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        int i;
        for (i = 0; i < n; i++) {
            if (keys[i].equals(key))
                break;
        }
        if (i == n)
            return;
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        n--;
        keys[n] = null;
        values[n] = null;
        if (n > 0 && n == keys.length / 4)
            resize(keys.length / 2);
    }

    /**
     * @return all the keys in this symbol table
     */
    public Iterable<Key> keys() {
        LinkedQueue<Key> queue = new LinkedQueue<>();
        for (int i = 0; i < n; i++)
            queue.enqueue(keys[i]);
        return queue;
    }

    /**
     * Resizes the underlying arrays
     * @param capacity the new size
     */
    private void resize(int capacity) {
        Key[] tempk = (Key[]) new Object[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
