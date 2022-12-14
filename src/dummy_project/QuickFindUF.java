/**
 * dummy_project.Quick find implementation for union find
 */
package dummy_project;

import edu.princeton.cs.algs4.*;

public class QuickFindUF {
    private final int[] id;       // access to component id (site indexed)
    private int count;      // number of components

    public QuickFindUF(int n) {
        // Initialize component id array.
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        // Put p and q into the same component
        int pID = find(p);
        int qID = find(q);

        // Nothing to do if p and q are alrady in the same component
        if (pID == qID) return;

        // Change values from id[p] to id[q]
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) id[i] = qID;
        }
        count--;
    }

    public static void main(String[] args) {
        // Solve dynamic connectivity problem on StdIn
        int n = StdIn.readInt();                    // Read number of sites
        QuickFindUF uf = new QuickFindUF(n);        // Initialize n components
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();                // Read pair to connect
            if (uf.connected(p, q)) continue;       // Ignore if connected
            uf.union(p, q);                         // Combine components
            StdOut.println(p + " " + q);            // And print connection
        }
        StdOut.println(uf.count() + " components");
    }
}
