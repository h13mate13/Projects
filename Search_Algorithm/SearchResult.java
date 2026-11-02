//stores one matching document and it's score

//initial variables
// int documentId
// double score


//Methods:
//main(String[] args)	Creates the objects, builds the index once, then repeatedly reads queries and displays results until the user exits.	void
//createSampleDocuments()	Returns a few hardcoded document strings for development.	List<String>
//displayResults(List<SearchResult> results, SearchIndex index)	Prints each result’s text and score, or “No matches found.”	void

// Stores the ID and similarity score of one matching document
public class SearchResult {

    private int documentId;
    private double score;

    // Creates a result using a document's ID and calculated score, constructor
    public SearchResult(int documentId, double score) {
        this.documentId = documentId;
        this.score = score;
    }

    // Returns the document's position in SearchIndex
    public int getDocumentId() {
        return documentId;
    }

    // Returns the similarity score used to rank this result in SearchIndex
    public double getScore() {
        return score;
    }
}