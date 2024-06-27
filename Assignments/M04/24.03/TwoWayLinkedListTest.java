import java.util.ListIterator;

public class TwoWayLinkedListTest {
    public static void main(String[] args) {
        TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();

        // Initial population of the list
        for (int i = 0; i < 10; i++) {
            list.add(i, (double) i);
        }

        System.out.println("Initial list: " + list.toString());

        // Testing addFirst and addLast methods
        System.out.println("Testing addFirst and addLast methods...");
        list.addFirst(-1.0);
        list.addLast(10.0);
        if (list.getFirst() == -1.0 && list.getLast() == 10.0) {
            System.out.println("correct addFirst and addLast!");
        } else {
            System.out.println("incorrect addFirst and addLast!");
            return;
        }

        System.out.println("List after addFirst and addLast: " + list.toString());

        // Testing removeFirst and removeLast methods
        System.out.println("Testing removeFirst and removeLast methods...");
        list.removeFirst();
        list.removeLast();

        if (list.getFirst() == 0.0 && list.getLast() == 9.0) {
            System.out.println("correct removeFirst and removeLast!");
        } else {
            System.out.println("incorrect removeFirst and removeLast!");
            return;
        }

        System.out.println("List after removeFirst and removeLast: " + list.toString());

        // Testing contains method
        System.out.println("Testing contains method...");
        if (list.contains(5.0)) {
            System.out.println("contains method works!");
        } else {
            System.out.println("contains method does not work!");
            return;
        }

        // Testing indexOf method
        System.out.println("Testing indexOf method...");
        if (list.indexOf(5.0) == 5) {
            System.out.println("indexOf method works!");
        } else {
            System.out.println("indexOf method does not work!");
            return;
        }

        // Testing lastIndexOf method
        System.out.println("Testing lastIndexOf method...");
        list.add(5, 5.0); // Adding another 5.0 to test lastIndexOf
        if (list.lastIndexOf(5.0) == 6) {
            System.out.println("lastIndexOf method works!");
        } else {
            System.out.println("lastIndexOf method does not work!");
            return;
        }

        // Testing set method
        System.out.println("Testing set method...");
        double result = list.set(5, 50.0);
        if (result == 5.0) {
            System.out.println("set method works!");
        } else {
            System.out.println(list.toString());
            System.out.println("set method does not work!: " + result + ". Expected: " + 5.0);
            return;
        }

        System.out.println("List after set method: " + list.toString());

        // Testing add method at specific index
        System.out.println("Testing add method at specific index...");
        list.add(5, 55.0);
        if (list.get(5) == 55.0) {
            System.out.println("add method at specific index works!");
        } else {
            System.out.println("add method at specific index does not work!");
            return;
        }

        System.out.println("List after adding 55.0 at index 5: " + list.toString());

        // Testing remove method at specific index
        System.out.println("Testing remove method at specific index...");
        double removedElement = list.remove(5);
        if (removedElement == 55.0) {
            System.out.println("remove method at specific index works!");
        } else {
            System.out.println("remove method at specific index does not work!");
            return;
        }

        System.out.println("List after removing element at index 5: " + list.toString());

        // Testing listIterator method
        System.out.println("Testing listIterator method...");
        ListIterator<Double> iterator = list.listIterator();
        System.out.print("List elements in forward direction: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        System.out.print("List elements in backward direction: ");
        ListIterator<Double> iterator2 = list.listIterator(list.size() - 1);
        while (iterator2.hasPrevious()) {
            System.out.print(iterator2.previous() + " ");
        }
        System.out.println();

        // Testing clear method
        System.out.println("Testing clear method...");
        list.clear();
        if (list.isEmpty()) {
            System.out.println("clear method works!");
        } else {
            System.out.println("clear method does not work!");
            return;
        }

        System.out.println("List after clear: " + list.toString());

        System.out.println("All tests passed!"); 
    }
}