package com.project.sightseeing.Object;

public class CalculateDistanceToOtherObj {

	// A utility function to find the vertex with minimum distance value,
	// from the set of vertices not yet included in shortest path tree
	static int V = 9;

	public CalculateDistanceToOtherObj() {

		int graph[][] = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 }, 
				{ 4, 0, 8, 0, 0, 0, 0, 11, 0},
				{ 0, 8, 0, 7, 0, 4, 0, 0, 2}, 
				{ 0, 0, 7, 0, 9, 14, 0, 0, 0},
				{ 0, 0, 0, 9, 0, 10, 0, 0, 0}, 
				{ 0, 0, 4, 14, 10, 0, 2, 0, 0},
				{ 0, 0, 0, 0, 0, 2, 0, 1, 6}, 
				{ 8, 11, 0, 0, 0, 0, 1, 0, 7}, 
				{ 0, 0, 2, 0, 0, 0, 6, 7, 0},
 };

		int graphCorrect[][] = new int[graph.length + 1][graph[0].length + 1];
 
		for (int i = 0; i < graphCorrect.length; i++) {
			for (int j = 0; j < graphCorrect[0].length; j++) {
				if(i<graph.length && j<graph[0].length) {
				graphCorrect[i][j]=graph[i][j];
			}else {
				graphCorrect[i][j]=Integer.MAX_VALUE;
			}
			}
		}
		
		graphCorrect[9][1]=2;
		graphCorrect[9][3]=5;
		graphCorrect[9][7]=5;
		
	    this.dijkstra(graphCorrect, graphCorrect.length-1); 

	}

	int minDistance(int dist[], Boolean sptSet[]) {
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	// A utility function to print the constructed distance array
	void printSolution(int dist[], int n, int graph[][]) {
		System.out.println("Vertex   Distance from Source");
		for (int i = 0; i < V; i++) {

			graph[i][n - 1] = dist[i];
			graph[n - 1][i] = dist[i];
			System.out.println(i + "  ----  " + dist[i]);
		}
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[0].length; j++) {

				System.out.print(graph[i][j] + " ");

			}
			System.out.println("");
		}

	}

	// Function that implements Dijkstra's single source shortest path
	// algorithm for a graph represented using adjacency matrix
	// representation
	void dijkstra(int graph[][], int src) {
		CalculateDistanceToOtherObj.V = graph[0].length;

		int dist[] = new int[V]; // The output array. dist[i] will hold
		// the shortest distance from src to i

		// sptSet[i] will true if vertex i is included in shortest
		// path tree or shortest distance from src to i is finalized
		Boolean sptSet[] = new Boolean[V];

		// Initialize all distances as INFINITE and stpSet[] as false
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		// Distance of source vertex from itself is always 0
		dist[src] = 0;

		// Find shortest path for all vertices
		for (int count = 0; count < V - 1; count++) {
			// Pick the minimum distance vertex from the set of vertices
			// not yet processed. u is always equal to src in first
			// iteration.
			int u = minDistance(dist, sptSet);

			// Mark the picked vertex as processed
			sptSet[u] = true;

			// Update dist value of the adjacent vertices of the
			// picked vertex.
			for (int v = 0; v < V; v++)

				// Update dist[v] only if is not in sptSet, there is an
				// edge from u to v, and total weight of path from src to
				// v through u is smaller than current value of dist[v]
				if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
					dist[v] = dist[u] + graph[u][v];
		}

		// print the constructed distance array
		printSolution(dist, V, graph);
	}

}