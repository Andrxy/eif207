package searchengine.io;

import searchengine.datastructures.Vector;
import searchengine.model.Document;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Base64;

public class FileReader {
    private static final String FILES_PATH = "files";

    public Vector<Document> readFiles() throws IOException {
        Vector<Document> documents = new Vector<>();

        File dir = new File(getClass().getClassLoader().getResource(FILES_PATH).getFile());
        File[] files = dir.listFiles();

        if (files == null) {
            return documents;
        }

        for (File file : files) {
            String decodedName = decodeBase64(file.getName().replace(".txt", ""));
            String content = readFileContent(file);

            Document document = new Document(decodedName, content);
            documents.add(document);
        }

        return documents;
    }

    private String readFileContent(File file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        }
        return contentBuilder.toString();
    }

    private String decodeBase64(String base64Name) {
        byte[] bytes = Base64.getDecoder().decode(base64Name);
        return new String(bytes);
    }
}
