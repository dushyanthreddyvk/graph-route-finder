package graphroutefinder;

import java.util.Objects;

public class Node {
    private final String name;

    public Node(String name) {
        this.name = Graph.normalize(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Node)) {
            return false;
        }
        Node node = (Node) other;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
