package project4;

import java.awt.*;
import edu.princeton.cs.algs4.*;

/**
 * Accepts {@code filename} ({@link String}) as command-line argument
 * <p>
 * Using the student's {@link Board} and {@link Solver} data types graphically solves the sliding-tiles puzzle with
 * initial configuration specified by the file
 */
public class SolverVisualizer {
    /**
     * Time in milliseconds to animate each move of the solution
     */
    private static final int ANIMATE_TIME = 800;
    /**
     * Time in milliseconds to pause after each move
     */
    private static final int PAUSE_TIME = 300;
    /**
     * Time in milliseconds for each frame of animation. 20 is sensible
     */
    private static final int FRAME_TIME = 20;
    /**
     * Background color of tile
     */
    private static final Color TILE_BACKGROUND_COLOR = Color.WHITE;
    /**
     * Foreground color of tile
     */
    private static final Color TILE_FOREGROUND_COLOR = Color.BLACK;
    /**
     * Background color of board
     */
    private static final Color BOARD_COLOR = Color.LIGHT_GRAY;
    /**
     * Text color
     */
    private static final Color TEXT_COLOR = Color.BLACK;
    /**
     * Text font
     */
    private static final String TEXT_FONT = "SansSerif";
    /**
     * Dimension of grid
     */
    private static int n;
    /**
     * Number of tile at [row][column]
     */
    private static int[][] tileAt;
    /**
     * Which tile is moving, 0 if none
     */
    private static int movingTile;
    /**
     * Number of moves to solve puzzle
     */
    private static int totalMoves;
    /**
     * Number of moves currently displayed
     */
    private static int currentMoves;
    /**
     * Manhattan distance currently displayed
     */
    private static int manhattan;
    /**
     * Window title
     */
    private static String title;

    /**
     * Entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Read in the board specified in the filename.
        String filename = args[0];
        In in = new In(filename);
        n = in.readInt();
        tileAt = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tileAt[i][j] = in.readInt();
            }
        }

        title = filename;
        movingTile = 0;                                     // show initial state for first 'move time'

        // Enable double buffering to avoid flicker.
        StdDraw.enableDoubleBuffering();

        // Solve the slider puzzle.
        Board initial = new Board(tileAt);
        manhattan = initial.manhattan();
        long start = System.currentTimeMillis();
        if (!initial.isSolvable()) {
            long now = System.currentTimeMillis();
            title += " (no solution possible)";
            movingTile = 0;
            currentMoves = 0;
            animateMove();                                  // display start position only
        } else {
            Solver solver = new Solver(initial);
            totalMoves = solver.moves();
            currentMoves = 0;
            for (Board board : solver.solution()) {
                manhattan = board.manhattan();
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        int tile = board.tileAt(row, col);
                        if (tileAt[row][col] == 0) {        // If this position was previously empty
                            movingTile = tile;              // animate the tile into it
                        }
                        tileAt[row][col] = tile;
                    }
                }
                currentMoves++;
                animateMove();                              // show move (or static initial state 1st time)
            }
            // Show final position for one extra 'move time' before (possibly) moving on to
            // display next puzzle solution.
            StdDraw.show();
            StdDraw.pause(ANIMATE_TIME + PAUSE_TIME);
        }
    }

    /**
     * Draws one frame of animation. If the start position is being displayed (movingTile == 0)
     * or during the {@link #PAUSE_TIME} following each animated move, the display is unchanged
     * @param moveTime the number of milliseconds since the current move began
     */
    private static void refresh(int moveTime) {
        // Find where the empty space (0 tile) is located.
        int emptyRow = 0;
        int emptyCol = 0;
        while (tileAt[emptyRow][emptyCol] != 0) {
            if (++emptyCol == n) {
                emptyCol = 0;
                ++emptyRow;
            }
        }

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.1 * n, 1.1 * n);
        StdDraw.setYscale(-0.1 * n, 1.1 * n);
        StdDraw.setPenRadius(0.03 / n);
        int fontSize = 200 / n;
        StdDraw.setFont(new Font(TEXT_FONT, Font.PLAIN, fontSize));
        StdDraw.setPenColor(BOARD_COLOR);
        StdDraw.filledSquare(n / 2.0, n / 2.0, n / 1.96);
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                double x = col + 0.5;
                double y = n - row - 0.5;
                if (tileAt[row][col] == movingTile) {
                    double moveFraction = (double) moveTime / ANIMATE_TIME;
                    if (moveFraction > 1.0) {
                        moveFraction = 1.0;
                    }
                    x += (1.0 - moveFraction) * (emptyCol - col);
                    y -= (1.0 - moveFraction) * (emptyRow - row);
                }
                if (tileAt[row][col] != 0) {
                    StdDraw.setPenColor(TILE_BACKGROUND_COLOR);
                    StdDraw.filledSquare(x, y, 0.475);
                    StdDraw.setPenColor(TILE_FOREGROUND_COLOR);
                    StdDraw.square(x, y, 0.475);
                    StdDraw.text(x, y - 0.05, "" + tileAt[row][col]);
                }
            }
        }

        // Write status text.
        StdDraw.setFont(new Font(TEXT_FONT, Font.PLAIN, 12));
        StdDraw.setPenColor(TEXT_COLOR);
        StdDraw.text(0.100 * n, -0.06 * n, currentMoves + "/" + totalMoves + " moves");
        StdDraw.text(0.775 * n, -0.06 * n, "Manhattan distance: " + manhattan);
        StdDraw.text(0.500 * n, 1.06 * n, title);
    }

    /**
     * Animates a move
     */
    private static void animateMove() {
        int milliseconds = 0;
        while (milliseconds < ANIMATE_TIME + PAUSE_TIME) {
            refresh(milliseconds);
            StdDraw.show();
            StdDraw.pause(FRAME_TIME);
            milliseconds += FRAME_TIME;
        }
    }
}
