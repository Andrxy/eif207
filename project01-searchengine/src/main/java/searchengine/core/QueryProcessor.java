package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Document;
import searchengine.utils.Tokenizer;

public class QueryProcessor {
    private RankingStrategy rankingStrategy;
    private InvertedIndex index;

    public QueryProcessor() {
        this.rankingStrategy = rankingStrategy;
        this.index = InvertedIndex.getInstance();
    }

    public Vector<Document> process(String query) {
        Vector<String> tokenization = Tokenizer.tokenize(query);

        Vector<Document> candidateDocs = retrievePostingLists(tokenization);

        return rankingStrategy.rank(candidateDocs, terms);
    }

    private Vector<Document> retrievePostingLists(Vector<String> tokenization) {
        Vector<Document> candidateDocs = new Vector<>();

        for (String token : tokenization) {
            Vector<Document> postingList = index.getPostingList(token);

            for (Document doc : postingList) {
                Document document = candidateDocs.find(doc);
                if (document != null) {
                    postingList.add(document);
                }
            }
        }

        return candidateDocs;
    }
}
