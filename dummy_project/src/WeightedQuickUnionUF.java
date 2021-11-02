/**
 * Weighted quick union implementation for union find
 */

import stdlib.StdIn;
import stdlib.StdOut;

public class WeightedQuickUnionUF {
    private int[] id;           // parent link (site indexed)
    private int[] sz;           // size of component for roots (site indexed)
    private int count;          // number of components

    public WeightedQuickUnionUF(int n) {
        count = n;
        id = new int[n];
        sz = new int[n];

        for (int i = 0; i < n; i++) id[i] = i;
        for (int i = 0; i < n; i++) sz[i] = 1;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        // Follow links to find a root
        while (p != id[p]) p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        // Make smaller root point to larger one
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }

    public static void main(String[] args) {
        // Solve dynamic connectivity problem on StdIn
        int n = StdIn.readInt();                                    // Read number of sites
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);      // Initialize n components
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();                                // Read pair to connect
            if (uf.connected(p, q)) continue;                       // Ignore if connected
            uf.union(p, q);                                         // Combine components
            StdOut.println(p + " " + q);                            // And print connection
        }
        StdOut.println(uf.count() + " components");
    }
}
