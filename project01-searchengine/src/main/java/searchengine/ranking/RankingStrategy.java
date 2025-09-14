package searchengine.ranking;

import searchengine.core.IndexManager;
import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.DocumentScore;

import javax.swing.*;

public interface RankingStrategy {
    Vector<DocumentScore> rank(Vector<Double> queryVector, Vector<Document> candidates, IndexManager index);
}
