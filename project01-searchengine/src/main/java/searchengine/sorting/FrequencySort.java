package searchengine.sorting;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Term;

public class FrequencySort implements SortStrategy {
    public void sort(InvertedIndex index) {
        Vector<Term> corpus = index.getCorpus();

        if (corpus == null || corpus.getSize() < 2) return;
        mergeSort(corpus, 0, corpus.getSize() - 1);
    }

    private void mergeSort(Vector<Term> corpus, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(corpus, left, mid);
            mergeSort(corpus, mid + 1, right);

            merge(corpus, left, mid, right);
        }
    }

    private void merge(Vector<Term> terms, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Term[] L = new Term[n1];
        Term[] R = new Term[n2];

        for (int i = 0; i < n1; i++) L[i] = terms.getAt(left + i);
        for (int j = 0; j < n2; j++) R[j] = terms.getAt(mid + 1 + j);

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i].getTtf() <= R[j].getTtf()) {
                // descendente: términos con mayor frecuencia primero
                terms.setAt(L[i++], k++);
            } else {
                terms.setAt(R[j++], k++);
            }
        }

        while (i < n1) terms.setAt(L[i++], k++);
        while (j < n2) terms.setAt(R[j++], k++);
    }
}
