package graphroutefinder;

public class Edge {
    private final String from;
    private final String to;
    private final double distance;
    private final double cost;
    private final double travelTime;

    public Edge(String from, String to, double distance, double cost, double travelTime) {
        this.from = Graph.normalize(from);
        this.to = Graph.normalize(to);
        this.distance = distance;
        this.cost = cost;
        this.travelTime = travelTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
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

    public String getLabel() {
        return format(distance) + " km | Rs " + format(cost) + " | " + format(travelTime) + " min";
    }

    public static String format(double value) {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        }
        return String.format("%.1f", value);
    }
}
