//converts text into words, prepares the words for processing, when the words are processed they can be used by other functions


//methods:
//tokenize(String text)     Converts the text to lowercase, removes punctuation and whitespaces, splits into words.
// Return List(String)

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextProcessor {

    public List<String> tokenize(String text) {

        // Return an empty list if the text is null or blank
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }

        // Convert all letters to lowercase
        String cleanedText = text.toLowerCase();

        // Replace punctuation with spaces
        cleanedText = cleanedText.replaceAll("[^a-z0-9\\s]", " ");

        // Remove spaces from the beginning and end
        cleanedText = cleanedText.trim();

        // Check whether any usable text remains
        if (cleanedText.isEmpty()) {
            return new ArrayList<>();
        }

        // Split the cleaned text into separate words
        String[] words = cleanedText.split("\\s+");

        // Convert the array of words into a list
        return new ArrayList<>(Arrays.asList(words));
    }
}