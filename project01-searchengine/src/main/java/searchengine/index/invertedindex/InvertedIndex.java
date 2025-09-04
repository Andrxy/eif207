package searchengine.index.invertedindex;

import searchengine.datastructures.Vector;
import searchengine.model.Document;
import searchengine.model.Term;

public class InvertedIndex {
    private static InvertedIndex instance = null;
    private Vector<Document> documents;
    private Vector<Term> corpus;

    private InvertedIndex() {
        corpus =  new Vector<>();
        documents = new Vector<>();
    }

    public static InvertedIndex getInstance() {
        if (instance == null) {
            instance = new InvertedIndex();
        }
        return instance;
    }

    public Vector<Term> getCorpus() {
        return corpus;
    }

    public void setDocuments(Vector<Document> documents) {
        this.documents = documents;
    }

    public Vector<Document> getDocuments() {
        return documents;
    }
}
