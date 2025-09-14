package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.IndexBuilder;
import searchengine.io.FileReader;
import searchengine.io.IndexPersistence;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.IOException;

public class IndexManager {
    private InvertedIndex index;
    private IndexBuilder builder;

    public IndexManager() {
        this.index = InvertedIndex.getInstance();
        this.builder = new IndexBuilder();
    }

    public InvertedIndex getIndex() {
        return index;
    }

    public Vector<Term> getCorpus() {
        return index.getCorpus();
    }

    public void buildIndex(Vector<Document> documents) {
        builder.buildIndex(index, documents);
        computeIDF(documents.getSize());
    }

    /**
     * Guarda el índice completo en un archivo binario.
     */
    public void saveIndex() {
        IndexPersistence.saveIndex(index);
    }

    /**
     * Carga un índice desde un archivo binario.
     */
    public void loadIndex() {
        IndexPersistence.loadIndex(index);
    }

    /**
     * Calcula IDF para todos los términos del índice.
     */
    private void computeIDF(int totalDocuments) {
        for (Term term : index.getCorpus()) {
            term.calculateIDF(totalDocuments);
        }
    }
}
