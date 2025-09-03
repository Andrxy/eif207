package searchengine.index;

import searchengine.datastructures.Vector;

public class Corpus {
    private static Corpus instance;
    private Vector<String> corpus;

    private Corpus() {
        corpus = new Vector<>();
    }

    public static Corpus getInstance() {
        if (instance == null) {
            instance = new Corpus();
        }

        return instance;
    }

    public Vector<String> getCorpus() {
        return corpus;
    }
}
