package searchengine.index;

import searchengine.datastructures.Vector;

public class CosineSimilairty {
    public static double compute(Vector<Double> q, Vector<Double> d) {
        double dotProduct = 0.0;
        double normQ = 0.0;
        double normD = 0.0;

        for (int i = 0; i < q.getSize(); i++) {
            double qi = q.getAt(i);
            double di = d.getAt(i);

            dotProduct += qi * di;
            normQ += qi * qi;
            normD += di * di;
        }

        if (normQ == 0 || normD == 0) {
            return 0.0; // no overlap or empty vector
        }

        return dotProduct / (Math.sqrt(normQ) * Math.sqrt(normD));
    }
}
