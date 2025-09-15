package searchengine.app;

import searchengine.core.QueryProcessor;
import searchengine.datastructures.Vector;
import searchengine.core.IndexBuilder;
import searchengine.index.InvertedIndex;
import searchengine.core.ZipfApplier;
import searchengine.io.IndexPersistence;
import searchengine.model.Document;
import searchengine.model.DocumentScore;
import searchengine.ranking.CosineSimilarityRanking;

import searchengine.io.DocumentReader;
import searchengine.core.IndexSorter;

import java.io.IOException;
import java.util.Scanner;

public class Application {
    private Scanner scanner;
    private InvertedIndex index;
    private DocumentReader reader;
    private QueryProcessor queryProcessor;
    private IndexBuilder builder;
    private ZipfApplier zipf;

    public Application() {
        scanner = new Scanner(System.in);
        index = InvertedIndex.getInstance();
        builder = new IndexBuilder();
        queryProcessor = new QueryProcessor(index, new CosineSimilarityRanking());
        reader = new DocumentReader();
        zipf = new ZipfApplier(0.1); // por defecto
    }

    public void run() throws IOException {
        initializeEngine();
        configureZipf();
        queryLoop();
    }

    private void initializeEngine() throws IOException {
        System.out.println("=== Motor de Busqueda ===");
        System.out.println("1. Cargar indice binario");
        System.out.println("2. Leer archivos y construir indice");
        System.out.print("Opcion: ");

        int opcion = Integer.parseInt(scanner.nextLine());

        if (opcion == 1) {
            IndexPersistence.loadIndex(index);
        } else if (opcion == 2) {
            // Se lee los codumentos
            Vector<Document> docs = reader.readFiles();
            // Se construye el indice
            builder.buildIndex(index, docs);
            // Se guarda al momento
            IndexPersistence.saveIndex(index);
        } else {
            System.out.println("Opcion invalida");
        }
    }

    private void configureZipf() {
        System.out.print("Ingrese porcentaje de Zipf (0 a 1. ej: 0.1): ");
        double percent = Double.parseDouble(scanner.nextLine());

        zipf = new ZipfApplier(percent);

        // Se ordena por TTF
        IndexSorter.sortByTTF(index.getCorpus());

        // Se aplica Zipf
        zipf.filter(index);

        // Se ordena alfabeticamente para poder usar Busqueda Binaria
        IndexSorter.sortAlphabetically(index.getCorpus());
    }

    private void queryLoop() {
        System.out.println("Indice listo! Escriba su consulta (o 'salir')");

        while (true) {
            System.out.print("> ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("salir")) {
                break;
            }

            Vector<DocumentScore> results = queryProcessor.process(query);

            if (results.getSize() == 0) {
                System.out.println("No se encontraron documentos");
            } else {
                System.out.println("Resultados:");
                for (DocumentScore documentScore : results) {
                    System.out.println("- " + documentScore.getDocument().getDecodedName() + " (score: " + documentScore.getScore() + ")");
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.run();
    }
}
