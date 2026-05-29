package graphroutefinder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
    private static final int NODE_WIDTH = 118;
    private static final int NODE_HEIGHT = 42;
    private static final Color BACKGROUND = new Color(248, 250, 252);
    private static final Color EDGE_COLOR = new Color(148, 163, 184);
    private static final Color NODE_COLOR = Color.WHITE;
    private static final Color VISITED_COLOR = new Color(191, 219, 254);
    private static final Color ROUTE_COLOR = new Color(34, 197, 94);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);

    private final Graph graph;
    private List<String> traversalOrder = List.of();
    private List<String> shortestPath = List.of();

    public GraphPanel(Graph graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(760, 460));
        setBackground(BACKGROUND);
    }

    public void updateHighlights(List<String> traversalOrder, List<String> shortestPath) {
        this.traversalOrder = List.copyOf(traversalOrder);
        this.shortestPath = List.copyOf(shortestPath);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        Map<String, Point> positions = calculateNodePositions();
        drawEdges(g2, positions);
        drawNodes(g2, positions);

        g2.dispose();
    }

    private Map<String, Point> calculateNodePositions() {
        Map<String, Point> positions = new LinkedHashMap<>();
        int count = graph.getNodes().size();
        if (count == 0) {
            return positions;
        }

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.max(115, Math.min(getWidth(), getHeight()) / 2 - 86);
        int index = 0;

        for (String node : graph.getNodes()) {
            double angle = (2 * Math.PI * index / count) - Math.PI / 2;
            int x = centerX + (int) (Math.cos(angle) * radius);
            int y = centerY + (int) (Math.sin(angle) * radius);
            positions.put(node, new Point(x, y));
            index++;
        }

        return positions;
    }

    private void drawEdges(Graphics2D g2, Map<String, Point> positions) {
        Set<String> routeEdges = buildRouteEdges();

        for (Edge edge : graph.getUniqueEdges()) {
            Point from = positions.get(edge.getFrom());
            Point to = positions.get(edge.getTo());
            if (from == null || to == null) {
                continue;
            }

            boolean routeEdge = routeEdges.contains(Graph.edgeKey(edge.getFrom(), edge.getTo()));
            g2.setColor(routeEdge ? ROUTE_COLOR : EDGE_COLOR);
            g2.setStroke(new BasicStroke(routeEdge ? 4f : 2f));
            g2.draw(new Line2D.Double(from.x, from.y, to.x, to.y));
            drawEdgeLabel(g2, from, to, edge);
        }
    }

    private void drawEdgeLabel(Graphics2D g2, Point from, Point to, Edge edge) {
        String label = Edge.format(edge.getDistance()) + " km";
        FontMetrics metrics = g2.getFontMetrics();
        int x = (from.x + to.x) / 2;
        int y = (from.y + to.y) / 2;
        int width = metrics.stringWidth(label) + 10;
        int height = metrics.getHeight() + 2;

        g2.setColor(new Color(255, 255, 255, 230));
        g2.fillRoundRect(x - width / 2, y - height / 2, width, height, 8, 8);
        g2.setColor(new Color(71, 85, 105));
        g2.drawString(label, x - metrics.stringWidth(label) / 2, y + metrics.getAscent() / 2 - 2);
    }

    private void drawNodes(Graphics2D g2, Map<String, Point> positions) {
        Set<String> visitedNodes = new HashSet<>(traversalOrder);
        Set<String> pathNodes = new HashSet<>(shortestPath);
        FontMetrics metrics = g2.getFontMetrics();

        for (Map.Entry<String, Point> entry : positions.entrySet()) {
            String node = entry.getKey();
            Point point = entry.getValue();
            boolean onPath = pathNodes.contains(node);
            boolean visited = visitedNodes.contains(node);

            g2.setColor(onPath ? ROUTE_COLOR : visited ? VISITED_COLOR : NODE_COLOR);
            g2.fillRoundRect(
                    point.x - NODE_WIDTH / 2,
                    point.y - NODE_HEIGHT / 2,
                    NODE_WIDTH,
                    NODE_HEIGHT,
                    12,
                    12);
            g2.setColor(onPath ? new Color(21, 128, 61) : new Color(71, 85, 105));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(
                    point.x - NODE_WIDTH / 2,
                    point.y - NODE_HEIGHT / 2,
                    NODE_WIDTH,
                    NODE_HEIGHT,
                    12,
                    12);

            String label = fitText(node, metrics, NODE_WIDTH - 12);
            int textWidth = metrics.stringWidth(label);
            int textHeight = metrics.getAscent();
            g2.setColor(TEXT_COLOR);
            g2.drawString(label, point.x - textWidth / 2, point.y + textHeight / 3);
        }
    }

    private String fitText(String value, FontMetrics metrics, int maxWidth) {
        if (metrics.stringWidth(value) <= maxWidth) {
            return value;
        }

        String ellipsis = "...";
        for (int i = value.length() - 1; i > 0; i--) {
            String candidate = value.substring(0, i) + ellipsis;
            if (metrics.stringWidth(candidate) <= maxWidth) {
                return candidate;
            }
        }
        return ellipsis;
    }

    private Set<String> buildRouteEdges() {
        Set<String> edges = new HashSet<>();
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            edges.add(Graph.edgeKey(shortestPath.get(i), shortestPath.get(i + 1)));
        }
        return edges;
    }

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
