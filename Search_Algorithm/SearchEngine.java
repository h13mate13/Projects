//scores and ranks the documents, is actually doing the predicting part

//Methods:
//search(String query, int limit)	Tokenizes and vectorizes the query, scores every document, sorts results, and returns up to limit matches.	List<SearchResult>
//dotProduct(double[] a, double[] b)	Multiplies corresponding entries and adds them.	double
//magnitude(double[] vector)	Calculates the length of a vector.	double
//cosineSimilarity(double[] a, double[] b)	Uses dotProduct() and magnitude() to calculate similarity. Returns 0 if either vector has zero length.	double

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchEngine {

    private SearchIndex searchIndex;
    private TextProcessor textProcessor;

    //constructor
    public SearchEngine(
            SearchIndex searchIndex,
            TextProcessor textProcessor) {

        this.searchIndex = searchIndex;
        this.textProcessor = textProcessor;
    }


    public List<SearchResult> search(String query, int limit) {

        List<SearchResult> results = new ArrayList<>();

        // if there is a limit of zero or less means it returns no results
        if (limit <= 0) {
            return results;
        }

        // Prepare the query(list) using textProcessor and tokenizing the query input variable
        List<String> queryTokens =
                textProcessor.tokenize(query);

        // Convert the query into a word-count vector
        double[] queryVector =
                searchIndex.vectorize(queryTokens);

        // Compare the query(list) with every document
        for (int documentId = 0;
             documentId < searchIndex.getDocumentCount();
             documentId++) {

            double[] documentVector =
                    searchIndex.getDocumentVector(documentId);

            double score =
                    cosineSimilarity(queryVector, documentVector);

            // Keep only documents with some similarity
            if (score > 0) {
                SearchResult result =
                        new SearchResult(documentId, score);

                results.add(result);
            }
        }

        // Sort from highest score to lowest score
        //comparator uses the comparing double function to sort through each SearchResult
        //and compares it by the number returned by it's getScore()
        //.reversed displays the highest score first and then the smallest in the last
        results.sort(
                Comparator.comparingDouble(SearchResult::getScore)
                        .reversed()
        );

        // Return every result if there are fewer results than the limit
        int resultCount = Math.min(limit, results.size());

        return new ArrayList<>(
                results.subList(0, resultCount)
        );
    }

    public double dotProduct(double[] a, double[] b) {

        if (a.length != b.length) {
            throw new IllegalArgumentException(
                    "Vectors must have the same length."
            );
        }

        double total = 0;

        for (int i = 0; i < a.length; i++) {
            total += a[i] * b[i];
        }

        return total;
    }

    public double magnitude(double[] vector) {

        double sumOfSquares = 0;

        for (double value : vector) {
            sumOfSquares += value * value;
        }

        return Math.sqrt(sumOfSquares);
    }

    public double cosineSimilarity(double[] a, double[] b) {

        double magnitudeA = magnitude(a);
        double magnitudeB = magnitude(b);

        // Prevent division by zero
        if (magnitudeA == 0 || magnitudeB == 0) {
            return 0;
        }

        return dotProduct(a, b) / (magnitudeA * magnitudeB);
    }
}