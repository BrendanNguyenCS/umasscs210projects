package project5;

import edu.princeton.cs.algs4.*;

public class KdTreePointST<Value> implements PointST<Value> {
    /**
     * Reference to the root of the 2D tree
     */
    private Node root;
    /**
     * Number of nodes in the tree
     */
    private int n;

    /**
     * Constructs an empty symbol table
     */
    public KdTreePointST() {
        root = null;
        n = 0;
    }

    /**
     * @return {@code true} if this symbol table is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Getter for {@link #n}
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Inserts the given point and value into this symbol table
     * @param p the point
     * @param value the value
     * @throws NullPointerException if either p or value are {@code null}
     */
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        double negInf = -1 * Double.POSITIVE_INFINITY;
        double posInf = Double.POSITIVE_INFINITY;
        if (this.isEmpty()) {
            root = new Node(p, value, new RectHV(negInf, negInf, posInf, posInf));
            n++;
            return;
        }
        put(root, p, value, new RectHV(negInf, negInf, posInf, posInf), true);
    }

    /**
     * @param p the point
     * @return the value associated with the given point in this symbol table, or {@code null}
     * @throws NullPointerException if p is {@code null}
     */
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(root, p, true);
    }

    /**
     * @param p the point
     * @return {@code true} if this symbol table contains the given point, {@code false} otherwise
     * @throws NullPointerException if p is {@code null}
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return this.get(p) != null;
    }

    /**
     * @return all the points in this symbol table
     */
    public Iterable<Point2D> points() {
        LinkedQueue<Point2D> keys = new LinkedQueue<>();    // keys queue
        LinkedQueue<Node> queue = new LinkedQueue<>();      // nodes queue
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.p);                              // add current point to keys queue
            queue.enqueue(x.lb);                            // add left child tree to node queue
            queue.enqueue(x.rt);                            // add right child tree to node queue
        }
        return keys;
    }

    /**
     * @param rect the rectangle that is the bounds of the returned group
     * @return all the points in this symbol table that are inside the given rectangle
     * @throws NullPointerException if rect is {@code null}
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> queue = new LinkedQueue<>();
        range(root, rect, queue);
        return queue;
    }

    /**
     * @param p the point
     * @return the point in this symbol table that is different from and closest to the given point, or {@code null}
     * @throws NullPointerException if p is {@code null}
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        // default return value
        return nearest(root, p, root.p, true);
    }

    /**
     * @param p the point
     * @param k the maximum amount of points to return
     * @return up to k points from this symbol table that are different from and closest to the given point
     * @throws NullPointerException if p is {@code null}
     */
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        MaxPQ<Point2D> maxpq = new MaxPQ<>(p.distanceToOrder());
        nearest(root, p, k, maxpq, true);
        return maxpq;
    }

    /*
    In the helper methods that have lr as a parameter, its value specifies how to compare the point p with the point
    x.p. If true, the points are compared by their x-coordinates; otherwise, the points are compared by their y-coordinates.
    If the comparison of the coordinates (x or y) is true, the recursive call is made on x.lb; otherwise, the call is
    made on x.rt.
    */

    /**
     * Inserts the given point and value into the KdTree {@code x} having {@code rect} as its axis-aligned rectangle
     * @param x the KdTree
     * @param p the point
     * @param value the value
     * @param rect the rectangle that defines the alignment
     * @param lr the comparison measure ({@code true} calls for x-coordinate comparisons, {@code false} calls for y-coordinate comparisons)
     * @return a reference to the modified tree
     */
    private Node put(Node x, Point2D p, Value value, RectHV rect, boolean lr) {
        if (x == null) {
            n++;
            return new Node(p, value, rect);
        }
        if (lr) {

            double compare = Double.compare(p.x(), x.p.x());
            if (compare < 0.0) {                    // the current point is to the left of the node
                x.lb = put(x.lb, p, value, new RectHV(rect.xmin(), rect.ymin(), x.p.x(),
                        rect.ymax()), false);
            } else {                                // current point is to the right of the node
                x.rt = put(x.rt, p, value, new RectHV(x.p.x(), rect.ymin(), rect.xmax(),
                        rect.ymax()), false);
            }
        } else {
            double compare = Double.compare(p.y(), x.p.y());
            if (compare < 0.0) {                    // current point is below the node
                x.lb = put(x.lb, p, value, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(),
                        x.p.y()), true);
            } else {                                // current point is above the node
                x.rt = put(x.rt, p, value, new RectHV(rect.xmin(), x.p.y(), rect.xmax(),
                        rect.ymax()), true);
            }
        }
        return x;
    }

    /**
     * @param x the KdTree
     * @param p the point
     * @param lr the comparison measure ({@code true} calls for x-coordinate comparisons, {@code false} calls for y-coordinate comparisons)
     * @return the value associated with the given point in the KdTree x, or {@code null}
     */
    private Value get(Node x, Point2D p, boolean lr) {
        if (x == null) {
            return null;
        }
        if (lr) {   // check x coordinates
            double compare = Double.compare(p.x(), x.p.x());
            if (compare < 0.0) {
                return get(x.lb, p, false);     // check left tree y coordinates
            } else if (compare > 0.0) {
                return get(x.rt, p, false);     // check right tree y coordinates
            } else {
                return x.value;
            }
        } else {    // check y coordinates
            double compare = Double.compare(p.y(), x.p.y());
            if (compare < 0.0) {
                return get(x.lb, p, true);      // check left tree
            } else if (compare > 0.0) {
                return get(x.rt, p, true);      // check right tree
            } else {
                return x.value;
            }
        }
    }

    /**
     * Collects in the given queue all the points in the KdTree {@code x} that are inside {@code rect}
     * @param x the KdTree
     * @param rect the rectangle that is the bounds of the returned group
     * @param q the queue
     */
    private void range(Node x, RectHV rect, LinkedQueue<Point2D> q) {
        if (x == null) {
            return;
        }
        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }
        // pruning rule
        if (x.lb != null && rect.intersects(x.lb.rect)) {
            range(x.lb, rect, q);
        }
        if (x.rt != null && rect.intersects(x.rt.rect)) {
            range(x.rt, rect, q);
        }
    }

    /**
     * @param x the KdTree
     * @param p the point
     * @param nearest the closest point discovered so far
     * @param lr the comparison measure ({@code true} calls for x-coordinate comparisons, {@code false} calls for y-coordinate comparisons)
     * @return the point in the KdTree {@code x} that is closest to {@code p}, or {@code null}
     */
    private Point2D nearest(Node x, Point2D p, Point2D nearest, boolean lr) {
        Point2D min = nearest;  // tracking nearest point
        if (x == null) {
            return min;
        }
        if (!x.p.equals(p) && p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearest)) {
            min = x.p;
        }
        if (lr) {
            if (x.p.x() < p.x()) {
                min = nearest(x.rt, p, min, false);
                if (x.lb != null && (min.distanceSquaredTo(p) > x.lb.rect.distanceSquaredTo(p))) {
                    min = nearest(x.lb, p, min, false);
                }
            } else {
                min = nearest(x.lb, p, min, false);
                if (x.rt != null && (min.distanceSquaredTo(p) > x.rt.rect.distanceSquaredTo(p))) {
                    min = nearest(x.rt, p, min, false);
                }
            }
        } else {
            if (x.p.y() < p.y()) {
                min = nearest(x.rt, p, min, true);
                if (x.lb != null && (min.distanceSquaredTo(p) > x.lb.rect.distanceSquaredTo(p))) {
                    min = nearest(x.lb, p, min, true);
                }
            } else {
                min = nearest(x.lb, p, min, true);
                if (x.rt != null && (min.distanceSquaredTo(p) > x.rt.rect.distanceSquaredTo(p))) {
                    min = nearest(x.rt, p, min, true);
                }
            }
        }
        return min;
    }

    /**
     * Collects in the given {@link MaxPQ} up to {@code k} points from the KdTree {@code x} that are different from
     * and closest to {@code p}
     * @param x the KdTree
     * @param p the point
     * @param k the maximum amount of points to return
     * @param pq the max priority queue
     * @param lr the comparison measure ({@code true} calls for x-coordinate comparisons, {@code false} calls for y-coordinate comparisons)
     */
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, boolean lr) {
        if (x == null || pq.size() > k) {
            return;
        }
        if (!p.equals(x.p)) {
            pq.insert(x.p);
            if (pq.size() > k) {
                pq.delMax();
            }
        }
        if (lr) {
            if (p.x() > x.p.x()) {
                nearest(x.rt, p, k, pq, false);
                nearest(x.lb, p, k, pq, false);
            } else {
                nearest(x.lb, p, k, pq, false);
                nearest(x.rt, p, k, pq, false);
            }
        } else {
            if (p.y() > x.p.y()) {
                nearest(x.rt, p, k, pq, true);
                nearest(x.lb, p, k, pq, true);
            } else {
                nearest(x.lb, p, k, pq, true);
                nearest(x.rt, p, k, pq, true);
            }
        }
    }

    /**
     * A representation of node in a KdTree in two dimensions (ie, a 2dTree). Each node stores a
     * 2d point (the key), a value, an axis-aligned rectangle, and references to the left/bottom
     * and right/top subtrees
     */
    private class Node {
        /**
         * The point (key)
         */
        private final Point2D p;
        /**
         * The value
         */
        private final Value value;
        /**
         * The axis-aligned rectangle
         */
        private final RectHV rect;
        /**
         * The left/bottom subtree
         */
        private Node lb;
        /**
         * The right/top subtree
         */
        private Node rt;


        /**
         * Constructs a node given the point (key), the associated value, and the corresponding
         * axis-aligned rectangle
         * @param p the point (key)
         * @param value the value
         * @param rect the axis-aligned rectangle
         */
        Node(Point2D p, Value value, RectHV rect) {
            this.p = p;
            this.value = value;
            this.rect = rect;
        }
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<>();
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
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
