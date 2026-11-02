//builds the vocabulary and data(files) matrix, prepares searchable data

//methods:
//buildIndex(List(String)[of documents])    builds the vocab, builds the matrix, stores the resulting data.     returns void
//buildVocabulary(List<List<String>> tokenizedDocuments)    gives each unique word a fixed row number.      returns Map<string, integer>
//vectorize(List<String> tokens)	Counts words using the existing vocabulary positions. Ignores unknown words.	double[]
//buildDocumentMatrix(List<List<String>> tokenizedDocuments)	Calls vectorize() for each document and places each vector into a matrix column.	double[][]
//getDocumentVector(int documentId)	Extracts a document’s column from the matrix.	double[]
//getDocumentText(int documentId)	Retrieves the original document text.	String
//getDocumentCount()	Reports how many documents are indexed.	int
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SearchIndex {

    private List<String> documents;
    private Map<String, Integer> vocabulary;
    private double[][] documentMatrix;
    private TextProcessor textProcessor;

    public SearchIndex() {
        documents = new ArrayList<>();
        vocabulary = new LinkedHashMap<>();
        documentMatrix = new double[0][0];
        textProcessor = new TextProcessor();
    }

    // Prepare all the documents for searching
    public void buildIndex(List<String> inputDocuments) {

        // Store a copy of the documents
        documents = new ArrayList<>();

        for (int i = 0; i < inputDocuments.size(); i++) {
            documents.add(inputDocuments.get(i));
        }

        // Each document gets its own list of words
        List<List<String>> allWords = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            String text = documents.get(i);
            List<String> words = textProcessor.tokenize(text);

            allWords.add(words);
        }

        vocabulary = buildVocabulary(allWords);
        documentMatrix = buildDocumentMatrix(allWords);
    }

    // Give each unique word a number
    public Map<String, Integer> buildVocabulary(
            List<List<String>> allWords) {

        Map<String, Integer> wordPositions = new LinkedHashMap<>();
        int nextPosition = 0;

        for (int i = 0; i < allWords.size(); i++) {
            List<String> words = allWords.get(i);

            for (int j = 0; j < words.size(); j++) {
                String word = words.get(j);

                // Only add a word if we have not seen it before
                if (!wordPositions.containsKey(word)) {
                    wordPositions.put(word, nextPosition);
                    nextPosition++;
                }
            }
        }

        return wordPositions;
    }

    // Count how many times each vocabulary word appears
    public double[] vectorize(List<String> words) {

        double[] counts = new double[vocabulary.size()];

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            if (vocabulary.containsKey(word)) {
                int position = vocabulary.get(word);
                counts[position] = counts[position] + 1;
            }
        }

        return counts;
    }

    // Rows represent words; columns represent documents
    public double[][] buildDocumentMatrix(
            List<List<String>> allWords) {

        int rows = vocabulary.size();
        int columns = allWords.size();

        double[][] matrix = new double[rows][columns];

        for (int column = 0; column < columns; column++) {
            List<String> words = allWords.get(column);
            double[] counts = vectorize(words);

            for (int row = 0; row < rows; row++) {
                matrix[row][column] = counts[row];
            }
        }

        return matrix;
    }

    // Copy one document's column into an array
    public double[] getDocumentVector(int documentId) {

        checkDocumentId(documentId);

        double[] counts = new double[vocabulary.size()];

        for (int row = 0; row < vocabulary.size(); row++) {
            counts[row] = documentMatrix[row][documentId];
        }

        return counts;
    }

    // Return the original document text
    public String getDocumentText(int documentId) {
        checkDocumentId(documentId);
        return documents.get(documentId);
    }

    // Return the number of documents
    public int getDocumentCount() {
        return documents.size();
    }

    // Check that the requested document exists
    private void checkDocumentId(int documentId) {

        if (documentId < 0 || documentId >= documents.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid document ID: " + documentId
            );
        }
    }
}