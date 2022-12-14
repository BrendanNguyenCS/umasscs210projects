package project1;

// The percolation API.
public interface Percolation {
    // Opens site (i, j) if it is not already open.
    void open(int i, int j);

    // Returns true if site (i, j) is open, and false otherwise.
    boolean isOpen(int i, int j);

    // Returns true if site (i, j) is full, and false otherwise.
    boolean isFull(int i, int j);

    // Returns the number of open sites.
    int numberOfOpenSites();

    // Returns true if this system percolates, and false otherwise.
    boolean percolates();
}