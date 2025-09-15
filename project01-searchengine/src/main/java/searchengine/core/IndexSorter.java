package searchengine.core;

import searchengine.datastructures.Vector;
import searchengine.model.Term;

public class IndexSorter {

    // MergeSort por TTF
    public static void sortByTTF(Vector<Term> corpus) {
        if (corpus.getSize() <= 1) return;

        int mid = corpus.getSize() / 2;
        Vector<Term> left = new Vector<>();
        Vector<Term> right = new Vector<>();

        for (int i = 0; i < mid; i++) left.add(corpus.getAt(i));
        for (int i = mid; i < corpus.getSize(); i++) right.add(corpus.getAt(i));

        sortByTTF(left);
        sortByTTF(right);

        mergeByTTF(corpus, left, right);
    }

    private static void mergeByTTF(Vector<Term> corpus, Vector<Term> left, Vector<Term> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.getSize() && j < right.getSize()) {
            if (left.getAt(i).getTtf() <= right.getAt(j).getTtf()) {
                corpus.setAt(left.getAt(i), k++);
                i++;
            } else {
                corpus.setAt(right.getAt(j), k++);
                j++;
            }
        }
        while (i < left.getSize()) corpus.setAt(left.getAt(i++), k++);

        while (j < right.getSize()) corpus.setAt(right.getAt(j++), k++);
    }

    // RadixSort alfabético
    public static void sortAlphabetically(Vector<Term> corpus) {
        int n = corpus.getSize();
        int maxLen = 0;

        // encontrar longitud máxima de palabra
        for (int i = 0; i < n; i++) {
            int len = corpus.getAt(i).getTerm().length();
            if (len > maxLen) maxLen = len;
        }

        // se hace radix sort carácter por carácter
        for (int pos = maxLen - 1; pos >= 0; pos--) {
            countingSortByChar(corpus, pos);
        }
    }

    private static void countingSortByChar(Vector<Term> corpus, int pos) {
        int n = corpus.getSize();
        Term[] output = new Term[n];
        int[] count = new int[128]; // ASCII simple

        for (int i = 0; i < 128; i++) count[i] = 0;

        // contar ocurrencias del caracter en la posición
        for (int i = 0; i < n; i++) {
            String term = corpus.getAt(i).getTerm();
            char c = pos < term.length() ? term.charAt(pos) : 0;
            count[c]++;
        }

        // acumulado
        for (int i = 1; i < 128; i++) count[i] += count[i - 1];

        // construir arreglo ordenado
        for (int i = n - 1; i >= 0; i--) {
            String term = corpus.getAt(i).getTerm();
            char c = pos < term.length() ? term.charAt(pos) : 0;
            output[count[c] - 1] = corpus.getAt(i);
            count[c]--;
        }

        // copiar al vector original
        for (int i = 0; i < n; i++) corpus.setAt(output[i], i);
    }
}
