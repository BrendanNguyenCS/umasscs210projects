package project1;

import edu.princeton.cs.algs4.*;

public class Transpose {
    /**
     * Entry point. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        double[][] x = StdArrayIO.readDouble2D();
        StdArrayIO.print(transpose(x));
    }

    /**
     * @param x
     * @return a new matrix that is the transpose of x
     */
    private static double[][] transpose(double[][] x) {
        // Create a new transposed 2D matrix t with dimensions n-by-m, where m-by-n are the
        // dimensions of x.
        int m = x.length;
        int n = x[0].length;
        double[][] t = new double[n][m];

        // For each 0 <= i < m and 0 <= j < n, set t[j][i] to x[i][j].
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                t[j][i] = x[i][j];
            }
        }

        // Return t.
        return t;
    }
}
