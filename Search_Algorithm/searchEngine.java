//scores and ranks the documents, is actually doing the predicting part

//Methods:
//search(String query, int limit)	Tokenizes and vectorizes the query, scores every document, sorts results, and returns up to limit matches.	List<SearchResult>
//dotProduct(double[] a, double[] b)	Multiplies corresponding entries and adds them.	double
//magnitude(double[] vector)	Calculates the length of a vector.	double
//cosineSimilarity(double[] a, double[] b)	Uses dotProduct() and magnitude() to calculate similarity. Returns 0 if either vector has zero length.	double
