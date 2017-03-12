# Christofides-Approximation
Christofides approximation algorithm(Implementation of TSP)

The project has covered all five parts asked in the problem description:-

1. Minimum spanning tree of the given graph, using Kruskal's algorithm. The input edges are given in sorted order of weight.  Do not change this order.  Let the MST be tree T.

2. Find the nodes of the graph that have odd degree in T.  Let this set be S.

3. Find all-pairs shortest paths in G.  Create a complete graph K on the nodes of S, with w(u,v) = weight of shortest path from u to v in G.

4. Find a minimum-weight perfect matching M in K.

5. Each edge in M corresponds to a shortest path in G. Add to G the paths corresponding to the edges of M in G, resulting in a multi-graph H.

6. Find an Euler tour of H and output this tour.

Methodology: 

1. As given in description we have used Kruskal’s algorithm for finding the minimum spanning tree and then we found the nodes in the graph having odd degree.

2. We have used Floyd warshall algorithm to find the all pair shortest path .

3. We are finding minimum weight matching by
  	- Feasible labeling of the vertices using min (L(u), weight(u,v)-L(v)).
  	- Created a  Zero graph based on the condition L(u)+L(v)=w(u,v)
  	- Found the maximum cardinality matching in the general graph.
  	- After getting a blossom (we shrink the node for blossom) or when augmented path is not found in the graph, calculate the slack change the label of the vertices.

