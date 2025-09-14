package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.Term;

import java.io.*;

public class IndexPersistence {
    public static void saveIndex(InvertedIndex index, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Guardar documentos
            oos.writeObject(index.getDocuments());

            // Guardar corpus (Terms con postings)
            oos.writeObject(index.getCorpus());

            System.out.println("Índice guardado correctamente en " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga el índice invertido desde un archivo binario.
     */
    @SuppressWarnings("unchecked")
    public static void loadIndex(InvertedIndex index, String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Cargar documentos
            Vector<Document> documents = (Vector<Document>) ois.readObject();
            index.setDocuments(documents);

            // Cargar corpus
            Vector<Term> corpus = (Vector<Term>) ois.readObject();
            index.setCorpus(corpus);

            System.out.println("Índice cargado correctamente desde " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
