package project5;

import edu.princeton.cs.algs4.*;

public class BrutePointST<Value> implements PointST<Value> {
    /**
     * Underlying data structure
     */
    private final RedBlackBST<Point2D, Value> bst;

    /**
     * Constructs an empty symbol table
     */
    public BrutePointST() { bst = new RedBlackBST<>(); }

    /**
     * @return {@code true} if this symbol tabl eis empty, {@code false} otherwise
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return bst.size(); }

    /**
     * Inserts the given point and value into this symbol table
     * @param p the point
     * @param value the value
     */
    public void put(Point2D p, Value value) {
        if (p == null)
            throw new NullPointerException("p is null");
        if (value == null)
            throw new NullPointerException("value is null");
        bst.put(p, value);
    }

    /**
     * @param p the point
     * @return the value associated with the given point in this symbol table, or {@code null}
     */
    public Value get(Point2D p) {
        if (p == null)
            throw new NullPointerException("p is null");
        return bst.get(p);
    }

    /**
     * @param p the point
     * @return {@code true} if this symbol table contains the given point, {@code false} otherwise
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("p is null");
        return bst.contains(p);
    }

    /**
     * @return all the points in this symbol table
     */
    public Iterable<Point2D> points() { return this.bst.keys(bst.min(), bst.max()); }

    /**
     * @param rect the rectangle that are the bounds of the returned group
     * @return all the points in this symbol table that are inside the given rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("rect is null");
        // create queue of points within rect in bst
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        for (Point2D p : this.points()) {
            // if point is within rect bounds, enqueue it
            if (rect.contains(p))
                queue.enqueue(p);
        }
        return queue;
    }

    /**
     * @param p the point
     * @return the point in this symbol table that is different from and closest to the given point, or {@code null}
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("p is null");
        // default return value
        Point2D result = null;
        // you only want the first nearest point
        for (Point2D point : this.nearest(p, 1))
            result = point;
        return result;
    }

    /**
     * @param p the point
     * @param k the maximum amount of points to return
     * @return up to k points from this symbol table that are different from and closest to the given point
     */
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null)
            throw new NullPointerException("p is null");
        MinPQ<Point2D> minpq = new MinPQ<Point2D>(p.distanceToOrder());
        LinkedQueue<Point2D> q = new LinkedQueue<Point2D>();
        // counter
        int queueLength = 0;
        for (Point2D point : this.points())
            minpq.insert(point);
        while (queueLength < k) {
            Point2D temp = minpq.delMin();
            // making sure p is not returned
            if (!p.equals(temp)) {
                q.enqueue(temp);
                ++queueLength;
            }
        }
        return q;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect))
            StdOut.println("  " + p);
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k))
            StdOut.println("  " + p);
    }
}
