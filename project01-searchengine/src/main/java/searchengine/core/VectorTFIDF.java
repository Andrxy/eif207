package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Posting;
import searchengine.model.Term;
import searchengine.model.Document;

public class VectorTFIDF {

    public static Vector<Double> buildDocumentVector(Document document, InvertedIndex index) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> corpus = index.getCorpus();

        for (Term term  : corpus) {
            double tf = getTF(term, document);
            double idf = term.getIdf();

            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    public static Vector<Double> buildQueryVector(Vector<String> tokenizedQuery, InvertedIndex index) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> corpus = index.getCorpus();

        for (Term term  : corpus) {
            double tf = getQueryTF(term.getTerm(), tokenizedQuery);
            double idf = term.getIdf();

            vector.add(tf * idf);
        }

        return normalize(vector);
    }

    private static double getTF(Term term, Document document) {
        Vector<Posting> postings = term.getPostings();

        for (Posting posting : postings) {

            if (posting.getDocument().getId() == document.getId()) {
                return posting.getTf();
            }
        }
        return 0.0;
    }

    private static double getQueryTF(String term, Vector<String> queryTokenization) {
        int freq = 0;

        for (String token : queryTokenization) {
            if (token.equals(term)) {
                freq++;
            }
        }
        return (double) freq;
    }

    private static Vector<Double> normalize(Vector<Double> vector) {
        double norm = 0.0;

        for (Double value : vector) {
            norm += value * value;
        }

        norm = Math.sqrt(norm);

        Vector<Double> normalized = new Vector<>();
        if (norm == 0) {
            return vector;
        }

        for (Double  value : vector) {
            normalized.add(value / norm);
        }

        return normalized;
    }
}
