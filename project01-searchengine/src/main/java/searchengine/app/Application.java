package searchengine.app;

import searchengine.core.IndexManager;
import searchengine.core.QueryProcessor;
import searchengine.datastructures.Vector;
import searchengine.model.Document;
import searchengine.model.DocumentScore;
import searchengine.ranking.CosineSimilarityRanking;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    private Scanner scanner;
    private IndexManager indexManager;
    private QueryProcessor queryProcessor;

    private static final String BINARY_INDEX_PATH = "data/index.bin";
    private static final String DOCUMENTS_DIR_PATH = "files";

    public Application() {
        scanner = new Scanner(System.in);
        indexManager = new IndexManager();
        queryProcessor = new QueryProcessor(indexManager, new CosineSimilarityRanking());
    }

    public void run() throws IOException {
        initializeEngine();
        configureZipf();
        queryLoop();
    }

    private void initializeEngine() throws IOException {
        System.out.println("=== Motor de Búsqueda ===");
        System.out.println("Elija una opción de inicialización:");
        System.out.println("1. Cargar índice binario");
        System.out.println("2. Leer archivos y construir índice");
        System.out.print("> ");

        String line = scanner.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(line);
        } catch (Exception e) {
            choice = 0;
        }

        if (choice == 1) {
            indexManager.loadIndex(BINARY_INDEX_PATH);
        } else if (choice == 2) {
            Vector<Document> docs = indexManager.readDocuments(DOCUMENTS_DIR_PATH);
            indexManager.buildIndex(docs);
            indexManager.saveIndex(BINARY_INDEX_PATH);
        } else {
            System.out.println("Opción inválida.");
            return;
        }
    }

    private void configureZipf() {
        System.out.print("Ingrese porcentaje de Ley de Zipf a aplicar (0 a 1, ej: 0.1): ");
        String line = scanner.nextLine();
        double percent = 0.0;
        try {
            percent = Double.parseDouble(line);
        } catch (Exception e) {
            percent = 0.0;
        }

        indexManager.applyZipf(percent);
        indexManager.sortAlphabetically();
    }

    private void queryLoop() {
        System.out.println("\nÍndice listo! Ingrese sus consultas (escriba 'salir' para terminar):");

        while (true) {
            System.out.print("> ");
            String query = scanner.nextLine();
            if (query.toLowerCase().equals("salir")) {
                break;
            }

            Vector<DocumentScore> results = queryProcessor.process(query);

            if (results.getSize() == 0) {
                System.out.println("No se encontraron documentos.");
            } else {
                System.out.println("Resultados:");
                for (int i = 0; i < results.getSize(); i++) {
                    DocumentScore ds = results.getAt(i);
                    System.out.printf("- %s (score: %.4f)\n", ds.getDocument().getDecodedName(), ds.getScore());
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        Application app = new Application();
        app.run();
    }
}
