package graphroutefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathResult {
    private final List<String> traversalOrder;
    private final List<String> path;
    private final double totalDistance;
    private final double totalCost;
    private final double totalTravelTime;

    public PathResult(
            List<String> traversalOrder,
            List<String> path,
            double totalDistance,
            double totalCost,
            double totalTravelTime) {
        this.traversalOrder = new ArrayList<>(traversalOrder);
        this.path = new ArrayList<>(path);
        this.totalDistance = totalDistance;
        this.totalCost = totalCost;
        this.totalTravelTime = totalTravelTime;
    }

    public List<String> getTraversalOrder() {
        return Collections.unmodifiableList(traversalOrder);
    }

    public List<String> getPath() {
        return Collections.unmodifiableList(path);
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

    public boolean hasPath() {
        return !path.isEmpty();
    }
}
