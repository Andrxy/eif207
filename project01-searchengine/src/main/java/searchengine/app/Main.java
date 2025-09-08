package searchengine.app;

import searchengine.core.SearchEngine;
import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.index.InvertedIndexBuilder;
import searchengine.io.FileReader;
import searchengine.model.Document;

public class Main {
    public static void main(String[] args)  {
        SearchEngine  searchEngine = new SearchEngine();
        FileReader fileReader = new  FileReader();

        try {
            Vector<Document> documents = fileReader.readFiles();

            searchEngine.buildIndex(documents);

            System.out.println(InvertedIndex.getInstance().getCorpus().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
