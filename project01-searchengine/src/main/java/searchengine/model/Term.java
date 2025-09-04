package searchengine.model;

import searchengine.datastructures.Vector;

public class Term {
    private String term;
    private int ttf;
    private Vector<Posting> postings;
    private double idf;

    public Term(String term) {
        this.term = term;
        this.ttf = 1;
        this.postings = new Vector<>();
        this.idf = 0;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getTtf() {
        return ttf;
    }

    public void setTtf(int ttf) {
        this.ttf = ttf;
    }

    public Vector<Posting> getPostings() {
        return postings;
    }

    public void setPostings(Vector<Posting> postings) {
        this.postings = postings;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public void incrementTTF() {
        ++this.ttf;
    }

    @Override
    public String toString() {
        return "Term{" + term + " - " + ttf + " Postings{" + postings +"}" + "}\n";
    }

    @Override
    public boolean equals(Object obj) {
        if  (this == obj)  return true;
        if (obj == null || getClass() != obj.getClass())  return false;

        Term other =  (Term) obj;
        return term.equals(other.term);
    }
}