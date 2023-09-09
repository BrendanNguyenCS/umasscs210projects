package project1;

import edu.princeton.cs.algs4.*;

/**
 * An implementation of the Percolation API using a 2D array
 */
public class ArrayPercolation implements Percolation {
    /**
     * Percolation system size
     */
    private final int n;
    /**
     * Percolation system
     */
    private final boolean[][] open;
    /**
     * Total number of open sites in percolation system
     */
    private int openSites;

    /**
     * Constructs an n x n percolation system, with all sites blocked.
     * @param n the percolation system size (one dimension)
     * @throws IllegalArgumentException if the requested array size is invalid
     */
    public ArrayPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
    }

    /**
     * Opens {@code site(i, j)} if it is not already open.
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds
     */
    public void open(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            open[i][j] = true;
            openSites++;
        }
    }

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @return {@code true} if {@code site(i, j)} is open, {@code false} otherwise
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds
     */
    public boolean isOpen(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @return {@code true} if {@code site(i, j)} is full, {@code false} otherwise
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds
     */
    public boolean isFull(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        boolean[][] full = new boolean[n][n];
        for (int a = 0; a < n; a++) {
            floodFill(full, 0, a);
        }
        return full[i][j];
    }

    /**
     * Getter for {@link #openSites}
     * @return the number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * @return {@code true} if this system percolates, {@code false} otherwise
     */
    public boolean percolates() {
        // local variable to store whether there are any full sites in last row
        boolean percolates = false;
        for (int a = 0; a < n; a++) {
            percolates = percolates || isFull(n - 1, a);
        }
        return percolates;
    }

    /**
     * Recursively flood fills {@code full[][]} using depth-first exploration, starting at {@code (i,j)}.
     * @param full the array of full sites
     * @param i the row coordinate of the source
     * @param j the column coordinate of the source
     */
    private void floodFill(boolean[][] full, int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n) || !isOpen(i, j) || full[i][j]) {
            return;
        }

        full[i][j] = true;                     // fill the current site
        floodFill(full, i - 1, j);          // recursive call on element north of current site
        floodFill(full, i, j + 1);          // recursive call on element east of current site
        floodFill(full, i, j - 1);          // recursive call on element west of current site
        floodFill(full, i + 1, j);          // recursive call on element south of current site
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}