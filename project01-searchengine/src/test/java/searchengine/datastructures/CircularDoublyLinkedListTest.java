package searchengine.datastructures;

import org.junit.jupiter.api.Test;
import searchengine.datastructures.circulardoublylinkedlist.CircularDoublyLinkedList;
import searchengine.datastructures.circulardoublylinkedlist.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CircularDoublyLinkedListTest {
    @Test
    public void checkHeadTest() {
        CircularDoublyLinkedList<String> list = new  CircularDoublyLinkedList<>();

        list.insertEnd("Hola");
        list.insertEnd("Wilson");
        list.insertEnd("Viva");
        list.insertEnd("Heredia");

        assertEquals("Hola", list.getHead().getData(), "Correct head");
    }

    @Test
    public void checkTailTest() {
        CircularDoublyLinkedList<String> list = new  CircularDoublyLinkedList<>();

        list.insertEnd("Viva");
        list.insertEnd("Heredia");

        assertEquals("Heredia", list.getTail().getData(), "Correct tail");
    }
}
