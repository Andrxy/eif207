package searchengine.utils;

import searchengine.datastructures.Vector;

public class Tokenizer {
    public static Vector<String> tokenize(String rawContent) {
        Vector<String> tokens = new Vector<String>();

        String[] parts = rawContent.split("\\W+");

        for (String part : parts) {
            if (part.length() > 0) tokens.add(part);
        }

        return tokens;
    }
}
