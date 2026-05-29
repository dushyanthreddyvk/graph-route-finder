# Graph Route Finder

A comprehensive Java application for exploring and visualizing graph algorithms. This project demonstrates multiple pathfinding and traversal algorithms including **BFS**, **DFS**, and **Dijkstra's Algorithm** with an interactive GUI for real-time graph visualization and algorithm comparison.

## 📋 Project Overview

Graph Route Finder is designed to help developers and students understand how graph algorithms work in practice. The application simulates real-world routing scenarios like those used in **Google Maps**, **Metro Navigation Systems**, and **Network Routing Protocols**. 

### Why Graph Algorithms Matter?
Graph algorithms are fundamental to computer science and are used extensively in:
- Navigation systems (Google Maps, GPS)
- Social network analysis (friend recommendations)
- Network routing (internet infrastructure)
- Game AI pathfinding
- Recommendation engines

## ✨ Features

- **Add Nodes Dynamically** - Create graph nodes on the fly
- **Add Weighted Edges** - Define connections with customizable weights and costs
- **BFS Traversal** - Breadth-First Search for level-order exploration
- **DFS Traversal** - Depth-First Search for deep exploration
- **Dijkstra's Shortest Path** - Find optimal routes in weighted graphs
- **Algorithm Comparison** - Compare results from multiple algorithms simultaneously
- **Interactive GUI** - Visual graph representation and algorithm execution
- **Adjacency List Representation** - Efficient graph storage and traversal
- **Real-time Visualization** - Watch algorithms execute step-by-step

## 🧠 DSA Concepts Used

- **Graphs** - Nodes and edges representing relationships
- **BFS (Breadth-First Search)** - Queue-based traversal exploring level by level
- **DFS (Depth-First Search)** - Stack-based traversal exploring deeply
- **Dijkstra's Algorithm** - Greedy algorithm for shortest path in weighted graphs
- **Queue Data Structure** - For BFS implementation
- **Priority Queue** - For Dijkstra's algorithm
- **HashSet** - For tracking visited nodes
- **HashMap** - For storing graph adjacency lists and distances
- **Adjacency List** - Space-efficient graph representation

## 🏗️ Project Architecture

```
┌─────────────────┐
│   User Input    │
│  (Nodes/Edges)  │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Graph Creation  │
│(Adjacency List) │
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────────┐
│ Algorithm Selection (BFS/DFS/Dijkstra)
└────────┬────────────────────────────┘
         │
         ▼
┌──────────────────────────┐
│ Traversal & Path Finding │
└────────┬─────────────────┘
         │
         ▼
┌─────────────────────────┐
│ Result Visualization &  │
│ Comparison Output       │
└─────────────────────────┘
```

## ⏱️ Time Complexity Analysis

### BFS (Breadth-First Search)
- **Time Complexity**: O(V + E)
  - V = number of vertices
  - E = number of edges
- **Space Complexity**: O(V) for queue and visited set

### DFS (Depth-First Search)
- **Time Complexity**: O(V + E)
- **Space Complexity**: O(V) for recursion stack

### Dijkstra's Algorithm
- **Time Complexity**: O((V + E) log V) with min-heap
- **Space Complexity**: O(V) for distance and visited tracking

## 🔍 How It Works

### Node and Edge Storage
- **Nodes** are stored as unique identifiers with optional properties
- **Edges** are stored in an **adjacency list** (HashMap<Node, List<Edge>>)
- **Weights** represent edge costs (distance, time, or other metrics)

### BFS Traversal Process
1. Start from source node
2. Add source to queue
3. While queue is not empty:
   - Dequeue a node
   - Mark as visited
   - Explore all unvisited neighbors
   - Enqueue unvisited neighbors
4. Return traversal order

### Shortest Path Finding
1. **BFS Path** - Works for unweighted graphs (equal edge weights)
2. **Dijkstra's Path** - Works for weighted graphs with non-negative weights
3. **DFS Path** - Explores deeply but not guaranteed shortest

## 📊 Sample Graph Example

```
City Route Network:

    Chennai
       │ (100 km)
       │
    Mumbai ──────── Ahmedabad (530 km, ₹1500)
       │ (200 km)
       │
    Delhi ──────── Bangalore
       └─(150 km)
```

**Finding Shortest Path: Chennai → Bangalore**

| Algorithm | Path | Distance |
|-----------|------|----------|
| BFS | Chennai → Mumbai → Bangalore | 300 km |
| Dijkstra | Chennai → Mumbai → Bangalore | 300 km |
| DFS | Chennai → Mumbai → Delhi → Bangalore | 450 km |

## 🛠️ Technologies Used

- **Java** - Core programming language
- **Java Swing** - GUI framework for visualization
- **Java Collections Framework** - HashMap, ArrayList, Queue, PriorityQueue, HashSet
- **Object-Oriented Design** - Modular and maintainable code structure

## 📁 Project Structure

```
src/graphroutefinder/
├── Graph.java                    # Main graph data structure
├── Node.java                     # Node representation
├── Edge.java                     # Edge representation
├── BFSAlgorithm.java            # BFS implementation
├── DFSAlgorithm.java            # DFS implementation
├── DijkstraAlgorithm.java       # Dijkstra's algorithm
├── PathResult.java               # Result container
├── TraversalResult.java          # Traversal results
├── BFSResult.java                # BFS specific results
├── GraphRouteFinder.java         # Core logic
├── GraphPanel.java               # GUI rendering
├── GraphUI.java                  # UI components
├── GraphRouteFinderApp.java      # Application entry point
└── SampleGraphTest.java          # Test suite
```

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt access

### Compilation

```bash
javac -d out src/graphroutefinder/*.java
```

### Running the GUI Application

```bash
java -cp out graphroutefinder.GraphRouteFinderApp
```

### Running Sample Tests

```bash
java -cp out graphroutefinder.SampleGraphTest
```

## 📝 Usage Example

### Default Demo
The GUI loads a pre-configured city route graph:

```
Source: Chennai
Destination: Mumbai
```

Then click one of:
- **BFS** - Find path using breadth-first search
- **DFS** - Find path using depth-first search
- **Dijkstra** - Find shortest path with weights
- **Compare Algorithms** - Run all three and compare

### Adding Custom Routes

```
From: Mumbai
To: Ahmedabad
Km: 530
Cost: 1500
Min: 500
```

## � Sample Output & Screenshots

### GUI Application Interface
Add application UI screenshot here:
```
![Graph Route Finder GUI](./screenshots/gui-main.png)
```

### BFS Algorithm Execution
Add BFS result screenshot here:
```
![BFS Traversal Result](./screenshots/bfs-output.png)
```

### Dijkstra's Shortest Path
Add Dijkstra result screenshot here:
```
![Dijkstra Algorithm Result](./screenshots/dijkstra-output.png)
```

### Algorithm Comparison
Add comparison result screenshot here:
```
![Algorithm Comparison](./screenshots/comparison-output.png)
```

**To add screenshots:**
1. Create a `screenshots/` folder in your project root
2. Add your `.png` or `.jpg` files there
3. Uncomment the image links above

## �🔮 Future Improvements

- ✅ **A* Algorithm** - Heuristic-based pathfinding
- ✅ **Bellman-Ford Algorithm** - Handle negative weights
- ✅ **Bidirectional BFS** - Faster pathfinding
- ✅ **Graph Visualization Improvements** - Better node positioning
- ✅ **Real Map Integration** - Use actual geographic data
- ✅ **Algorithm Statistics** - Nodes visited, time taken metrics
- ✅ **Import/Export** - Save and load custom graphs
- ✅ **Performance Benchmarking** - Compare algorithm speeds
- ✅ **Mobile Version** - Android/iOS app
- ✅ **Network Simulation** - Real-world traffic patterns

## 💡 Learning Outcomes

By studying this project, you'll understand:
- ✓ How to implement core graph data structures
- ✓ How BFS and DFS traversals work
- ✓ How Dijkstra's algorithm finds shortest paths
- ✓ How to visualize algorithms with GUI
- ✓ Java Collections Framework usage
- ✓ OOP principles and design patterns
- ✓ Algorithm complexity analysis

## 🎓 Conclusion

Graph Route Finder is a practical demonstration of fundamental graph algorithms and their real-world applications. Whether you're preparing for technical interviews, learning DSA, or building routing systems, this project provides a solid foundation for understanding graph-based problem solving.

The modular design allows for easy extension and modification, making it an excellent learning resource and portfolio project for software engineers and computer science students.

---

**Author**: Dushyanth Reddy VK  
**Repository**: [GitHub - Graph Route Finder](https://github.com/dushyanthreddyvk/graph-route-finder)  
**License**: MIT
