package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.model.Document;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

public class FileReader {
    private static String path = "files";

    public Vector<Document> readFiles() throws IOException {
        Vector<Document> documents = new Vector<>();

        File dir = new File(FileReader.class.getClassLoader().getResource(path).getFile());
        File[] files = dir.listFiles();

        if (files == null) return documents;

        for (File file : files) {
            String base64Name = file.getName().replace(".txt", "");

            String decodedName = decodeBase64(base64Name);

            String rawContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            Document document = new Document(decodedName, rawContent);

            documents.add(document);
        }

        return documents;
    }

    private String decodeBase64(String base64Name) {
        byte[] bytes = Base64.getDecoder().decode(base64Name);
        return new String(bytes);
    }
}
