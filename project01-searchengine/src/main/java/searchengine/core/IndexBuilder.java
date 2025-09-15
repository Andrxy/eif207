package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.model.Term;
import searchengine.utils.Normalizer;
import searchengine.utils.Tokenizer;

public class IndexBuilder {
    public void buildForDocument(InvertedIndex index, Document document) {
        String normalizedContent = Normalizer.normalize(document.getRawContent());
        Vector<String> tokens = Tokenizer.tokenize(normalizedContent);

        for (String token : tokens) {
            Term term = index.getCorpus().find(new Term(token));

            if (term == null) {
                term = new Term(token);
                index.getCorpus().add(term);
            }

            term.addPosting(document);
        }
    }

    public void buildIndex(InvertedIndex index, Vector<Document> documents) {
        for (Document document : documents) {
            buildForDocument(index, document);
        }
        computeIDF(index, documents.getSize());
    }

    private void computeIDF(InvertedIndex index, int totalDocuments) {
        for (Term term : index.getCorpus()) {
            term.calculateIDF(totalDocuments);
        }
    }
}
