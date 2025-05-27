package DSA;

import java.util.NoSuchElementException;

public class CustomQueue<T> {

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> front; // Head of the linked list
    private Node<T> rear;  // Tail of the linked list
    private int size;

    public CustomQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Adds an item to the end of the queue.
     * @param item The item to be added.
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the item from the front of the queue.
     * @return The item at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot dequeue.");
        }
        T data = front.data;
        front = front.next;
        if (front == null) { // If list becomes empty, reset rear as well
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Returns the item from the front of the queue without removing it.
     * @return The item at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot peek.");
        }
        return front.data;
    }

    /**
     * Checks if the queue is empty.
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the queue.
     * @return The size of the queue.
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}

