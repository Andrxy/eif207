package searchengine.index;

import searchengine.core.IndexManager;
import searchengine.datastructures.Vector;
import searchengine.model.Posting;
import searchengine.model.Term;
import searchengine.model.Document;

public class VectorTFIDF {

    public static Vector<Double> buildDocumentVector(Document doc, IndexManager manager) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> corpus = manager.getCorpus();

        for (int i = 0; i < corpus.getSize(); i++) {
            Term term = corpus.getAt(i);
            double tf = getTF(term, doc);
            double idf = term.getIdf();
            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    public static Vector<Double> buildQueryVector(Vector<String> tokenizedQuery, IndexManager manager) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> corpus = manager.getCorpus();

        for (int i = 0; i < corpus.getSize(); i++) {
            Term term = corpus.getAt(i);
            double tf = getQueryTF(term.getTerm(), tokenizedQuery);
            double idf = term.getIdf();
            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    private static double getTF(Term term, Document doc) {
        Vector<Posting> postings = term.getPostings();
        for (int i = 0; i < postings.getSize(); i++) {
            Posting posting = postings.getAt(i);
            if (posting.getDocument().getId() == doc.getId()) {
                return posting.getTf();
            }
        }
        return 0.0;
    }

    private static double getQueryTF(String term, Vector<String> tokens) {
        double freq = 0.0;
        for (int i = 0; i < tokens.getSize(); i++) {
            if (tokens.getAt(i).equals(term)) {
                freq = freq + 1.0;
            }
        }
        return freq;
    }

    private static Vector<Double> normalize(Vector<Double> vector) {
        double norm = 0.0;
        for (int i = 0; i < vector.getSize(); i++) {
            double val = vector.getAt(i);
            norm = norm + val * val;
        }
        norm = Math.sqrt(norm);

        if (norm == 0.0) {
            return vector;
        }

        Vector<Double> normalized = new Vector<>();
        for (int i = 0; i < vector.getSize(); i++) {
            double val = vector.getAt(i);
            normalized.add(val / norm);
        }

        return normalized;
    }
}
