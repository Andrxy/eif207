package searchengine.ranking;

import searchengine.core.IndexManager;
import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.VectorTFIDF;
import searchengine.model.Document;
import searchengine.model.DocumentScore;

public class CosineSimilarityRanking implements RankingStrategy {
    @Override
    public Vector<DocumentScore> rank(Vector<Double> queryVector, Vector<Document> candidates, IndexManager index) {
        Vector<DocumentScore> results = new Vector<>();

        for (Document doc : candidates) {
            Vector<Double> documentVector = VectorTFIDF.buildDocumentVector(doc, index);

            double score = computeCosineSimilarity(queryVector, documentVector);

            results.add(new DocumentScore(doc, score));
        }

        sortScores(results);

        return results;
    }

    private double computeCosineSimilarity(Vector<Double> v1, Vector<Double> v2) {
        double dot = 0.0;
        double normV1 = 0.0;
        double normV2 = 0.0;

        int size1 = v1.getSize();
        int size2 = v2.getSize();
        int size;

        if (size2 < size1) {
            size = size2;
        } else {
            size = size1;
        }

        for (int i = 0; i < size; i++) {
            double a = v1.getAt(i);
            double b = v2.getAt(i);
            dot = dot + (a * b);
            normV1 = normV1 + (a * a);
            normV2 = normV2 + (b * b);
        }

        if (normV1 == 0.0) {
            return 0.0;
        }

        if (normV2 == 0.0) {
            return 0.0;
        }

        return dot / (Math.sqrt(normV1) * Math.sqrt(normV2));
    }


    private void sortScores(Vector<DocumentScore> results) {
        int n = results.getSize();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (results.getAt(j).getScore() < results.getAt(j + 1).getScore()) {
                    results.swap(j, j + 1);
                }
            }
        }
    }
}
