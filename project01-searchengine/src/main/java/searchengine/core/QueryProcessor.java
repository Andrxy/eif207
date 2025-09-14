package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.VectorTFIDF;
import searchengine.model.Document;
import searchengine.model.DocumentScore;
import searchengine.model.Posting;
import searchengine.model.Term;
import searchengine.ranking.RankingStrategy;
import searchengine.utils.Normalizer;
import searchengine.utils.Tokenizer;

public class QueryProcessor {
    private IndexManager manager;
    private RankingStrategy ranking;

    public QueryProcessor(IndexManager manager, RankingStrategy ranking) {
        this.manager = manager;
        this.ranking = ranking;
    }

    public Vector<DocumentScore> process(String rawQuery) {
        String normalizedQuery =  Normalizer.normalize(rawQuery);
        Vector<String> tokenization = Tokenizer.tokenize(normalizedQuery);

        Vector<Double> queryVector = VectorTFIDF.buildQueryVector(tokenization, manager);

        Vector<Document> candidates = getCandidates(tokenization);

        return ranking.rank(queryVector, candidates, manager);
    }

    public void setRanking(RankingStrategy ranking) {
        this.ranking = ranking;
    }

    private Vector<Document> getCandidates(Vector<String> tokenization) {
        Vector<Document> candidates = new Vector<>();
        for (String token : tokenization) {
            Term term = searchTermBinary(token);
            if (term != null) {
                term.getPostings().forEach(posting -> {
                    Document doc = posting.getDocument();
                    if (candidates.find(doc) == null) candidates.add(doc);
                });
            }
        }
        return candidates;
    }

    private Term searchTermBinary(String term) {
        Vector<Term> corpus = manager.getCorpus();
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
