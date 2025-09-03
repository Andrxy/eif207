package searchengine.model;

public class Document {
    private static int nextId = 0;

    private int id;
    private String decodedName;
    private String rawContent;

    public Document(String decodedName, String rawContent) {
        this.id = nextId++;
        this.decodedName = decodedName;
        this.rawContent = rawContent;
    }
}
