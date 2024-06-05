
import java.util.Scanner;

public class CreditCardValidator {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter a credit card number as a long integer (-1 to exit):");
            Scanner input = new Scanner(System.in);

            long cardNumber = input.nextLong();

            if (cardNumber == -1) {
                break;
            }

            boolean result = isValid(cardNumber);
            String resultString = result ? "valid" : "invalid";

            System.out.println(cardNumber + " is " + resultString);
        }
    }

    /** Return true if the card number is valid */
    public static boolean isValid(long number) {
        // check if number has valid prefix
        if (!containsValidPrefix(number)) {
            return false;
        }

        // add up even numbers
        int sumEven = sumOfDoubleEvenPlace(number);

        // add up odd numbers
        int sumOdd = sumOfOddPlace(number);

        // sum of even and odd numbers
        int sum = sumEven + sumOdd;

        // return if sum is divisible by 10
        return (sum % 10 == 0);
    }

    /** Get the result from Step 2 */
    public static int sumOfDoubleEvenPlace(long number) {
        int numberSize = getSize(number);

        String numberString = Long.toString(number);

        int sum = 0;
        for (int i = (numberSize - 2); i >= 0; i -= 2) {
            String numSubString = numberString.substring(i, i + 1);
            int num = Integer.parseInt(numSubString);
            int doubledNum = num * 2;
            sum += getDigit(doubledNum);
        }

        return sum;
    }

    /**
     * Return this number if it is a single digit, otherwise,
     * return the sum of the two digits
     */
    public static int getDigit(int number) {
        return number < 10 ? number : number / 10 + number % 10;
    }

    /** Return sum of odd-place digits in number */
    public static int sumOfOddPlace(long number) {
        int numberSize = getSize(number);

        String numberString = Long.toString(number);

        int sum = 0;

        for (int i = (numberSize - 1); i >= 0; i -= 2) {
            String numSubString = numberString.substring(i, i + 1);
            int num = Integer.parseInt(numSubString);
            sum += getDigit(num);
        }

        return sum;
    }

    /** Return true if the number d is a prefix for number */
    public static boolean prefixMatched(long number, int d) {
        long prefix = getPrefix(number, getSize(d));
        return (prefix == d);
    }

    /** Return the number of digits in d */
    public static int getSize(long d) {
        return Long.toString(d).length();
    }

    /**
     * Return the first k number of digits from number. If the
     * number of digits in number is less than k, return number.
     */
    public static long getPrefix(long number, int k) {
        String numberString = Long.toString(number);
        String subString = numberString.substring(0, k);
        return Long.parseLong(subString);
    }

    /**
     * Returns a boolean indicating whether or not the number contains a
     * valid credit card prefix
     */
    public static boolean containsValidPrefix(long number) {
        int[] prefixes = { 4, 5, 37, 6 };

        for (int prefix : prefixes) {
            if (prefixMatched(number, prefix)) {
                // valid prefix
                return true;
            }
        }
        // no valid prefix
        return false;
    }
}
