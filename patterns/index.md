---
layout: custom_page
parent: DSA
title: Patterns
---

## Coding Interview Patterns

A consolidated map of the 15 core LeetCode patterns (from [AlgoMaster's 15 LeetCode patterns](https://blog.algomaster.io/p/15-leetcode-patterns)) and the 20 DP sub-patterns (from [AlgoMaster's 20 DP patterns](https://blog.algomaster.io/p/20-patterns-to-master-dynamic-programming)), cross-referenced with problems already covered in this repo.

Each pattern lists:
- **When to use it**
- **Interview importance** — how often it shows up in real interviews
- **Covered here** — problems you've already solved or documented, linked to the code file or the section where they're listed

Importance legend:
- 🔴 **Must-know** — expected in most SDE interviews
- 🟡 **Common** — appears regularly, especially at senior levels
- 🟢 **Occasional** — niche or advanced, worth knowing but lower frequency

---

## Part 1 — 15 Core LeetCode Patterns

### 1. Prefix Sum 🔴
Preprocess an array so any range-sum query becomes O(1). Extends to prefix-XOR, prefix-product, 2D prefix sums, and difference arrays.

**When to use:** multiple range-sum / range-count queries, subarray-sum-equals-K, problems where you can convert "range" to "difference of prefixes".

**Covered here:**
- [Corporate Flight Bookings]({{ site.code_path }}problems/miscellaneous/CorpFlightBookings.java) — diff-array (companion of prefix sum)
- [XOR Sequence]({{ site.code_path }}problems/miscellaneous/XorSequence.java) — prefix XOR
- [Largest Magic Square]({{ site.code_path }}problems/miscellaneous/LargestMagicSquare.java) — 2D prefix sums
- Concept note in [main DSA page]({{ site.baseurl }}/#prefix-sum)

---

### 2. Two Pointers 🔴
Two indices moving toward each other, in the same direction, or at different speeds through a (usually sorted) array or two arrays.

**When to use:** pair/triplet with target sum, palindrome checks, partitioning, merging sorted inputs, in-place array rewrites.

**Covered here:**
- [3Sum]({{ site.code_path }}problems/miscellaneous/ThreeSum.java)
- [Next Permutation]({{ site.code_path }}problems/miscellaneous/NextPermutationTwo.java)
- [Trapping Rain Water]({{ site.code_path }}problems/miscellaneous/TrappingRainWater.java)
- [Dutch National Flag (3-way partition)]({{ site.baseurl }}/#dutch-national-flag-algorithm)
- [Maximum Score of a Good Subarray — greedy two-pointer]({{ site.code_path }}problems/miscellaneous/MaximumScoreTwoPointers.java)
- See the [Two Pointers section]({{ site.baseurl }}/#two-pointers) on the main DSA page for more.

---

### 3. Sliding Window 🔴
Maintain a window over a contiguous subarray/substring; expand and contract as you scan.

**When to use:** longest/shortest substring with a constraint, fixed-size window aggregates, counting subarrays that satisfy a condition.

**Covered here:**
- [Sliding Window Maximum]({{ site.code_path }}problems/miscellaneous/MaxSlidingWindow.java) — also a monotonic deque
- [Longest Substring Without Repeating Characters]({{ site.code_path }}problems/miscellaneous/LongestSubstringWithoutDupl.java)
- [Longest Repeating Character Replacement]({{ site.code_path }}problems/miscellaneous/LongestRepeatingCharReplacement.java)
- [Longest Substring with At Least K Repeating Characters]({{ site.code_path }}problems/miscellaneous/LongestSubstring.java) — sliding window + D&C
- [Number of Sub-arrays of Size K With Average ≥ Threshold]({{ site.code_path }}problems/miscellaneous/NumOfSubarrays.java)
- [Minimum Consecutive Cards to Pick Up]({{ site.code_path }}problems/miscellaneous/MinimumConsecutiveCardsToPickUp.java)
- Minimum Window Substring and Minimum Size Subarray Sum — see [Two Pointers section]({{ site.baseurl }}/#two-pointers)

---

### 4. Fast & Slow Pointers (Floyd's Tortoise and Hare) 🟡
Two pointers at different speeds to detect cycles, find middles, or locate cycle starts in linked lists and implicit graphs.

**When to use:** linked-list cycle detection, finding the duplicate number in `[1..n]`, Happy Number, finding the middle node.

**Covered here:**
- Listed as **todo** under [Floyd's tortoise and hare algorithm]({{ site.baseurl }}/#floyds-tortoise-and-hare-algorithm-for-cycle-detection) — not yet solved. Good gap to fill: LeetCode 141, 202, 287.

---

### 5. LinkedList In-place Reversal 🟡
Reverse all or part of a linked list by re-wiring `next` pointers without extra memory.

**When to use:** reverse a list / sublist, swap adjacent pairs, reverse in groups of K, palindrome linked list check.

**Covered here:**
- [Swap Nodes in Pairs]({{ site.code_path }}problems/miscellaneous/SwapNodesInPairs.java)
- [Rotate List]({{ site.code_path }}problems/miscellaneous/RotateList.java)
- [Copy List with Random Pointer]({{ site.code_path }}problems/miscellaneous/CopyListwithRandomPointer.java) — pointer gymnastics, related idiom

---

### 6. Monotonic Stack 🔴
Stack whose elements stay in increasing or decreasing order; pop whenever the invariant would break.

**When to use:** next/previous greater or smaller element, largest rectangle in histogram, stock span, building subsequences with greedy constraints.

**Covered here:**
- [Largest Rectangle in Histogram]({{ site.code_path }}problems/miscellaneous/LargestHistogram.java)
- [Most Competitive Subsequence]({{ site.code_path }}problems/miscellaneous/MostCompetitive.java)
- [Remove Duplicate Letters]({{ site.code_path }}problems/miscellaneous/RemoveDuplicateLetters.java)
- [Shortest Unsorted Continuous Subarray]({{ site.code_path }}problems/miscellaneous/ShortestUnsortedContinuousSubarray.java) / [v2]({{ site.code_path }}problems/miscellaneous/ShortestUnsortedContinuousSubarray2.java)
- [Maximum Score of a Good Subarray]({{ site.code_path }}problems/miscellaneous/MaximumScore.java) — histogram-style
- [Trapping Rain Water]({{ site.code_path }}problems/miscellaneous/TrappingRainWater.java) — also solvable with monotonic stack
- Remove K Digits — see [Monotonic queue section]({{ site.baseurl }}/#monotonic-queue)

---

### 7. Top 'K' Elements 🔴
Use a heap of size K (min-heap for top-K largest, max-heap for top-K smallest), or quickselect.

**When to use:** K largest/smallest, K most frequent, K closest points, running median (two heaps).

**Covered here:**
- [Top K Frequent Elements]({{ site.code_path }}problems/miscellaneous/TopKFrequentElements.java)
- [IPO]({{ site.code_path }}problems/miscellaneous/IPO.java)
- [Maximum Jobs in Non-consecutive Towns]({{ site.code_path }}problems/miscellaneous/MaximumNonCons.java)
- [Skyline Problem]({{ site.code_path }}problems/miscellaneous/SkylineProblem.java) — heap + events
- Median of a Data Stream — see [Heap section]({{ site.baseurl }}/#heap---priority-queue) (two heaps)

---

### 8. Overlapping Intervals 🔴
Sort by start time, then sweep, merging or counting overlaps.

**When to use:** merge intervals, insert interval, non-overlapping intervals, meeting rooms, calendar bookings.

**Covered here:**
- [My Calendar]({{ site.code_path }}problems/miscellaneous/MyCalendar.java)
- [My Calendar II]({{ site.code_path }}problems/miscellaneous/MyCalendarTwo.java) — also [published solution](https://leetcode.com/problems/my-calendar-ii/solutions/7530045/using-two-treemaps-and-merging-overlappi-q3cq/)
- [Skyline Problem]({{ site.code_path }}problems/miscellaneous/SkylineProblem.java) — interval sweep line

---

### 9. Modified Binary Search 🔴
Binary search on rotated arrays, 2D matrices, or — most importantly — the *answer space* of an optimization problem.

**When to use:** search in rotated sorted array, find peak element, "minimum value such that condition holds" problems, square root, capacity-to-ship-packages style.

**Covered here:**
- [Count Rectangles Containing Point]({{ site.code_path }}problems/binarysearch/CountRectanglesContainingPoint.java)
- [Split Array Largest Sum]({{ site.code_path }}problems/dp/SplitArrayLargestSum.java) — binary search over answer
- [Binary Search helper]({{ site.code_path }}binarysearch/BinarySearch.java)
- Heaters, BS-over-solution-space notes — see [Binary search section]({{ site.baseurl }}/#binary-search)

---

### 10. Binary Tree Traversal 🔴
Preorder / inorder / postorder / level-order, plus Morris traversal for O(1) space.

**When to use:** any problem touching a binary tree or BST. Inorder on BST yields sorted order (k-th smallest). Postorder is natural for "compute from children up" problems.

**Covered here:**
- [Recover Binary Search Tree]({{ site.code_path }}problems/miscellaneous/RecoverBinarySearchTree.cpp) — inorder
- [Sum Root to Leaf Numbers]({{ site.code_path }}problems/miscellaneous/SumRootLeafNumbers.java) — preorder / DFS
- [Path Sum II]({{ site.code_path }}problems/backtracking/PathSumII.java) — DFS with backtracking
- [House Robber III]({{ site.code_path }}problems/miscellaneous/HouseRobberThree.java) — postorder DP on tree

---

### 11. Depth-First Search (DFS) 🔴
Explore as deep as possible before backtracking. Core tool for trees, graphs, and implicit search spaces.

**When to use:** connected components, path existence, tree problems, cycle detection, topological sort, island problems, generating all configurations.

**Covered here:**
- [Pacific Atlantic Water Flow]({{ site.baseurl }}/treeandgraph/graphs.html#problems-list) — reverse DFS from oceans
- [Bipartite Graph]({{ site.code_path }}problems/miscellaneous/BiPartiteGraph.java)
- [Can Finish Courses]({{ site.code_path }}problems/miscellaneous/CanFinishCourses.java) — cycle detection
- [Longest Increasing Path in a Matrix]({{ site.code_path }}problems/miscellaneous/LongestIncreasingPath.java) — DFS + memo
- [Word Search]({{ site.code_path }}problems/miscellaneous/WordSearch.java)
- Cycle detection & SCC (Kosaraju's) — see [Graphs page]({{ site.baseurl }}/treeandgraph/graphs.html)

---

### 12. Breadth-First Search (BFS) 🔴
Level-by-level traversal with a queue. Default for shortest path in unweighted graphs.

**When to use:** shortest path in unweighted graph, level-order traversal, multi-source BFS (rotten oranges), word ladder, minimum-moves puzzles.

**Covered here:**
- [Word Ladder II]({{ site.code_path }}problems/backtracking/WordLadderII.java) — BFS to build parents + DFS for paths
- [Course Schedule II]({{ site.code_path }}problems/miscellaneous/CourseScheduleTwo.java) — Kahn's algorithm
- [Can Finish Courses]({{ site.code_path }}problems/miscellaneous/CanFinishCourses.java)
- BFS concept + problems list — see [BFS section]({{ site.baseurl }}/treeandgraph/graphs.html#breadth-first-search-bfs)

---

### 13. Matrix Traversal 🔴
DFS/BFS on a 2D grid with 4- or 8-directional moves. Often combined with flood fill, island counting, or shortest path.

**When to use:** flood fill, number of islands, shortest path in a grid/maze, surrounded regions, matrix island/lake problems.

**Covered here:**
- [Maximal Square]({{ site.code_path }}problems/dp/MaximalSquare.java)
- [Longest Increasing Path in a Matrix]({{ site.code_path }}problems/miscellaneous/LongestIncreasingPath.java)
- [Word Search]({{ site.code_path }}problems/miscellaneous/WordSearch.java)
- [Word Search II]({{ site.code_path }}problems/backtracking/WordSearchII.java) / [new]({{ site.code_path }}problems/backtracking/WordSearchIINew.java) — trie + matrix DFS
- [Largest Magic Square]({{ site.code_path }}problems/miscellaneous/LargestMagicSquare.java)
- [Count Lattice Points Inside a Circle]({{ site.code_path }}problems/miscellaneous/CountLatticePoints.java)
- [Rat in a Maze]({{ site.code_path }}javapractice/RatMaze.java)

---

### 14. Backtracking 🔴
Recursive brute-force with pruning: choose → explore → un-choose. Exponential in general, so prune aggressively.

**When to use:** permutations, combinations, subsets, N-Queens, Sudoku, word search, partitioning, palindrome partitioning, generating valid parentheses.

**Covered here:**
- [N-Queens]({{ site.code_path }}problems/miscellaneous/SolveNQueens.java)
- [N-Queens II]({{ site.code_path }}problems/backtracking/NQueensII.java)
- [Permutations]({{ site.code_path }}problems/miscellaneous/Permutations.java) / [II]({{ site.code_path }}problems/miscellaneous/Permutationtwo.java)
- [Subset Sums]({{ site.code_path }}problems/miscellaneous/SubsetSums.java)
- [Word Search II]({{ site.code_path }}problems/backtracking/WordSearchII.java) — backtracking + Trie
- [Word Ladder II]({{ site.code_path }}problems/backtracking/WordLadderII.java)
- [Path Sum II]({{ site.code_path }}problems/backtracking/PathSumII.java)
- [Smallest Number From DI String]({{ site.code_path }}problems/backtracking/SmallestNumberFromDIString.java)
- [Password Cracker]({{ site.code_path }}problems/miscellaneous/PasswordCracker.java)
- [Palindrome Partitioning]({{ site.code_path }}problems/dp/PalindromePartitioning.java) / [Jan 2025]({{ site.code_path }}problems/dp/PalindromePartitioningJan2025.java)

---

### 15. Dynamic Programming 🔴
Overlapping subproblems + optimal substructure. See [Part 2](#part-2--20-dp-sub-patterns) below for all the DP sub-patterns.

---

## Part 2 — 20 DP Sub-Patterns

All DP problems on this site live on the [Dynamic Programming page]({{ site.baseurl }}/dp/). Below is the sub-pattern view.

### DP.1 Fibonacci Sequence 🔴
`F(n) = F(n-1) + F(n-2)`. Classic 1D DP with two-variable rolling state.

**Typical problems:** Climbing Stairs, Min Cost Climbing Stairs, House Robber.

**Covered here:** None directly as a standalone solution file, though many DP problems use this skeleton. Good gap to fill.

---

### DP.2 Kadane's Algorithm 🔴
Maximum contiguous subarray sum in O(n). Track `currentMax = max(nums[i], currentMax + nums[i])`.

**Covered here:**
- [Maximum Product Subarray]({{ site.code_path }}problems/miscellaneous/MaxProductSubArray.java) — Kadane variant with min/max tracking
- Kadane concept + walkthrough — see [Kadane's algorithm]({{ site.baseurl }}/#kadanes-algorithm)

---

### DP.3 0/1 Knapsack 🔴
Include or exclude each item, with a weight/capacity constraint. State = `(index, remaining_capacity)`.

**Covered here:**
- [Partition Equal Subset Sum]({{ site.code_path }}problems/dp/EqualPartition.java) — classic reduction to 0/1 knapsack
- [Maximum Profit in Job Scheduling (DP)]({{ site.code_path }}problems/dp/MaximumProfitInJobSchedulingDp.java) — knapsack-style after sorting by end time
- [Maximum Profit in Job Scheduling (recursive)]({{ site.code_path }}problems/dp/MaximumProfitInJobScheduling.java)
- [Minimum Operations to Achieve At Least K Peaks]({{ site.code_path }}problems/miscellaneous/MinOperationsKPeaksKnapsack.java) — pick/skip on circular array; `solve(i+2, peaks-1)` on pick (jump by 2 for non-adjacency) is the only difference from classic 0/1 knapsack. See [DP page]({{ site.baseurl }}/dp/#minimum-operations-to-achieve-at-least-k-peaks) for full write-up.
- [Maximum Sum Alternating Subsequence with Distance K]({{ site.code_path }}problems/dp/MaximumSumAlternatingWithDistK.java) — state `(i, prevWasSmall)`, pick any j ≤ i-k with alternating constraint. O(n²k) memoization. [O(n log n) BIT-optimized version]({{ site.code_path }}problems/dp/MaximumSumAlternatingWithDistKOptimized.java) uses prefix/suffix max BITs indexed by value with a lag-by-k insertion window.
- Problem notes — see [DP page]({{ site.baseurl }}/dp/)

---

### DP.4 Unbounded Knapsack 🟡
Like 0/1 knapsack but each item can be picked any number of times. Transition stays in the same item rather than advancing the index.

**Covered here:**
- [Perfect Squares]({{ site.code_path }}problems/miscellaneous/PerfectSquares.java) — coin-change style
- [Unlimited quantities knapsack code template]({{ site.baseurl }}/dp/#unlimited-quantities-knapsack) on DP page

---

### DP.5 Longest Common Subsequence (LCS) 🔴
2D DP on two strings. Base for edit distance, SCS, delete ops for two strings.

**Covered here:**
- [Shortest Common Supersequence reconstruction]({{ site.baseurl }}/dp/) — LCS-based construction
- [Minimum Delete Operations]({{ site.code_path }}problems/miscellaneous/MinimumDeleteOperations.java)

---

### DP.6 Longest Increasing Subsequence (LIS) 🔴
O(n²) DP or O(n log n) with patience-sorting / binary search on `tails`.

**Covered here:**
- [LIS — O(n²) DP]({{ site.code_path }}problems/dp/LIS.java)
- [LIS — alternate DP]({{ site.code_path }}problems/dp/LISDP.java)
- Russian Doll Envelopes (sort + LIS) — see [DP page]({{ site.baseurl }}/dp/)
- Largest Divisible Subset (sort + LIS-on-divisibility) — see [DP page]({{ site.baseurl }}/dp/)

---

### DP.7 Palindromic Subsequence 🟡
Interval DP on a string. `dp[i][j]` = answer for substring `s[i..j]`.

**Covered here:**
- [Longest Palindromic Substring]({{ site.code_path }}problems/miscellaneous/LongestPalindromicSubstring.java) — substring variant
- [Palindrome Partitioning]({{ site.code_path }}problems/dp/PalindromePartitioning.java)
- [Palindrome Partitioning — Jan 2025]({{ site.code_path }}problems/dp/PalindromePartitioningJan2025.java)
- Minimum insertions to make string palindrome (LPS-based) — see [DP page]({{ site.baseurl }}/dp/)

---

### DP.8 Edit Distance 🔴
2D DP on two strings with insert/delete/replace transitions. Variants: min-cost, ASCII-weighted, wildcard matching, regex matching.

**Covered here:**
- [Wildcard Matching]({{ site.code_path }}problems/dp/WildcardMatching.java)
- Edit Distance listed on [DP page]({{ site.baseurl }}/dp/)
- [Minimum Delete Operations]({{ site.code_path }}problems/miscellaneous/MinimumDeleteOperations.java) — delete-only edit distance
- [Scramble String]({{ site.code_path }}problems/miscellaneous/ScrambleString.java) — related string DP

---

### DP.9 Subset Sum 🔴
Close cousin of 0/1 knapsack. "Does any subset sum to target?" or "how many subsets sum to target?".

**Covered here:**
- [Equal Partition]({{ site.code_path }}problems/dp/EqualPartition.java) — subset sum = total/2
- [Subset Sums]({{ site.code_path }}problems/miscellaneous/SubsetSums.java) — enumeration variant
- [1D-space DP template]({{ site.baseurl }}/dp/#1d-space-dp-solution) on DP page

---

### DP.10 String Partition 🟡
Break a string at decision points; DP over prefix lengths or intervals.

**Covered here:**
- [Word Break]({{ site.code_path }}problems/miscellaneous/WordBreak.java)
- [Word Break II]({{ site.code_path }}problems/miscellaneous/WordBreakII.java)
- [String Partitioner]({{ site.code_path }}problems/dp/StringPartitioner.java)
- [Palindrome Partitioning]({{ site.code_path }}problems/dp/PalindromePartitioning.java) / [Jan 2025]({{ site.code_path }}problems/dp/PalindromePartitioningJan2025.java)
- [Password Cracker]({{ site.code_path }}problems/miscellaneous/PasswordCracker.java) — dictionary-based string partition
- [Longest Valid Parentheses]({{ site.code_path }}problems/dp/LongestValidParentheses.java)

---

### DP.11 Catalan Numbers 🟢
Counting recursive structures — unique BSTs of size n, valid parentheses, polygon triangulations.

**Covered here:** None directly. Good gap: Unique Binary Search Trees (LC 96), Generate Parentheses (LC 22).

---

### DP.12 Matrix Chain Multiplication (Interval DP) 🟡
Pick a split point `k` in interval `[i..j]`; combine subinterval answers. Generalizes to triangulation, burst balloons, merge stones.

**Covered here:**
- [Minimum Score Triangulation of Polygon]({{ site.code_path }}problems/dp/MinScoreTriangulation.java)
- Burst Balloons — detailed write-up on [DP page]({{ site.baseurl }}/dp/)
- Matrix Chain Multiplication notes — see [DP page]({{ site.baseurl }}/dp/)

---

### DP.13 Count Distinct Ways 🟡
Count the number of ways to reach a target state. Additive transitions instead of max/min.

**Covered here:**
- [Integers with No Consecutive Ones]({{ site.code_path }}problems/miscellaneous/IntNoConsOnes.java)
- [Count Indices with Opposite Parity]({{ site.code_path }}problems/CountIndicesWithOppositeParity.java)
- Good gap to fill: Decode Ways (LC 91).

---

### DP.14 DP on Grids 🔴
2D DP where `dp[i][j]` depends on neighbors (up/left/diagonal).

**Covered here:**
- [Maximal Square]({{ site.code_path }}problems/dp/MaximalSquare.java)
- Maximal Rectangle — histogram reduction — see [DP page]({{ site.baseurl }}/dp/)
- [Longest Increasing Path in a Matrix]({{ site.code_path }}problems/miscellaneous/LongestIncreasingPath.java) — DFS + memo, same family
- [Largest Magic Square]({{ site.code_path }}problems/miscellaneous/LargestMagicSquare.java)

---

### DP.15 DP on Trees 🟡
Postorder DP: each node aggregates answers from its children. Return a tuple / struct when you need multiple states.

**Covered here:**
- [House Robber III]({{ site.code_path }}problems/miscellaneous/HouseRobberThree.java) — return `(rob, notRob)` from each node

---

### DP.16 DP on Graphs 🟡
DP layered over a graph — often shortest-path variants with K hops, DAG longest path, or state-on-node DP.

**Covered here:**
- [Minimum Cost to Reach Destination in Time]({{ site.code_path }}problems/miscellaneous/MinCostToDestInTime.java) — Cheapest-Flights-Within-K-Stops family
- Dijkstra / Bellman-Ford / Floyd-Warshall — see [Graphs page]({{ site.baseurl }}/treeandgraph/graphs.html)

---

### DP.17 Digit DP 🟢
Count numbers ≤ N satisfying a digit constraint by DP over `(position, tight, leading_zero, state)`. Almost never in a standard SDE interview but appears in competitive programming.

**Covered here:** None. Only pursue this if competitive programming is on your radar.

---

### DP.18 Bitmasking DP 🟢
Use an integer's bits to encode a subset of a small set (n ≤ ~20). State = `(mask, ...)`.

**Covered here:** None directly. Good gap for harder interviews: Shortest Path Visiting All Nodes (LC 847), Partition to K Equal Sum Subsets (LC 698).

---

### DP.19 Probability DP 🟢
Expected value / probability over states. Rare in interviews.

**Covered here:** None. Skip unless targeting quant / advanced roles.

---

### DP.20 State Machine DP 🟡
Model the problem as a state machine; DP over `(index, state)`. The buy/sell stock family is the canonical example.

**Covered here:**
- Best Time to Buy and Sell Stock IV — write-up on [DP page]({{ site.baseurl }}/dp/)
- Good gap to fill: Best Time to Buy and Sell Stock with Cooldown (LC 309) — direct state-machine formulation.

---

## Part 3 — Additional Patterns Covered in This Repo (Beyond the 15)

These patterns aren't in AlgoMaster's core 15, but you've invested time in them here and they do appear in interviews.

### Union-Find (Disjoint Set) 🔴
Near-O(1) union + find with path compression and union by rank.

**Covered here:**
- [Network Sum via Union-Find]({{ site.code_path }}problems/miscellaneous/NetworkSumUnionFind.java) / [optimized]({{ site.code_path }}problems/miscellaneous/NetworkSumUnionFindGood.java)
- [Couples Holding Hands]({{ site.code_path }}problems/miscellaneous/Coupleholdinghands.java)
- Concept + Kruskal's usage — see [Disjoint sets]({{ site.baseurl }}/treeandgraph/graphs.html#disjoint-sets-union-find)
- [Standalone Graph / Union-Find demo]({{ site.code_path }}union-find/Graph.java)

---

### Topological Sort 🔴
DFS postorder or Kahn's BFS on a DAG. Foundation for scheduling/dependency problems.

**Covered here:**
- [Course Schedule]({{ site.code_path }}problems/miscellaneous/CanFinishCourses.java)
- [Course Schedule II]({{ site.code_path }}problems/miscellaneous/CourseScheduleTwo.java)
- [Alien Dictionary]({{ site.code_path }}problems/Alien_Dictionary.java)
- Concept — see [Topological sort]({{ site.baseurl }}/treeandgraph/graphs.html#topological-sort)

---

### Greedy 🔴
Locally-optimal choices that provably lead to a global optimum. Often paired with sorting or a heap.

**When greedy fails:** if picking the locally cheapest item can block multiple other cheap items and force an expensive choice, greedy gives the wrong answer. Classic signal: non-adjacent selection with a cost constraint (e.g. K Peaks below). Use DP instead.

**Covered here:**
- [Jump Game]({{ site.code_path }}problems/miscellaneous/JumpGame.java) / [Jump Game II]({{ site.code_path }}problems/miscellaneous/JumpGame2.java)
- [Candy]({{ site.code_path }}problems/miscellaneous/Candy.java)
- [Gas Station]({{ site.code_path }}problems/miscellaneous/GasStation.java)
- [Largest Multiple of Three]({{ site.code_path }}problems/miscellaneous/LargestMultipleOfThree.java)
- [Largest Number (custom comparator)]({{ site.code_path }}problems/miscellaneous/LargestNumber.java)
- [Maximum Score of a Good Subarray (greedy two-pointer)]({{ site.code_path }}problems/miscellaneous/MaximumScoreTwoPointers.java)
- [Create Maximum Number from Two Arrays]({{ site.baseurl }}/#monotonic-queue) — greedy + monotonic stack
- **Greedy fails example:** [Minimum Operations to Achieve At Least K Peaks]({{ site.baseurl }}/dp/#minimum-operations-to-achieve-at-least-k-peaks) — sorting by cost and picking cheapest non-adjacent peaks doesn't work; requires DP
- More examples — see [Greedy section]({{ site.baseurl }}/#greedy)

---

### Weighted-Graph Shortest Paths 🔴
Dijkstra (non-negative), Bellman-Ford (handles negatives), Floyd-Warshall (all pairs). Core interview material for senior roles.

**Covered here:**
- [Minimum Cost to Reach Destination in Time]({{ site.code_path }}problems/miscellaneous/MinCostToDestInTime.java) — Dijkstra variant
- Concept write-ups for Dijkstra, Bellman-Ford, Floyd-Warshall — see [Graphs page]({{ site.baseurl }}/treeandgraph/graphs.html)

---

### Minimum Spanning Tree (Prim / Kruskal) 🟡
Prim's (heap from a starting node) and Kruskal's (sort edges + union-find).

**Covered here:**
- Concept — see [MST section]({{ site.baseurl }}/treeandgraph/graphs.html#minimum-spanning-tree)

---

### Network Flow (Ford-Fulkerson / Edmonds-Karp) 🟢
Max-flow = min-cut. Very occasional at senior/specialist interviews.

**Covered here:**
- Concept — see [Ford-Fulkerson]({{ site.baseurl }}/treeandgraph/graphs.html#ford-fulkerson-algorithm)

---

### Strongly Connected Components (Kosaraju's) 🟢
Two DFS passes; second on the transpose.

**Covered here:**
- Concept — see [SCC]({{ site.baseurl }}/treeandgraph/graphs.html#strongly-connected-components)

---

### Bit Manipulation 🟡
XOR tricks, bit counting, and bucket-counting for "find the unique element" problems.

**Covered here:**
- [XOR Sequence]({{ site.code_path }}problems/miscellaneous/XorSequence.java)
- [Swap using XOR]({{ site.code_path }}javapractice/SwapXor.java)
- Concept + Single Number notes — see [Bit manipulation section]({{ site.baseurl }}/#bit-manipulation)

---

### Hashing 🔴
HashMap / HashSet for O(1) lookups; key design is choosing the right key.

**Covered here:**
- [Group Anagrams]({{ site.code_path }}problems/miscellaneous/GroupAnagrams.java)
- [Longest Consecutive Sequence]({{ site.code_path }}problems/miscellaneous/LongestConsecutive.java) / [v2]({{ site.code_path }}problems/miscellaneous/LongestConsecutiveTwo.java)
- [Max Points on a Line]({{ site.code_path }}problems/MaxPointsOnLine.java)
- [Max Pair With Equal Digits]({{ site.code_path }}problems/miscellaneous/MaxPairWithEqualDigits.java)

---

### Divide & Conquer 🟡
Split, solve subproblems, combine. Merge sort is the cleanest example.

**Covered here:**
- [Merge Sort]({{ site.code_path }}sorting/MergeSort.java)
- [Quick Sort]({{ site.code_path }}sorting/QuickSort.java)
- [Count of Smaller Numbers After Self (merge-sort variant)]({{ site.code_path }}problems/miscellaneous/CountNumbersSmallerAfterSelf.java)
- [Longest Substring With At Least K Repeating Characters]({{ site.code_path }}problems/miscellaneous/LongestSubstring.java) — D&C approach

---

### In-place Array Manipulation 🟡
Use sign flipping, swapping into sorted positions, or value-as-index to solve in O(1) extra space.

**Covered here:**
- [First Missing Positive]({{ site.code_path }}problems/miscellaneous/FirstMissingPositive.java)
- [Larry's Array]({{ site.code_path }}problems/miscellaneous/LarrysArray.java)

---

### Trie 🟡
Prefix tree. Pair with DFS/backtracking on grids for multi-word searches.

**Covered here:**
- [Word Search II]({{ site.code_path }}problems/backtracking/WordSearchII.java) / [v2]({{ site.code_path }}problems/backtracking/WordSearchIINew.java)
- Concept mention on [main DSA page]({{ site.baseurl }}/#trie)

---

### Segment Tree / BST-based Range Structures 🟡
`TreeMap` for floor/ceiling queries; segment trees / BITs for range sum/min/max updates.

**Covered here:**
- [My Calendar II]({{ site.code_path }}problems/miscellaneous/MyCalendarTwo.java) — two TreeMaps
- Note on segment trees — see [Segment tree section]({{ site.baseurl }}/#segment-tree-and-binary-search-tree-variants)

---

## Interview-Prep Priority Summary

If you had to study only a handful, these 10 pay back the most time:

1. Two Pointers
2. Sliding Window
3. Binary Search (standard + modified + on answer space)
4. BFS / DFS on graphs and grids
5. Backtracking
6. Monotonic Stack
7. Heap / Top-K
8. Union-Find
9. Topological Sort
10. DP — Kadane, 0/1 Knapsack, LIS, LCS/Edit Distance, DP on Grids, State Machine DP

### Noticeable gaps in this repo
Worth adding dedicated practice for these — they're high-frequency and currently thin or missing:

- **Fast & Slow Pointers** — not solved yet (Floyd's cycle detection noted as todo).
- **Fibonacci / Climbing Stairs–style DP** — no standalone file, though the concept shows up implicitly.
- **Catalan Numbers** — not covered (Generate Parentheses, Unique BSTs).
- **Count Distinct Ways** — Decode Ways would be a clean addition.
- **State Machine DP** — Buy/Sell Stock with Cooldown is the canonical problem.
- **Bitmasking DP** — Shortest Path Visiting All Nodes, Partition to K Equal Sum Subsets.

### Niche / lower priority
- Digit DP, Probability DP, Ford-Fulkerson, Kosaraju's SCC. Study these only if time permits or targeting a specific domain.
