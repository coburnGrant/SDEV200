package stringsCollections;

import java.util.ArrayList;

public class StoreStrings {

    public static int count(ArrayList<String> words) {
        return words.size();
    }

    public static boolean duplicateString(ArrayList<String> words) {
        ArrayList<String> items = new ArrayList<>();

        for (String word : words) {
            if (items.contains(word)) {
                return true;
            } else {
                items.add(word);
            }
        }

        return false;
    }
}
