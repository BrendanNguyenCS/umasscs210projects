package project6;

import edu.princeton.cs.algs4.*;

public class DiGraphProperties {
    /**
     * Is the digraph a DAG?
     */
    private final boolean isDAG;
    /**
     * Is the digraph a map?
     */
    private boolean isMap;
    /**
     * The sources in the digraph
     */
    private final LinkedBag<Integer> sources;
    /**
     * The sinks in the digraph
     */
    private final LinkedBag<Integer> sinks;

    /**
     * Computes the graph properties for the digraph {@code G}
     */
    public DiGraphProperties(Digraph G) {
        isDAG = !(new DirectedCycle(G).hasCycle());
        isMap = true;
        for (int v = 0; v < G.V(); v++) {
            isMap = isMap && (G.outdegree(v) == 1);
        }

        sources = new LinkedBag<>();
        sinks = new LinkedBag<>();
        for (int v = 0; v < G.V(); v++) {
            if (G.indegree(v) == 0) {
                sources.add(v);
            }
            if (G.outdegree(v) == 0) {
                sinks.add(v);
            }
        }
    }

    /**
     * Getter for {@link #isDAG}
     * @return {@code true} if the digraph is a directed acyclic graph (DAG), {@code false} otherwise.
     */
    public boolean isDAG() {
        return isDAG;
    }

    /**
     * Getter for {@link #isMap}
     * @return {@code true} if the digraph is a map, {@code false} otherwise
     */
    public boolean isMap() {
        return isMap;
    }

    /**
     * Getter for {@link #sources}
     * @return all the sources (ie, vertices without any incoming edges) in the digraph
     */
    public Iterable<Integer> sources() {
        return sources;
    }

    /**
     * Getter for {@link #sinks}
     * @return all the sinks (ie, vertices without any outgoing edges) in the digraph
     */
    public Iterable<Integer> sinks() {
        return sinks;
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        DiGraphProperties gp = new DiGraphProperties(G);
        StdOut.print("Sources: ");
        for (int v : gp.sources()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.print("Sinks: ");
        for (int v : gp.sinks()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.println("Is DAG? " + gp.isDAG());
        StdOut.println("Is Map? " + gp.isMap());
    }
}
