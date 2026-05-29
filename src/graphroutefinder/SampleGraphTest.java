package graphroutefinder;

public class SampleGraphTest {
    public static void main(String[] args) {
        runBfsVsDijkstraTest();
        runDfsTraversalTest();
        runDisconnectedGraphTest();
        runDynamicWeightedInsertionTest();
    }

    private static void runBfsVsDijkstraTest() {
        Graph graph = buildCityGraph();
        BFSAlgorithm bfs = new BFSAlgorithm(graph);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        BFSResult bfsResult = bfs.findShortestPath("Chennai", "Mumbai");
        PathResult dijkstraResult = dijkstra.findShortestPath("Chennai", "Mumbai");

        System.out.println("Test 1: BFS vs Dijkstra on weighted city graph");
        System.out.println("BFS route: " + bfsResult.getShortestPath());
        System.out.println("BFS distance: " + Edge.format(bfsResult.getTotalDistance()) + " km");
        System.out.println("Dijkstra route: " + dijkstraResult.getPath());
        System.out.println("Dijkstra distance: " + Edge.format(dijkstraResult.getTotalDistance()) + " km");
        System.out.println();
    }

    private static void runDfsTraversalTest() {
        Graph graph = buildCityGraph();
        DFSAlgorithm dfs = new DFSAlgorithm(graph);
        TraversalResult result = dfs.traverseFrom("Chennai");

        System.out.println("Test 2: DFS traversal");
        System.out.println("DFS order: " + result.getTraversalOrder());
        System.out.println();
    }

    private static void runDisconnectedGraphTest() {
        Graph graph = new Graph();
        graph.addEdge("AirportA", "AirportB", 12, 80, 10);
        graph.addEdge("AirportC", "AirportD", 18, 120, 14);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        PathResult result = dijkstra.findShortestPath("AirportA", "AirportD");

        System.out.println("Test 3: Disconnected weighted graph");
        System.out.println("Dijkstra traversal: " + result.getTraversalOrder());
        System.out.println("Shortest path AirportA to AirportD: " + result.getPath());
        System.out.println();
    }

    private static void runDynamicWeightedInsertionTest() {
        Graph graph = new Graph();
        graph.addNode("Metro1");
        graph.addNode("Metro2");
        graph.addNode("Metro3");
        graph.addEdge("Metro1", "Metro2", 4, 20, 8);
        graph.addEdge("Metro2", "Metro3", 6, 30, 12);

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        PathResult result = dijkstra.findShortestPath("Metro1", "Metro3");

        System.out.println("Test 4: Dynamic weighted node and edge insertion");
        System.out.println("Route: " + result.getPath());
        System.out.println("Distance: " + Edge.format(result.getTotalDistance()) + " km");
        System.out.println("Cost: Rs " + Edge.format(result.getTotalCost()));
        System.out.println("Time: " + Edge.format(result.getTotalTravelTime()) + " min");
        System.out.println();
    }

    private static Graph buildCityGraph() {
        Graph graph = new Graph();
        graph.addEdge("Chennai", "Bangalore", 345, 850, 360);
        graph.addEdge("Chennai", "Hyderabad", 630, 1400, 620);
        graph.addEdge("Bangalore", "Hyderabad", 570, 1200, 540);
        graph.addEdge("Bangalore", "Pune", 840, 2100, 780);
        graph.addEdge("Hyderabad", "Pune", 560, 1300, 520);
        graph.addEdge("Pune", "Mumbai", 150, 450, 180);
        graph.addEdge("Hyderabad", "Mumbai", 710, 1900, 700);
        return graph;
    }
}
