package searchengine.utils;

import searchengine.datastructures.Vector;

public class Tokenizer {
    public static Vector<String> tokenize(String content) {
        Vector<String> tokens = new Vector<>();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);

            if ((c >= 'a' && c <= 'z') ||
                    (c >= 'A' && c <= 'Z') ||
                    (c >= '0' && c <= '9')) {
                current.append(c);
            } else {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            }
        }

        if (current.length() > 0) {
            tokens.add(current.toString());
        }

        return tokens;
    }
}
