


/**
 * This class provides an abstract data type for an Edge between any two
 * vertices of any type.
 * 
 * @author Abhishek Gupta (axg137230)
 *
 * @param <T>
 *            Type of objects between which there is an edge.
 */
public class Edge<T> implements Comparable<Edge<T>>{
	/**
	 * This holds the source of this edge.
	 */
	private T u;
	
	/**
	 * This holds the destination of this edge.
	 */
	private T v;
	
	/**
	 * This holds the weight of the edge.
	 */
	private double w;
	
	
	private boolean eularVisited;

	/**
	 * This constructor initializes an Edge.
	 * @param from
	 * @param to
	 * @param w
	 */
	public Edge(T from, T to, double w) {
		this.u = from;
		this.v = to;
		this.w = w;
	}

	/**
	 * This method returns the start object of the edge.
	 * @return
	 */
	public T from() {
		return u;
	}

	/**
	 * This method returns the end object of the edge.
	 * @return
	 */
	public T to() {
		return v;
	}

	/**
	 * This method returns the weight of the edge.
	 * @return
	 */
	public double weight() {
		return w;
	}
	 /**
	 * @param w the w to set
	 */
	public void setW(double w) {
		this.w = w;
	}

	/**
     * Compares two edges by weight.
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *    this edge is less than, equal to, or greater than that edge
     */
	@Override
	public int compareTo(Edge<T> that) {
        if      (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else                                    return  0;
    }
    
    /**
     * Returns the endpoint of the edge that is different from the given vertex
     * (unless the edge represents a self-loop in which case it returns the same vertex).
     * @param vertex one endpoint of the edge
     * @return the endpoint of the edge that is different from the given vertex
     *   (unless the edge represents a self-loop in which case it returns the same vertex)
     * @throws java.lang.IllegalArgumentException if the vertex is not one of the endpoints
     *   of the edge
     */
    public T other(T vertex) {
        if      (vertex == u) return v;
        else if (vertex == v) return u;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((u == null) ? 0 : u.hashCode());
		result = prime * result + ((v == null) ? 0 : v.hashCode());
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
		Edge other = (Edge) obj;
		if (u == null) {
			if (other.u != null)
				return false;
		} else if (!u.equals(other.u))
			return false;
		if (v == null) {
			if (other.v != null)
				return false;
		} else if (!v.equals(other.v))
			return false;
		return true;
	}

	/**
	 * @return the eularVisited
	 */
	public boolean isEularVisited() {
		return eularVisited;
	}

	/**
	 * @param eularVisited the eularVisited to set
	 */
	public void setEularVisited(boolean eularVisited) {
		this.eularVisited = eularVisited;
	}

	
	
}
