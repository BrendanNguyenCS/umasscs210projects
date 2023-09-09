package project6;

import edu.princeton.cs.algs4.*;
import java.util.*;

public class WordNet {
    /**
     * Symbol table mapping synset noun to set of synset ids
     */
    private final SeparateChainingHashST<String, Set<Integer>> st;
    /**
     * Symbol table mapping synset id to synset string
     */
    private final SeparateChainingHashST<Integer, String> rst;
    /**
     * Shortest common ancestor
     */
    private final ShortestCommonAncestor sca;

    /**
     * Constructs a {@link WordNet} object given the names of the input (synset and hypernym) files.
     * @param synsets the synset input file name or path
     * @param hypernyms the hypernyms input file name or path
     * @throws NullPointerException if either the synset or hypernym file paths are {@code null}
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new NullPointerException("synsets is null");
        }
        if (hypernyms == null) {
            throw new NullPointerException("hypernyms is null");
        }
        rst = new SeparateChainingHashST<>();
        st = new SeparateChainingHashST<>();
        In synIn = new In(synsets);
        In hypIn = new In(hypernyms);

        // parsing synsets file
        while (synIn.hasNextLine()) {
            // split up comma separated line into array
            String[] line = synIn.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            rst.put(id, line[1]);
            String[] synsetSet = line[1].split("\\s");
            for (String synSet : synsetSet) {
                if (!st.contains(synSet)) {
                    st.put(synSet, new HashSet<>());
                }
                st.get(synSet).add(id);
            }
        }
        Digraph G = new Digraph(rst.size());

        // parsing hypernyms file
        while (hypIn.hasNextLine()) {
            // split up comma separated line into array
            String[] line = hypIn.readLine().split(",");
            int synset = Integer.parseInt(line[0]);
            for (String s : line) {
                int hypernym = Integer.parseInt(s);
                G.addEdge(synset, hypernym);
            }
        }
        sca = new ShortestCommonAncestor(G);
    }

    /**
     * @return all {@link WordNet} nouns
     */
    public Iterable<String> nouns() {
        return st.keys();
    }

    /**
     * @param word the word
     * @return {@code true} if the given word is a WordNet noun, {@code false} otherwise
     * @throws NullPointerException if the word is {@code null}
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException("word is null");
        }
        return st.contains(word);
    }

    /**
     * @param noun1 a noun
     * @param noun2 a noun
     * @return a synset that is a shortest common ancestor of {@code noun1} and {@code noun2}
     * @throws NullPointerException if either of the nouns are {@code null}
     * @throws IllegalArgumentException if either of the nouns are not nouns
     */
    public String sca(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }
        return rst.get(sca.ancestor(st.get(noun1), st.get(noun2)));
    }

    /**
     * @param noun1 a noun
     * @param noun2 a noun
     * @return the length of the shortest ancestral path between {@code noun1} and {@code noun2}
     * @throws NullPointerException if either of the nouns are {@code null}
     * @throws IllegalArgumentException if either of the nouns are not nouns
     */
    public int distance(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }
        return sca.length(st.get(noun1), st.get(noun2));
    }

    /**
     * Unit tests the data type. [DO NOT EDIT]
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.printf("# of nouns = %d\n", nouns);
        StdOut.printf("isNoun(%s)? %s\n", word1, wordnet.isNoun(word1));
        StdOut.printf("isNoun(%s)? %s\n", word2, wordnet.isNoun(word2));
        StdOut.printf("isNoun(%s %s)? %s\n", word1, word2, wordnet.isNoun(word1 + " " + word2));
        StdOut.printf("sca(%s, %s) = %s\n", word1, word2, wordnet.sca(word1, word2));
        StdOut.printf("distance(%s, %s) = %s\n", word1, word2, wordnet.distance(word1, word2));
    }
}
