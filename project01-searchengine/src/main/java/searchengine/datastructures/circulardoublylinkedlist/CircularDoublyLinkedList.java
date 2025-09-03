package searchengine.datastructures.circulardoublylinkedlist;

public class CircularDoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    public CircularDoublyLinkedList() {
        head = null;
        tail = null;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public void insertEnd(T data) {
        Node<T> newNode = new Node<T>(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            newNode.setNext(head);
            tail = newNode;
        }
    }
}
