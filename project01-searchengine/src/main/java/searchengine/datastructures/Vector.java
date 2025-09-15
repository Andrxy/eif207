package searchengine.datastructures;

import java.io.Serializable;
import java.util.Iterator;

public class Vector<T> implements Iterable<T>, Serializable {
    private T[] vector;
    private int capacity;
    private int size;

    public Vector() {
        capacity = 2;
        size = 0;
        vector = (T[]) new Object[capacity];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void add(T element) {
        if (size == capacity) expand();

        vector[size++] = element;
    }

    private void expand() {
        T[] newVector = (T[]) new Object[capacity * 2];

        for (int i = 0; i < capacity; i++)
            newVector[i] = vector[i];

        vector = newVector;
        capacity *= 2;
    }

    public T find(T element) {
        for (int i = 0; i < size; i++)
            if (vector[i].equals(element)) return vector[i];

        return null;
    }

    public T getAt(int index) {
        if (index >= size || index < 0) return null;
        return vector[index];
    }

    public void setAt(T element, int index) {
        if (index >= size || index < 0) return;
        vector[index] = element;
    }

    public void swap(int i, int j) {
        T temp = vector[i];
        vector[i] = vector[j];
        vector[j] = temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            if (vector[i] != null) sb.append(i + " ").append(vector[i]);
        }

        return sb.toString();
    }

    // Implementacion Iterador
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return vector[index++];
            }
        };
    }
}