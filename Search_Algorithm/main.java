
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