package searchengine.sorting;

import searchengine.datastructures.Vector;
import searchengine.index.InvertedIndex;
import searchengine.model.Term;

public class AlphabeticalSort implements SortStrategy {
    public void sort(InvertedIndex index) {
        Vector<Term> terms = index.getCorpus();

        if (terms == null || terms.getSize() < 2) return;

        // Obtener la longitud máxima de los términos
        int maxLength = 0;
        for (Term t : terms) {
            if (t.getTerm().length() > maxLength) {
                maxLength = t.getTerm().length();
            }
        }

        // Radix sort comenzando desde el último carácter
        for (int pos = maxLength - 1; pos >= 0; pos--) {
            countingSortByCharacter(terms, pos);
        }
    }

    private void countingSortByCharacter(Vector<Term> terms, int pos) {
        int n = terms.getSize();
        Term[] output = new Term[n];
        int range = 256; // ASCII extendido
        int[] count = new int[range];

        // Contar ocurrencias del carácter en la posición pos
        for (Term t : terms) {
            char ch = getCharAt(t.getTerm(), pos);
            count[ch]++;
        }

        // Convertir count en posición acumulativa
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        // Construir el array de salida (desde atrás para mantener estabilidad)
        for (int i = n - 1; i >= 0; i--) {
            Term t = terms.getAt(i);
            char ch = getCharAt(t.getTerm(), pos);
            output[count[ch] - 1] = t;
            count[ch]--;
        }

        // Copiar de vuelta a la lista original
        for (int i = 0; i < n; i++) {
            terms.setAt(output[i], i);
        }
    }

    private char getCharAt(String s, int pos) {
        if (pos < s.length()) {
            return s.charAt(pos);
        } else {
            return 0; // carácter "nulo" para strings cortos
        }
    }

}
