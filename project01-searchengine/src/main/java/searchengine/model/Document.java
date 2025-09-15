    package searchengine.model;

    import java.io.Serializable;

    public class Document implements Serializable {
        private static int nextId = -1;

        private int id;
        private String decodedName;
        private String rawContent;

        public Document(String decodedName, String rawContent) {
            this.id = ++nextId;
            this.decodedName = decodedName;
            this.rawContent = rawContent;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDecodedName() {
            return decodedName;
        }

        public void setDecodedName(String decodedName) {
            this.decodedName = decodedName;
        }

        public String getRawContent() {
            return rawContent;
        }

        public void setRawContent(String rawContent) {
            this.rawContent = rawContent;
        }

        @Override
        public String toString() {
            return id + " " + decodedName + " " + rawContent + "\n";
        }
    }
