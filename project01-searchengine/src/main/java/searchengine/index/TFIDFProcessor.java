package searchengine.index;

import searchengine.datastructures.Vector;
import searchengine.model.Document;

public class TFIDFProcessor {
    public static Vector<Double> computeQueryVector(Vector<String> queryTerms, InvertedIndex index) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> vocabulary = index.getCorpus().getTerms();

        // Para cada término en el vocabulario
        for (int i = 0; i < vocabulary.size(); i++) {
            Term term = vocabulary.get(i);

            // --- calcular TF en la query ---
            int tf = 0;
            for (int j = 0; j < queryTerms.size(); j++) {
                if (queryTerms.get(j).equals(term.getText())) {
                    tf++; // cuenta repeticiones en la query
                }
            }

            // --- calcular IDF del índice ---
            double idf = term.getIdf();

            // --- calcular peso TF-IDF ---
            double weight = tf * idf;
            vector.add(weight);
        }
        return vector;
    }

    public static Vector<Double> computeDocumentVector(Document doc, InvertedIndex index) {
        Vector<Double> vector = new Vector<>();
        Vector<Term> vocabulary = index.getCorpus().getTerms();

        // Para cada término en el vocabulario
        for (int i = 0; i < vocabulary.size(); i++) {
            Term term = vocabulary.get(i);

            // --- calcular TF en este documento ---
            int tf = 0;
            Vector<Posting> postings = term.getPostings();
            for (int j = 0; j < postings.size(); j++) {
                Posting p = postings.get(j);
                if (p.getDocument().equals(doc)) {
                    tf = p.getTf();
                    break;
                }
            }

            // --- calcular IDF ---
            double idf = term.getIdf();

            // --- calcular peso TF-IDF ---
            double weight = tf * idf;
            vector.add(weight);
        }
        return vector;
    }
}
