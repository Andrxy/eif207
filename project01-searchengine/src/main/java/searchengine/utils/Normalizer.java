package searchengine.utils;

public class Normalizer {
    public static String normalize(String content) {
        content = toLowerCase(content);
        content = replacePunctuation(content);
        content = replaceAccents(content);

        return content;
    }

    private static String toLowerCase(String content) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c = (char)(c + 32);
            }
            result.append(c);
        }
        return result.toString();
    }

    private static String replacePunctuation(String content) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                result.append(c);
            } else {
                result.append(' ');
            }
        }
        return result.toString();
    }

    private static String replaceAccents(String content) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if (c == 'á') {
                c = 'a';
            } else if (c == 'é') {
                c = 'e';
            } else if (c == 'í') {
                c = 'i';
            } else if (c == 'ó') {
                c = 'o';
            } else if (c == 'ú') {
                c = 'u';
            }
            result.append(c);
        }
        return result.toString();
    }

}
