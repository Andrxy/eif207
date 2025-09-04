package searchengine.index.invertedindex;

import searchengine.datastructures.Vector;
import searchengine.index.ZipfLaw;
import searchengine.model.Document;
import searchengine.model.Posting;
import searchengine.model.Term;
import searchengine.utils.Normalizer;
import searchengine.utils.Tokenizer;

public class InvertedIndexBuilder {
    public void buildIndex(Vector<Document> documents) {
        buildCorpus(InvertedIndex.getInstance().getDocuments());
        ZipfLaw.filter();
    }

    private void buildCorpus(Vector<Document> documents) {
        for (Document document : documents) {
            String normalizedContent = Normalizer.normalize(document.getRawContent());
            Vector<String> tokens = Tokenizer.tokenize(normalizedContent);

            for (String token : tokens) {
                Term found = InvertedIndex.getInstance().getCorpus().find(new Term(token));

                if (found == null) {
                    Term newTerm = new Term(token);
                    InvertedIndex.getInstance().getCorpus().add(newTerm);
                    newTerm.getPostings().add(new Posting(document.getId()));
                }
                else {
                    found.incrementTTF();
                    Posting posting = found.getPostings().find(new Posting(document.getId()));

                    if (posting == null) {
                        Posting newPosting = new Posting(document.getId());
                        found.getPostings().add(newPosting);
                    }
                    else {
                        posting.incrementTF();
                    }
                }
            }
        }
    }
}
