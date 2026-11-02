
//main function
//sets up the sample data and handles user input



//flowchart:
//A["main(): start application"] --> B["createSampleDocuments(): sample text"]
//    B --> C["buildIndex(): prepare searchable data"]
//    C --> D["tokenize(): words for each document"]
//    D --> E["buildVocabulary(): word → row number"]
//    E --> F["buildDocumentMatrix(): assemble columns"]
//    F --> G["vectorize(): one vector per document"]
//    G --> H["Store vocabulary and document matrix"]


//buildDocumentMatrix()
//A["main(): read query"] --> B["search(): coordinate search"]
//    B --> C["tokenize(): query words"]
//    C --> D["vectorize(): query vector"]
//    D --> E["For each document"]
//    E --> F["getDocumentVector(): document vector"]
//    F --> G["cosineSimilarity(): relevance score"]
//    G --> H["dotProduct(): measure overlap"]
//    G --> I["magnitude(): measure vector lengths"]
//    H --> J["Combine into similarity score"]
//    I --> J
//    J --> K["search(): keep positive scores and sort"]
//    K --> L["Return top SearchResult objects"]
//    L --> M["displayResults(): print text and scores"]

//Build it in this order
//1. createSampleDocuments() and tokenize() — print the words from each document.
//2. buildVocabulary() — print each word and its assigned index.
//3. vectorize() — print document vectors and verify their word counts.
//4. buildDocumentMatrix() and buildIndex() — assemble and store the index.
//5. dotProduct(), magnitude(), and cosineSimilarity() — identical nonzero vectors should score 1; vectors with no shared words should score 0.
//6. search() and SearchResult — score and rank the documents.
//7. Console input and displayResults() — make the application interactive.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Create some sample documents, for the data stored
        List<String> documents = createSampleDocuments();

        // Prepare the vocabulary and document matrix, by creating new searchIndex for
        // searching the already made sample documents to search through
        SearchIndex index = new SearchIndex();

        //uses the sample documents to build an index
        index.buildIndex(documents);

        // Create the objects needed to search, text processor and and
        // a search engine for dynamically ranking the documents based on your searches
        TextProcessor processor = new TextProcessor();
        SearchEngine engine = new SearchEngine(index, processor);

        // Read what the user types in the console, prompt the user for the document
        Scanner scanner = new Scanner(System.in);

        System.out.println("Search Engine");
        System.out.println("Documents loaded: " + index.getDocumentCount());
        System.out.println("Type a search query, or type exit to quit.");

        boolean running = true;

        while (running) {

            System.out.print("\nSearch: ");

            // Stop if the console input has ended, ends if the user enters and creates a new line
            if (!scanner.hasNextLine()) {
                break;
            }

            //trims the whitespaces and bypasses the empty nextLine in the input box
            //using nextLine() creates a nextLine() buffer in the input box, need to use another
            //nextLine() to clear the contents of the input box
            String query = scanner.nextLine().trim();

            //if user enters exit it ends the loop
            if (query.equalsIgnoreCase("exit")) {
                running = false;

                //if empty it prompts the user to enter a searching term and loops again
            } else if (query.isEmpty()) {
                System.out.println("Please enter a search term.");

            } else {
                // if entered a result, the user is given their query using
                //the search func that gives the user their query document
                // Return up to three matching documents
                List<SearchResult> results = engine.search(query, 3);

                //displays the result using the result and the index of the result
                displayResults(results, index);
            }
        }

        scanner.close();
        System.out.println("End of Search.");
    }

    // Supply sample text before we add file loading
    public static List<String> createSampleDocuments() {

        //documents sample data list
        List<String> documents = new ArrayList<>();

        //prompt documents
        documents.add("Linear algebra uses matrices and vectors.");
        documents.add("Graphs contain vertices and edges.");
        documents.add("A search engine uses vectors to rank documents.");
        documents.add("Java is a programming language.");
        documents.add("Cosine similarity compares vectors.");

        return documents;
    }

    // Show the matching documents in their ranked order, the actual math is done by searchengine.java
    // this only displays the results
    public static void displayResults(
            //creates a list of searchresult class to input the results
            // inputs an index of searchIndex class
            List<SearchResult> results, SearchIndex index) {

        //if should only return empty because of what? ********
        if (results.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }


        System.out.println("\nMatching documents:");

        //uses a simple for loop to display the search results this is O(n) which is terrible
        for (int i = 0; i < results.size(); i++) {

            // Get one search result
            //prints out results just by going through the list one at a time
            SearchResult result = results.get(i);

            // uses an id to return the original text of the document needs an ID so this must be private, this is like a key
            // Use its ID to retrieve the original text
            int documentId = result.getDocumentId();

            //what happens when an incorrect id is entered? **********
            String text = index.getDocumentText(documentId);

            //the computing for the score is done in getscore in the search result class
            //should be moved to searchengine class? ************
            double score = result.getScore();

            // Display ranks starting at 1
            System.out.println((i + 1) + ". " + text);
            System.out.println("   Similarity score: " + score);
        }
    }
}