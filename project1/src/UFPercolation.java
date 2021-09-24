import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n; // percolation system size
    private boolean[][] open; // percolation system
    private int openSites; // total number of open sites in percolation system
    private WeightedQuickUnionUF uf; // union find percolation system
    private WeightedQuickUnionUF uf2; // union find percolation system created for the backwash
    // problem

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 2);
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }

        if (!isOpen(i, j)) {
            open[i][j] = true;
            int ufSite = encode(i, j);
            openSites++;

            // if the site is in the first row, connect it to the source
            if (i == 0) {
                uf.union(0, ufSite);
            }
            if (i == n - 1) {
                uf.union(ufSite, n * n + 1);
            }

            // if northern neighbor in row i-1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(i - 1 < 0) && isOpen(i - 1, j)) {
                uf.union(ufSite, encode(i - 1, j));
            }

            // if eastern neighbor in column j+1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(j + 1 >= n) && isOpen(i, j + 1)) {
                uf.union(ufSite, encode(i, j + 1));
            }

            // if western neighbor in column j-1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(j - 1 < 0) && isOpen(i, j - 1)) {
                uf.union(ufSite, encode(i, j - 1));
            }

            // if southern neighbor in row i+1 is not out of bounds and neighbor is open,
            // connect said neighbor to current site
            if (!(i + 1 >= n) && isOpen(i + 1, j)) {
                uf.union(ufSite, encode(i + 1, j));
            }
        }

    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if ((i < 0 || i >= n) || (j < 0 || j >= n)) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        int ufSite = encode(i, j);
        return isOpen(i, j) && uf.connected(0, ufSite);
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.connected(0, n*n+1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return n * i + j + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
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