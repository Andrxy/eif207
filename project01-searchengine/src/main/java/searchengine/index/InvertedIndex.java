package searchengine.index;

import searchengine.datastructures.Vector;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.Serializable;

public class InvertedIndex implements Serializable {
    private static InvertedIndex instance = null;
    private Vector<Term> corpus;
    private Vector<Document> documents;

    private InvertedIndex() {
        corpus =  new Vector<>();
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

    public Vector<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Vector<Document> documents) {
        this.documents = documents;
    }

    public void setCorpus(Vector<Term> corpus) {
        this.corpus = corpus;
    }
}
