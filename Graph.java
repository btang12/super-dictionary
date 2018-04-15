import java.util.ArrayList;
import java.util.List;

public class Graph<E> implements GraphADT<E> {
    protected final int defCapacity = 10;
    protected int vertexCount;
    protected boolean[][] adjacencyMatrix;
    protected E[] vertices;

    @SuppressWarnings("unchecked")
    public Graph() {
        vertexCount = 0;
        this.adjacencyMatrix = new boolean[defCapacity][defCapacity];
        this.vertices = (E[]) (new String[defCapacity]);
    }

    /**
     * Expands the array of vertices, as well as the adjacency matrix, two double their current size
     * in order to accommodate future vertex additions
     */
    @SuppressWarnings("unchecked")
    public void expandArrays() {
        E[] expandedVertices = (E[]) (new String[vertices.length * 2]);
        boolean[][] expandedAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                expandedAdjMatrix[i][j] = adjacencyMatrix[i][j];
            }
            expandedVertices[i] = vertices[i];
        }

        vertices = expandedVertices;
        adjacencyMatrix = expandedAdjMatrix;
    }

    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should not already exist in
     * the graph
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        if (vertex == null) {
            return null;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex) {
                return null;
            }
        }
        if (vertexCount == vertices.length)
            expandArrays();

        vertices[vertexCount] = vertex;
        for (int i = 0; i <= vertexCount; i++) {
            adjacencyMatrix[vertexCount][i] = false;
            adjacencyMatrix[i][vertexCount] = false;
        }
        vertexCount++;
        return vertex;
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should exist in the graph
     * 
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be
     *         removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        boolean check = false;
        int target = 0;
        if (vertex == null) {
            return null;
        }
        for (int i = 0; i < vertexCount; i++) {

            if (check == true) {
                vertices[i - 1] = vertices[i];
            }
            if (vertices[i] == vertex) {
                check = true;
                vertices[i] = null;
                target = i;
            }
        }
        if (!check) {
            return null;
        }
        vertexCount--;
        for (int i = target; i < vertexCount; i++) {
            for (int j = 0; j <= vertexCount; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }
        for (int i = target; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjacencyMatrix[j][i] = adjacencyMatrix[j][i + 1];
            }
        }

        return vertex;
    }

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        adjacencyMatrix[index1][index2] = true;
        adjacencyMatrix[index2][index1] = true;
        return true;
    }

    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }            
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        adjacencyMatrix[index1][index2] = false;
        adjacencyMatrix[index2][index1] = false;
        return true;
    }

    /**
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1
     *         and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        return adjacencyMatrix[index1][index2];
    }

    /**
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions: 1. vertex is not null 2. vertex exists
     * 
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        boolean check = false;
        int target = 0;
        if(vertex == null) {
            return null;
        }
        for(int i = 0; i < vertexCount; i++) {
            if(vertices[i] == vertex) {
                check = true;
                target = i;
            }
             
        }
        if(!check) {
            return null;
        }  
        List<E> neighbors = new ArrayList<E>();
        for(int i = 0; i < vertexCount; i++) {
            if(adjacencyMatrix[target][i] == true) {
                neighbors.add(vertices[i]);
                neighbors.add(vertices[target]);
            }
            else if(adjacencyMatrix[i][target] == true) {
                neighbors.add(vertices[i]);
                neighbors.add(vertices[target]);
            }
        }
        while(neighbors.contains(vertex)) {
            neighbors.remove(vertex);
        }
        return neighbors;
        
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        ArrayList<E> vertexList = new ArrayList<E>();
        for(int i = 0; i < vertexCount; i++) {
            vertexList.add(vertices[i]);
        }
        return vertexList;
    }
    
    /**
	 * Gets the list of words that create the shortest path between word1 and word2
	 * 
	 * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between
	 * cat and wheat is the following list of words: [cat, hat, heat, wheat]
	 * 
	 * @param src2
	 *            first word
	 * @param dest
	 *            second word
	 * @return List<String> list of the words
	 */
	public List<String> getShortestPath(E src2, E dest) {

		int src = 0;
		int dist = 0;
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] == src2)
				src = i;
			if (vertices[i] == dest)
				dist = i;

		}
		int[][] m = new int[vertexCount][vertexCount];
		for (int i = 0; i < vertexCount; i++) {

			for (int j = 0; j < vertexCount; j++) {
				if (i == j)
					m[i][j] = 0;
				else if (adjacencyMatrix[i][j] == true) {
					m[i][j] = 1;
				} else
					m[i][j] = Integer.MAX_VALUE;
			}
		}
		ArrayList<String> p = FloydWarshell(m, vertexCount, src, dist);
		for(String a: p) {  //check if it is impossible to get the path from src to dist
			if (a == "unreachable") {
				p.clear();
				p.add("unreachable!");
				break;
			}
		}
		return p;
	}

	/*
	 * The following part is used for finding shortest path by using Floyd's Algorithm
	 */

	/*
	 * Helper method for adding path between every possible pair of vertices into an array list.
	 * path - a two-d array storing the indexes of each vertices in a path.
	 * v - index of starting vertex
	 * u - index of ending vertex
	 * p - an array list storing every vertex in this path
	 */
	private void addPath(int[][] path, int v, int u, ArrayList<String> p) {
		if (path[v][u] == v)
	        return;
	    if(path[v][u] == -1) {
			p.add("unreachable");
			return;
	    }
	    addPath(path, v, path[v][u],p);
	    p.add((String) vertices[path[v][u]]);
	}

	/* Function to add the shortest cost with path
	 * information between all pairs of vertices
	 * path[][] - a two-d array storing the indexes of each vertices in a path.
	 * v - index of starting vertex
	 * u - index of ending vertex
	*/
	private ArrayList<String> addSolution(int[][] path, int src, int dist) {
		ArrayList<String> P = new ArrayList<String>();

		P.add((String) vertices[src]);
		addPath(path,src,dist,P);  //  add vertices recursively
		P.add((String) vertices[dist]);
		return P;
	}

	// Function to run Floyd-Warshell algorithm
	private ArrayList<String> FloydWarshell(int[][] adjMatrix, int N, int src, int dist) {
		// cost[] and parent[] stores shortest-path
		// (shortest-cost/shortest route) information
		int[][] cost = new int[N][N];
		int[][] path = new int[N][N];

		// initialize cost[] and parent[]
		for (int v = 0; v < N; v++) {
			for (int u = 0; u < N; u++) {
				// initally cost would be same as weight
				// of the edge
				cost[v][u] = adjMatrix[v][u];

				if (v == u)
					path[v][u] = 0;
				else if (cost[v][u] != Integer.MAX_VALUE)
					path[v][u] = v;
				else
					path[v][u] = -1;
			}
		}

		// run Floyd-Warshell
		for (int k = 0; k < N; k++) {
			for (int v = 0; v < N; v++) {
				for (int u = 0; u < N; u++) {
					// If vertex k is on the shortest path from v to u,
					// then update the value of cost[v][u], path[v][u]

					if (cost[v][k] != Integer.MAX_VALUE && cost[k][u] != Integer.MAX_VALUE
							&& (cost[v][k] + cost[k][u] < cost[v][u])) {
						cost[v][u] = cost[v][k] + cost[k][u];
						path[v][u] = path[k][u];
					}
				}

				// if diagonal elements become negative, the
				// graph contains a negative weight cycle
				if (cost[v][v] < 0) {
					System.out.println("Negative Weight Cycle Found!!");
					return null;
				}
			}
		}

		// Add the shortest path between all pairs of vertices to a list
	    ArrayList<String> P = addSolution(path, src,dist);
	    return P;
	}

}
