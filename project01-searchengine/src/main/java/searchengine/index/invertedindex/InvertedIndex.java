package searchengine.index.invertedindex;

import searchengine.datastructures.Vector;
import searchengine.datastructures.circulardoublylinkedlist.CircularDoublyLinkedList;
import searchengine.model.Posting;
import searchengine.model.Term;

public class InvertedIndex {
    private Vector<Term> corpus;
    private CircularDoublyLinkedList<Posting> invertedIndex;


}
