package searchengine;

import searchengine.datastructures.Vector;
import searchengine.index.invertedindex.InvertedIndex;
import searchengine.index.invertedindex.InvertedIndexBuilder;
import searchengine.io.FileReader;
import searchengine.model.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  {
        FileReader file = new FileReader();
        try {
            Vector<Document> documents = file.readFiles();
            InvertedIndex.getInstance().setDocuments(documents);
            InvertedIndexBuilder a = new InvertedIndexBuilder();
            a.buildIndex(documents);

            System.out.println(InvertedIndex.getInstance().getCorpus() + "\n" + String.valueOf(InvertedIndex.getInstance().getCorpus().getSize()));
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}
