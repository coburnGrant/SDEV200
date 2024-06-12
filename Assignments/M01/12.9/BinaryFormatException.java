public class BinaryFormatException extends Exception {
    /** The string that is not in a valid binary format */
    private final String invalidString;

    public BinaryFormatException(String invalidString) {
        this.invalidString = invalidString;
    }

    @Override
    public String toString() {
        return "Binary Format Exception! `" + invalidString + "` is not a valid binary string.";
    }
}
