
import java.util.Scanner;

public class BinaryToDecimal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a binary string: ");
        String binaryString = input.next();

        try {
            int decimal = bin2Dec(binaryString);
            System.out.println(binaryString + " to decimal -> " + decimal);
        } catch (BinaryFormatException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Attempts to convert a binary number string into a integer. 
     * Throws a BinaryFormatException if fails to do so. 
     */
    public static int bin2Dec(String binaryString) throws BinaryFormatException {
        try {
            int decimal = Integer.parseInt(binaryString, 2);

            return decimal;
        } catch (NumberFormatException e) {
            throw new BinaryFormatException(binaryString); 
        }
    }
}