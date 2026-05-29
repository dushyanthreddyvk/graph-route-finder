package graphroutefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFSResult {
    private final List<String> traversalOrder;
    private final List<String> shortestPath;
    private final double totalDistance;
    private final double totalCost;
    private final double totalTravelTime;

    public BFSResult(List<String> traversalOrder, List<String> shortestPath) {
        this(traversalOrder, shortestPath, 0, 0, 0);
    }

    public BFSResult(
            List<String> traversalOrder,
            List<String> shortestPath,
            double totalDistance,
            double totalCost,
            double totalTravelTime) {
        this.traversalOrder = new ArrayList<>(traversalOrder);
        this.shortestPath = new ArrayList<>(shortestPath);
        this.totalDistance = totalDistance;
        this.totalCost = totalCost;
        this.totalTravelTime = totalTravelTime;
    }

    public List<String> getTraversalOrder() {
        return Collections.unmodifiableList(traversalOrder);
    }

    public List<String> getShortestPath() {
        return Collections.unmodifiableList(shortestPath);
    }

    public boolean hasPath() {
        return !shortestPath.isEmpty();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getTotalTravelTime() {
        return totalTravelTime;
    }
}
