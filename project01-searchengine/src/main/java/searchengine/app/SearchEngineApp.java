package searchengine.app;

import searchengine.core.IndexManager;
import searchengine.core.QueryProcessor;
import searchengine.datastructures.Vector;
import searchengine.model.Document;
import searchengine.model.DocumentScore;
import searchengine.ranking.CosineSimilarityRanking;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SearchEngineApp {

    private Scanner scanner;
    private IndexManager indexManager;
    private QueryProcessor queryProcessor;

    // Rutas absolutas predefinidas
    private static final String BINARY_INDEX_PATH = "data/index.bin";
    private static final String DOCUMENTS_DIR_PATH = "files";

    public SearchEngineApp() {
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

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            indexManager.loadIndex(BINARY_INDEX_PATH);
        } else if (choice == 2) {
            Vector<Document> documents = indexManager.readDocuments(DOCUMENTS_DIR_PATH);
            indexManager.buildIndex(documents);
            indexManager.saveIndex(BINARY_INDEX_PATH);
        } else {
            System.out.println("Opción inválida. Saliendo...");
            System.exit(0);
        }
    }

    private void configureZipf() {
        System.out.print("Ingrese porcentaje de Ley de Zipf a aplicar (0 a 1, ej: 0.1): ");
        double percentile = Double.parseDouble(scanner.nextLine());
        indexManager.applyZipf(percentile);
        indexManager.sortAlphabetically();
    }

    private void queryLoop() {
        System.out.println("\nÍndice listo! Ingrese sus consultas (escriba 'salir' para terminar):");

        while (true) {
            System.out.print("> ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("salir")) break;

            Vector<DocumentScore> results = queryProcessor.process(query);

            if (results.getSize() == 0) {
                System.out.println("No se encontraron documentos.");
            } else {
                System.out.println("Resultados:");
                for (DocumentScore ds : results) {
                    System.out.printf("- %s (score: %.4f)\n",
                            ds.getDocument().getDecodedName(), ds.getScore());
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        new SearchEngineApp().run();
    }
}
