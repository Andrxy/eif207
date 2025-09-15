package searchengine.ranking;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.DocumentScore;

public interface RankingStrategy {
    Vector<DocumentScore> rank(Vector<Double> queryVector, Vector<Document> candidates, InvertedIndex index);
}
