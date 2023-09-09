package project4;

import edu.princeton.cs.algs4.*;

/**
 * A data type that implements the A* algorithm for solving the 8-puzzle and its generalizations
 */
public class Solver {
    /**
     * Minimum number of moves to solve
     */
    private int moves;
    /**
     * Short solution sequence of boards
     */
    private final LinkedStack<Board> solution;

    /**
     * Finds a solution to the initial board using the A* algorithm
     * @param board the initial board
     * @throws NullPointerException if the board is {@code null}
     * @throws IllegalArgumentException if the board is unsolvable
     */
    public Solver(Board board) {
        // corner cases
        if (board == null) {
            throw new NullPointerException("board is null");
        }
        if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is unsolvable");
        }

        int moves = 0;
        solution = new LinkedStack<>();
        MinPQ<SearchNode> pq = new MinPQ<>();
        // insert initial board to the queue
        pq.insert(new SearchNode(board, 0, null));
        while (!pq.isEmpty()) {
            SearchNode node = pq.delMin();
            if (node.board.isGoal()) {
                solution.push(node.board);
                this.moves = node.moves;
                break;
            } else {
                for (Board neighbor : node.board.neighbors()) {
                    if (node.previous == null) {
                        continue;
                    }
                    if (!neighbor.equals(node.previous.board)) {
                        pq.insert(new SearchNode(neighbor, moves + 1, node));
                    }
                }
            }
        }
    }

    /**
     * Getter for {@link #moves}
     * @return the minimum number of moves needed to solve the initial board
     */
    public int moves() {
        return moves;
    }

    /**
     * @return a sequence of boards in a shortest solution of the initial board
     */
    public Iterable<Board> solution() {
        return solution;
    }

    /**
     * A data type that represents a search node in the game tree. Each node includes a
     * reference to a board, the number of moves to the node from the initial node, and a
     * reference to the previous node
     */
    private class SearchNode implements Comparable<SearchNode> {
        /**
         * Board reference
         */
        private final Board board;
        /**
         * Number of moves to the node from the initial node
         */
        private final int moves;
        /**
         * Reference to the previous node
         */
        private final SearchNode previous;

        /**
         * Constructs a new search node
         * @param board the board reference
         * @param moves the number of moves from the initial node
         * @param previous the previous node
         */
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        /**
         * Compares nodes based on the following sum:<br>
         * Manhattan distance of the board in the node + the number of moves to the node
         * @param other the object to be compared
         * @return {@code -1} if this node's sum is smaller, {@code 1} if it's larger, {@code 0} otherwise
         */
        public int compareTo(SearchNode other) {
            int sumA = this.board.manhattan() + this.moves;         // current node's sum
            int sumB = other.board.manhattan() + other.moves;       // other node's sum
            return Integer.compare(sumA, sumB);                     // compare
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
