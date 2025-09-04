package searchengine.utils;

import searchengine.datastructures.Vector;

public class Tokenizer {
    public static Vector<String> tokenize(String content) {
        Vector<String> tokens = new Vector<>();

        String[] parts = content.split("\\W+");

        for (String part : parts) {
            if (!part.isEmpty()) tokens.add(part);
        }

        return tokens;
    }
}
