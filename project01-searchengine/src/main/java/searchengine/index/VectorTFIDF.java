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

        for (Term term : corpus) {
            double tf = getTF(term, doc);
            double idf = term.getIdf();

            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    public static Vector<Double> buildQueryVector(Vector<String> tokenizedQuery, IndexManager manager) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> corpus = manager.getCorpus();

        for (Term term : corpus) {
            double tf = getQueryTF(term.getTerm(), tokenizedQuery);
            double idf = term.getIdf();
            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    private static double getTF(Term term, Document doc) {
        for (Posting posting : term.getPostings()) {
            if (posting.getDocument().getId() == doc.getId()) {
                return posting.getTf();
            }
        }
        return 0.0;
    }

    private static double getQueryTF(String term, Vector<String> tokens) {
        int freq = 0;
        for (String token : tokens) {
            if (token.equals(term)) freq++;
        }
        return (double) freq;
    }

    private static Vector<Double> normalize(Vector<Double> vector) {
        double norm = 0.0;
        for (Double val : vector) norm += val * val;
        norm = Math.sqrt(norm);

        Vector<Double> normalized = new Vector<>();
        if (norm == 0) return vector;

        for (Double val : vector) {
            normalized.add(val / norm);
        }
        return normalized;
    }
}
