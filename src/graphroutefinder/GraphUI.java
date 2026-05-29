package graphroutefinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GraphUI extends JFrame {
    private final Graph graph;
    private final BFSAlgorithm bfsAlgorithm;
    private final DFSAlgorithm dfsAlgorithm;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final GraphPanel graphPanel;
    private final JTextField nodeField;
    private final JTextField edgeFromField;
    private final JTextField edgeToField;
    private final JTextField distanceField;
    private final JTextField costField;
    private final JTextField timeField;
    private final JComboBox<String> sourceBox;
    private final JComboBox<String> destinationBox;
    private final JTextArea outputArea;
    private final JTextArea logArea;

    public GraphUI() {
        super("Advanced Graph Route Finder - BFS | DFS | Dijkstra");
        this.graph = new Graph();
        this.bfsAlgorithm = new BFSAlgorithm(graph);
        this.dfsAlgorithm = new DFSAlgorithm(graph);
        this.dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        this.graphPanel = new GraphPanel(graph);
        this.nodeField = new JTextField(10);
        this.edgeFromField = new JTextField(9);
        this.edgeToField = new JTextField(9);
        this.distanceField = new JTextField("5", 5);
        this.costField = new JTextField("100", 5);
        this.timeField = new JTextField("30", 5);
        this.sourceBox = new JComboBox<>();
        this.destinationBox = new JComboBox<>();
        this.outputArea = new JTextArea(12, 46);
        this.logArea = new JTextArea(12, 28);

        configureWindow();
        loadRealWorldSampleGraph();
        refreshNodeSelectors();
        refreshOutput("Real-world city graph loaded.");
    }

    private void configureWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, graphPanel, new JScrollPane(logArea));
        splitPane.setResizeWeight(0.76);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        add(buildControlPanel(), BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        panel.setBackground(new Color(241, 245, 249));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(4, 4, 4, 4);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JButton addNodeButton = new JButton("Add Node");
        addNodeButton.addActionListener(event -> addNode());
        addToPanel(panel, constraints, new JLabel("City/Station"), 0, 0);
        addToPanel(panel, constraints, nodeField, 1, 0);
        addToPanel(panel, constraints, addNodeButton, 2, 0);

        JButton addEdgeButton = new JButton("Add Weighted Edge");
        addEdgeButton.addActionListener(event -> addWeightedEdge());
        addToPanel(panel, constraints, new JLabel("From"), 0, 1);
        addToPanel(panel, constraints, edgeFromField, 1, 1);
        addToPanel(panel, constraints, new JLabel("To"), 2, 1);
        addToPanel(panel, constraints, edgeToField, 3, 1);
        addToPanel(panel, constraints, new JLabel("Km"), 4, 1);
        addToPanel(panel, constraints, distanceField, 5, 1);
        addToPanel(panel, constraints, new JLabel("Cost"), 6, 1);
        addToPanel(panel, constraints, costField, 7, 1);
        addToPanel(panel, constraints, new JLabel("Min"), 8, 1);
        addToPanel(panel, constraints, timeField, 9, 1);
        addToPanel(panel, constraints, addEdgeButton, 10, 1);

        addToPanel(panel, constraints, new JLabel("Source"), 0, 2);
        addToPanel(panel, constraints, sourceBox, 1, 2);
        addToPanel(panel, constraints, new JLabel("Destination"), 2, 2);
        addToPanel(panel, constraints, destinationBox, 3, 2);
        addToPanel(panel, constraints, buildAlgorithmButtons(), 4, 2, 7);

        return panel;
    }

    private JPanel buildAlgorithmButtons() {
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        buttons.setOpaque(false);

        JButton bfsButton = new JButton("BFS");
        JButton dfsButton = new JButton("DFS");
        JButton dijkstraButton = new JButton("Dijkstra");
        JButton compareButton = new JButton("Compare Algorithms");
        JButton resetButton = new JButton("Reset Graph");

        bfsButton.addActionListener(event -> runBfs());
        dfsButton.addActionListener(event -> runDfs());
        dijkstraButton.addActionListener(event -> runDijkstra());
        compareButton.addActionListener(event -> compareAlgorithms());
        resetButton.addActionListener(event -> resetGraph());

        buttons.add(bfsButton);
        buttons.add(dfsButton);
        buttons.add(dijkstraButton);
        buttons.add(compareButton);
        buttons.add(resetButton);
        return buttons;
    }

    private void addToPanel(JPanel panel, GridBagConstraints constraints, java.awt.Component component, int x, int y) {
        addToPanel(panel, constraints, component, x, y, 1);
    }

    private void addToPanel(
            JPanel panel,
            GridBagConstraints constraints,
            java.awt.Component component,
            int x,
            int y,
            int width) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.weightx = component instanceof JTextField || component instanceof JComboBox ? 1.0 : 0.0;
        panel.add(component, constraints);
        constraints.gridwidth = 1;
    }

    private void addNode() {
        String node = nodeField.getText();
        boolean added = graph.addNode(node);
        nodeField.setText("");
        refreshNodeSelectors();
        graphPanel.updateHighlights(List.of(), List.of());
        refreshOutput(added ? "Node added: " + Graph.normalize(node) : "Node was empty or already exists.");
        log(added ? "Inserted node " + Graph.normalize(node) : "Node insert ignored.");
    }

    private void addWeightedEdge() {
        try {
            String from = edgeFromField.getText();
            String to = edgeToField.getText();
            double distance = Double.parseDouble(distanceField.getText().trim());
            double cost = Double.parseDouble(costField.getText().trim());
            double travelTime = Double.parseDouble(timeField.getText().trim());
            boolean added = graph.addEdge(from, to, distance, cost, travelTime);

            edgeFromField.setText("");
            edgeToField.setText("");
            refreshNodeSelectors();
            graphPanel.updateHighlights(List.of(), List.of());
            refreshOutput(added
                    ? "Weighted route added: " + Graph.normalize(from) + " <-> " + Graph.normalize(to)
                    : "Edge was invalid or already exists.");
            log(added ? "Inserted weighted edge " + Graph.normalize(from) + " <-> " + Graph.normalize(to) : "Edge insert ignored.");
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Distance, cost, and time must be valid numbers.");
        }
    }

    private void runBfs() {
        String source = selectedSource();
        String destination = selectedDestination();
        if (!hasValidRouteInput(source, destination)) {
            return;
        }

        BFSResult result = bfsAlgorithm.findShortestPath(source, destination);
        animateTraversal(result.getTraversalOrder(), result.getShortestPath());
        refreshOutput(formatBfsResult(result));
        log("BFS used Queue. Complexity: O(V + E).");
    }

    private void runDfs() {
        String source = selectedSource();
        if (!hasNodeInput(source)) {
            return;
        }

        TraversalResult result = dfsAlgorithm.traverseFrom(source);
        animateTraversal(result.getTraversalOrder(), List.of());
        refreshOutput("DFS Traversal Order: " + joinOrEmpty(result.getTraversalOrder())
                + "\nDFS Complexity: O(V + E)\nStack usage: explicit stack explores depth-first.");
        log("DFS used Stack. Traversal size: " + result.getTraversalOrder().size());
    }

    private void runDijkstra() {
        String source = selectedSource();
        String destination = selectedDestination();
        if (!hasValidRouteInput(source, destination)) {
            return;
        }

        PathResult result = dijkstraAlgorithm.findShortestPath(source, destination);
        animateTraversal(result.getTraversalOrder(), result.getPath());
        refreshOutput(formatDijkstraResult(result));
        log("Dijkstra used PriorityQueue. Complexity: O((V + E) log V).");
    }

    private void compareAlgorithms() {
        String source = selectedSource();
        String destination = selectedDestination();
        if (!hasValidRouteInput(source, destination)) {
            return;
        }

        BFSResult bfs = bfsAlgorithm.findShortestPath(source, destination);
        TraversalResult dfs = dfsAlgorithm.traverseFrom(source);
        PathResult dijkstra = dijkstraAlgorithm.findShortestPath(source, destination);
        animateTraversal(dijkstra.getTraversalOrder(), dijkstra.getPath());

        String comparison = "ALGORITHM COMPARISON\n"
                + "\nBFS Result\n" + formatBfsResult(bfs)
                + "\n\nDFS Traversal\nDFS Order: " + joinOrEmpty(dfs.getTraversalOrder())
                + "\nDFS Complexity: O(V + E)"
                + "\n\nDijkstra Result\n" + formatDijkstraResult(dijkstra)
                + "\n\nRoute Cost Comparison"
                + "\nBFS total distance: " + Edge.format(bfs.getTotalDistance()) + " km"
                + "\nBFS total cost: Rs " + Edge.format(bfs.getTotalCost())
                + "\nDijkstra total distance: " + Edge.format(dijkstra.getTotalDistance()) + " km"
                + "\nDijkstra total cost: Rs " + Edge.format(dijkstra.getTotalCost())
                + "\n\nComplexity Comparison"
                + "\nBFS: O(V + E), optimal for unweighted shortest edge count"
                + "\nDFS: O(V + E), traversal/search but not shortest path"
                + "\nDijkstra: O((V + E) log V), optimal for non-negative weighted edges";

        refreshOutput(comparison);
        log("Compared BFS, DFS, and Dijkstra for " + source + " to " + destination + ".");
    }

    private void resetGraph() {
        graph.clear();
        loadRealWorldSampleGraph();
        refreshNodeSelectors();
        graphPanel.updateHighlights(List.of(), List.of());
        refreshOutput("Graph reset to the real-world sample network.");
        log("Graph reset.");
    }

    private void animateTraversal(List<String> traversalOrder, List<String> path) {
        if (traversalOrder.isEmpty()) {
            graphPanel.updateHighlights(List.of(), List.of());
            return;
        }

        final int[] step = {0};
        Timer timer = new Timer(360, null);
        timer.addActionListener(event -> {
            List<String> visibleTraversal = traversalOrder.subList(0, Math.min(step[0] + 1, traversalOrder.size()));
            boolean done = step[0] >= traversalOrder.size() - 1;
            graphPanel.updateHighlights(visibleTraversal, done ? path : List.of());
            log("Visited: " + traversalOrder.get(Math.min(step[0], traversalOrder.size() - 1)));
            step[0]++;
            if (done) {
                timer.stop();
            }
        });
        timer.start();
    }

    private String formatBfsResult(BFSResult result) {
        return "BFS Traversal Path: " + joinOrEmpty(result.getTraversalOrder())
                + "\nBFS Shortest Route by edge count: " + joinOrNoRoute(result.getShortestPath())
                + "\nTotal distance on BFS route: " + Edge.format(result.getTotalDistance()) + " km"
                + "\nTotal cost on BFS route: Rs " + Edge.format(result.getTotalCost())
                + "\nTotal travel time on BFS route: " + Edge.format(result.getTotalTravelTime()) + " min"
                + "\nBFS Complexity: O(V + E)";
    }

    private String formatDijkstraResult(PathResult result) {
        return "Dijkstra Traversal Order: " + joinOrEmpty(result.getTraversalOrder())
                + "\nDijkstra Shortest Weighted Route: " + joinOrNoRoute(result.getPath())
                + "\nTotal distance: " + Edge.format(result.getTotalDistance()) + " km"
                + "\nTotal cost: Rs " + Edge.format(result.getTotalCost())
                + "\nTotal travel time: " + Edge.format(result.getTotalTravelTime()) + " min"
                + "\nDijkstra Complexity: O((V + E) log V)";
    }

    private void refreshOutput(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(message).append("\n\nWeighted Adjacency List:\n");

        for (Map.Entry<String, List<Edge>> entry : graph.getWeightedAdjacencyListSnapshot().entrySet()) {
            builder.append(entry.getKey()).append(" -> ");
            for (Edge edge : entry.getValue()) {
                builder.append(edge.getTo()).append(" (").append(edge.getLabel()).append(") ");
            }
            builder.append("\n");
        }

        outputArea.setText(builder.toString());
        graphPanel.repaint();
    }

    private void refreshNodeSelectors() {
        String currentSource = selectedSource();
        String currentDestination = selectedDestination();
        sourceBox.removeAllItems();
        destinationBox.removeAllItems();

        for (String node : graph.getNodes()) {
            sourceBox.addItem(node);
            destinationBox.addItem(node);
        }

        sourceBox.setSelectedItem(currentSource);
        destinationBox.setSelectedItem(currentDestination);
    }

    private void loadRealWorldSampleGraph() {
        graph.addEdge("Chennai", "Bangalore", 345, 850, 360);
        graph.addEdge("Chennai", "Hyderabad", 630, 1400, 620);
        graph.addEdge("Bangalore", "Hyderabad", 570, 1200, 540);
        graph.addEdge("Bangalore", "Pune", 840, 2100, 780);
        graph.addEdge("Hyderabad", "Pune", 560, 1300, 520);
        graph.addEdge("Pune", "Mumbai", 150, 450, 180);
        graph.addEdge("Hyderabad", "Mumbai", 710, 1900, 700);
        graph.addEdge("Mumbai", "Delhi", 1400, 4200, 1280);
        graph.addEdge("Pune", "Delhi", 1450, 3800, 1320);
    }

    private boolean hasValidRouteInput(String source, String destination) {
        return hasNodeInput(source) && hasNodeInput(destination);
    }

    private boolean hasNodeInput(String node) {
        if (node == null || node.isEmpty() || !graph.containsNode(node)) {
            JOptionPane.showMessageDialog(this, "Please select valid graph nodes.");
            return false;
        }
        return true;
    }

    private String selectedSource() {
        Object selected = sourceBox.getSelectedItem();
        return selected == null ? "" : selected.toString();
    }

    private String selectedDestination() {
        Object selected = destinationBox.getSelectedItem();
        return selected == null ? "" : selected.toString();
    }

    private String joinOrEmpty(List<String> values) {
        return values.isEmpty() ? "No traversal" : String.join(" -> ", values);
    }

    private String joinOrNoRoute(List<String> values) {
        return values.isEmpty() ? "No route found" : String.join(" -> ", values);
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new GraphUI().setVisible(true));
    }
}
