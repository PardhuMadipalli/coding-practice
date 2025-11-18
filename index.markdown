---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: home
title: Home
nav_order: 1
---
{:toc}
**Table ends here**
<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [Search](#search)
- [Sorting](#sorting)
  - [Insertion sort](#insertion-sort)
  - [Bubble sort](#bubble-sort)
  - [Merge sort](#merge-sort)
  - [Quick sort](#quick-sort)
- [Data structures](#data-structures)
  - [Binary Heap (min-heap)](#binary-heap-min-heap)
    - [Insertion](#insertion)
    - [Building](#building)
    - [Deletion](#deletion)
  - [Binary Search Tree](#binary-search-tree)
    - [Insertion](#insertion-1)
    - [Deletion](#deletion-1)
    - [Trie](#trie)
  - [Graphs](#graphs)
    - [Graph vs Tree](#graph-vs-tree)
- [Mathematical concepts](#mathematical-concepts)
- [Concepts and problems list](#concepts-and-problems-list)
    - [Divide and Conquer](#divide-and-conquer)
    - [Monotonic queue](#monotonic-queue)
    - [Binary search](#binary-search)
      - [Binary search over solution space](#binary-search-over-solution-space)
    - [Two pointers](#two-pointers)
    - [Diff array technique](#diff-array-technique)
    - [Dynamic Programming (DP)](#dynamic-programming-dp)
    - [Using original indexes array](#using-original-indexes-array)
    - [Bit manipulation](#bit-manipulation)
    - [Hashing](#hashing)
    - [Sorting](#sorting-1)
    - [Recursion](#recursion)
    - [Binary Tree](#binary-tree)
    - [Heap - Priority Queue](#heap---priority-queue)
    - [Sliding window](#sliding-window)
    - [No or constant extra space](#no-or-constant-extra-space)
    - [Graphs](#graphs-1)
      - [Union Find](#union-find)
      - [Topological sort](#topological-sort)
      - [Breadth First Search (BFS)](#breadth-first-search-bfs)
        - [Problems examples from chatGPT](#problems-examples-from-chatgpt)
        - [Problems](#problems)
      - [Depth First Search (DFS)](#depth-first-search-dfs)
        - [Problems list](#problems-list)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Search
- Binary search
  - [binary Helper codes](https://github.com/PardhuMadipalli/coding-practice/blob/main/binarysearch)
- Linear search

## Sorting

### Insertion sort
- Time complexity O(n<sup>2</sup>)

### Bubble sort
- Time complexity O(n<sup>2</sup>)

### Merge sort
- Time complexity is always `O(nlog(n))`, for all three cases worst, average and best cases. So, when you don't know about the input use merge sort.
- When using arrays
    - we need additional heap space of O(n) to keep the merged array.
    - A disadvantage is that it is not an in-place sort.
- When using linked list
    - no need for the additional heap space. We only need stack space to keep recursion information. So memory required is only `O(log(n))`.
    - This is an in-place sort, because we only need to modify next pointers of the nodes.

- [Code for merge sort]({{ site.code_path }}sorting/MergeSort.java)
- [Code for merge sort with linked list](https://www.geeksforgeeks.org/dsa/merge-sort-for-linked-list/)
  - In this code, the author used _recursion_ intelligently in the merge function.

### Quick sort
- Time complexity
  - Best: O(n*log(n))
  - Average: O(n*log(n))
  - Worst: O(n<sup>2</sup>), when we choose the partition in such a way that the partition ends up at the beginning or at ending of the array.
- Quick sort is not stable.
- This is in-place and is faster when good partition strategies are used.

[Code for quick sort]({{ site.code_path }}sorting/QuickSort.java)

## Data structures

### Binary Heap (min-heap)

- Use heap when you quickly want to find the smallest (or biggest) element in a collection.
- This is always a complete binary tree. So it can be represented easily in an array.
- Children of node at *i* will be at *2i-2* and *2i+2*. Parent of node *i* will be at *(i-1)/2*.
- In a min-heap, a parent node will always be less than or equal to their children.

#### Insertion
- First insert it at then end of the heap.
- See if it still follows the heap property, i.e compare it's value with the parent and swap if needed.
- If you had to swap, then repeat the previous step till you don't need to swap or reached the root.
- Time complexity: O(log n) = O(height)

#### Building
- Initialize and keep on inserting all the nodes.
- If you are inserting a total of n nodes, we might conclude that the whole operation takes `O(n * log n)` time. But remember that initially
the size of tree is very smaller than `O(log n)`. So, on careful calculation the building process takes `O(n)` time.

#### Deletion
- You can only delete the root of a heap.
- Replace the heap root with the last element.
- Then start the heapify process from the root in a top-down manner.
- Time complexity: `O(log n)` = `O(height)`
- If you want to delete an intermediate element (as available in Java's PriotityQueue), we first need to find the element by checking through each element, which takes O(n) operations and
then delete it in the same manner as we delete the root. So deletion of an arbitrary element takes `O(n)` complexity.

### Binary Search Tree
- All the elements smaller than the root are on the left sub-tree and all higher are on the right sub-tree. This is valid for all underlying sub-trees too.
- In-order traversal will print all values in a sorted manner. This concept can be used to find the kth smallest element.

#### Insertion
- Similar to search.
- Go till you encounter a leaf node and attach to left/right depending on whether the new value is smaller/bigger than the leaf value.
- Time complexity: `O(log n)` = `O(height)` for an average case. In the worst case, it can be `O(n)` as the tree can look like a straight line.

#### Deletion
- If node does not have any children, you can simply mark it as null.
- If the node has only one child, replace the existing node with its child.
- If it has both the children, find the immediate successor (or predecessor) and replace the current node with that.

#### Trie
- Used for searching as in a dictionary.

### Graphs
- Maximum flow - min cut algorithm [Ford Fulkerson](https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/)

#### Graph vs Tree
- A tree is an undirected graph which is 
  - connected: Every node must be able to reach all other nodes
  - no cycle: A tree should not have a cycle
- The validation can be done using DFS as mentioned in this [Algo Monster Medium article](https://algomonster.medium.com/leetcode-261-graph-valid-tree-f27c212c1db1).

## Mathematical concepts

- [Modular division and inverse](https://www.geeksforgeeks.org/modular-division/)
- [Moyer-Moore Majority voting algorithm](https://www.geeksforgeeks.org/boyer-moore-majority-voting-algorithm/)
- [Recursive sum of digits Leetcode](https://leetcode.com/problems/add-digits/solution/): Modulo of 9 or the base digit - 1
```java
class Solution {
    public int addDigits(int num) {
        if (num == 0) return 0;
        if (num % 9 == 0) return 9;
        return num % 9;
    }
}
```


## Concepts and problems list

#### Divide and Conquer
1. A very good problem: [Longest Substring with At Least K Repeating Characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/)

#### Monotonic queue
1. [Shortest Unsorted Continuous Subarray](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/ShortestUnsortedContinuousSubarray.java)
2. [Sliding Window Maximum](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MaxSlidingWindow.java)
3. [Remove duplicate numbers Leetcode](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/RemoveDuplicateLetters.java)
4. [Largest Rectangle in a histogram](https://leetcode.com/problems/largest-rectangle-in-histogram) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestHistogram.java)
5. [Remove k digits Leetcode](https://leetcode.com/problems/remove-k-digits/discuss/2572477/Java-oror-Stack)

#### Binary search
[Binary search problems folder](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/binarysearch)

1. You have to find not the exact match, but the least absolute distance to the elements in the array. [heaters problem](https://leetcode.com/problems/heaters/)

##### Binary search over solution space
1. [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/discuss/2553108/Java-oror-Binary-Search-oror-With-Full-and-Easy-Explanation)

#### Two pointers
Sometimes, you may have to sort the data when using this. Also this can be extended to three pointers and more.

1. [Three sum problem](https://leetcode.com/problems/3sum)
2. [Next Permutation](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/NextPermutation.java)
3. [Longest substring without repeating characters: O(n) solution](https://leetcode.com/problems/longest-substring-without-repeating-characters/discuss/2506453/Java-HashSet-%2B-Sliding-Window)
4. [Minimum window substring](https://leetcode.com/problems/minimum-window-substring/)
5. [Minimum size subarray sum](https://leetcode.com/problems/minimum-size-subarray-sum/) This problem can be solved using cumulative sum method too. But it takes `O(n^2)` time.
6. [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/discuss/2468752/can-anyone-clarify-this-doubt). Also watch the YT video [here](https://youtu.be/gqXU1UyA8pk?t=726).
7. [Longest substring with at least k repeating characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/2444441/Java-Solution-or-Recursive-or-Sliding-Window)

#### Diff array technique
1. [Corporate flight bookings](https://leetcode.com/problems/corporate-flight-bookings) - [solution](problems/miscellaneous/CorpFlightBookings.java)

#### Dynamic Programming (DP)
1. [Maximal Square](https://leetcode.com/problems/maximal-square/) - Difficult to come up with the logic.
2. [Largest magic square](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestMagicSquare.java)
3. [Edit Distance](https://leetcode.com/problems/edit-distance/)
4. [Maximum Profit in Job Scheduling](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/MaximumProfitInJobScheduling.java)
5. [Palindrome partitioning](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/PalindromePartitioning.java)
6. [Longest Valid Parentheses](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/LongestValidParentheses.java)
7. A little tough: [Best time to buy and sell stock IV Leetcode](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/2558269/JAVA-oror-Recursive-DP-Tabulation-and-Space-Optimization)
8. [Bursting balloons](https://leetcode.com/problems/burst-balloons/discuss/2446689/Simple-Java-Solution-with-Explanation-DP)
9. **LIS variant**: First sort the data based on one field, then find the LIS based on the second field.  [Russian doll envelopes](https://leetcode.com/problems/russian-doll-envelopes/discuss/2521874/Java-DP-based-on-Longest-Increasing-Subsequence)
10. **LIS variant**: first sort and then check LIS property based on whether one is divisible by another. [Largest divisble subset](https://leetcode.com/problems/largest-divisible-subset/) 
11. Similar to knapsack: [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/discuss/2532411/JAVA-solution-using-(subset-sum-equals-k))

#### Using original indexes array
1. [Couples holding hands](https://leetcode.com/problems/couples-holding-hands/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/Coupleholdinghands.java)

#### Bit manipulation

This can be used in a lot of places. Some are:
- Use XOR to find duplicates, swap numbers etc.
- Efficient way to save whether a test case is covered or not. For example if there are a max of 32 elements in array and you want to track whether element is used, just set that bit to 1 in an integer.

1. [Find non-duplicate number without extra space](https://leetcode.com/problems/single-number/)

#### Hashing
1. [Group anagrams](https://leetcode.com/problems/group-anagrams/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/GroupAnagrams.java)
2. [Longest Consecutive Sequence](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LongestConsecutive.java)

#### Sorting
1. [Group anagrams](https://leetcode.com/problems/group-anagrams/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/GroupAnagrams.java)
2. [My Calendar](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MyCalendar.java)
3. **Sometimes numbers can be converted to strings and sorted. Here a custom comparator is used to figure which type of concatenation is better (a+b or b+a)** - [Largest Number Leetcode](https://leetcode.com/problems/largest-number/solution/)
4. A variation of merge sort - [Count of smaller numbers after self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/2556347/Java-ororMerge-Sort-Solution-oror)

#### Recursion
1. [House Robber Three](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/HouseRobberThree.java)
2. [Longest substring with at least k repeating characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/2444441/Java-Solution-or-Recursive-or-Sliding-Window)

#### Binary Tree
1. [House Robber Three](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/HouseRobberThree.java)

#### Heap - Priority Queue
1. [Leetcode IPO](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/IPO.java)
2. **Asked in an interview** [Maximum jobs in non-consecutive towns](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MaximumNonCons.java)

#### Sliding window
1. [Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/)

#### No or constant extra space
See if you can manipulate the existing array/DS itself. For example, you can change the sign of the element etc to remember wehther visited or not.
1. [First Missing Positive](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/FirstMissingPositive.java)


#### Graphs
1. [Bipartite graph](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/BiPartiteGraph.java)

##### Union Find
1. **Asked in an interview**: [Group nodes into a network and find sum after each network merge](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/NetworkSumUnionFind.java)

##### Topological sort
1. [Course schedule two](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/CourseScheduleTwo.java) 
2. [Course Schedule](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/CanFinishCourses.java)
3. [Alien dictionary](https://leetcode.ca/2016-08-25-269-Alien-Dictionary/) The key is to understand that a dictionary problem is nothing but topological sort. 

##### Breadth First Search (BFS)
- Used when you want to find the closest path in an unweighted graph.
- We have to use Dijkstra's etc when the graph is weighted.
- Note that for some problems we may use multi-source BFS, where multiple datapoints can be source.
- When we are tracing one layer after the other, we may need to store the parent element of the current element. For example, in a word ladder problem, we may need to store the parent word to trace the path back.
###### Problems examples from [chatGPT](https://chatgpt.com/share/690c6924-c07c-8006-9ef8-3c346bcc2bcf)
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

##### Depth First Search (DFS)
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