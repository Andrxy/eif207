package searchengine.ranking;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.core.VectorTFIDF;
import searchengine.model.Document;
import searchengine.model.DocumentScore;

public class CosineSimilarityRanking implements RankingStrategy {

    @Override
    public Vector<DocumentScore> rank(Vector<Double> queryVector, Vector<Document> documents, InvertedIndex index) {
        Vector<DocumentScore> scores = new Vector<>();

        // calcular el score de cada documento con respecto a la query
        for (Document document : documents) {
            Vector<Double> docVector = VectorTFIDF.buildDocumentVector(document, index);

            double similarity = computeCosine(queryVector, docVector);

            scores.add(new DocumentScore(document, similarity));
        }

        // ordenar
        bubbleSort(scores);

        return scores;
    }

    // calculo de la similitud coseno
    private double computeCosine(Vector<Double> v1, Vector<Double> v2) {
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        int n = v1.getSize();
        for (int i = 0; i < n; i++) {
            double val1 = v1.getAt(i);
            double val2 = v2.getAt(i);

            dotProduct += val1 * val2;
            norm1 += val1 * val1;
            norm2 += val2 * val2;
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0; // evitar división entre 0
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // ordenar scores de mayor a menor
    private void bubbleSort(Vector<DocumentScore> scores) {
        int size = scores.getSize();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (scores.getAt(j).getScore() < scores.getAt(j + 1).getScore()) {
                    scores.swap(j, j + 1);
                }
            }
        }
    }
}
