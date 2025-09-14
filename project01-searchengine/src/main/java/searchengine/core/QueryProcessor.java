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

        Vector<Document> candidates = manager.getCandidates(tokenization);

        return ranking.rank(queryVector, candidates, manager);
    }

    public void setRanking(RankingStrategy ranking) {
        this.ranking = ranking;
    }
}
