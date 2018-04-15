import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphProcessor {
	
	 /**
     * Graph which stores the dictionary words and their associated connections
     */
    private Graph<String> graph;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
	public GraphProcessor() 
	{
		this.graph = new Graph<>();
	}
	        
	 /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     *
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if encountering other exceptions
     */
	public Integer populateGraph(String filepath)
	{
		
		graph = new Graph<String>();
		List<String> words;
		try {
			words = WordProcessor.getWordStream(filepath).collect(Collectors.toList());
		} catch(IOException e) {
			System.out.println(e);
			return -1; 
		}
		Integer count = 0;
		for(String w : words)
		{
			graph.addVertex(w);
			for(String s : words)
			{
				if(WordProcessor.isAdjacent(w,s))
					graph.addEdge(w,s);
			}
			count++;
		}
		
		shortestPathPrecomputation();
		return count;
	}
	
	 /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     *
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if encountering other exceptions
     */
	public Integer populate(String filepath)
	{
		
		graph = new Graph<String>();
		List<String> words;
		try {
			words = WordProcessor.getWordStream(filepath).collect(Collectors.toList());
		} catch(Exception e) {
			System.out.println(e);
			return -1; 
		}
		Integer count = 0;
		for(String w : words)
		{
			graph.addVertex(w);
			for(String s : words)
			{
				if(WordProcessor.isAdjacent(w,s))
					graph.addEdge(w,s);
			}
			count++;
		}
		
		//shortestPathPrecomputation();
		return count;
	}
    
	
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
	public List<String> getShortestPath(String word1, String word2) {
		return graph.getShortestPath(word1, word2);
	}

	/**
	 * Gets the distance of the shortest path between word1 and word2
	 * 
	 * Example: Given a dictionary, cat rat hat neat wheat kit distance of the
	 * shortest path between cat and wheat, [cat, hat, heat, wheat] = 3 (the number
	 * of edges in the shortest path)
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return Integer distance
	 */
	public Integer getShortestDistance(String word1, String word2) {
		return getShortestPath(word1, word2).size()-1;
	}

	/**
	 * Computes shortest paths and distances between all possible pairs of vertices.
	 * This method is called after every set of updates in the graph to recompute
	 * the path information. Any shortest path algorithm can be used (Djikstra's or
	 * Floyd-Warshall recommended).
	 */
	public void shortestPathPrecomputation() {
		for (String src : graph.getAllVertices()) {
			for (String dest : graph.getAllVertices()) {
				if(src == dest) continue;
				System.out.print("The shortest path from " + src + " to " + dest + "is" + getShortestPath(src,dest).toString());
				System.out.println("; and the length is; " + getShortestDistance(src,dest));			}
		}
	}
}
