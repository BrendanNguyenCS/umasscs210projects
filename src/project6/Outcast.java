package project6;

import edu.princeton.cs.algs4.*;

public class Outcast {
    private final WordNet wordnet;    // WordNet semantic lexicon

    // Constructs an Outcast object given the WordNet semantic lexicon.
    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException("wordnet is null");
        }
        this.wordnet = wordnet;
    }

    // Returns the outcast noun from nouns.
    public String outcast(String[] nouns) {
        int max = 0;    // max distance
        String  out = null;    // variables to track temp and farthest word
        for (String noun : nouns) {
            int distance = 0;
            for (String n : nouns) {
                // get total distance between current noun and all other nouns
                distance += wordnet.distance(noun, n);
            }
            // if this total distance is higher than current max,
            // make current noun and distance the max
            if (distance > max) {
                max = distance;
                out = noun;
            }
        }
        return out;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = StdIn.readAllStrings();
        String outcastNoun = outcast.outcast(nouns);
        for (String noun : nouns) {
            StdOut.print(noun.equals(outcastNoun) ? "*" + noun + "* " : noun + " ");
        }
        StdOut.println();
    }
}
