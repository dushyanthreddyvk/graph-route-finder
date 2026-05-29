package graphroutefinder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraAlgorithm {
    private final Graph graph;

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public PathResult findShortestPath(String source, String destination) {
        String start = Graph.normalize(source);
        String target = Graph.normalize(destination);

        if (!graph.containsNode(start) || !graph.containsNode(target)) {
            return new PathResult(List.of(), List.of(), 0, 0, 0);
        }

        Map<String, Double> distanceByNode = new HashMap<>();
        Map<String, String> parentByNode = new HashMap<>();
        Set<String> settled = new HashSet<>();
        List<String> traversalOrder = new ArrayList<>();
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(NodeDistance::distance));

        for (String node : graph.getNodes()) {
            distanceByNode.put(node, Double.POSITIVE_INFINITY);
        }
        distanceByNode.put(start, 0.0);
        parentByNode.put(start, null);
        priorityQueue.add(new NodeDistance(start, 0));

        // Dijkstra always expands the unsettled node with the smallest known
        // total distance, which is why a PriorityQueue is the core data structure.
        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            if (!settled.add(current.node())) {
                continue;
            }

            traversalOrder.add(current.node());
            if (current.node().equals(target)) {
                break;
            }

            for (Edge edge : graph.getEdgesFrom(current.node())) {
                if (settled.contains(edge.getTo())) {
                    continue;
                }

                double candidateDistance = distanceByNode.get(current.node()) + edge.getDistance();
                if (candidateDistance < distanceByNode.get(edge.getTo())) {
                    distanceByNode.put(edge.getTo(), candidateDistance);
                    parentByNode.put(edge.getTo(), current.node());
                    priorityQueue.add(new NodeDistance(edge.getTo(), candidateDistance));
                }
            }
        }

        List<String> path = buildPath(parentByNode, start, target);
        Graph.PathMetrics metrics = graph.calculatePathMetrics(path);
        return new PathResult(
                traversalOrder,
                path,
                metrics.getDistance(),
                metrics.getCost(),
                metrics.getTravelTime());
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

    private static class NodeDistance {
        private final String node;
        private final double distance;

        private NodeDistance(String node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        private String node() {
            return node;
        }

        private double distance() {
            return distance;
        }
    }
}
