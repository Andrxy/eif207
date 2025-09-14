package searchengine.index;

import searchengine.core.IndexManager;
import searchengine.datastructures.Vector;
import searchengine.model.Term;

public class ZipfLaw {
    private double percentile;

    public ZipfLaw(double percentile) {
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
