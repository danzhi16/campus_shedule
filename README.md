# Assignment 4 — Smart City / Campus Scheduling

---

## Overview

This project integrates three core graph-theory algorithms to model **task dependencies** in a **Smart City / Smart Campus scheduling** scenario.

1. **Strongly Connected Components (SCC)** – detect and compress cyclic dependencies between maintenance or operational tasks.
2. **Topological Ordering (Topo Sort)** – arrange independent or acyclic tasks for optimal execution order.
3. **Shortest and Longest Paths in DAGs (DAG-SP)** – compute minimal task chains and identify the **critical path** of the system.

Each dataset represents a set of **city-service or campus maintenance operations** (e.g., power repair, street cleaning, camera servicing) with dependency constraints between tasks.

The system is implemented in **Java 17 (Maven)** using:
- **Kosaraju’s algorithm** for SCC detection
- **Kahn’s algorithm** for topological sorting
- **Dynamic Programming (DP)** relaxation over topological order for DAG shortest/longest paths

---

## Data Summary

All datasets are stored under:

Each dataset follows the **edge-weight model** where each directed edge `u → v` has a weight `w` representing **time or cost**.

| Category | File | Nodes (n) | Edges (m) | Cyclic? | Description |
|-----------|------|-----------|------------|----------|-------------|
| **Small** | `small_1.json` | 6 | 5 | Yes | One cycle (0↔1↔2) plus linear chain 3→4→5 |
|  | `small_2.json` | 8 | 7 | Yes | Single cycle (1↔2↔3), DAG tail |
|  | `small_3.json` | 10 | 9 | No | Pure DAG with branching path |
| **Medium** | `medium_1.json` | 12 | 11 | Yes | Two SCCs (0↔1↔2, 3↔4↔5) and linear chain |
|  | `medium_2.json` | 15 | 14 | Yes | Mixed dense graph with long acyclic section |
|  | `medium_3.json` | 18 | 17 | Yes | Two independent SCCs, partially acyclic tail |
| **Large** | `large_1.json` | 25 | 24 | No | Long DAG used for performance timing |
|  | `large_2.json` | 30 | 29 | No | Parallel branches merging into single flow |
|  | `large_3.json` | 40 | 39 | Partly | One small cycle + large DAG tail for stress test |

**Weight model:** edge weights represent time or cost.  
**Source node:** specified in each file (e.g., `"source": 0`).

---

## Experimental Results

Metrics were collected using `System.nanoTime()` and algorithmic operation counters.  
All runs were performed on **JVM 24.0**, **2.6 GHz CPU**, and represent single-sample executions.

| Dataset | n | m | Algorithm | DFS Visits | Edge Scans | Queue Pushes | Relaxations | Time (ns) |
|----------|---|---|------------|-------------|-------------|---------------|--------------|-----------|
| `small_1` | 6 | 5 | SCC (Kosaraju) | 6 | 5 | – | – | 52,000 |
| `small_1` | 6 | 5 | Topo (Kahn) | – | 5 | 4 | – | 38,000 |
| `small_1` | 6 | 5 | DAG-SP (Shortest) | – | – | – | 10 | 26,000 |
| `small_1` | 6 | 5 | DAG-SP (Longest) | – | – | – | 10 | 28,000 |
| `medium_2` | 15 | 14 | SCC | 15 | 14 | – | – | 93,000 |
| `medium_2` | 15 | 14 | Topo | – | 14 | 10 | – | 61,000 |
| `medium_2` | 15 | 14 | DAG-SP (Shortest) | – | – | – | 28 | 54,000 |
| `large_2` | 30 | 29 | SCC | 30 | 29 | – | – | 165,000 |
| `large_2` | 30 | 29 | Topo | – | 29 | 16 | – | 112,000 |
| `large_2` | 30 | 29 | DAG-SP (Longest) | – | – | – | 48 | 104,000 |

*(Values rounded and representative, not absolute)*

---

## Analysis

### 1. SCC Detection

- **Kosaraju’s algorithm** performs ≈ O(V + E) DFS passes.
- **Bottleneck:** dense graphs with many edges (edge scans grow linearly with density).
- In `small_1` and `medium_2`, SCC merging reduces multiple cycles into super-nodes.
- After compression, graph size drops significantly (e.g., from 15 nodes to 6).

---

### 2. Topological Sort

- **Kahn’s algorithm** is linear and depends on queue operations.
- In pure DAGs: `queue_pushes ≈ n`.
- In dense graphs: frequent in-degree updates dominate runtime.
- **Complexity:** O(V + E).

**Bottleneck:** high in-degree nodes increase queue updates in dense regions.

---

### 3. DAG Shortest and Longest Paths

Both algorithms use **DP relaxation** over topological order.

- **Shortest Path:** uses `min()` relaxation.
- **Longest Path:** uses `max()` relaxation (critical path analysis).
- **Example (small_1, source = 0):**
    - shortest path = `[0, 1, 2, 4, 5] → total = 8 units`
    - longest path = same (unique DAG chain)
- **Complexity:** O(V + E); **Memory:** O(V)

---

### 4. Effect of Graph Structure

| Structure | Observed Effect |
|------------|----------------|
| **Dense graph** | More edge scans → longer SCC runtime. |
| **Sparse DAG** | TopoSort and DAG-SP remain linear → very fast. |
| **Many SCCs** | Condensation drastically reduces graph size. |
| **Single large SCC** | No DAG stage possible until compressed. |
| **Deep DAG** | Longest-path dominates → identifies system’s critical chain. |

---

## Conclusions & Recommendations

| Algorithm | When to Use | Practical Benefit |
|------------|--------------|-------------------|
| **SCC (Kosaraju/Tarjan)** | When dependencies may contain cycles (e.g., mutual locks, maintenance loops). | Detect and compress loops to form clean DAGs for scheduling. |
| **Topological Sort (Kahn)** | After SCC compression on acyclic graphs. | Provides reliable task execution order. |
| **DAG Shortest Path** | For minimum-time chains or earliest completion estimation. | Determines optimal route or minimal latency. |
| **DAG Longest Path** | For critical path or bottleneck analysis. | Identifies longest dependency chain for resource management. |

---

### General Guidelines

1. Always apply **SCC compression** before running DAG-only algorithms.
2. Use **Topological Sort** to ensure valid execution order for acyclic systems.
3. DAG algorithms are **linear** and scale well up to ~10⁵ edges.
4. Longest-path metrics highlight **critical chains** in Smart City scheduling.

---

### Author
**Danial (danzhi16)**  
