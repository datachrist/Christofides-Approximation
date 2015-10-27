// Members: Shabad Sehgal 

/**
 * This class provides an abstract data type for a vertex.
 * 
 * @author Abhishek Gupta (axg137230)
 *
 * @param <T>
 *            Type of data that vertex is holding.
 */
public class Vertex<T> {
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + obj;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (this.obj != other.obj)
			return false;
		return true;
	}


	/**
	 * This holds the data that vertex is storing.
	 */
	private int obj;
	
	private boolean visited;
	private Vertex<T> mate;
	private Vertex<T> parent;
	private Character type;
	private double label;
	private Vertex<T> root;
	private boolean real=true;
	/**
	 * @return the root
	 */
	public Vertex<T> getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Vertex<T> root) {
		this.root = root;
	}


	/**
	 * This holds the list of edges that starts from this vertex.
	 */
	private Bag<Edge<Vertex<T>>> adj;

	/**
	 * This constructor initializes the vertex with a value.
	 * 
	 * @param value
	 */
	public Vertex(int value) {
		this.obj = value;
		this.adj = new Bag<Edge<Vertex<T>>>();
	}

	/**
	 * This method returns the value the vertex is holding.
	 * 
	 * @return
	 */
	public int getValue() {
		return obj;
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * @param visited the visited to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return the mate
	 */
	public Vertex<T> getMate() {
		return mate;
	}

	/**
	 * @param mate the mate to set
	 */
	public void setMate(Vertex<T> mate) {
		this.mate = mate;
	}

	/**
	 * @return the parent
	 */
	public Vertex<T> getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Vertex<T> parent) {
		this.parent = parent;
	}

	/**
	 * @return the type
	 */
	public Character getType() {
		return type;
	}

	/**
	 * @return the label
	 */
	public double getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(double label) {
		this.label = label;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Character type) {
		this.type = type;
	}

	/**
	 * This method returns the list of edges which have this vertex has starting
	 * point.
	 * 
	 * @return
	 */
	public Bag<Edge<Vertex<T>>> adjacencyList() {
		return this.adj;
	}

	
	 public int compareTo(Vertex<T> that) {
	        if      (this.getValue() < that.getValue()) return -1;
	        else if (this.getValue() > that.getValue()) return +1;
	        else                                    return  0;
	    }

	/**
	 * @return the real
	 */
	public boolean isReal() {
		return real;
	}

	/**
	 * @param real the real to set
	 */
	public void setReal(boolean real) {
		this.real = real;
	}
	
}
