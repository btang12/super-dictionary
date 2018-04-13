import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    /**
     * Instance variables and constructors
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        
    }

    class Edge {
		public final Vertex target;
		public final double weight;

		public Edge(Vertex target, double weight) {
			this.target = target;
			this.weight = weight;
		}
	}

	class Vertex implements Comparable<Vertex> {
		public final E name;
		public ArrayList<Edge> neighbours;
		public LinkedList<Vertex> path;
		public double minDistance = Double.POSITIVE_INFINITY;
		public Vertex previous;

		public int compareTo(Vertex other) {
			return Double.compare(minDistance, other.minDistance);
		}

		public Vertex(E name) {
			this.name = name;
			neighbours = new ArrayList<Edge>();
			path = new LinkedList<Vertex>();
		}

		public String toString() {
			return (String) name;
		}
	}

	private ArrayList<Vertex> vertices;  //Vertices list
	private Vertex destination;
	
	public ArrayList<E> Dijkstra(E src, E dest) {
		destination = new Vertex(src);
		Vertex source = new Vertex(src);
		source.minDistance = 0;
		ArrayList<Vertex> list = new ArrayList<Vertex>();
		for (E name : getNeighbors(src)) {
			list.add(new Vertex(name)); // keep track of each vertex
			Edge e = new Edge(new Vertex(name), 1);
			source.neighbours.add(e);
		}

		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(source);

		while (!queue.isEmpty()) {

			Vertex v = queue.poll(); // pop

			for (Edge neighbour : v.neighbours) {
				Double newDist = v.minDistance + neighbour.weight;

				if (neighbour.target.minDistance > newDist) {
					// Remove the node from the queue to update the distance value.
					queue.remove(neighbour.target);
					neighbour.target.minDistance = newDist;

					// Take the path visited till now and add the new node.s
					neighbour.target.path = new LinkedList<Vertex>(v.path);
					neighbour.target.path.add(v);

					// Reenter the node with new distance.
					queue.add(neighbour.target);
				}
				if(neighbour.target.name == dest) {
					destination = neighbour.target;
				}
			}
		}
		ArrayList<E> path =new ArrayList<E>();
		ArrayList<E> p =new ArrayList<E>();
		int i = 0;
		while(true) {
			path.add(destination.name); 
			if(destination.previous == null) break;
		}
		int length = path.size();
		for(int k = 0; k < length; k++) {
			p.add(length-k, path.get(k));
		}
		return p;
	}

}
