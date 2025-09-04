package searchengine.model;

public class Posting {
    private int docId;
    private int tf;

    public Posting(int docId) {
        this.docId = docId;
        this.tf = 1;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public void incrementTF() {
        ++this.tf;
    }

    @Override
    public boolean equals(Object obj) {
        if  (this == obj)  return true;
        if (obj == null || getClass() != obj.getClass())  return false;

        Posting other =  (Posting) obj;
        return docId == other.docId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(docId)).append("   ");
        return sb.toString();
    }
}
