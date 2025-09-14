package searchengine.model;

import java.io.Serializable;

public class Posting implements Serializable {
    private Document document;
    private int tf;

    public Posting(Document document) {
        this.document = document;
        this.tf = 1;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public void incrementTf() {
        this.tf++;
    }

    @Override
    public boolean equals(Object obj) {
        if  (this == obj)  return true;
        if (obj == null || getClass() != obj.getClass())  return false;

        Posting other =  (Posting) obj;
        return document.getId() == other.document.getId();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(document.getId());
        return sb.toString();
    }
}
