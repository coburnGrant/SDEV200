import java.util.ListIterator;
import java.util.Scanner;

public class Exercise24_03 {
  public static void main(String[] args) {
    new Exercise24_03();
  }

  public Exercise24_03() {
    TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();

    System.out.print("Enter five numbers: ");

    try (Scanner input = new Scanner(System.in)) {
      double[] v = new double[5];

      for (int i = 0; i < 5; i++) {
        v[i] = input.nextDouble();
      }

      list.add(v[0]);
      list.add(v[1]);
      list.add(v[2]);
      list.add(v[3]);
      list.add(v[4]);
      list.add(0, v[0]);
      list.add(2, 10.55);
      list.remove(3);
    }
    
    ListIterator<Double> iterator1 = list.listIterator();

    System.out.print("The list in forward order: ");

    while (iterator1.hasNext()) {
      System.out.print(iterator1.next() + " ");
    }

    ListIterator<Double> iterator2 = list.listIterator(list.size() - 1);

    System.out.print("\nThe list in backward order: ");

    while (iterator2.hasPrevious()) {
      System.out.print(iterator2.previous() + " ");
    }
  }
}