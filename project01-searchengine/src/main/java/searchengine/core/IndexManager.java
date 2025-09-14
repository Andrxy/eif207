package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.IndexBuilder;
import searchengine.index.ZipfLaw;
import searchengine.io.FileReader;
import searchengine.io.IndexPersistence;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.IOException;

public class IndexManager {
    private InvertedIndex index;
    private IndexBuilder builder;

    public IndexManager() {
        this.index = InvertedIndex.getInstance(); // O podrías permitir instanciación normal para tests
        this.builder = new IndexBuilder();
    }

    public InvertedIndex getIndex() {
        return index;
    }

    public Vector<Term> getCorpus() {
        return index.getCorpus();
    }

    /**
     * Construye un índice a partir de documentos.
     */
    public void buildIndex(Vector<Document> documents) {
        builder.buildIndex(index, documents);

        // Calcular IDF
        computeIDF(documents.getSize());
    }

    /**
     * Aplica Ley de Zipf al índice.
     */
    public void applyZipf(double percentile) {
        ZipfLaw zipf = new ZipfLaw(percentile);
        sortByTTF();
        zipf.filter(index);
    }

    /**
     * Guarda el índice completo en un archivo binario.
     */
    public void saveIndex(String filePath) {
        IndexPersistence.saveIndex(index, filePath);
    }

    /**
     * Carga un índice desde un archivo binario.
     */
    public void loadIndex(String filePath) {
        IndexPersistence.loadIndex(index, filePath);
    }

    /**
     * Lee documentos desde la ruta de archivos.
     */
    public Vector<Document> readDocuments(String directoryPath) throws IOException {
        FileReader reader = new FileReader();
        return reader.readFiles();
    }

    /**
     * Calcula IDF para todos los términos del índice.
     */
    private void computeIDF(int totalDocuments) {
        for (Term term : index.getCorpus()) {
            term.calculateIDF(totalDocuments);
        }
    }

    public Vector<Document> getCandidates(Vector<String> tokens) {
        Vector<Document> candidates = new Vector<>();

        for (String token : tokens) {
            Term term = searchTermBinary(token);
            if (term != null) {
                term.getPostings().forEach(posting -> {
                    Document doc = posting.getDocument();
                    if (candidates.find(doc) == null) {
                        candidates.add(doc);
                    }
                });
            }
        }

        return candidates;
    }

    public void sortByTTF() {
        Vector<Term> corpus = getCorpus();
        int n = corpus.getSize();

        // Bubble sort simple sobre Vector<Term> (podría optimizarse)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (corpus.getAt(j).getTtf() > corpus.getAt(j + 1).getTtf()) {
                    corpus.swap(j, j + 1);
                }
            }
        }
    }

    public void sortAlphabetically() {
        Vector<Term> corpus = getCorpus();
        int n = corpus.getSize();
        // Simple bubble sort over Vector<Term>
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (corpus.getAt(j).getTerm().compareTo(corpus.getAt(j + 1).getTerm()) > 0) {
                    corpus.swap(j, j + 1);
                }
            }
        }
    }

    public Term searchTermBinary(String term) {
        Vector<Term> corpus = getCorpus();
        int left = 0, right = corpus.getSize() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = corpus.getAt(mid).getTerm().compareTo(term);
            if (cmp == 0) return corpus.getAt(mid);
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }
}
