//builds the vocabulary and data(files) matrix, prepares searchable data

//methods:
//buildIndex(List(String)[of documents])    builds the vocab, builds the matrix, stores the resulting data.     returns void
//buildVocabulary(List<List<String>> tokenizedDocuments)    gives each unique word a fixed row number.      returns Map<string, integer>
//vectorize(List<String> tokens)	Counts words using the existing vocabulary positions. Ignores unknown words.	double[]
//buildDocumentMatrix(List<List<String>> tokenizedDocuments)	Calls vectorize() for each document and places each vector into a matrix column.	double[][]
//getDocumentVector(int documentId)	Extracts a document’s column from the matrix.	double[]
//getDocumentText(int documentId)	Retrieves the original document text.	String
//getDocumentCount()	Reports how many documents are indexed.	int


