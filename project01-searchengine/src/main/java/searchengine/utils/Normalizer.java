package searchengine.utils;

public class Normalizer {
    public static String normalize(String rawContent) {
        rawContent = toLowerCase(rawContent);
        rawContent = replacePunctuation(rawContent);
        rawContent = replaceAccents(rawContent);

        return rawContent;
    }

    private static String toLowerCase(String rawContent) {
        return rawContent.toLowerCase();
    }

    private static String replacePunctuation(String rawContent) {
        return rawContent.replaceAll("[^a-zA-Z0-9]", " ");
    }

    private static String replaceAccents(String rawContent) {
        return rawContent
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u");

    }
}
