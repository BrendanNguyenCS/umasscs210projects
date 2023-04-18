package dummy_project;

import edu.princeton.cs.algs4.*;

/**
 * Quick union implementation for union find
 */
public class QuickUnionUF {
    /**
     * Access to component id (site indexed)
     */
    private final int[] id;
    /**
     * Number of components
     */
    private int count;

    /**
     * Constructor
     * @param n the number of components
     */
    public QuickUnionUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    public int count() { return count; }

    public boolean connected(int p, int q) { return find(p) == find(q); }

    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    public void union(int p, int q) {
        // Give p and q the same root
        int i = find(p);
        int j = find(q);
        if (i == j)
            return;
        id[i] = j;
        count--;
    }

    public static void main(String[] args) {
        // Solve dynamic connectivity problem on StdIn
        int n = StdIn.readInt();                    // Read number of sites
        QuickUnionUF uf = new QuickUnionUF(n);      // Initialize n components
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();                // Read pair to connect
            if (uf.connected(p, q))
                continue;                           // Ignore if connected
            uf.union(p, q);                         // Combine components
            StdOut.println(p + " " + q);            // And print connection
        }
        StdOut.println(uf.count() + " components");
    }
}
