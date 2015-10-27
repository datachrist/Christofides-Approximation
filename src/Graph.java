//Shabad Sehgal 




/**
 * This class provides an abstract data type for a Graph.
 * 
 * @author Abhishek Gupta (axg137230)
 *
 * @param <T>
 *            Type of data that vertices in the graph will be holding.
 */
public class Graph<T> {
	private final int INFINITY = Integer.MAX_VALUE;
	/**
	 * This holds the number of vertex in the graph.
	 */
	private final int V;
	/**
	 * This holds the number of edges in the graph.
	 */
	private int E;
	/**
	 * This holds array of Vertices of type T.
	 */
	private Vertex<T>[] vertices;

	private double[][] adjMatrix;

	/**
	 * This initializes the graph with V vertices and values.
	 * 
	 * @param V
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V, Integer[] values) {
		this.V = V;
		// Array is 1 based indexing.
		this.vertices = new Vertex[V + 1];
		this.adjMatrix = new double[V + 1][V + 1];
		for (int i = 1, j = 0; i < V + 1; i++, j++) {
			this.vertices[i] = new Vertex<T>(values[j]);
			for (int k = 1; k < V + 1; k++) {
				this.adjMatrix[i][k] = INFINITY;
			}
		}
	}

	/**
	 * This method adds an edge between two vertices in a graph.
	 * 
	 * @param u
	 * @param v
	 * @param w
	 */
	public void addEdge(int u, int v, double w) {
//		if (u <= 0 || u > V)
//			throw new IndexOutOfBoundsException("Illeagal vertex numeber.");
//		if (v <= 0 || v > V)
//			throw new IndexOutOfBoundsException("Illeagal vertex numeber.");
		Vertex<T> from = this.vertices[u];
		Vertex<T> to = this.vertices[v];
		//unweighted graph adding edge in both vertices adjacency
		from.adjacencyList().add(new Edge<Vertex<T>>(from, to, w));
		to.adjacencyList().add(new Edge<Vertex<T>>(to, from, w));
		adjMatrix[u][v] = w;
		adjMatrix[v][u] = w;
		E++;
	}
	
	/**
	 * this method is used to add new vertex to graph
	 * 
	 * */
	public Vertex<T> addVertex(Vertex<T> newVertex,int index){
		//this.V = this.V+1;		
		this.vertices[index]= newVertex;
		return newVertex;
	}

	/**
	 * this method is used to remove given vertex from graph
	 * 
	 * */
	public int removeVertex(Vertex<Integer> remVertex) {
		int i=1;
		for(;i<this.vertices.length;i++){
			Vertex<T> vertex=this.vertices[i];
			if(vertex!=null && remVertex.getValue()==vertex.getValue()){
				this.vertices[i]=null;
				break;
			}
		}
	return i;
	}

	/**
	 * 
	 * this method is used to remove given edge from graph both outgoing and incoming edge 
	 * 
	 * */
	public int removeEdge(Edge<Vertex<T>> removingEdge) {
		int f =0;
		for(int i=1;i<this.vertices.length;i++){
			Vertex<T> u = this.vertices[i];
			if(u!=null&&u.getValue()==removingEdge.to().getValue()){
				f= u.adjacencyList().remove(new Edge<Vertex<T>>(removingEdge.to(), removingEdge.from(), removingEdge.weight()));
			}
		}
		return f;
	}
	/**
	 * This method returns the number of vertices in the graph.
	 * 
	 * @return
	 */
	public int noOfVertices() {
		return V;
	}

	/**
	 * This method returns the array of vertices.
	 * 
	 * @return
	 */
	public Vertex<T>[] vertices() {
		return this.vertices;
	}

	/**
	 * This method returns the number of edges in the graph.
	 * 
	 * @return
	 */
	public int noOfEdges() {
		return E;
	}

	

	/**
	 * This method returns if an edge is present between two vertices or not.
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean edgePresent(int u, int v) {
		Vertex<T> from = this.vertices[u];
		Vertex<T> to = this.vertices[v];
		for (Edge<Vertex<T>> edge : from.adjacencyList()) {
			if (to.equals(edge.to()))
				return true;
		}
		return false;
		
		
	}
	
	public Iterable<Edge<Vertex<T>>> edges() {
        Bag<Edge<Vertex<T>>> list = new Bag<Edge<Vertex<T>>>();
        
        for (int v = 1; v < this.vertices.length; v++) {
        	Vertex< T> ver=this.vertices[v];
            int selfLoops = 0;
            if(ver!=null)
            for (Edge<Vertex<T>> e : ver.adjacencyList()) {
                if ((e.other(ver)).getValue()>ver.getValue() ) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if ((e.other(ver)).getValue()==ver.getValue() ) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }
    
}
