package project6;

import edu.princeton.cs.algs4.*;

public class GraphProperties {
    private final RedBlackBST<Integer, Integer> st;          // degree -> frequency
    private final double avgDegree;                                // average degree of the graph
    private double avgPathLength;                            // average path length of the graph
    private double clusteringCoefficient;                    // clustering coefficient of the graph

    // Computes graph properties for the undirected graph G.
    public GraphProperties(Graph G) {
        st = new RedBlackBST<>();
        for (int v = 0; v < G.V(); v++) {
            int degree = G.degree(v);
            if (!st.contains(degree)) {
                st.put(degree, 0);
            }
            st.put(degree, st.get(degree) + 1);
        }

        avgDegree = 2.0 * G.E() / G.V();

        for (int v = 0; v < G.V(); v++) {
            BreadthFirstPaths paths = new BreadthFirstPaths(G, v);
            for (int w = 0; w < G.V(); w++) {
                if (paths.hasPathTo(w)) {
                    avgPathLength += paths.distTo(w);
                }
            }
        }

        avgPathLength /= G.V() * (G.V() - 1);

        for (int u = 0; u < G.V(); u++) {
            int possible = G.degree(u) * (G.degree(u) - 1) / 2;
            int actual = 0;
            for (int v : G.adj(u)) {
                for (int w : G.adj(u)) {
                    if (hasEdge(G, v, w)) {
                        actual++;
                    }
                }
            }

            actual /= 2;
            if (possible > 0) {
                clusteringCoefficient += 1.0 * actual / possible;
            }
        }

        clusteringCoefficient /= G.V();
    }

    // Returns the degree distribution of the graph (a symbol table mapping each degree value to
    // the number of vertices with that value).
    public RedBlackBST<Integer, Integer> degreeDistribution() {
        return st;
    }

    // Returns the average degree of the graph.
    public double averageDegree() {
        return avgDegree;
    }

    // Returns the average path length of the graph.
    public double averagePathLength() {
        return avgPathLength;
    }

    // Returns the global clustering coefficient of the graph.
    public double clusteringCoefficient() {
        return clusteringCoefficient;
    }

    // Returns true if G has an edge between vertices v and w, and false otherwise.
    private static boolean hasEdge(Graph G, int v, int w) {
        for (int u : G.adj(v)) {
            if (u == w) {
                return true;
            }
        }
        return false;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        GraphProperties gp = new GraphProperties(G);
        RedBlackBST<Integer, Integer> st = gp.degreeDistribution();
        StdOut.println("Degree distribution:");
        for (int degree : st.keys()) {
            StdOut.println("  " + degree + ": " + st.get(degree));
        }
        StdOut.printf("Average degree         = %7.3f\n", gp.averageDegree());
        StdOut.printf("Average path length    = %7.3f\n", gp.averagePathLength());
        StdOut.printf("Clustering coefficient = %7.3f\n", gp.clusteringCoefficient());
    }
}
