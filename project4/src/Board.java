import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    private int[][] tiles; // tiles in the board
    private int n; // board size
    private int hamming; // Hamming distance to the goal board
    private int manhattan; // Manhattan distance to the goal board
    private int blankPos; // position of the blank tile in row-major order

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        n = tiles[0].length;
        this.tiles = tiles;
        // hamming calculation
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
        // manhattan calculation
        this.manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // not blank tile and not in right order
                if (this.tiles[i][j] != 0 && this.tiles[i][j] != n * i + j + 1) {
                    // math in row-major order mindset
                    int colDif = (tiles[i][j] - 1) % n;     // goal board column
                    int rowDif = (tiles[i][j] - 1) / n;     // goal board row
                    // add difference between goal board values and current values
                    // add to distance
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

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return hamming == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
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
            int blankRow = (blankPos - 1) / n; // row of blank tile
            return (blankRow + Inversions.count(rowMajorArray)) % 2 != 0;
        }
        return true;
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        int i = (blankPos - 1) / n; // row of blank tile
        int j = (blankPos - 1) % n; // column of blank tile
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        int[][] clone = null; // defensive copy 2d array
        if (i > 0) {                        // if row is within top bounds
            clone = cloneTiles();
            int v = clone[i][j];            // temp value for current tile
            clone[i][j] = clone[i - 1][j];  // set current to value of tile above current
            clone[i - 1][j] = v;            // set above tile to temp
            q.enqueue(new Board(clone));
        }
        if (i < n - 1) {                    // if row is within bottom bounds
            clone = cloneTiles();
            int v = clone[i][j];            // temp value for current tile
            clone[i][j] = clone[i + 1][j];  // set current to value of tile below current
            clone[i + 1][j] = v;            // set below tile to temp
            q.enqueue(new Board(clone));
        }
        if (j > 0) {                        // if column is within left bounds
            clone = cloneTiles();
            int v = clone[i][j];            // temp value for current tile
            clone[i][j] = clone[i][j - 1];  // set current to value of tile left
            clone[i][j - 1] = v;            // set left tile to temp
            q.enqueue(new Board(clone));
        }
        if (j < n - 1) {                    // if column is within right bounds
            clone = cloneTiles();
            int v = clone[i][j];            // temp value for current tile
            clone[i][j] = clone[i][j + 1];  // set current to value of tile right
            clone[i][j + 1] = v;            // set right tile to temp
            q.enqueue(new Board(clone));
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Board a = this;
        Board b = (Board) other;
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (a.tileAt(i, j) != b.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Returns a string representation of this board.
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

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
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
