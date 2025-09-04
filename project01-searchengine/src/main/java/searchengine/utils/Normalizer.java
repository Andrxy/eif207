package searchengine.utils;

public class Normalizer {
    public static String normalize(String content) {
        content = toLowerCase(content);
        content = replacePunctuation(content);
        content = replaceAccents(content);

        return content;
    }

    private static String toLowerCase(String content) {
        return content.toLowerCase();
    }

    private static String replacePunctuation(String content) {
        return content.replaceAll("[^a-zA-Z0-9]", " ");
    }

    private static String replaceAccents(String content) {
        return content
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");
    }
}
