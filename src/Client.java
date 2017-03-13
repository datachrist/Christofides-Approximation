
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Client {
	private static double weight; // weight of MST
	private static Queue<Edge> mst = new Queue<Edge>(); // edges in MST
	private static double[][] distTo;
	private static Edge[][] edgeTo;
	private static int msize;
	private static int tVertices;
	private static ArrayList<Vertex<Integer>> ShortestMatchedPath = new ArrayList<Vertex<Integer>>();
	private static Vertex[][] next;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// nt tVertices = 0;
		int tEdges = 0;
		try {
			Scanner console = new Scanner(System.in);
			Scanner lineTokenizer2;
			Graph<Integer> G;
			Scanner lineTokenizer = new Scanner(console.nextLine());
			tVertices = lineTokenizer.nextInt();
			tEdges = lineTokenizer.nextInt();
			Integer[] values = new Integer[tVertices];
			for (int i = 0; i < tVertices; i++) {
				values[i] = i + 1;
			}
			G = new Graph<Integer>(values.length, values);
			lineTokenizer.close();
			while (console.hasNextLine()) {

				lineTokenizer2 = new Scanner(console.nextLine());
				if (lineTokenizer2.hasNextInt()) {
					G.addEdge(lineTokenizer2.nextInt(), lineTokenizer2.nextInt(), lineTokenizer2.nextInt());
				} else {
					break;
				}
				lineTokenizer2.close();
			}
			// Starting timer!!
			long start = System.currentTimeMillis();
			// Queue<Vertex<Integer>> queue = new Queue<Vertex<Integer>>();

			Kruskal(G);
			System.out.println("mst size: " + mst.size());
			
			//mst GRAPH
			Graph<Integer> T = new Graph<Integer>(values.length, values);
			
			for(Edge<Vertex<Integer>> edge: mst){
				T.addEdge(edge.from().getValue(), edge.to().getValue(), edge.weight());
			}
			

			// System.out.println("total weight: "+weight);
			int arr[] = new int[tVertices + 1];
			Arrays.fill(arr, 0);
			for (int j = 1; j < tVertices + 1; j++) {
				Vertex<Integer> u = G.vertices()[j];
				for (Edge<Vertex<Integer>> edge : u.adjacencyList()) {
					Iterator<Edge> i = mst.iterator();
					while (i.hasNext()) {
						Edge<Vertex<Integer>> e = i.next();
						if (e.compareTo(edge) == 0 && (edge.to().equals(e.to())) && e.from().equals(u)) {
							arr[j]++;
							arr[e.to().getValue()]++;
						}
					}
				}
			}
			int count = 0;
			// odd vertex list to be used later
			List<Vertex<Integer>> oddVertexs = new ArrayList<Vertex<Integer>>();
			for (int k = 1; k < tVertices + 1; k++) {
				System.out.println(arr[k]);
				if (!(arr[k] % 2 == 0)) {
					count++;
					oddVertexs.add(G.vertices()[k]);
				}
				// else{
				// System.out.println(k);
				//
				// }
			}
			if (count == tVertices) {
				System.out.println(count + "All nodes have odd degrees");
			}

			// running floyd warshall Algo for calculating all pair shortest
			// path algo

			floydWarshall(G);
			
			
			

			// creating a complete graph K on the nodes of S, with
			// w(u,v)=shortest path weight.

			Graph<Integer> K = new Graph<Integer>(values.length, values);

			for (int i = 0; i < oddVertexs.size(); i++) {
				Vertex<Integer> u = new Vertex<Integer>(oddVertexs.get(i).getValue());
				for (int j = 0; j < oddVertexs.size(); j++) {
					Vertex<Integer> v = new Vertex<Integer>(oddVertexs.get(j).getValue());
					if (u.getValue() < v.getValue())
						K.addEdge(u.getValue(), v.getValue(), (int) distTo[u.getValue()][v.getValue()]);
				}
			}
			System.out.println("Complete graph done");

			//point $ : matching implementation
			
			List<Edge<Vertex<Integer>>> matchedEdges = findMinWeightGraph(K, values);
			
			//testing purposes
//			List<Edge<Vertex<Integer>>> matchedEdges = new ArrayList<Edge<Vertex<Integer>>>();
//			matchedEdges.add(new Edge<Vertex<Integer>>(new Vertex<Integer>(1),new Vertex<Integer>(2), 4));
//			matchedEdges.add(new Edge<Vertex<Integer>>(new Vertex<Integer>(3),new Vertex<Integer>(4), 4));

			System.out.println("Matching Done!");
			
			// point 5 implementation
			//creating multigraph

			
			Graph<Integer> H = new Graph<Integer>(values.length, values);
			
			//adding mst to multigrpah H
			for(Edge<Vertex<Integer>> edge: mst){
				H.addEdge(edge.from().getValue(), edge.to().getValue(), edge.weight());
			}
			
			//adding shortest path of M in H		
				
				for(Edge<Vertex<Integer>> edge : matchedEdges){
					//calculating shortest path for matched edge
					ArrayList<Vertex<Integer>> path = new ArrayList<Vertex<Integer>>(); 
					 path = floydWarshalPath(edge.from(),edge.to());					
					
					ListIterator<Vertex<Integer>> pathiter = path.listIterator();
					Vertex<Integer> u = pathiter.next();
					while(pathiter.hasNext()){
						double weight =0;
						Vertex<Integer> v = pathiter.next();
						
						for(int i=1;i<G.vertices().length;i++){
							if(u.equals(G.vertices()[i])){
								u = G.vertices()[i];
								break;
							}
						}
						
						for(Edge<Vertex<Integer>> existingEdge : u.adjacencyList()){
							if(existingEdge.to().equals(v)){
								weight=existingEdge.weight();
								break;
							}
						}
						H.addEdge(u.getValue(), v.getValue(), weight);
						u=v;
					}
					

				}
			
			
			//point 6 implementation
			
				Vertex<Integer> u = H.vertices()[1];
				//System.out.print(u.getValue()+" ");
		
				Stack<Vertex<Integer>> eularStack= new Stack<Vertex<Integer>>();
			eularStack.push(u);
			eularTour(eularStack);

			while(!eularStack.isEmpty()){
				System.out.print(eularStack.pop().getValue()+" ");
			}
			
			
			

		} catch (Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();

		}

	}

	private static void eularTour(Stack<Vertex<Integer>> eularStack) {
		
			for (Edge<Vertex<Integer>> edge : eularStack.lastElement().adjacencyList()) {
				if (!edge.isEularVisited()) {
					edge.setEularVisited(true);
					for(Edge<Vertex<Integer>> otherEdge : edge.to().adjacencyList()){
						if(otherEdge.to().equals(edge.from())&&!otherEdge.isEularVisited()){
							otherEdge.setEularVisited(true);
							break;
						}
					}
					eularStack.push(edge.to());
					eularTour(eularStack);
				}
			
			
			
		}
		
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<Vertex<Integer>> floydWarshalPath(Vertex<Integer> u, Vertex<Integer> v) {
		
		if(next[u.getValue()][v.getValue()]==null){
			return new ArrayList<Vertex<Integer>>();
		}
		ShortestMatchedPath = new ArrayList<Vertex<Integer>>();
		ShortestMatchedPath.add(u);
		while(!u.equals(v)){
			u=next[u.getValue()][v.getValue()];
			ShortestMatchedPath.add(u);
		}
		return ShortestMatchedPath;
	}

	private static List<Edge<Vertex<Integer>>>  findMinWeightGraph(Graph<Integer> K, Integer[] values) {

		// finding min weight perfect matching
		// DFS(K.vertices()[1]);
		char augmentPathFoundFlag = '0';
		boolean firstblosomcheck = false;
		List<Edge<Vertex<Integer>>> matchedEdges = null;
		reduceLabel(K);

		int counter = 0;
		Map<Vertex<Integer>, List<Vertex<Integer>>> ShrinkMap = new HashMap<Vertex<Integer>, List<Vertex<Integer>>>();

		while (true) {

			// Computing now zero weight graph

			Graph<Integer> Z = new Graph<Integer>(values.length, values);

			for (Edge<Vertex<Integer>> edge : K.edges()) {
				if (edge.to().getLabel() + edge.from().getLabel() == edge.weight()) {
					Z.addEdge(edge.from().getValue(), edge.to().getValue(), edge.weight());
				}
			}

			for (int i = 1; i < Z.vertices().length; i++) {
				Z.vertices()[i].setLabel(K.vertices()[i].getLabel());
			}

			// Finding the maximal matching in the graph
			// Finding the maximal matching in the graph

			for (int i = 1; i < Z.vertices().length; i++) {
				for (Edge<Vertex<Integer>> edge : Z.vertices()[i].adjacencyList()) {
					Vertex<Integer> v = edge.to();
					if (Z.vertices()[i].getMate() == null && v.getMate() == null) {
						Z.vertices()[i].setMate(v);
						v.setMate(Z.vertices()[i]);
						msize++;
					}
				}

			}
			// Finding the maximum matching in the graph after getting the
			// maximal
			// matching

			// enqueue those vertex which have mate null ie Free Vertex

			Queue<Vertex<Integer>> outerQueue = new Queue<Vertex<Integer>>();
			for (Vertex<Integer> u : Z.vertices()) {
				if (u != null) {
					if (u.getMate() == null) {
						// u.setType('o');
						outerQueue.enqueue(u);
					}
				}
			}
			List<Vertex<Integer>> blosomList = null;
			boolean blosomflag = false;
			// maximum matching algo
			while (!outerQueue.isEmpty()) {
				Vertex<Integer> u = outerQueue.dequeue();
				u.setVisited(true);
				for (Edge<Vertex<Integer>> edge : u.adjacencyList()) {
					Vertex<Integer> v = edge.to();
					if (v.getMate().equals(u)) {
						continue;
					}
					if (!v.isVisited()) {
						if (v.getMate() == null && v.getType() == 'i') {
							System.out.println("Augment path found inside tree");
							augmentPathFoundFlag = '1';
							processAugPath(v);
							break;
						} else {
							v.setType('i');
							v.setParent(u);
							v.setRoot(u.getRoot() == null ? u : u.getRoot());
							Vertex<Integer> x = v.getMate();
							x.setType('o');
							x.setParent(v);
							x.setVisited(true);
							outerQueue.enqueue(x);
						}
					} else {
						if (v.getType() == 'i') {
							continue;
						} else if (v.getType() == 'o') {
							if (v.getRoot() != u.getRoot()) {
								System.out.println("Augmentating path found outside tree");
								// processAugPath(vertex);
								augmentPathFoundFlag = '2';

								v.setMate(u);
								u.setMate(v);
								processAugPath(v.getParent());
								processAugPath(u.getParent());
								break;
							} else {
								if (v.getLabel() == 0) {
									System.out.println("Augment path found inside tree with label 0");
									augmentPathFoundFlag = '3';
									System.out.println("Yet to be implemented!");
									break;
								} else {
									System.out.println("Blossom found!");
									blosomList = new ArrayList<Vertex<Integer>>();
									blosomList.add(v);
									createBlossomList(u, v, blosomList);
									blosomflag = true;

									// checking if it first blosom or not
									if (ShrinkMap.isEmpty()) {
										firstblosomcheck = true;
									}
									break;
								}
							}

						}
					}
				}
				// either blosomflag is set or one of augmentPathFoundFlag is
				// set
				if (blosomflag || augmentPathFoundFlag != '0') {
					break;
				}

			}

			if (blosomflag) {
				// now shrinking cycle and adding to original MST
				List<Edge<Vertex<Integer>>> edgeList = new ArrayList<Edge<Vertex<Integer>>>();

				for (Edge<Vertex<Integer>> edge : K.edges()) {
					if (blosomList.contains(edge.from()) && !blosomList.contains(edge.to())) {
						edgeList.add(edge);
					}
				}

				// removing cycle vertices from graph
				int index = 0;
				for (Vertex<Integer> cycleEdge : blosomList) {
					index = Z.removeVertex(cycleEdge);
					index = K.removeVertex(cycleEdge);
				}

				// new vertex added to graph Z
				Vertex<Integer> newVertex = new Vertex<Integer>(tVertices + (++counter));
				newVertex.setLabel(0);
				newVertex.setType('o');
				newVertex.setReal(false);
				newVertex.setRoot(blosomList.get(blosomList.size()-1));
				Z.addVertex(newVertex, index);
				K.addVertex(newVertex, index);

				// removing old edges and adding new edge
				for (Edge<Vertex<Integer>> removingEdge : edgeList) {
					// Z.addEdge(removingEdge.from().getValue(),
					// newVertex.getValue(), removingEdge.weight());
					// Z.removeEdge(removingEdge);
					if (blosomList.get(blosomList.size() - 1).getValue() == removingEdge.from().getValue()) {
						Z.addEdge(index, removingEdge.to().getValue(), removingEdge.weight());
						K.addEdge(index, removingEdge.to().getValue(), removingEdge.weight());
					}
					K.removeEdge(removingEdge);
					Z.removeEdge(removingEdge);
				}

				// storing newvertex and blosomlist into map
				ShrinkMap.put(newVertex, blosomList);

			}

			// calculating delta
			if (blosomflag || outerQueue.isEmpty()) {
				// finding slack of the tree
				double delta = Double.MAX_VALUE;
				for (Edge<Vertex<Integer>> edge : Z.edges()) {
					if ((edge.from().getType() == 'o') && (!edge.to().getRoot().equals(edge.from().getRoot()))) {
						double slack = edge.from().getLabel() + edge.to().getLabel() - edge.weight();
						if (delta < slack) {
							delta = slack;
						}
					}
				}

				// updating labels of the graph Z with delta
				if (delta != Double.MAX_VALUE) {

					for (int i = 0; i < Z.vertices().length; i++) {
						Vertex<Integer> u = Z.vertices()[i];
						if (u != null) {
							if (u.getType() == 'o' && u.isReal()) {
								u.setLabel(u.getLabel() + delta);
							} else if (u.getType() == 'o' && !u.isReal()) {
								if (firstblosomcheck) {
									u.setLabel(u.getLabel() - 2 * delta);
									firstblosomcheck = false;
								}
							} else if (u.getType() == 'i' && u.isReal()) {
								u.setLabel(u.getLabel() - delta);
							} else {
								if (firstblosomcheck) {
									u.setLabel(u.getLabel() + 2 * delta);
									firstblosomcheck = false;
								}
							}
						}

					}
				}

				// if augmentating path is found by all three cases
			}

			if (augmentPathFoundFlag != '0') {

				// check if perfect matching is found
				// Queue<Vertex<Integer>> perfectQueue = new
				// Queue<Vertex<Integer>>();
				boolean perfectCheck = false;
				for (Vertex<Integer> u : Z.vertices()) {
					if (u != null) {
						if (u.getMate() == null) {
							// u.setType('o');
							// perfectQueue.enqueue(u);
							perfectCheck = true;
							break;
						}
					}
				}
				if (!perfectCheck) {
					System.out.println("Perfect Matching Found");

				}

				if (!ShrinkMap.isEmpty()) {
					System.out.println("It has Blossom Nodes!");

					// expanding nodes
					// change hashmap with efficient data structures
					Vertex<Integer> blossomVertex = null;
					for (int i = 1; i < K.vertices().length; i++) {
						if (ShrinkMap.containsKey(K.vertices()[i])) {
							blossomVertex = K.vertices()[i];
							break;
						}
					}

					List<Vertex<Integer>> blossomCycle = ShrinkMap.get(blossomVertex);

					// for inner blossom node
					if (blossomVertex.getType() == 'i') {
						System.out.println("Matching for inner blossom node yet to be implemented");

						// for outer blossom node
					} else if (blossomVertex.getType() == 'o') {

						// setting all mates to null in blosomcyclelist
						for (int i = 0; i < blossomCycle.size(); i++)
							blossomCycle.get(i).setMate(null);

						for (int i = 0; i < blossomCycle.size(); i++) {
							for (Edge<Vertex<Integer>> edge : blossomCycle.get(i).adjacencyList()) {

								if (blossomCycle.contains(edge.from()) && blossomCycle.contains(edge.to())) {
									Vertex<Integer> v = edge.to();
									if (blossomCycle.get(i).getMate() == null && v.getMate() == null
											&& blossomCycle.get(i).getLabel() != 0 && v.getLabel() != 0) {
										blossomCycle.get(i).setMate(v);
										v.setMate(blossomCycle.get(i));
									}
								}
							}
						}
					}

					// expanding blossom
					for (Edge<Vertex<Integer>> edge : blossomVertex.adjacencyList()) {

						// removing all edges of blossom vertex
						K.removeEdge(edge);

					}

					K.removeVertex(blossomVertex);

					for (int i = 0; i < blossomCycle.size(); i++) {
						for (Edge<Vertex<Integer>> edge : blossomCycle.get(i).adjacencyList()) {
							if (!blossomCycle.contains(edge.to())) {
								K.vertices()[edge.to().getValue()].adjacencyList().add(
										new Edge<Vertex<Integer>>(edge.to(), edge.from(), edge.weight()));
							}
						}
					}

					// removing blossom from hashmap
					ShrinkMap.remove(blossomVertex);

				} else {
					System.out.println("perfect matching with no blossom");
					double totalWeight = 0;
					matchedEdges = new ArrayList<Edge<Vertex<Integer>>>();
					for (int i = 1; i < K.vertices().length; i++) {
						Vertex<Integer> u = K.vertices()[i];
						for (Edge<Vertex<Integer>> edge : K.vertices()[i].adjacencyList()) {
							Vertex<Integer> v = edge.to();
							if ((u.getMate().equals(v)&&(!matchedEdges.contains(edge)))) {
								totalWeight += edge.weight();
								matchedEdges.add(edge);
							}
						}

					}
					System.out.println("Total Weight of Min Weight Matching is " + totalWeight);
					System.out.println("Matched Edges");
					for(Edge<Vertex<Integer>> edge: matchedEdges){
						System.out.println(edge.from()+" , "+edge.to());
					}
					break;
				}

			}

		}
		return matchedEdges;
	}

	private static void createBlossomList(Vertex<Integer> u, Vertex<Integer> v, List<Vertex<Integer>> blosomList) {
		Vertex<Integer> x = v.getParent();
		if (x != u) {
			blosomList.add(x);
			createBlossomList(u, x, blosomList);
		} else {
			blosomList.add(x);
		}

	}

	private static void reduceLabel(Graph<Integer> K) {

		double[] labels = new double[K.vertices().length];
		for (int i = 1; i < K.vertices().length; i++) {
			Vertex<Integer> u = K.vertices()[i];
			if (u != null)
				for (Edge<Vertex<Integer>> edge : u.adjacencyList()) {
					Vertex<Integer> v = edge.to();
					if (edge.weight() - v.getLabel() > 0) {
						if (labels[i] == 0)
							labels[i] = edge.weight() - v.getLabel();
						else if (labels[i] > edge.weight() - v.getLabel())
							labels[i] = edge.weight() - v.getLabel();
					}
				}
		}

		for (int i = 1; i < K.vertices().length; i++) {
			Vertex<Integer> u = K.vertices()[i];
			if (u != null)
				u.setLabel(labels[i]);
		}
	}

	private static void processAugPath(Vertex<Integer> vertex) {
		Vertex<Integer> p = vertex.getParent();
		Vertex<Integer> x = p.getParent();
		vertex.setMate(p);
		p.setMate(vertex);
		while (x != null) {
			Vertex<Integer> newMate = x.getParent();
			Vertex<Integer> y = newMate.getParent();
			x.setMate(newMate);
			newMate.setMate(x);
			x = y;
		}
		msize++;
		return;
	}

	private static void DFS(Vertex<Integer> vertex) {

		if (!vertex.isVisited()) {

			vertex.setVisited(true);

			for (Edge<Vertex<Integer>> edge : vertex.adjacencyList()) {
				Vertex<Integer> v = edge.to();
				if (vertex.getType() == 'o')
					v.setType('i');
				else
					v.setType('o');
				DFS(v);
			}
		}

	}

	private static void floydWarshall(Graph<Integer> G) {
		int V = G.vertices().length;
		distTo = new double[V][V];
		edgeTo = new Edge[V][V];
		next = new Vertex[V][V];

		// initialize distances to infinity
		for (int v = 1; v < V; v++) {
			for (int w = 1; w < V; w++) {
				distTo[v][w] = Double.POSITIVE_INFINITY;
			}
		}

		// initialize distances using edge-weighted digraph's
		for (int v = 1; v < G.vertices().length; v++) {
			for (Edge<Vertex<Integer>> e : G.vertices()[v].adjacencyList()) {
				distTo[e.from().getValue()][e.to().getValue()] = e.weight();
				edgeTo[e.from().getValue()][e.to().getValue()] = e;
				next[e.from().getValue()][e.to().getValue()]=e.to();
			}
			// in case of self-loops
			if (distTo[v][v] >= 0.0) {
				distTo[v][v] = 0.0;
				edgeTo[v][v] = null;
				next[v][v]=null;
			}
		}

		// Floyd-Warshall updates
		for (int i = 1; i < V; i++) {
			// compute shortest paths using only 0, 1, ..., i as intermediate
			// vertices
			for (int v = 1; v < V; v++) {
				if (edgeTo[v][i] == null)
					continue; // optimization
				for (int w = 0; w < V; w++) {
					if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
						distTo[v][w] = distTo[v][i] + distTo[i][w];
						edgeTo[v][w] = edgeTo[i][w];
						next[v][w]=next[v][i];
					}
				}
				// check for negative cycle
				if (distTo[v][v] < 0.0) {
					System.out.println("Graph has negative cycles!");
					return;
				}
			}
		}
		System.out.println("Done");
	}

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * 
	 * @param G
	 *            the edge-weighted graph
	 */
	public static void Kruskal(Graph<Integer> G) {
		// more efficient to build heap by passing array of edges
		MinPQ<Edge<Vertex<Integer>>> pq = new MinPQ<Edge<Vertex<Integer>>>();
		for (Edge<Vertex<Integer>> e : G.edges()) {
			pq.insert(e);
		}

		// run greedy algorithm
		UF uf = new UF(G.noOfVertices() + 1);
		while (!pq.isEmpty() && mst.size() < G.noOfVertices() - 1) {
			Edge<Vertex<Integer>> e = pq.delMin();
			Vertex<Integer> ver1 = e.to();
			Vertex<Integer> ver2 = e.other(ver1);
			if (!uf.connected(ver1.getValue(), ver2.getValue())) { // v-w does
																	// not
																	// create a
																	// cycle
				uf.union(ver1.getValue(), ver2.getValue()); // merge v and w
															// components
				mst.enqueue(e); // add edge e to mst
				weight += e.weight();
			}
		}

		// check optimality conditions
		// assert check(G);
	}

}
