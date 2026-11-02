
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

        // Create some sample documents
        List<String> documents = createSampleDocuments();

        // Prepare the vocabulary and document matrix
        SearchIndex index = new SearchIndex();
        index.buildIndex(documents);

        // Create the objects needed to search
        TextProcessor processor = new TextProcessor();
        SearchEngine engine = new SearchEngine(index, processor);

        // Read what the user types in the console
        Scanner scanner = new Scanner(System.in);

        System.out.println("Document Search Engine");
        System.out.println("Documents loaded: " + index.getDocumentCount());
        System.out.println("Type a search query, or type exit to quit.");

        boolean running = true;

        while (running) {

            System.out.print("\nSearch: ");

            // Stop if the console input has ended
            if (!scanner.hasNextLine()) {
                break;
            }

            String query = scanner.nextLine().trim();

            if (query.equalsIgnoreCase("exit")) {
                running = false;

            } else if (query.isEmpty()) {
                System.out.println("Please enter a search term.");

            } else {
                // Return up to three matching documents
                List<SearchResult> results = engine.search(query, 3);

                displayResults(results, index);
            }
        }

        scanner.close();
        System.out.println("Search engine closed.");
    }

    // Supply sample text before we add file loading
    public static List<String> createSampleDocuments() {

        List<String> documents = new ArrayList<>();

        documents.add("Linear algebra uses matrices and vectors.");
        documents.add("Graphs contain vertices and edges.");
        documents.add("A search engine uses vectors to rank documents.");
        documents.add("Java is a programming language.");
        documents.add("Cosine similarity compares vectors.");

        return documents;
    }

    // Show the matching documents in their ranked order
    public static void displayResults(
            List<SearchResult> results, SearchIndex index) {

        if (results.isEmpty()) {
            System.out.println("No matches found.");
            return;
        }

        System.out.println("\nMatching documents:");

        for (int i = 0; i < results.size(); i++) {

            // Get one search result
            SearchResult result = results.get(i);

            // Use its ID to retrieve the original text
            int documentId = result.getDocumentId();
            String text = index.getDocumentText(documentId);
            double score = result.getScore();

            // Display ranks starting at 1
            System.out.println((i + 1) + ". " + text);
            System.out.println("   Similarity score: " + score);
        }
    }
}