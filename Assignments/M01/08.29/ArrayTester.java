
import java.util.Scanner;

public class ArrayTester {
    public static void main(String[] args) {
        int[][] list1 = readArray(1);
        int[][] list2 = readArray(2);

        boolean result = equals(list1, list2);
        String resultString = result ? "identical" : "not identical";

        System.out.println("The two arrays are " + resultString);
    }

    public static Scanner input = new Scanner(System.in);

    /**
     * Uses the scanner to read in an array. 
     */
    public static int[][] readArray(int listNum) {
        int[][] list = new int[3][3];

        System.out.print("Enter list " + listNum + ": ");

        for (int row = 0; row < list.length; row++) {
            for (int col = 0; col < list[row].length; col++) {
                list[row][col] = input.nextInt();
            }
        }

        return list;
    }

    /**
     * Returns a boolean indicating if m1 and m2 are identical. 
     */
    public static boolean equals(int[][] m1, int[][] m2) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (m1[row][col] != m2[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }
}
