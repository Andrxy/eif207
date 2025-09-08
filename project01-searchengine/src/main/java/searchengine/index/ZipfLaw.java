package searchengine.index;

public class ZipfLaw {
    private double percentile;

    public ZipfLaw(double percentile) {
        this.percentile = percentile;
    }

    public void filter(InvertedIndex index) {
        int size = index.getCorpus().getSize();
        int sizePercentile = (int) (size * percentile);
        int newSize = size -  sizePercentile;

        index.getCorpus().setSize(newSize);
    }
}
