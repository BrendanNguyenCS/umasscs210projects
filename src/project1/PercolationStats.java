package project1;

import edu.princeton.cs.algs4.*;

public class PercolationStats {
    private final int m; // number of independent experiments
    private final double[] x; // sample population of experiment

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.m = m;
        x = new double[m];

        // local variable for total number of sites in system (n * n)
        double systemSize = Math.pow(n, 2);

        for (int i = 0; i < m; i++) {
            // create new percolation grid
            UFPercolation uf = new UFPercolation(n);
            // randomly populate grid until grid percolates
            while (!uf.percolates()) {
                // random values
                int randI = StdRandom.uniformInt(n);
                int randJ = StdRandom.uniformInt(n);

                // open if random site is closed
                if (!uf.isOpen(randI, randJ)) {
                    uf.open(randI, randJ);
                }
            }

            // record number of open sites when uf percolates divided by the total sites in system
            double trial = uf.numberOfOpenSites() / systemSize;
            x[i] = trial;
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / Math.sqrt(m);
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / Math.sqrt(m);
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}