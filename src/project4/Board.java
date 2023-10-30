package project4;

import edu.princeton.cs.algs4.*;

/**
 * A data type to represent a board in the 8-puzzle game or its generalizations
 */
public class Board {
    /**
     * Tiles in the board
     */
    private final int[][] tiles;
    /**
     * Board size
     */
    private final int n;
    /**
     * Hamming distance to the goal board
     */
    private int hamming;
    /**
     * Manhattan distance to the goal board
     */
    private int manhattan;
    /**
     * Position of the blank tile in row-major order
     */
    private int blankPos;

    /**
     * Constructs a board from an n x n array; {@code tiles[i][j]} is the tile at row {@code i} and column {@code j},
     * with 0 denoting the blank tile
     * @param tiles the array which creates the board
     */
    public Board(int[][] tiles) {
        n = tiles[0].length;
        this.tiles = tiles;

        // Hamming distance calculation
        hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // if empty tile, skip
                if (tiles[i][j] == 0) {
                    continue;
                }

                // in order if tile value is equal to index in row-major order
                if (tiles[i][j] != n * i + j + 1) {
                    hamming++;
                }
            }
        }

        // Manhattan distance calculation
        this.manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // not blank tile and not in right order
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != n * i + j + 1) {
                    int colDif = (tiles[i][j] - 1) % n;     // goal board column
                    int rowDif = (tiles[i][j] - 1) / n;     // goal board row
                    this.manhattan += Math.abs(i - rowDif) + Math.abs(j - colDif);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankPos = n * i + j + 1;
                }
            }
        }
    }

    /**
     * Getter for {@link #n}
     * @return the size of this board
     */
    public int size() {
        return n;
    }

    /**
     * @param i the row of the selected tile
     * @param j the column of the selected tile
     * @return the tile at row {@code i} and column {@code j} of this board
     */
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    /**
     * Getter for {@link #hamming}
     * @return the Hamming distance between this board and the goal board
     */
    public int hamming() {
        return hamming;
    }

    /**
     * Getter for {@link #manhattan}
     * @return the Manhattan distance between this board and the goal board
     */
    public int manhattan() {
        return manhattan;
    }

    /**
     * @return {@code true} if this board is the goal board, {@code false} otherwise
     */
    public boolean isGoal() {
        return hamming == 0;
    }

    /**
     * @return {@code true} if this board is solvable, {@code false} otherwise
     */
    public boolean isSolvable() {
        int[] rowMajorArray = new int[n * n - 1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                rowMajorArray[count] = tiles[i][j];
                count++;
            }
        }
        if (n % 2 != 0 && Inversions.count(rowMajorArray) % 2 != 0) {
            return false;
        }
        if (n % 2 == 0) {
            int blankRow = (blankPos - 1) / n;          // row of blank tile
            return (blankRow + Inversions.count(rowMajorArray)) % 2 != 0;
        }
        return true;
    }

    /**
     * @return an iterable object containing the neighboring boards of this board
     */
    public Iterable<Board> neighbors() {
        int i = (blankPos - 1) / n;                     // row of blank tile
        int j = (blankPos - 1) % n;                     // column of blank tile
        LinkedQueue<Board> q = new LinkedQueue<>();
        int[][] clone;                                  // defensive copy 2d array
        if (i > 0) {                                    // if row is within top bounds
            clone = cloneTiles();
            int v = clone[i][j];                        // temp value for current tile
            clone[i][j] = clone[i - 1][j];              // set current to value of tile above current
            clone[i - 1][j] = v;                        // set above tile to temp
            q.enqueue(new Board(clone));
        }
        if (i < n - 1) {                                // if row is within bottom bounds
            clone = cloneTiles();
            int v = clone[i][j];                        // temp value for current tile
            clone[i][j] = clone[i + 1][j];              // set current to value of tile below current
            clone[i + 1][j] = v;                        // set below tile to temp
            q.enqueue(new Board(clone));
        }
        if (j > 0) {                                    // if column is within left bounds
            clone = cloneTiles();
            int v = clone[i][j];                        // temp value for current tile
            clone[i][j] = clone[i][j - 1];              // set current to value of tile left
            clone[i][j - 1] = v;                        // set left tile to temp
            q.enqueue(new Board(clone));
        }
        if (j < n - 1) {                                // if column is within right bounds
            clone = cloneTiles();
            int v = clone[i][j];                        // temp value for current tile
            clone[i][j] = clone[i][j + 1];              // set current to value of tile right
            clone[i][j + 1] = v;                        // set right tile to temp
            q.enqueue(new Board(clone));
        }
        return q;
    }

    /**
     * @param other the other {@link Board} object
     * @return {@code true} if this board is the same as {@code other}, {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (!(other instanceof Board b)) {
            return false;
        }
        if (this.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tileAt(i, j) != b.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the string representation of this board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    /**
     * @return a defensive copy of {@link #tiles}
     */
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, clone[i], 0, n);
        }
        return clone;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
