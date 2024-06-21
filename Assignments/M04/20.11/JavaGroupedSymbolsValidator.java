import java.io.*;
import java.util.*;

public class JavaGroupedSymbolsValidator {

    /** Prompt user to input file path into CLI and validate file path */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter file path to java source file to validate symbol groupings: ");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        scanner.close();

        if (file.exists() && file.canRead() && file.getName().endsWith(".java")) {
            String fileToString = fileToString(file);
            boolean isValid = isValidGrouping(fileToString);

            String resultString = isValid ? "Valid" : "Invalid";

            System.out.println(
                    resultString + " Java source code file based on group symbols! (" + file.getName() + ")");
        } else {
            System.out.println("Invalid file path.");
        }
    }

    /** Get string value all lines in a File object */
    public static String fileToString(File file) {
        try {
            Scanner scanner = new Scanner(file);

            StringBuilder stringBuilder = new StringBuilder();

            // Parse the file content
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line).append('\n');
            }

            scanner.close();

            return stringBuilder.toString();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);

            return "";
        }
    }

    /** Indicates if the java source file contains valid symbols. */
    public static boolean isValidGrouping(String fileContentString) {
        Stack<Character> groupCharStack = new Stack<>();
        Stack<Character> stringCharStack = new Stack<>();

        for (int i = 0; i < fileContentString.length(); i++) {
            char c = fileContentString.charAt(i);
            boolean stringStackIsEmpty = stringCharStack.isEmpty();
            boolean stackIsEmpty = groupCharStack.isEmpty();
            boolean validChar = true;

            switch (c) {
                case '"':
                case '\'':
                    if (stringStackIsEmpty) {
                        // ignore escaped quotes
                        if (i == 0 || fileContentString.charAt(i - 1) != '\\') {
                            // add new start of quote
                            stringCharStack.push(c);
                        }
                    } else if (stringCharStack.peek() == c) {
                        // ignore escaped quotes
                        if (i > 0 && fileContentString.charAt(i - 1) == '\\') {
                            break;
                        }

                        // remove quote
                        stringCharStack.pop();
                    }
                    break;
                case '(':
                case '{':
                case '[':
                    // ensure group opening symbol is not in a string/char
                    if (stringStackIsEmpty) {
                        // add it to the stack
                        groupCharStack.push(c);
                    }
                    break;
                case ')':
                    // ensure group closing symbol is not in a string/char
                    if (stringStackIsEmpty) {
                        // make sure last symbol was opening symbol
                        if (stackIsEmpty || groupCharStack.pop() != '(') {
                            validChar = false;
                        }
                    }
                    break;
                case '}':
                    // ensure group closing symbol is not in a string/char
                    if (stringStackIsEmpty) {
                        // make sure last symbol was opening symbol
                        if (stackIsEmpty || groupCharStack.pop() != '{') {
                            validChar = false;
                        }
                    }
                    break;
                case ']':
                    if (stringStackIsEmpty) {
                        // make sure last symbol was opening symbol
                        if (stackIsEmpty || groupCharStack.pop() != '[') {
                            validChar = false;
                        }
                    }
                    break;
                default:
                    break;
            }

            if (!validChar) {
                return false;
            }
        }

        return groupCharStack.isEmpty() && stringCharStack.isEmpty();
    }
}