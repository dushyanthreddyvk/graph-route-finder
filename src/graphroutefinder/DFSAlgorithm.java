package graphroutefinder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DFSAlgorithm {
    private final Graph graph;

    public DFSAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public TraversalResult traverseFrom(String source) {
        String start = Graph.normalize(source);
        if (!graph.containsNode(start)) {
            return new TraversalResult(List.of());
        }

        List<String> traversalOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        ArrayDeque<String> stack = new ArrayDeque<>();
        stack.push(start);

        // DFS goes deep before backtracking. The stack models that last-in,
        // first-out behavior explicitly for interview visibility.
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.add(current)) {
                continue;
            }

            traversalOrder.add(current);
            List<String> neighbors = graph.getNeighbors(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }

        return new TraversalResult(traversalOrder);
    }
}
