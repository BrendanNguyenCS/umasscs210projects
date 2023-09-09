package project6;

import edu.princeton.cs.algs4.*;

public class ShortestCommonAncestor {
    /**
     * Associated {@link Digraph}
     */
    private final Digraph G;

    /**
     * Constructs a {@link ShortestCommonAncestor} object given a rooted DAG
     * @param G the rooted DAG
     */
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        this.G = G;
    }

    /**
     * @param v a vertex
     * @param w a vertex
     * @return the length of the shortest ancestral path between vertices {@code v} and {@code w}
     * @throws IndexOutOfBoundsException if either of the vertices are outside the DAG
     */
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

    /**
     * @param v a vertex
     * @param w a vertex
     * @return a shortest common ancestor of vertices {@code v} and {@code w}
     * @throws IndexOutOfBoundsException if either of the vertices are outside the DAG
     */
    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V())
            throw new IndexOutOfBoundsException("v is invalid");
        if (w < 0 || w >= G.V())
            throw new IndexOutOfBoundsException("w is invalid");
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

    /**
     * @param A a vertex subset
     * @param B a vertex subset
     * @return the length of the shortest ancestral path of vertex subsets {@code A} and {@code B}
     * @throws NullPointerException if either of the subsets are {@code null}
     * @throws IllegalArgumentException if either of the subsets are empty
     */
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

    /**
     * @param A a vertex subset
     * @param B a vertex subset
     * @return a shortest common ancestor of vertex subsets {@code A} and {@code B}
     * @throws NullPointerException if either of the subsets are {@code null}
     * @throws IllegalArgumentException if either of the subsets are empty
     */
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

    /**
     * @param v a vertex
     * @return a map of vertices reachable from {@code v} and their respective shortest distances from {@code v}
     */
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> st = new SeparateChainingHashST<>();
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        st.put(v, 0);
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int n = queue.dequeue();
            for (int m : G.adj(n)) {
                // each neighbor of the vertex in queue and add that vertex and its distance from v
                if (!st.contains(m)) {
                    st.put(m, st.get(n) + 1);
                    queue.enqueue(m);
                }
            }
        }
        return st;
    }

    /**
     * @param A a vertex subset
     * @param B a vertex subset
     * @return an array consisting of the shortest common ancestors of vertex subsets {@code A} and {@code B},
     * vertex {@code v} from {@code A}, and vertex {@code w} from {@code B} such that the path {@code v-a-w} is the
     * shortest ancestral path of {@code A} and {@code B}
     */
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

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
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
