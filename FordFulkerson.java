import java.io.*;
import java.util.*;

public class FordFulkerson {
    //constant to represent that a node has no parent
    private static final int NO_PARENT = 0; 
    public static void main(String[] args) throws IOException {
        String filename = "deliverable2.csv";
        //reading the network matrix from the csv file
        int[][] capacityMatrix = readNetworkMatrix(filename);
        //setting source node to 0
        int source = 0;
        //sink node is set to the last node
        int sink = capacityMatrix.length - 1;

        //calculate max flow using fordfulk algorithm
        int maxFlow = fordFulkerson(capacityMatrix, source, sink);
        //this doesn't work but I don't have the heart to remove it after spending 48 hours and changing from python to java. I quit keep trying but I have so so so so so so many implementations
        rescueAttempt(capacityMatrix, source, sink);
        System.out.println("Total flow: " + maxFlow);
    }

    /**
     * method to read the network matrix from a csv file
     * @param filename
     * @return
     * @throws IOException
     */
    public static int[][] readNetworkMatrix(String filename) throws IOException {
        //creates a buffered reader to read the file
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        String[] data = line.split(",");
        
        //number of vertices is the first element
        int n = Integer.parseInt(data[0]);

        //creating a matrix to store the network
        int[][] matrix = new int[n][n];
        //start reading matrix values from the second element
        int index = 1;

        //loop to fill the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //parsing each string as an integer and storing it into the matrix
                matrix[i][j] = Integer.parseInt(data[index++]);
            }
        }
        br.close();
        return matrix;
    }

    /**
     * the big method! used to calculate the max flow using ford fulkerson
     * @param capacity
     * @param source
     * @param sink
     * @return
     */
    public static int fordFulkerson(int[][] capacity, int source, int sink) {
        //number of nodes in the network
        int n = capacity.length;
        //matrix to store the residual capacities
        int[][] residualCapacity = new int[n][n];
        //loop to copy the capacity matrix into the resiudal capacity matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        //array to store the parent of each node in the path found by BFS
        int[] parent = new int[n];
        int maxFlow = 0;

        //while there is a path from the source to the sink
        while (bfs(residualCapacity, source, sink, parent)) {
            //stores augmenting path
            List<Integer> path = new ArrayList<>();
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                path.add(v);
                int u = parent[v];
                //updating max flow of the path
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
            }
            path.add(source);
            //reversing the path to get the correct order from source to sink
            Collections.reverse(path);

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                //decreasing the residual capacity of the edge
                residualCapacity[u][v] -= pathFlow;
            }

            maxFlow += pathFlow;
            System.out.println("Augmenting Path found: " + path + " with flow: " + pathFlow);
        }
        return maxFlow;
    }

    /**
     * A BFS method to find if there is a path from source to sink, bfs = breadth first search
     * @param residualCapacity
     * @param source
     * @param sink
     * @param parent
     * @return
     */
    public static boolean bfs(int[][] residualCapacity, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[residualCapacity.length];
        //fills the array with 0's
        Arrays.fill(parent, NO_PARENT);
        //stores the nodes visited (array doesn't work for this)
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            //remove a node from teh queue
            int u = queue.poll();

            //each node in teh network
            for (int v = 0; v < residualCapacity.length; v++) {
                //if the node has not been visitied and the edge from u to v ahs positive capacity
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.offer(v);
                    visited[v] = true;
                    parent[v] = u;
                    //if the node is the sink
                    if (v == sink) {
                        //path is found
                        return true;
                    }
                }
            }
        }
        //no path
        return false;
    }

    /**
     * attempts a rescue attempt
     * @param residualCapacity
     * @param source
     * @param sink
     */
    public static void rescueAttempt(int[][] residualCapacity, int source, int sink) {
        //number of nodes in the network
        int n = residualCapacity.length;
        int[] parentSource = new int[n];
        int[] parentSink = new int[n];
    
        // check if there is a path from source to any vertex
        for (int v = 0; v < n; v++) {
            if (bfs(residualCapacity, source, v, parentSource)) {
                // check if there is a path from this vertex to sink
                if (bfs(residualCapacity, v, sink, parentSink)) {
                    // check if there is an edge with capacity 0 between them
                    for (int u = 0; u < n; u++) {
                        if (residualCapacity[u][v] == 0) {
                            System.out.println("Rescue attempt:");
                            System.out.println("Found path from source: " + Arrays.toString(parentSource) + " with capacity " + residualCapacity[source][v]);
                            System.out.println("Found path to sink: " + Arrays.toString(parentSink) + " with capacity " + residualCapacity[v][sink]);
                            System.out.println("Found linking path: [" + u + " " + v + "] with flow " + residualCapacity[u][v]);
                            System.out.println("Increasing total flow by 1.");
                            residualCapacity[u][v] += 1; // Increase the flow by 1
    
                            // decrease the residual capacities of the edges along the path from source to v
                            for (int w = v; w != source; w = parentSource[w]) {
                                int z = parentSource[w];
                                residualCapacity[z][w] -= 1;
                            }
    
                            // decrease the residual capacities of the edges along the path from v to sink
                            for (int w = sink; w != v; w = parentSink[w]) {
                                int z = parentSink[w];
                                residualCapacity[w][z] -= 1;
                            }
                            return;
                        }
                    }
                }
            }
        }
    }
    
    
    
}
