
/////////////////////////////////////
// p4 Dictionary Graph
// Chris Sullivan, Brain Tang, Matthew Thomas, Patrick Stiles, Xiaoyu Sun
// csullivan9@wisc.edu, btang12@wisc.edu, mcthomas4@wisc.edu, pdstiles@wisc.edu, SeanXiaoyuSun@wisc.edu
// 4/16/18
// Outside Sources: none
// Bugs: none
// Professor: Deb Deppeler
//////////////////////////////////////

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Class that constructs a graph with words as vertices and edges corresponding to replacement, deletion, and addition between words
 */
public class GraphProcessor {
	private ArrayList<String>[][] p; //
	/**
	 * Graph which stores the dictionary words and their associated connections
	 */
	private Graph<String> graph;

	/**
	 * Constructor for this class. Initializes instances variables to set the
	 * starting state of the object
	 */
	public GraphProcessor() {
		this.graph = new Graph<String>();
	}

	/**
	 * Builds a graph from the words in a file. Populate an internal graph, by
	 * adding words from the dictionary as vertices and finding and adding the
	 * corresponding connections (edges) between existing words.
	 * 
	 * Reads a word from the file and adds it as a vertex to a graph. Repeat for all
	 * words.
	 * 
	 * For all possible pairs of vertices, finds if the pair of vertices is adjacent
	 * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds
	 * an undirected and unweighted edge between the pair of vertices in the graph.
	 *
	 * Log any issues encountered (print the issue details)
	 * 
	 * @param filepath
	 *            file path to the dictionary
	 * @return Integer the number of vertices (words) added; return -1 if file not
	 *         found or if encountering other exceptions
	 * @throws IOException
	 *             - when file is not found.
	 */
	public Integer populateGraph(String filepath) {
		List<String> words;
		try {
			words = WordProcessor.getWordStream(filepath).collect(Collectors.toList());// Load words from dictionary
																						// file via Collecting a Stream
																						// into List<String>
		} catch (IOException e) {
			System.out.println(e);
			return -1;
		}
		Integer count = 0;
		// add vertices and edges to graph. Adds edges by first testing adjacency.
		for (String w : words) {
			graph.addVertex(w);
			for (String s : words) {
				if (WordProcessor.isAdjacent(w, s))
					graph.addEdge(w, s);
			}
			count++;
		}
		shortestPathPrecomputation(); // pre-compute shortest path
		return count;
	}

	/**
	 * Gets the list of words that create the shortest path between word1 and word2
	 * 
	 * Example: Given a dictionary, cat rat hat heat wheat kit shortest path between
	 * cat and wheat is the following list of words: [cat, hat, heat, wheat]
	 * 
	 * @param word1
	 *            first word
	 * @param word2
	 *            second word
	 * @return List<String> list of the words
	 */
	public List<String> getShortestPath(String word1, String word2) {
		if (word1.toLowerCase().equals(word2))
			return null;
		boolean T1 = false;
		boolean T2 = false;
		int s = 0;
		int d = 0;
		for (int i = 0; i < graph.vertexCount; i++) {
			if (graph.vertices[i].equals(word1.toUpperCase())) {
				T1 = true;
				s = i;
			}
			if (graph.vertices[i].equals(word2.toUpperCase())) {
				T2 = true;
				d = i;
			}
		}
		if (T1 == true && T2 == true) {
			for (String n : p[s][d])
				if (n.equals("unreachable"))
					return null;
			return (List<String>) p[s][d];
		} else
			return null;
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
		if (word1.toLowerCase().equals(word2))
			return -1;
		if (getShortestPath(word1.toUpperCase(), word2.toUpperCase()) == null) {
			return -1;
		} else {
			return getShortestPath(word1.toUpperCase(), word2.toUpperCase()).size() - 1;
		}
	}

	/**
	 * Computes shortest paths and distances between all possible pairs of vertices.
	 * This method is called after every set of updates in the graph to recompute
	 * the path information. Any shortest path algorithm can be used (Djikstra's or
	 * Floyd-Warshall recommended).
	 */
	public void shortestPathPrecomputation() {
		int[][] m = new int[graph.vertexCount][graph.vertexCount];
		for (int i = 0; i < graph.vertexCount; i++) {

			for (int j = 0; j < graph.vertexCount; j++) {
				if (i == j)
					m[i][j] = 0;
				else if (graph.adjacencyMatrix[i][j] == true) {
					m[i][j] = 1;
				} else
					m[i][j] = Integer.MAX_VALUE;
			}
		}
		p = graph.FloydWarshell(m, graph.vertexCount);
	}
}
