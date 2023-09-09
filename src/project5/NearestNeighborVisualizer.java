package project5;

import edu.princeton.cs.algs4.*;

/**
 * Accepts {@code filename} ({@link String}) and {@code k} ({@link Integer}) as command-line arguments
 * <p>
 * Draws points from the file using standard draw and highlights the k points closest to the mouse. The search results
 * obtained using {@link BrutePointST} are highlighted in red while those obtained using the {@link KdTreePointST} are
 * highlighted in blue
 */
public class NearestNeighborVisualizer {
    /**
     * Entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);

        // Initialize the data structures with n points from the file.
        BrutePointST<Integer> brute = new BrutePointST<>();
        KdTreePointST<Integer> kdtree = new KdTreePointST<>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }

        // Enable double buffering to avoid flicker.
        StdDraw.enableDoubleBuffering();

        while (true) {
            // The location (x, y) of the mouse.
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // Draw all the points.
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            for (Point2D p : brute.points()) {
                p.draw();
            }

            // Highlight the k nearest neighbors obtained using BrutePointST.
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            if (k == 1) {
                Point2D p = brute.nearest(query);
                p.draw();
            } else {
                for (Point2D p : brute.nearest(query, k)) {
                    p.draw();
                }
            }
            StdDraw.setPenRadius(0.02);

            // Highlight the k nearest neighbors obtained using KdTreeST.
            StdDraw.setPenColor(StdDraw.BLUE);
            if (k == 1) {
                Point2D p = kdtree.nearest(query);
                p.draw();
            } else {
                for (Point2D p : kdtree.nearest(query, k)) {
                    p.draw();
                }
            }

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
