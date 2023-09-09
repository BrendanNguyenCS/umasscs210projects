package project3;

import java.util.*;
import edu.princeton.cs.algs4.*;

public class Point3D implements Comparable<Point3D> {
    /**
     * X coordinate
     */
    private final double x;
    /**
     * Y coordinate
     */
    private final double y;
    /**
     * Z coordinate
     */
    private final double z;

    /**
     * Constructs a point in 3D given its coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param other the other {@link Point3D}
     * @return the Euclidean distance between this point and {@code other}
     */
    public double distance(Point3D other) {
        return Math.sqrt(Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y),
                2) + Math.pow((this.z - other.z), 2));
    }

    /**
     * @return the string representation of this {@link Point3D} object
     */
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Compares this point with {@code other} based on their respective distances to teh origin (0, 0, 0)
     * @param other the object to be compared
     * @return {@code -1} if this point is closer, {@code 1} if the other point is closer, {@code 0} if they are the same
     */
    public int compareTo(Point3D other) {
        Point3D origin = new Point3D(0, 0, 0);
        double a = distance(origin);
        double b = other.distance(origin);
        return Double.compare(a, b);
    }

    /**
     * @return a comparator to compare two points by their x-coordinates
     */
    public static Comparator<Point3D> xOrder() {
        return new XOrder();
    }

    /**
     * @return a comparator to compare two points by their y-coordinates
     */
    public static Comparator<Point3D> yOrder() {
        return new YOrder();
    }

    /**
     * @return a comparator to compare two points by their z-coordinates
     */
    public static Comparator<Point3D> zOrder() {
        return new ZOrder();
    }

    /**
     * A class that compares two points based on their x-coordinates
     */
    private static class XOrder implements Comparator<Point3D> {
        /**
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return a comparison of {@code p1} and {@code p2} by their x-coordinate
         */
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.x, p2.x);
        }
    }

    /**
     * A class that compares two points based on their y-coordinates
     */
    private static class YOrder implements Comparator<Point3D> {
        /**
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return a comparison of {@code p1} and {@code p2} by their y-coordinate
         */
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.y, p2.y);
        }
    }

    /**
     * A class that compares two points based on their z-coordinates
     */
    private static class ZOrder implements Comparator<Point3D> {
        /**
         * @param p1 the first object to be compared.
         * @param p2 the second object to be compared.
         * @return a comparison of {@code p1} and {@code p2} by their z-coordinate
         */
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.z, p2.z);
        }
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        StdOut.print("How many points? ");
        int n = StdIn.readInt();
        Point3D[] points = new Point3D[n];
        StdOut.printf("Enter %d doubles, separated by whitespace: ", n * 3);
        for (int i = 0; i < n; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            double z = StdIn.readDouble();
            points[i] = new Point3D(x, y, z);
        }
        StdOut.println("Here are the points in the order entered:");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points);
        StdOut.println("Sorted by their natural ordering (compareTo)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.xOrder());
        StdOut.println("Sorted by their x coordinate (xOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.yOrder());
        StdOut.println("Sorted by their y coordinate (yOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.zOrder());
        StdOut.println("Sorted by their z coordinate (zOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
    }
}
