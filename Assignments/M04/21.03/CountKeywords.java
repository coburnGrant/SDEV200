import java.io.*;
import java.util.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter a Java source file: ");
        
        String filename = input.nextLine();
        
        File file = new File(filename);

        if (file.exists()) {
            System.out.println("The number of keywords in " + file.getName()
                    + " is " + countKeywords(file));
        } else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = { "abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null" };
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        
        int count = 0;

        Scanner input = new Scanner(file);

        boolean isMultilineComment = false;

        while (input.hasNext()) {
            String line = input.nextLine();

            // remove the leading whitespace from the line
            String noWhitespace =line.replaceAll(" ", "");

            if(noWhitespace.startsWith("//")) {
                // single line comment
                continue;
            } else if(noWhitespace.startsWith("/*")) {
                /* Beginning of multiline comment
                 * 
                 */
                isMultilineComment = true;
                continue;
            } else if(noWhitespace.startsWith("*") && isMultilineComment) {
                /*
                 * Within multiline comment 
                 */
                continue;
            } else {
                // no long a multiline comment
                isMultilineComment = false;
            }

            Scanner lineScanner = new Scanner(line);

            while(lineScanner.hasNext()) {
                String word = lineScanner.next();
                
                // Ignores words within Strings because they will contain ""
                if (keywordSet.contains(word)) {
                    count++;
                }
            }

            lineScanner.close();
        }

        input.close();

        return count;
    }
}
