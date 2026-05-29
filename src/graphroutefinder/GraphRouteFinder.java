package graphroutefinder;

import java.util.List;

public class GraphRouteFinder {
    private final BFSAlgorithm bfsAlgorithm;

    public GraphRouteFinder(Graph graph) {
        this.bfsAlgorithm = new BFSAlgorithm(graph);
    }

    public BFSResult findShortestPath(String source, String destination) {
        return bfsAlgorithm.findShortestPath(source, destination);
    }

    public List<String> traverseFrom(String source) {
        return bfsAlgorithm.traverseFrom(source).getTraversalOrder();
    }
}
