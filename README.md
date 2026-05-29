# Advanced Graph Route Finder

A Java Swing project for graph algorithms and route visualization. It supports dynamic graph building, weighted edges, BFS, DFS, Dijkstra, and algorithm comparison output.

## Compile

```bash
javac -d out src/graphroutefinder/*.java
```

## Run GUI

```bash
java -cp out graphroutefinder.GraphRouteFinderApp
```

## Run Sample Tests

```bash
java -cp out graphroutefinder.SampleGraphTest
```

## Demo Inputs

The GUI loads a city route graph by default.

Try:

```text
Source: Chennai
Destination: Mumbai
```

Then click:

```text
BFS
DFS
Dijkstra
Compare Algorithms
```

Add a custom weighted route:

```text
From: Mumbai
To: Ahmedabad
Km: 530
Cost: 1500
Min: 500
```
