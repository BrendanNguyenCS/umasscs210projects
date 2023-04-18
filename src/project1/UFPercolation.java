package project1;

import edu.princeton.cs.algs4.*;

/**
 * An implementation of the Percolation API using the UF data structure
 */
public class UFPercolation implements Percolation {
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
     * Union find percolation system
     */
    private final WeightedQuickUnionUF uf;
    /**
     * Union find percolation system created for the backwash problem
     */
    private final WeightedQuickUnionUF uf2;

    /**
     * Constructs an n x n percolation system, with all sites blocked
     */
    public UFPercolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Illegal n");
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);

        // backwash problem uf with only 1 virtual site
        uf2 = new WeightedQuickUnionUF(n * n + 1);
    }

    /**
     * Opens {@code site(i, j)} if it is not already open
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     */
    public void open(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n))
            throw new IndexOutOfBoundsException("Illegal i or j");

        if (!isOpen(i, j)) {
            open[i][j] = true;
            int ufSite = encode(i, j);
            openSites++;

            // if the site is in the first row, connect it to the source
            if (i == 0) {
                uf.union(0, ufSite);
                uf2.union(0, ufSite);
            }
            if (i == n - 1)
                uf.union(ufSite, n * n + 1);

            // if northern neighbor in row i-1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(i - 1 < 0) && isOpen(i - 1, j)) {
                uf.union(ufSite, encode(i - 1, j));
                uf2.union(ufSite, encode(i - 1, j));
            }

            // if eastern neighbor in column j+1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(j + 1 >= n) && isOpen(i, j + 1)) {
                uf.union(ufSite, encode(i, j + 1));
                uf2.union(ufSite, encode(i, j + 1));
            }

            // if western neighbor in column j-1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(j - 1 < 0) && isOpen(i, j - 1)) {
                uf.union(ufSite, encode(i, j - 1));
                uf2.union(ufSite, encode(i, j - 1));
            }

            // if southern neighbor in row i+1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(i + 1 >= n) && isOpen(i + 1, j)) {
                uf.union(ufSite, encode(i + 1, j));
                uf2.union(ufSite, encode(i + 1, j));
            }
        }
    }

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @return {@code true} if {@code site (i, j)} is open, {@code false} otherwise.
     */
    public boolean isOpen(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n))
            throw new IndexOutOfBoundsException("Illegal i or j");
        return open[i][j];
    }

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @return {@code true} if {@code site(i, j)} is full, {@code false} otherwise
     * @apiNote the deprecated {@link WeightedQuickUnionUF#connected(int, int) connected()} method has been replaced
     */
    public boolean isFull(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n))
            throw new IndexOutOfBoundsException("Illegal i or j");
        int ufSite = encode(i, j);
        return isOpen(i, j) && (uf2.find(0) == uf2.find(ufSite));
    }

    /**
     * @return the number of open sites
     */
    public int numberOfOpenSites() { return openSites; }

    /**
     * @return {@code true} if this system percolates, {@code false} otherwise
     * @apiNote the deprecated {@link WeightedQuickUnionUF#connected(int, int) connected()} method has been replaced
     */
    public boolean percolates() { return uf.find(0) == uf.find(n*n+1); }

    /**
     * @return an integer ID (1...n) for {@code site(i, j)}
     */
    private int encode(int i, int j) { return n * i + j + 1; }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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