package project6;

import edu.princeton.cs.algs4.*;

public class ShortestCommonAncestor {
    private final Digraph G;      // associated DiGraph

    // Constructs a ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        this.G = G;
    }

    // Returns length of the shortest ancestral path between vertices v and w.
    public int length(int v, int w) {
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is invalid");
        }
        // get distance maps for both vertices
        SeparateChainingHashST<Integer, Integer> vst = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wst = distFrom(w);
        // get the ancestor vertex
        int ancestor = ancestor(v, w);
        // return length
        return vst.get(ancestor) + wst.get(ancestor);
    }

    // Returns a shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is invalid");
        }
        // get distance maps for both vertices
        SeparateChainingHashST<Integer, Integer> vst = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wst = distFrom(w);
        // default minimum distance
        int minimumDistance = Integer.MAX_VALUE;
        // the vertex with the minimum distance
        int vertex = -1;
        // look for matching vertex keys in both maps
        for (int vkey : vst.keys()) {
            if (wst.contains(vkey)) {
                int d = vst.get(vkey) + wst.get(vkey);
                // if the sum of the distances is less than the current
                // minimum
                if (d < minimumDistance) {
                    minimumDistance = d;
                    vertex = vkey;
                }
            }
        }
        return vertex;
    }

    // Returns length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }
        // get triad
        int[] triad = triad(A, B);
        // get distances
        int v = distFrom(triad[1]).get(triad[0]);
        int w = distFrom(triad[2]).get(triad[0]);
        // add distances from ancestor to vertices in the subsets
        return v + w;
    }

    // Returns a shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }
        // get triad of vertices and ancestor
        int[] triad = triad(A, B);
        // return ancestor
        return triad[0];
    }

    // Returns a map of vertices reachable from v and their respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> st = new SeparateChainingHashST<>();
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        st.put(v, 0);
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int n = queue.dequeue();
            for (int m : G.adj(n)) {
                // each neighbor of the vertex in queue
                // add that vertex and its distance from v
                if (!st.contains(m)) {
                    st.put(m, st.get(n) + 1);
                    queue.enqueue(m);
                }
            }
        }
        return st;
    }

    // Returns an array consisting of a shortest common ancestor a of vertex subsets A and B,
    // vertex v from A, and vertex w from B such that the path v-a-w is the shortest ancestral
    // path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        // shortest distance
        int shortestDistance = Integer.MAX_VALUE;
        // shorest ancestor
        int shortestAncestor = -1;
        // vertices to track
        int v = -1;
        int w = -1;
        for (int i : A) {
            for (int j : B) {
                int d = length(i, j);
                if (d < shortestDistance) {
                    shortestDistance = d;
                    shortestAncestor = ancestor(i, j);
                    v = i;
                    w = j;
                }
            }
        }
        return new int[] {shortestAncestor, v, w};
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        in.close();
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
