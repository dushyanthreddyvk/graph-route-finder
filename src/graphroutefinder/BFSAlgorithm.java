package graphroutefinder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFSAlgorithm {
    private final Graph graph;

    public BFSAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public BFSResult findShortestPath(String source, String destination) {
        String start = Graph.normalize(source);
        String target = Graph.normalize(destination);

        if (!graph.containsNode(start) || !graph.containsNode(target)) {
            return new BFSResult(List.of(), List.of());
        }

        List<String> traversalOrder = new ArrayList<>();
        Map<String, String> parentByNode = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        // Queue-driven BFS expands nodes level by level. In an unweighted graph,
        // the first discovered route to the destination uses the fewest edges.
        visited.add(start);
        queue.add(start);
        parentByNode.put(start, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            traversalOrder.add(current);

            if (current.equals(target)) {
                break;
            }

            for (String neighbor : graph.getNeighbors(current)) {
                if (visited.add(neighbor)) {
                    parentByNode.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<String> shortestPath = buildPath(parentByNode, start, target);
        Graph.PathMetrics metrics = graph.calculatePathMetrics(shortestPath);
        return new BFSResult(
                traversalOrder,
                shortestPath,
                metrics.getDistance(),
                metrics.getCost(),
                metrics.getTravelTime());
    }

    public TraversalResult traverseFrom(String source) {
        BFSResult result = findShortestPath(source, source);
        if (!result.getTraversalOrder().isEmpty()) {
            return new TraversalResult(runFullTraversal(source));
        }
        return new TraversalResult(List.of());
    }

    private List<String> runFullTraversal(String source) {
        String start = Graph.normalize(source);
        List<String> traversalOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            traversalOrder.add(current);

            for (String neighbor : graph.getNeighbors(current)) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        return traversalOrder;
    }

    private List<String> buildPath(Map<String, String> parentByNode, String start, String target) {
        if (!parentByNode.containsKey(target)) {
            return List.of();
        }

        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null) {
            path.add(0, current);
            current = parentByNode.get(current);
        }

        if (!path.isEmpty() && path.get(0).equals(start)) {
            return path;
        }
        return List.of();
    }
}
