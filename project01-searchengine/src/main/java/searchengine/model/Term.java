package searchengine.model;

import searchengine.datastructures.Vector;

public class Term {
    private String term;
    private int documentFrecuency;
    private Vector<Posting> postings;
    private double idf;


}
