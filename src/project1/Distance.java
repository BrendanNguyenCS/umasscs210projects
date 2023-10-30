package project1;

import edu.princeton.cs.algs4.*;

public class Distance {
    /**
     * Entry point. [DO NOT EDIT]
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        double[] x = StdArrayIO.readDouble1D();
        double[] y = StdArrayIO.readDouble1D();
        StdOut.println(distance(x, y));
    }

    /**
     * The Euclidean distance is defined as the square root of the sum of squares of {@code (x[i] - y[i])},
     * where {@code 0 <= i < x.length}.
     * @param x a position vector
     * @param y a position vector
     * @return the Euclidean distance between the position vectors {@code x} and {@code y}
     */
    private static double distance(double[] x, double[] y) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow((x[i] - y[i]), 2);
        }
        return Math.sqrt(sum);
    }
}
