package searchengine.sorting;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Term;

public interface SortStrategy {
    void sort(InvertedIndex index);
}
