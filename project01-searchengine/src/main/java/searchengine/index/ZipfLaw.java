package searchengine.index;

import searchengine.index.invertedindex.InvertedIndex;
import searchengine.utils.Sorter;

public class ZipfLaw {
    private static double percentile = 0.1;

    public static void filter() {
        int size = InvertedIndex.getInstance().getCorpus().getSize();
        int sizePercentile = (int) (size * percentile);
        int newSize = size -  sizePercentile;

        InvertedIndex.getInstance().getCorpus().setSize(newSize);
    }
}
