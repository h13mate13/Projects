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

    // Fields
    private List<String> documents;
    private Map<String, Integer> vocabulary;
    private double[][] documentMatrix;
    private TextProcessor textProcessor;

    // Constructor
    public SearchIndex() {
        // Initialize the fields
    }

    // Coordinates the complete indexing process
    public void buildIndex(List<String> documents) {
        // Store documents, tokenize them,
        // build vocabulary, and build matrix
    }

    // Assigns an index to every unique word
    public Map<String, Integer> buildVocabulary(
            List<List<String>> tokenizedDocuments) {
        // Build and return vocabulary
    }

    // Turns tokens into a word-count vector
    public double[] vectorize(List<String> tokens) {
        // Build and return vector
    }

    // Places document vectors into matrix columns
    public double[][] buildDocumentMatrix(
            List<List<String>> tokenizedDocuments) {
        // Build and return matrix
    }

    // Retrieves one matrix column
    public double[] getDocumentVector(int documentId) {
        // Extract and return document vector
    }

    // Retrieves the original document
    public String getDocumentText(int documentId) {
        checkDocumentId(documentId);
        return documents.get(documentId);
    }

    // Reports the number of indexed documents
    public int getDocumentCount() {
        return documents.size();
    }

    // Checks whether the document ID exists
    private void checkDocumentId(int documentId) {
        if (documentId < 0 || documentId >= documents.size()) {
            throw new IndexOutOfBoundsException(
                    "Invalid document ID: " + documentId
            );
        }
    }
}