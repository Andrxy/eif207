package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.InvertedIndexBuilder;
import searchengine.index.ZipfLaw;
import searchengine.model.Document;
import searchengine.model.Term;
import searchengine.sorting.AlphabeticalSort;
import searchengine.sorting.FrequencySort;
import searchengine.sorting.SortStrategy;

public class SearchEngine {
    private InvertedIndex index;
    private InvertedIndexBuilder builder;
    private ZipfLaw zipf;
    private SortStrategy sorting;

    public SearchEngine() {
        index = InvertedIndex.getInstance();
        builder = new InvertedIndexBuilder();
        zipf = new ZipfLaw(0.1);
    }

    public void buildIndex(Vector<Document> documents) {
        builder.buildIndex(index, documents);

        sorting = new FrequencySort();
        sorting.sort(index);

        zipf.filter(index);

        sorting = new AlphabeticalSort();
        sorting.sort(index);
    }

}
