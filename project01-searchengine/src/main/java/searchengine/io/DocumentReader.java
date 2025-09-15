package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.model.Document;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class DocumentReader {
    private static String filePath = "files";

    public Vector<Document> readFiles() throws IOException {
        Vector<Document> documents = new Vector<>();

        File dir = new File(DocumentReader.class.getClassLoader().getResource(filePath).getFile());
        File[] files = dir.listFiles();

        if (files == null) return documents;

        for (File file : files) {
            String base64Name = file.getName().replace(".txt", "");

            String decodedName = decodeFileName(base64Name);

            String rawContent = readFileContent(file);

            Document document = new Document(decodedName, rawContent);
            documents.add(document);
        }

        return documents;
    }

    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append(" ");
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + file.getName());
            e.printStackTrace();
        }

        return content.toString();
    }

    private String decodeFileName(String base64Name) {
        byte[] bytes = Base64.getDecoder().decode(base64Name);
        return new String(bytes);
    }
}
