package searchengine.index;

import searchengine.datastructures.Vector;
import searchengine.model.Term;

public class InvertedIndex {
    private static InvertedIndex instance = null;
    private Vector<Term> corpus;

    private InvertedIndex() {
        corpus =  new Vector<>();
    }

    public static InvertedIndex getInstance() {
        if (instance == null) {
            instance = new InvertedIndex();
        }
        return instance;
    }

    public Vector<Term> getCorpus() {
        return corpus;
    }
}
