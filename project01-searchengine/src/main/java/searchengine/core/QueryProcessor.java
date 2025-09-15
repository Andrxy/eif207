package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.DocumentScore;
import searchengine.model.Posting;
import searchengine.model.Term;
import searchengine.ranking.RankingStrategy;
import searchengine.utils.Normalizer;
import searchengine.utils.Tokenizer;

public class QueryProcessor {
    private InvertedIndex index;
    private RankingStrategy ranking;

    public QueryProcessor(InvertedIndex idx, RankingStrategy rank) {
        this.index = idx;
        this.ranking = rank;
    }

    public Vector<DocumentScore> process(String rawQuery) {
        // Normaliza el query
        String normalizedQuery = Normalizer.normalize(rawQuery);

        // Tokeniza el query
        Vector<String> tokenization = Tokenizer.tokenize(normalizedQuery);

        // Vector de la consulta
        Vector<Double> queryVec = VectorTFIDF.buildQueryVector(tokenization, index);

        // Obtener Documentos candidatos
        Vector<Document> candidates = getCandidates(tokenization);

        // Rankear
        return ranking.rank(queryVec, candidates, index);
    }

    public void setRanking(RankingStrategy ranking) {
        this.ranking = ranking;
    }

    // Buscar Candidatos segun los terminos
    private Vector<Document> getCandidates(Vector<String> tokenization) {
        Vector<Document> candidates = new Vector<>();

        for (String token : tokenization) {
            Term term = searchTermBinary(token);

            if (term != null) {
                Vector<Posting> postings = term.getPostings();

                for (Posting posting : postings) {
                    Document document = posting.getDocument();
                    if (candidates.find(document) == null) { // Evitar Duplicados
                        candidates.add(document);
                    }
                }
            }
        }

        return candidates;
    }


    // busqueda binaria en el corpus
    private Term searchTermBinary(String term) {
        Vector<Term> corpus = index.getCorpus();

        int left = 0;
        int right = corpus.getSize() - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            int comparer = corpus.getAt(middle).getTerm().compareTo(term);

            if (comparer == 0) {
                return corpus.getAt(middle);
            } else if (comparer < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return null;
    }
}
