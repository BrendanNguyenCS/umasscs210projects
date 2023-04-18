package project1;

/**
 * The percolation API
 */
public interface Percolation {
    /**
     * Opens site (i, j) if it is not already open
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     */
    void open(int i, int j);

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinate of the site
     * @return {@code true} if site (i, j) is open, {@code false} otherwise.
     */
    boolean isOpen(int i, int j);

    /**
     * @param i the row coordinate of the site
     * @param j the column coordinte of the site
     * @return {@code true} if site (i, j) is full, {@code false} otherwise
     */
    boolean isFull(int i, int j);

    /**
     * @return the number of opens sites
     */
    int numberOfOpenSites();

    /**
     * @return {@code true} if this system percolates, {@code false} otherwise
     */
    boolean percolates();
}