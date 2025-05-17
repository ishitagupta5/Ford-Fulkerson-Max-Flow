# Ford-Fulkerson Maximum Flow Algorithm

This Java project implements the **Ford-Fulkerson algorithm** to calculate the **maximum flow** in a directed flow network. Input is provided via a CSV file representing the capacity graph, and the algorithm outputs all discovered augmenting paths along with their respective flow values and the total flow.

---

## Features

- Reads network capacity data from a CSV file
- Implements the Ford-Fulkerson method using DFS
- Tracks each augmenting path and its bottleneck capacity
- Outputs the total maximum flow and full trace of the algorithm

---

## What is the Ford-Fulkerson Algorithm?

The **Ford-Fulkerson method** is a greedy algorithm that computes the **maximum flow** between a **source** and **sink** in a flow network. It repeatedly finds augmenting paths (paths with available capacity), and increases the flow along these paths until no more exist.

**Key Concepts**:
- **Augmenting path**: A path from source to sink where every edge has unused capacity
- **Bottleneck capacity**: The smallest residual capacity along a path
- **Residual graph**: Graph showing remaining capacity after each flow push

**How it works**:
1. Start with 0 flow.
2. While there exists an augmenting path:
   - Find the path
   - Add the minimum capacity (bottleneck) to total flow
   - Update residual capacities
3. Repeat until no path remains.

---

## How the Code Works

1. The CSV file contains a capacity matrix for a directed graph.
2. The program:
   - Parses this matrix
   - Applies DFS to search for augmenting paths
   - Logs each path and its associated flow
   - Computes and outputs the total maximum flow

---

## Files

| File | Description |
|------|-------------|
| `FordFulkerson.java` | Java implementation of the Ford-Fulkerson algorithm |
| `deliverable1.csv`, `deliverable2.csv`, etc. | Input CSV files with capacity matrices (provided by professor) |
| `deliverable1.txt`, `deliverable2.txt` | Output logs showing augmenting paths and total flow |

---

## Getting Started

1. Compile and run:
   ```bash
   javac FordFulkerson.java
   java FordFulkerson
