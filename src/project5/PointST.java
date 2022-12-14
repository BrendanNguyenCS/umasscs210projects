package project5;

import edu.princeton.cs.algs4.*;

public interface PointST<Value> {
    // Returns true if this symbol table is empty, and false otherwise.
    boolean isEmpty();

    // Returns the number of key-value pairs in this symbol table.
    int size();

    // Inserts the given point and value into this symbol table.
    void put(Point2D p, Value value);

    // Returns the value associated with the given point in this symbol table, or null.
    Value get(Point2D p);

    // Returns true if this symbol table contains the given point, and false otherwise.
    boolean contains(Point2D p);

    // Returns all the points in this symbol table.
    Iterable<Point2D> points();

    // Returns all the points in this symbol table that are inside the given rectangle.
    Iterable<Point2D> range(RectHV rect);

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    Point2D nearest(Point2D p);

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    Iterable<Point2D> nearest(Point2D p, int k);

}
