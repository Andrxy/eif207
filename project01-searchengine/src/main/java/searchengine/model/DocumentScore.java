package searchengine.model;

public class DocumentScore {
    private Document document;
    private double score;

    public DocumentScore(Document document, double score) {
        this.document = document;
        this.score = score;
    }

    public Document getDocument() {
        return document;
    }

    public double getScore() {
        return score;
    }
}