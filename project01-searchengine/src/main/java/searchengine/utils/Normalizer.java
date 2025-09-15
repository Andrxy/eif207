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
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);

            if ((character >= 'a' && character <= 'z') || (character >= '0' && character <= '9') || character == ' ') {
                result.append(character);
            } else {
                result.append(' ');
            }
        }

        return result.toString();
    }

    private static String replaceAccents(String content) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);

            switch (character) {
                case 'á':
                    result.append('a');
                    break;
                case 'é':
                    result.append('e');
                    break;
                case 'í':
                    result.append('i');
                    break;
                case 'ó':
                    result.append('o');
                    break;
                case 'ú':
                    result.append('u');
                    break;
                default:
                    result.append(character);
            }
        }

        return result.toString();
    }
}
