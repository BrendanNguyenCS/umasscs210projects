package project5;

import edu.princeton.cs.algs4.*;

public interface PointST<Value> {
    /**
     * @return {@code true} if this symbol table is empty, {@code false} otherwise
     */
    boolean isEmpty();

    /**
     * @return the number of key-value pairs in this symbol table
     */
    int size();

    /**
     * Inserts the given point and value into this symbol table
     * @param p the point
     * @param value the value
     */
    void put(Point2D p, Value value);

    /**
     * @param p the point
     * @return the value associated with the given point in this symbol table, or {@code null}
     */
    Value get(Point2D p);

    /**
     * @param p the point
     * @return {@code true} if this symbol table contains the given point, {@code false} otherwise
     */
    boolean contains(Point2D p);

    /**
     * @return all the points in this symbol table
     */
    Iterable<Point2D> points();

    /**
     * @param rect the rectangle that is the bounds of the returned group
     * @return all the points in this symbol table that are inside the given rectangle
     */
    Iterable<Point2D> range(RectHV rect);

    /**
     * @param p the point
     * @return the point in this symbol table that is different from and closest to the given point, or {@code null}
     */
    Point2D nearest(Point2D p);

    /**
     * @param p
     * @param k
     * @return up to {@code k} points from this symbol table that are different from and closest to the given point
     */
    Iterable<Point2D> nearest(Point2D p, int k);
}
