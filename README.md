# Coding practice

##### Index

- [Java and OOP](javapractice)
- [System Design](system-design)
- [gRPC](grpc-protocol-buffers/docs/)

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
- [Mathematical concepts](#mathematical-concepts)
- [Problems list](#problems-list)
    - [Divide and Conquer](#divide-and-conquer)
    - [Monotonic queue](#monotonic-queue)
    - [Binary search](#binary-search)
    - [Two pointers](#two-pointers)
    - [Diff array technique](#diff-array-technique)
    - [Dynamic Programming (DP)](#dynamic-programming-dp)
    - [Monotonic stack](#monotonic-stack)
    - [Using original indexes array](#using-original-indexes-array)
    - [Hashing](#hashing)
    - [Sorting](#sorting-1)
    - [Recursion](#recursion)
    - [Binary Tree](#binary-tree)
    - [Heap - Priority Queue](#heap---priority-queue)
    - [Sliding window](#sliding-window)
    - [Graphs](#graphs-1)
      - [Union Find](#union-find)
      - [Topological sort](#topological-sort)

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
- Time complexity is always O(nlog(n)), for all three cases worst, average and best cases. So, when you don't know about the input use merge sort.
- When using arrays
    - we need additional heap space of O(n) to keep the merged array.
    - A disadvantage is that it is not an in-place sort.
- When using linked list
    - no need for the additional heap space. We only need stack space to keep recursion information. So memory required is only O(log(n)).
    - This is an in-place sort, because we only need to modify next pointers of the nodes.

[Code for merge sort](sorting/MergeSort.java)   

### Quick sort
- Time complexity
  - Best: O(n*log(n))
  - Average: O(n*log(n))
  - Worst: O(n<sup>2</sup>), when we choose the partition in such a way that the partition ends up at the beginning or at ending of the array.
- Quick sort is not stable.
- This is in-place and is faster when good partition strategies are used.

[Code for quick sort](sorting/QuickSort.java)

## Data structures

### Binary Heap (min-heap)

- This is always a complete binary tree.
- So it can be represented easily in an array.
- Children of node at *i* will be at *2i-2* and *2i+2*. Parent of node *i* will be at *(i-1)/2*.
- In a min-heap, a parent node will always be less than or equal to their children.

#### Insertion
- First insert it at then end of the heap.
- See if it still follows the heap property, i.e compare it's value with the parent and swap if needed.
- If you had to swap, then repeat the previous step till you don't need to swap or reached the root.
- Time complexity: O(log n) = O(height)

#### Building
- Initialize and keep on inserting all the nodes.
- If you are inserting a total of n nodes, we might conclude that the whole operation takes O(n * log n) time. But remember that initially
the size of tree is very smaller than O(log n). So, on careful calculation the building process takes O(n) time.

#### Deletion
- You can only delete the root of a heap.
- Replace the heap root with the last element.
- Then start the heapify process from the root in a top-down manner.
- Time complexity: O(log n) = O(height)
- If you want to delete an intermediate element (as available in PriorityQueue Java), we first need to find the element by checking through each element, which takes O(n) operations and
then delete it in the same manner as we delete the root. So deletion of an arbitrary element takes `O(n)` complexity.

### Binary Search Tree
- All the elements smaller than the root are on the left sub-tree and all higher are on the right sub-tree. This is valid for all underlying sub-tress too.
- In-order traversal will print all values in a sorted manner. This concept can be used to find the kth smallest element.

#### Insertion
- Similar to search.
- Go till you encounter a leaf node and attach to left/right depending on whether the new value is smaller/bigger than the leaf value.

#### Deletion
- If node does not have any children, you can simply mark it as null.
- If the node has only one child, replace the existing node with its child.
- If it has both the children, find the immediate successor (or predecessor) and replace the current node with that.

#### Trie
- Used for searching as in a dictionary.

### Graphs
- Maximum flow - min cut algorithm [Ford Fulkerson](https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/)


## Mathematical concepts

- [Modular division and inverse](https://www.geeksforgeeks.org/modular-division/)


## Problems list

#### Divide and Conquer
1. A very good problem: [Longest Substring with At Least K Repeating Characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/)

#### Monotonic queue
1. [Shortest Unsorted Continuous Subarray](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/ShortestUnsortedContinuousSubarray.java)
2. [Sliding Window Maximum](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MaxSlidingWindow.java)

#### Binary search

[Binary search problems folder](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/binarysearch)

1. You have to find not the exact match, but the least absolute distance to the elements in the array. [heaters problem](https://leetcode.com/problems/heaters/)


#### Two pointers
Sometimes, you may have to sort the data when using this. Also this can be extended to three pointers and more.

1. [Three sum problem](https://leetcode.com/problems/3sum)
2. [Next Permutation](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/NextPermutation.java)

#### Diff array technique
1. [Corporate flight bookings](https://leetcode.com/problems/corporate-flight-bookings) - [solution](problems/miscellaneous/CorpFlightBookings.java)

#### Dynamic Programming (DP)
1. [Maximal Square](https://leetcode.com/problems/maximal-square/) - Difficult to come up with the logic.
2. [Largest magic square](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestMagicSquare.java)
3. [Edit Distance](https://leetcode.com/problems/edit-distance/)
4. [Maximum Profit in Job Scheduling](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/MaximumProfitInJobScheduling.java)
5. [Palindrome partitioning](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/PalindromePartitioning.java)
6. [Longest Valid Parentheses](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/LongestValidParentheses.java)

#### Monotonic stack
1. [Largest Rectangle in a histogram](https://leetcode.com/problems/largest-rectangle-in-histogram) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestHistogram.java)

#### Using original indexes array
1. [Couples holding hands](https://leetcode.com/problems/couples-holding-hands/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/Coupleholdinghands.java)

#### Hashing
1. [Group anagrams](https://leetcode.com/problems/group-anagrams/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/GroupAnagrams.java)
2. [Longest Consecutive Sequence](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LongestConsecutive.java)

#### Sorting
1. [Group anagrams](https://leetcode.com/problems/group-anagrams/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/GroupAnagrams.java)
2. [My Calendar](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MyCalendar.java)

#### Recursion
1. [House Robber Three](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/HouseRobberThree.java)

#### Binary Tree
1. [House Robber Three](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/HouseRobberThree.java)

#### Heap - Priority Queue
1. [Leetcode IPO](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/IPO.java)
2. **Asked in an interview** [Maximum jobs in non-consecutive towns](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/MaximumNonCons.java)

#### Sliding window
1. [Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/)

#### Graphs
1. [Bipartite graph](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/BiPartiteGraph.java)

##### Union Find
1. **Asked in an interview**: [Group nodes into a network and find sum after each network merge](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/NetworkSumUnionFind.java)

##### Topological sort
1. [Course schedule two](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/CourseScheduleTwo.java) 
