package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class IndexPersistence {

    public static void saveIndex(InvertedIndex index, String filePath) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));

            Vector<Document> documents = index.getDocuments();
            oos.writeObject(documents);

            Vector<Term> corpus = index.getCorpus();
            oos.writeObject(corpus);

            System.out.println("Índice guardado correctamente en " + filePath);

            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadIndex(InvertedIndex index, String filePath) {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));

            Object objDocs = ois.readObject();
            Vector<Document> documents = (Vector<Document>) objDocs;
            index.setDocuments(documents);

            Object objCorpus = ois.readObject();
            Vector<Term> corpus = (Vector<Term>) objCorpus;
            index.setCorpus(corpus);

            System.out.println("Índice cargado correctamente desde " + filePath);

            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
