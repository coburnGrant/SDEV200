import java.util.Iterator;
import java.util.ListIterator;

class TwoWayLinkedList<E> implements MyList<E> {
  private Node<E> head, tail;
  private int size;

  /** Create a default list */
  public TwoWayLinkedList() {
  }

  /** Create a list from an array of objects */
  public TwoWayLinkedList(E[] objects) {
    for (E e : objects) {
      add(e);
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

  @Override
  public String toString() {
    if(head == null && tail == null) {
      return "[]";
    }

    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", "); // Separate two elements with a comma
      } else {
        result.append("]"); // Insert the closing ] in the string
      }
    }

    return result.toString();
  }

  /** Clear the list */
  @Override
  public void clear() {
    head = tail = null;
    size = 0;
  }

  /** Return true if this list contains the element o */
  @Override
  public boolean contains(Object e) {
    Node<E> current = head;

    for (int i = 0; i < size; i++) {
      if (current.element.equals(e)) {
        return true;
      }
      current = current.next;
    }

    return false;
  }

  /** Return the element from this list at the specified index */
  @Override
  public E get(int index) {
    if (index > (size - 1)) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    } else if (index < (size / 2)) {
      // start from head and go forwards
      Node<E> current = head;

      for (int i = 0; i < index; i++) {
        current = current.next;
      }

      return current.element;
    } else {
      // start from tail and go backward
      Node<E> current = tail;

      for (int i = size; i > index; i--) {
        current = current.previous;
      }

      return current.element;
    }
  }

  /**
   * Return the index of the head matching element in this list. Return -1 if
   * no match.
   */
  @Override
  public int indexOf(Object e) {
    Node<E> current = head;

    for (int i = 0; i < size; i++) {
      if (current.element.equals(e)) {
        return i;
      }
      current = current.next;
    }

    return -1;
  }

  /**
   * Return the index of the last matching element in this list Return -1 if
   * no match.
   */
  @Override
  public int lastIndexOf(Object e) {
    Node<E> current = tail;

    for (int i = (size -1); i > 0; i--) {
      if (current.element.equals(e)) {
        return i;
      }
      current = current.previous;
    }

    return -1;
  }

  /**
   * Replace the element at the specified position in this list with the
   * specified element.
   */
  @Override
  public E set(int index, E e) {
    if (index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    } else if (index < (size / 2)) {
      // start from head and go forwards
      Node<E> current = head;

      for (int i = 0; i < index; i++) {
        current = current.next;
      }

      E elementToReplace = current.element;

      current.element = e;

      return elementToReplace;
    } else {
      // start from tail and go backward
      Node<E> current = tail;

      for (int i = (size - 1); i > index; i--) {
        current = current.previous;
      }

      E elementToReplace = current.element;
      current.element = e;

      return elementToReplace;
    }
  }

  private class LinkedListIterator implements ListIterator<E> {
    // Current index
    private Node<E> current = head;

    public LinkedListIterator() {
    }

    public LinkedListIterator(int index) {
      if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      }
      for (int nextIndex = 0; nextIndex < index; nextIndex++) {
        current = current.next;
      }
    }

    public void setLast() {
      current = tail;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public E next() {
      E e = current.element;
      current = current.next;
      return e;
    }

    @Override
    public void remove() {
      System.out.println("Implementation left as an exercise");
    }

    @Override
    public void add(E e) {
      System.out.println("Implementation left as an exercise");
    }

    @Override
    public boolean hasPrevious() {
      return current != null;
    }

    @Override
    public int nextIndex() {
      System.out.println("Implementation left as an exercise");
      return 0;
    }

    @Override
    public E previous() {
      E e = current.element;
      current = current.previous;
      return e;
    }

    @Override
    public int previousIndex() {
      System.out.println("Implementation left as an exercise");
      return 0;
    }

    @Override
    public void set(E e) {
      current.element = e;
    }
  }

  private class Node<E> {
    E element;
    Node<E> next;
    Node<E> previous;

    public Node(E o) {
      element = o;
    }
  }

  @Override
  public int size() {
    return size;
  }

  public ListIterator<E> listIterator() {
    return new LinkedListIterator();
  }

  public ListIterator<E> listIterator(int index) {
    return new LinkedListIterator(index);
  }

  @Override
  public Iterator<E> iterator() {
    return null;
  }

  @Override
  public void add(int index, E e) {
    // System.out.println("adding node: " + e + " at index " + index);
    int lastIndex = size - 1;
    int middleIndex = size / 2;

    // System.out.println("size: " + size);
    // System.out.println("last index: " + lastIndex);
    // System.out.println("middle index: " + middleIndex);

    if (index == 0) {
      // Insert first
      addFirst(e);
    } else if (index >= lastIndex) {
      // Insert last
      addLast(e);
    } else if (index < middleIndex) {
      // // start from head and go forwards
      Node<E> current = head;

      for (int i = 0; i < index; i++) {
        current = current.next;
      }

      Node<E> newNode = new Node<>(e);
      newNode.previous = current.previous;
      newNode.next = current;

      if (current.previous != null) {
        current.previous.next = newNode;
      }

      current.previous = newNode;
      // increase the size of the list
      size++;

    } else {
      // start from tail and go backward
      Node<E> current = tail;
      for (int i = (size - 1); i > index; i--) {
        current = current.previous;
      }

      // Replace current with new node
      Node<E> newNode = new Node<>(e);

      newNode.previous = current.previous;

      newNode.next = current;

      if (current.previous != null) {
        current.previous.next = newNode;
      }

      current.previous = newNode;

      // increase the size of the list
      size++;
    }
  }

  public void addFirst(E e) {
    // Create a new node for e
    Node<E> newNode = new Node<>(e);

    if (head == null) {
      // If the list is empty, both head and tail point to the new node
      head = tail = newNode;
    } else {
      // Link the new node to the current head
      newNode.next = head;
      head.previous = newNode;
      head = newNode;
    }

    // Increase the list size
    size++;
  }

  public void addLast(E e) {
    // Create a new node for e
    Node<E> newNode = new Node<>(e);

    if (tail == null) {
      // The only node in list
      tail = head = newNode;
    } else {
      // Link the new node with the last node
      tail.next = newNode;

      // Link the tail to the new node's previous node
      newNode.previous = tail;
      newNode.next = null;

      // tail now points to the last node
      tail = newNode;
    }

    // Increase size
    size++;
  }

  /**
   * Remove the head node and return the object that is contained in the
   * removed node.
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

      if (head == null) {
        // No elements in the list
        tail = null;
      } else {
        // The new head has no previous element
        head.previous = null;
      }

      // Reduce size by 1
      size--;

      // Return the deleted element
      return temp.element;
    }
  }

  /**
   * Remove the last node and return the object that is contained in the
   * removed node.
   */
  public E removeLast() {
    if (size == 0) {
      return null;
    } else if (size == 1) {
      return removeFirst();
    } else {
      // Keep the tails element
      E tempTail = tail.element;

      // New tail is previous element
      tail = tail.previous;

      // Set new tail's next node to null
      tail.next = null;

      // Reduce the size by 1
      size--;

      return tempTail;
    }
  }

  /**
   * Remove the element at the specified position in this list. Return the
   * element that was removed from the list.
   */
  @Override
  public E remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } else if (index < (size / 2)) {
      Node<E> current = head;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }
      current.previous.next = current.next;
      current.next.previous = current.previous;

      size--;

      return current.element;
    } else {
      Node<E> current = tail;
      for (int i = size - 1; i > index; i--) {
        current = current.previous;
      }
      current.previous.next = current.next;

      current.next.previous = current.previous;

      size--;

      return current.element;
    }
  }
}
