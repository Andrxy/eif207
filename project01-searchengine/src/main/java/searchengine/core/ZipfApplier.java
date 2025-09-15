package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Term;

public class ZipfApplier {
    private double percentile;

    public ZipfApplier(double percentile) {
        this.percentile = percentile;
    }

    public void filter(InvertedIndex index) {
        Vector<Term> corpus =  index.getCorpus();

        int size = corpus.getSize();
        int sizePercentile = (int) (size * percentile);
        int newSize = size -  sizePercentile;

        corpus.setSize(newSize);
    }
}
