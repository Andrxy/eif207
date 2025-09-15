package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.*;

public class IndexPersistence {

    private static String filePath = "data/index.bin";

    public static void saveIndex(InvertedIndex index) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath));
            Vector<Document> documents = index.getDocuments();
            Vector<Term> corpus = index.getCorpus();

            output.writeObject(documents);
            output.writeObject(corpus);

            output.close();
            System.out.println("Indice guardado con exito en " + filePath);
        } catch (IOException e) {
            System.out.println("Error al guardar el indice");
            e.printStackTrace();
        }
    }

    public static void loadIndex(InvertedIndex index) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath));

            Vector<Document> documents = (Vector<Document>) input.readObject();
            Vector<Term> corpus = (Vector<Term>) input.readObject();

            index.setDocuments(documents);
            index.setCorpus(corpus);

            input.close();
            System.out.println("Indice cargado con exito de " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erroe al cargar el indice");
            e.printStackTrace();
        }
    }
}
