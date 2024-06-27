import java.util.Iterator;

/**
 * *24.3 (IMPLEMENT A DOUBLY LINKED LIST)
 * The MyLinkedList class used in Listing 24.5 is a one-way directional linked
 * list that enables one-way traversal of
 * the list. Modify the Node class to add the new data field name previous to
 * refer to the previous node in the list, as follows:
 * 
 * Implement a new class named TwoWayLinkedList that uses a doubly linked list
 * to store elements. Define TwoWayLinkedList to implements MyList. You need to
 * implement all the methods defined in MyLinkedList as well as the methods
 * listIterator() and listIterator(int index). Both return an instance of
 * java.util.ListIterator<E> (see Figure 20.4). The former sets the cursor to
 * the head of the list and the latter to the element at the specified index.
 * 
 * Test your new class using this code from
 * https://liveexample.pearsoncmg.com/test/Exercise24_03.txt.
 */

public class MyLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size = 0; // Number of elements in the list

    /** Create an empty list */
    public MyLinkedList() {
    }

    /** Create a list from an array of objects */
    public MyLinkedList(E[] objects) {
        for (E obj : objects) {
            this.add(obj);
        }
    }

    /** Return the head element in the list */
    public E getFirst() {
        if (size == 0) {
            return null;
        } else {
            return head.element;
        }
    }

    /** Return the last element in the list */
    public E getLast() {
        if (size == 0) {
            return null;
        } else {
            return tail.element;
        }
    }

    public void addFirst(E e) {
        // Create a new node
        Node<E> newNode = new Node<>(e);

        // link the new node with the head
        newNode.next = head;

        // head points to the new node
        head = newNode;

        // Increase list size
        size++;

        // The new node is the only node in list
        if (tail == null) {
            tail = head;
        }
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e); // Create a new node for e

        if (tail == null) {
            head = tail = newNode; // The only node in list
        } else {
            tail.next = newNode; // Link the new node with the last node
            tail = newNode; // tail now points to the last node
        }

        size++; // Increase size
    }

    /**
     * Add a new element at the specified index
     * in this list. The index of the head element is 0
     */
    @Override
    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e); // Insert first
        } else if (index >= size) {
            addLast(e); // Insert last
        } else {
            // Insert in the middle
            Node<E> current = head;

            for (int i = 1; i < index; i++) {
                current = current.next;
            }

            Node<E> temp = current.next;

            current.next = new Node<>(e);

            (current.next).next = temp;

            size++;
        }
    }

    /**
     * Remove the head node and
     * return the object that is contained in the removed node.
     */
    public E removeFirst() {
        if (size == 0) {
            // Nothing to delete
            return null;
        } else {
            // Keep the first node temporarily
            Node<E> temp = head;

            // Move head to point to next node
            head = head.next;

            // Reduce size by 1
            size--;

            // List becomes empty
            if (head == null)
                tail = null;

            // Return the deleted element
            return temp.element;
        }
    }

    /**
     * Remove the last node and
     * return the object that is contained in the removed node.
     */
    public E removeLast() {
        if (size == 0 || size == 1) {
            return removeFirst();
        } else {
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }

            E temp = tail.element;
            tail = current;
            tail.next = null;
            size--;
            return temp;
        }
    }

    /**
     * Remove the element at the specified position in this
     * list. Return the element that was removed from the list.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            // Out of range
            return null;
        } else if (index == 0) {
            // Remove first
            return removeFirst();
        } else if (index == size - 1) {
            // Remove last
            return removeLast();
        } else {
            Node<E> previous = head;

            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }

            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
    }

    /** Override toString() to return elements in the list */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) {
                // Separate two elements with a comma
                result.append(", ");
            } else {
                // Insert the closing ] in the string
                result.append("]");
            }
        }

        return result.toString();
    }

    /** Clear the list */
    @Override
    public void clear() {
        size = 0;
        head = tail = null;
    }

    /** Return true if this list contains the element e */
    @Override
    public boolean contains(Object e) {
        // Left as an exercise
        return true;
    }

    /** Return the element at the specified index */
    @Override
    public E get(int index) {
        // Left as an exercise
        return null;
    }

    /**
     * Return the index of the first matching element in
     * this list. Return −1 if no match.
     */
    @Override
    public int indexOf(Object e) {
        // Left as an exercise
        return 0;
    }

    /**
     * Return the index of the last matching element in
     * this list. Return −1 if no match.
     */
    @Override
    public int lastIndexOf(E e) {
        // Left as an exercise
        return 0;
    }

    /**
     * Replace the element at the specified position
     * in this list with the specified element.
     */
    @Override
    public E set(int index, E e) {
        // Left as an exercise
        return null;
    }

    /** Override iterator() defined in Iterable */
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /** Return the number of elements in this list */
    @Override
    public int size() {
        return size;
    }

    private class LinkedListIterator
            implements java.util.Iterator<E> {
        private Node<E> current = head; // Current index

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            return e;
        }

        @Override
        public void remove() {
            // Left as an exercise
        }
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }
}
