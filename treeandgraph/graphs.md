---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: custom_page
parent: DSA
title: Graphs
---
## Concepts
### [Bipartite graph](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/BiPartiteGraph.java)
Check if all the vertices can be divided into two sets where vertices among the same set do not have any edges, and edges only exist between vertices of distinct sets.

### Floyd Warshall Algorithm
- Finding the shortest distance between every two nodes.
- Initialize the matrix of size $$V * V$$ with diagonal elements as zero and edge weights between the vertices.
- For each intermediate vertex k, and for each combination of vertices i and j, update the value of mat[i][j] if it is greater than $$mat[i][k] + mat[k][j]$$.
- Time complexity is always $$O(V^3)$$. So use this when it is a dense graph where vertices are less than the number of edges.
- You can also use this to find transitive closure matrix where instead of noting down distances in the matrix, you just note down booleans of reachability - yes or no.

### Bellman Ford algorithm
- Iterate through each edge $$V-1$$ times by relaxing the edge at each iteration:
```
dist[v] = dist[u] + wt(u,v), if dist[u] + wt(u,v) < dist[v]
```

- Relaxation means updating the shortest distance to a node if a shorter path is found through another node.
- Even after $$V-1$$ iterations, if the distances change, then there is a negative weight cycle.
- The time complexity is $$O(V*E)$$.
- Prefer this to Dijkstra's when negative weights are present.

### Dijkstra's Algorithm
- Using a priority queue, pick the node with the smallest distance in each iteration.
- Update the distances of its neighbours if the new  distance is smaller, along with the new parent each time.
- After doing that $$V$$ times, we will have the smallest distances to all the nodes, including now to reach them.
- This does **not** work when there are negative weights because it can lead to decrease of distances which are already finalized.

### Johnson's algorithm

### Minimum spanning tree
Tree with the least weights and least number of edges possible to connect all the nodes in a graph. We need $$N-1$$ edges to connect N nodes in a connected graph.

#### Prim's algorithm
- Works with negative weights too.
- Similar to Dijkstra's, pick a starting node and add it the priority queue with weight as 0
- Remove the top node (with the least edge weight)
  - mark it visited
  - add all weigfhts of all the adjacent nodes to the queue
- Every time, update the adjacent node weights in the queue only if the current node is not marked as visted.
- It differs from Dijkstra's that here we update only weights, but there we update distances (by adding weight to existing distance). Since there is no accumulation of weights 

#### Kruskal's algorithm
- Sort all the edges in a non-decreasing order.
- Use a [Disjoint set](#disjoint-sets-union-find)  data structure to hold all vertices in their own set at the starting of the run.
- Pick the smallest edge from the edges sorted list and verify if *union*ing the two vertices of this edge, will form a cycle.
  - When will a cycle form? If both the nodes are already in the same set and now we are adding the edge joining them.
  - That means, we need to check if both parents are same. If they are same already we should skip this edge.
- The sum of all edge weights we added is the MST.
- If the entire graph is not connected, then we would not have gotten $$V-1$$ edges and there would be more than 1 parents are the end.

### Ford Fulkerson algorithm
Maximum flow - min cut algorithm [Ford Fulkerson](https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/)

$$maximum\_flow = minimum\_cut$$

- Build a **residual graph** where each edge `u→v` with capacity `c` also has a reverse edge `v→u` with capacity 0.
- Repeatedly find any augmenting path from source `s` to sink `t` in the residual graph (use BFS for Edmonds-Karp, DFS for basic Ford-Fulkerson).
- Find the bottleneck — the minimum residual capacity along the path. Decrease forward edges and increase reverse edges by that amount.
- Repeat until no augmenting path exists. Total max flow = sum of all bottlenecks.

**Finding actual flow on each edge**: For an original edge `u→v` with capacity `c`, actual flow = `c - residual_capacity(u→v)`. The residual capacity tells you how much is left unused.

**Finding min-cut edges**: After the algorithm terminates, do a BFS/DFS from `s` in the residual graph. Let `S` = set of reachable nodes, `T` = unreachable nodes. Every original edge from a node in `S` to a node in `T` is a min-cut edge — these are the saturated edges blocking further flow.

### Strongly connected components
In directled graphs, these are sub-graphs where each node is reachable from every other node in the both directions.

#### Kosaraju's algorithm
- Do a DFS on all nodes in the graph and stored in a decreasing order of finish time.
- Reverse all edges in the graph. Actually we just need to reverse the edges that are connecting various SCCs of the graph. But we don't know which all are SCCs, so reverse all of them. Nodes in each SCC don't care anyway if the edges are reversed or not.
- Now do DFS again in the reversed graph. You can do reach one SCC from another because edges are reversed.
- How many DFS you do on unvisited nodes represent number of SCCs.
- Each unvisited node you visit $$v_i$$ from a node $$u$$ are all part of the same SCC.

### Cycle detection in graphs

#### Undirected graphs
- **DFS with parent tracking**: During DFS, if a neighbour is already visited and is not the parent of the current node, a cycle exists.
- **BFS with parent tracking**: Same idea — while doing BFS, if a neighbour is already visited and is not the parent, there is a cycle.
- **Union-Find**: Process each edge. If both endpoints already belong to the same set, adding that edge creates a cycle. Otherwise, union them.

#### Directed graphs
- **DFS with three states (white/gray/black)**: Mark nodes as unvisited, in-progress (on the current recursion stack), or done. If you encounter an in-progress node, there is a back edge → cycle. A visited-but-done node is just a cross/forward edge, not a cycle.
- **Kahn's algorithm (BFS-based topological sort)**: Repeatedly remove nodes with in-degree 0. If all nodes are removed, no cycle. If some nodes remain, those nodes are part of a cycle. See [the Kahn's algorithm section.](#using-bfs---kahns-algorithm)
- **DFS + recursion stack (boolean array)**: Maintain a separate `recStack[]` alongside `visited[]`. On entering a node, set both to true. On backtracking, unset `recStack`. If you reach a node where `recStack[node]` is true, there is a cycle.

#### Key differences between undirected and directed
- In undirected graphs, a simple visited check plus parent skip is sufficient because every edge is bidirectional — revisiting a non-parent visited node always means a cycle.
- In directed graphs, visiting an already-visited node does not necessarily mean a cycle. It could be a cross edge or forward edge. You need to distinguish whether the node is still on the current DFS path (in-progress) or already fully processed (done).
- Union-Find works naturally for undirected graphs but does not directly apply to directed cycle detection.
- Kahn's algorithm (in-degree based) only works for directed graphs since in-degree is a directed concept.

#### Finding minimum weight cycle
- In a weighted graph, pick an edge (u->v) and remove it. Now find the min-path distance between u and v. If a cycle exists, you will find a path. Else no. Then do the same for all edges and find the smallest cycle circumference.

#### Finding maximum weight cycle when there is at most one outgoing edge
- Using DFS for all unvisited nodes. This is similar to [Cycle detection](#directed-graphs) except we also track the distance length. We stop the algorithm when a neighbour which is already in the recStack. Cycle length is `dist[node] - dist[already_recStack_neighbour]`.
- Using BFS Kahn's algorithm, you can use the in-degree reduction method, to eliminate all non-cyle nodes. What yuu are left with are one or more cycles. Now calculate the cycle lengths. Starting from each remaining node, keep moving to its neighbour (there's only one neighbour) until you reach the first node. You will get cycle length. There may be multiple cycles if there are multiple connected components in the intial graph. Find the max of all lengths of all cycles.

## Disjoint sets (Union-Find)
Rank of a disjoint set: The height of the tree assuming path compression is not there. Ideally height tree would reduce a lot when you are using path compression, because all the nodes under the main parent will become immediate children of parent node. But we are not updating ranking every time we do this. Even if we don't update ranks every time we do path compression, the efficiency still holds. So we avoid this step to reduce time complexity.

### Problems
1. **Asked in an interview**: [Group nodes into a network and find sum after each network merge](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/NetworkSumUnionFind.java) Use path compression and union by rank to optimize the code.
2. [Couples Holding Hands](https://leetcode.com/problems/couples-holding-hands/description/) Model each bench as an edge between the two couple-numbers sitting there. Each connected component of size `k` needs `k-1` swaps. Answer = total couple-nodes − number of connected components. [Solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/Coupleholdinghands.java). Note that this problem also has a greedy solution.

## Topological sort

### Using DFS
Run DFS from every univisted node, once processing is done for a node, add it to a stack.

### Using BFS - Kahn's algorithm
- Add nodes with in-degree of 0 to the queue.
- Remove the top node, add it a stack or list, update all the in-degrees of its adjacent nodes.
- If any new in-degree 0 nodes are found, add them to the end of the queue.
- Repeat the process until queue is processed.

### Problems
1. [Course schedule two](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/CourseScheduleTwo.java) 
2. [Course Schedule](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/CanFinishCourses.java)
3. [Alien dictionary](https://leetcode.ca/2016-08-25-269-Alien-Dictionary/) The key is to understand that a dictionary problem is nothing but topological sort. 

## Breadth First Search (BFS)
- Used when you want to find the closest path in an unweighted graph.
- We have to use Dijkstra's etc when the graph is weighted.
- Note that for some problems we may use multi-source BFS, where multiple datapoints can be source.
- When we are tracing one layer after the other, we may need to store the parent element of the current element. For example, in a word ladder problem, we may need to store the parent word to trace the path back.
While generally you stop the while loop when you find the element, sometimes you need to further process until the current level is finished (when the desired element is in the next level). This is needed when let's say you are trying to find all shortest paths to desired element, not just one path.
  To achieve this, you need to add next level elements only after current level is processed entirely, i.e. until queue is empty.
- As in [the rotten oranges problem](https://www.geeksforgeeks.org/dsa/minimum-time-required-so-that-all-oranges-become-rotten/), a problem might be solved by both BFS and DFS. But in DFS, a node may have to be visited multiple times, while that is not the case in BFS. So use BFS.


### Problems examples from [chatGPT](https://chatgpt.com/share/690c6924-c07c-8006-9ef8-3c346bcc2bcf)

| Category                                           | Example Problem / Use                                                                                                                                                                           |
| -------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Graph traversal**                                | Visiting all vertices in an undirected or directed graph.                                                                                                                                       |
| **Shortest path in unweighted graphs**             | Find minimum number of edges between two nodes.                                                                                                                                                 |
| **Grid / matrix problems**                         | - Shortest path in a maze<br>- Minimum steps to reach destination<br>- Flood fill (like Paint Bucket tool)<br>- Rotten oranges problem (multi-source BFS)<br>- Knight’s minimum moves in chess. |
| **Tree problems**                                  | - Level order traversal (print nodes level by level)<br>- Find depth or height of tree<br>- Connect nodes at same level.                                                                        |
| **Social network / web crawling**                  | - Find degree of connection (e.g., “friends of friends” up to 3 levels)<br>- Crawling webpages up to N links away.                                                                              |
| **Word or transformation puzzles**                 | - Word Ladder (minimum transformations from “hit” → “cog”)                                                                                                                                      |
| **State space / shortest transformation problems** | - Minimum number of operations to convert one number/state to another (e.g., multiply by 2 or subtract 1 to reach target).                                                                      |
| **AI / Games**                                     | - Find minimum number of moves (like in puzzles: 8-puzzle, sliding tiles, Rubik’s Cube – if branching is small).                                                                                |
| **Network broadcasting**                           | - Find time to spread message through network (multi-source BFS).                                                                                                                               |


###### Problems
1. [Word ladder II](https://leetcode.com/problems/word-ladder-ii/)

## Depth First Search (DFS)
- Finding any valid path: DFS is effective when you need to find any path between two nodes or check if a path exists, especially when the solution might be deep within the graph.
- Puzzle solving: It is well-suited for puzzles with a single solution, such as mazes or Sudoku, because it can systematically explore one path until it reaches a dead end or a solution.
- Cycle detection: DFS can determine if a graph contains any cycles.
- Topological sorting: It is used to find a linear ordering of vertices in a directed acyclic graph (DAG).
- Connected components: You can use DFS to find all the connected components within an undirected graph.
- Backtracking algorithms: DFS is a fundamental part of backtracking algorithms, where a solution is built incrementally, and if a path does not lead to a solution, the algorithm "backtracks" to try a different path.
- Web crawling: A website crawler can use DFS to explore links and go deep into a website's structure before returning to explore other links.
- AI and game theory: It is used in AI for tasks like game-tree search, where the system explores possible moves and counter-moves to find a winning strategy.


###### Problems list
1. [Pacific Atlantic Water Flow](https://leetcode.com/problems/pacific-atlantic-water-flow/discuss/2580074/Java-Solution-using-DFS)