package graphroutefinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraversalResult {
    private final List<String> traversalOrder;

    public TraversalResult(List<String> traversalOrder) {
        this.traversalOrder = new ArrayList<>(traversalOrder);
    }

    public List<String> getTraversalOrder() {
        return Collections.unmodifiableList(traversalOrder);
    }
}
