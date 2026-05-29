package graphroutefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Node> nodes;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.nodes = new LinkedHashMap<>();
        this.adjacencyList = new LinkedHashMap<>();
    }

    public boolean addNode(String node) {
        String normalizedNode = normalize(node);
        if (normalizedNode.isEmpty() || nodes.containsKey(normalizedNode)) {
            return false;
        }

        nodes.put(normalizedNode, new Node(normalizedNode));
        adjacencyList.put(normalizedNode, new ArrayList<>());
        return true;
    }

    public boolean addEdge(String firstNode, String secondNode) {
        return addEdge(firstNode, secondNode, 1, 1, 1);
    }

    public boolean addEdge(String firstNode, String secondNode, double distance, double cost, double travelTime) {
        String from = normalize(firstNode);
        String to = normalize(secondNode);

        if (from.isEmpty() || to.isEmpty() || from.equals(to) || distance <= 0 || cost < 0 || travelTime < 0) {
            return false;
        }

        addNode(from);
        addNode(to);

        if (hasEdge(from, to)) {
            return false;
        }

        // The graph is undirected, so each route is stored in both directions.
        adjacencyList.get(from).add(new Edge(from, to, distance, cost, travelTime));
        adjacencyList.get(to).add(new Edge(to, from, distance, cost, travelTime));
        return true;
    }

    public boolean containsNode(String node) {
        return nodes.containsKey(normalize(node));
    }

    public Set<String> getNodes() {
        return Collections.unmodifiableSet(nodes.keySet());
    }

    public List<String> getNeighbors(String node) {
        List<String> neighbors = new ArrayList<>();
        for (Edge edge : getEdgesFrom(node)) {
            neighbors.add(edge.getTo());
        }
        return neighbors;
    }

    public List<Edge> getEdgesFrom(String node) {
        List<Edge> edges = adjacencyList.get(normalize(node));
        if (edges == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(edges);
    }

    public List<Edge> getUniqueEdges() {
        List<Edge> edges = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (List<Edge> nodeEdges : adjacencyList.values()) {
            for (Edge edge : nodeEdges) {
                String key = edgeKey(edge.getFrom(), edge.getTo());
                if (seen.add(key)) {
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    public Edge getEdge(String from, String to) {
        for (Edge edge : getEdgesFrom(from)) {
            if (edge.getTo().equals(normalize(to))) {
                return edge;
            }
        }
        return null;
    }

    public PathMetrics calculatePathMetrics(List<String> path) {
        double distance = 0;
        double cost = 0;
        double travelTime = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Edge edge = getEdge(path.get(i), path.get(i + 1));
            if (edge != null) {
                distance += edge.getDistance();
                cost += edge.getCost();
                travelTime += edge.getTravelTime();
            }
        }

        return new PathMetrics(distance, cost, travelTime);
    }

    public Map<String, Set<String>> getAdjacencyListSnapshot() {
        Map<String, Set<String>> snapshot = new LinkedHashMap<>();
        for (String node : adjacencyList.keySet()) {
            snapshot.put(node, new LinkedHashSet<>(getNeighbors(node)));
        }
        return snapshot;
    }

    public Map<String, List<Edge>> getWeightedAdjacencyListSnapshot() {
        Map<String, List<Edge>> snapshot = new LinkedHashMap<>();
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            snapshot.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return snapshot;
    }

    public void clear() {
        nodes.clear();
        adjacencyList.clear();
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    private boolean hasEdge(String from, String to) {
        return getEdge(from, to) != null;
    }

    public static String edgeKey(String firstNode, String secondNode) {
        return firstNode.compareTo(secondNode) <= 0
                ? firstNode + "::" + secondNode
                : secondNode + "::" + firstNode;
    }

    public static String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    public static class PathMetrics {
        private final double distance;
        private final double cost;
        private final double travelTime;

        public PathMetrics(double distance, double cost, double travelTime) {
            this.distance = distance;
            this.cost = cost;
            this.travelTime = travelTime;
        }

        public double getDistance() {
            return distance;
        }

        public double getCost() {
            return cost;
        }

        public double getTravelTime() {
            return travelTime;
        }
    }
}
