//builds the vocabulary and data(files) matrix, prepares searchable data
//math part of the project

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

    //private list of String (documents),
    // Map of String and Integer (vocabulary), is in a linkedhashmap data structure
    //2 dimensional array (document matrix)(Matrices)
    //TextProcessor class for running text manipulating functions
    private List<String> documents;
    private Map<String, Integer> vocabulary;
    private double[][] documentMatrix;
    private TextProcessor textProcessor;

    //constructor
    public SearchIndex() {
        documents = new ArrayList<>();
        vocabulary = new LinkedHashMap<>();
        documentMatrix = new double[0][0];
        textProcessor = new TextProcessor();
    }

    // Prepare all the documents for searching, input list of string (inputDocuments)
    public void buildIndex(List<String> inputDocuments) {

        // Store a copy of the documents, temporary Arraylist
        documents = new ArrayList<>();

        //adds all the inputed documents to the ArrayList
        for (int i = 0; i < inputDocuments.size(); i++) {
            documents.add(inputDocuments.get(i));
        }

        // Each document gets its own list of words
        // Outer List holds a list of documents and then each document has it's own list
        List<List<String>> allWords = new ArrayList<>();

        //runs through however many documents there are in the database
        //a list of words holds tokenized elements
        for (int i = 0; i < documents.size(); i++) {
            String text = documents.get(i);
            List<String> words = textProcessor.tokenize(text);

            //tokenized list of words go into the list of list of strings
            allWords.add(words);
        }

        //the linkedhashmap holds the list of list of string through the buildVocabulary func
        vocabulary = buildVocabulary(allWords);
        //the matrix holds the list of list of string that goes through the buildDocumentMatrix func
        documentMatrix = buildDocumentMatrix(allWords);
    }

    // Give each unique word a number, returns a Map of string and integer; input takes the list of list of strings
    public Map<String, Integer> buildVocabulary(List<List<String>> allWords) {

        //temporary linkedhashmap of string and integers
        Map<String, Integer> wordPositions = new LinkedHashMap<>();
        //temp variable for holding position of text
        int nextPosition = 0;

        //goes through the whole list of list of string
        for (int i = 0; i < allWords.size(); i++) {
            //creates a temporary list of words and then stores each list from the allwords list
            List<String> words = allWords.get(i);

            //goes through the list of words and then stores the words in word variable
            for (int j = 0; j < words.size(); j++) {
                String word = words.get(j);

                // Only add a word if we have not seen it before
                //if the word is seen then it doesn't add the word in wordPositions and then keeps incrementing
                if (!wordPositions.containsKey(word)) {
                    wordPositions.put(word, nextPosition);
                    nextPosition++;
                }
            }
        }

        //and then returns that variable that is positions of words
        return wordPositions;
    }

    // Count how many times each vocabulary word appears
    // vectorize returns an array of double, input takes a list of strings
    public double[] vectorize(List<String> words) {

        //temporary array of doubles is of size vocabulary size
        double[] counts = new double[vocabulary.size()];

        //loops through the words list and then adds it to the variable word
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);

            //if the word is found in the vocabulary list then adds the word in the position
            if (vocabulary.containsKey(word)) {
                int position = vocabulary.get(word);
                //and then keeps incrementing the position in the list
                counts[position] = counts[position] + 1;
            }
        }

        //returns the array of the variable whose position is stored, counts is a array of doubles
        return counts;
    }

    // Rows represent words; columns represent documents, returns a matrice of double, this builds the documents matrix
    // rows are words and columns are each document
    // inputs a list of list of strings, so a list of each document
    public double[][] buildDocumentMatrix(
            List<List<String>> allWords) {

        //temporary variables of size of the vocabulary list and total matrix list
        int rows = vocabulary.size();
        int columns = allWords.size();

        //creates a temporary matrix of double variables
        double[][] matrix = new double[rows][columns];

        //goes through each of the coulums and gets the words in each of the columns and stores it in the counts variable
        for (int column = 0; column < columns; column++) {
            List<String> words = allWords.get(column);
            //creates vectors of each words and stores them in the array of doubles
            double[] counts = vectorize(words);

            //goes through the rows, basically each word and stores it in the matrix
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