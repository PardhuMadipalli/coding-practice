---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: custom_page
title: DSA
nav_order: 1
---
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
- Time complexity is always $$O(nlog(n))$$, for all three cases worst, average and best cases. So, when you don't know about the input use merge sort.
- When using arrays
    - we need additional heap space of O(n) to keep the merged array.
    - A disadvantage is that it is not an in-place sort.
- When using linked list
    - no need for the additional heap space. We only need stack space to keep recursion information. So memory required is only `O(log(n))`.
    - This is an in-place sort, because we only need to modify next pointers of the nodes.

- [Code for merge sort]({{ site.code_path }}sorting/MergeSort.java)
- [Code for merge sort with linked list](https://www.geeksforgeeks.org/dsa/merge-sort-for-linked-list/)

    {: .note }
    In this code, the author used _recursion_ intelligently in the merge function.


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


#### Graph vs Tree
- A tree is an undirected graph which is 
  - connected: Every node must be able to reach all other nodes
  - no cycle: A tree should not have a cycle
- The validation can be done using DFS as mentioned in this [Algo Monster Medium article](https://algomonster.medium.com/leetcode-261-graph-valid-tree-f27c212c1db1).

## Mathematical concepts

- [Modular division and inverse](https://www.geeksforgeeks.org/modular-division/)
- [Moyer-Moore Majority voting algorithm](https://www.geeksforgeeks.org/boyer-moore-majority-voting-algorithm/)
- [Recursive sum of digits Leetcode](https://leetcode.com/problems/add-digits/solution/): 
```java
// Modulo of 9 or the base digit - 1
// A number abcd is same as a+b+c+d(= s) in the modulo 9 world. 
// The number s again is same as sum of digits in `s`
// When you repeat this process until there is only 1 digit remaining, that is the answer.
public int addDigits(int num) {
    if (num == 0) return 0;
    if (num % 9 == 0) return 9;
    return num % 9;
}
```


## Concepts and problems list

#### Divide and Conquer

1. A very good problem: [Longest Substring with At Least K Repeating Characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/)
    - The problem can also be using Sliding Window `O(n)` approach.
    - Divide and conquer solution is `O(n * log_n)`
    - [D&C solution]({{ site.code_path }}problems/miscellaneous/LongestSubstring.java)
   
    {: .note }
    In this code, we should use int[] instead of map to store the counts of characters to significantly reduce the time needed for execution. 

#### Arrays problems

| Problem | Solution and details |
| Maximum sum of subarray | Use Kadane's algorithm. See [the section.](#kadanes-algorithm) |

#### Monotonic queue

| Problem | Solution & Details                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Shortest Unsorted Continuous Subarray](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/ShortestUnsortedContinuousSubarray.java) |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [Sliding Window Maximum problem](https://leetcode.com/problems/sliding-window-maximum/) | [Solution]({{ site.code_path }}problems/miscellaneous/MaxSlidingWindow.java)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [Remove duplicate numbers Leetcode](https://leetcode.com/problems/remove-duplicate-letters) | [Solution]({{ site.code_path }}problems/miscellaneous/RemoveDuplicateLetters.java)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| [Largest Rectangle in a histogram](https://leetcode.com/problems/largest-rectangle-in-histogram) | [Solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestHistogram.java).<br/>We find the next and previous smaller elements(indices `pr` and `nx`) from the current element. The maximum area involving the current element is `h[i] * (pr - nx - 1)`. The boundaries will be the elements just after `pr` and just before `nx`. Hence the width `pr - nx - 1`. <br/>Instead of using two stacks for finding both, you can use a single stack. Store the elements in a non-decreasing order. If the current element is shorter than the peek. That means peek encountered next smaller element. What is its previous smaller element? Of course, the next top of the stack (which you obtain by popping the current top). If the height is same, then you should keep popping. Now you have found the two boundaries of the stack top. Right is `i` and the left is `stack.peek()` after popping the top (and same height ones). Now calculate the area. If there are elements still left in the stack, that means the right boundary is n. Process rest of the stack accordingly. |
| [Maximum score including k-th element](https://leetcode.com/problems/maximum-score-of-a-good-subarray/) | Same as the above solution. Only just ensure the prev min and next min indices range include k.<br/>Also check [my greedy solution]({{ site.code_path }}problems/miscellaneous/MaximumScoreTwoPointers.java).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| [Remove k digits Leetcode](https://leetcode.com/problems/remove-k-digits/discuss/2572477/Java-oror-Stack) |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [Most competitive subsequence 🔗](https://leetcode.com/problems/find-the-most-competitive-subsequence/description/) | [My solution 🔗]({{ site.code_path }}problems/miscellaneous/MostCompetitive.java)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| [Create maximum number from two integer arrays 🔗](https://leetcode.com/problems/create-maximum-number/description/) | A complex problem involving the above problem. First take all possible splits i.e, if you take 2 elements from the first array, then you must take k-2 from the second. Use above problem to find the most competitive subsequence of given length from each array. Merge the solutions from two arrays greedily (start by comparing the first elements of each result array). Find the maximum from all the possible solutions.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |

#### Binary search
[Binary search problems folder](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/binarysearch)

1. You have to find not the exact match, but the least absolute distance to the elements in the array. [heaters problem](https://leetcode.com/problems/heaters/)

##### Binary search over solution space
1. [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/discuss/2553108/Java-oror-Binary-Search-oror-With-Full-and-Easy-Explanation)

#### Prefix sum
Use this when sums are involved and when the order of the elements to compute the sum matters.
- Find the longest subarray where sum of elements will be equals to K. Find all prefix sums. Then store them in a map with sums as keys. Now you take each sum $$s$$ and see if there exists another sum whose value is $$K-s$$.

#### Dutch National Flag Algorithm
Sort the array containing only 0s, 1s and 2s. Take three variables called low, mid and high. 
- [0, low) and [low, mid) are 0s and 1s respectively. [mid, high) is the unsorted subarray that we will process later, [high, n) are 2s.
- Process the subarray like this
```
    while mid <= high:
        if nums[mid] == 0:
            nums[low], nums[mid] = nums[mid], nums[low]
            low += 1
            mid += 1
        elif nums[mid] == 1:
            mid += 1
        else:
            nums[mid], nums[high] = nums[high], nums[mid]
            high -= 1
```

#### Kadane's algorithm
To find the maximum subarray sum, start computing the $$maxSumSubArrayEndingAtK$$ for all elements of the array. The value at the current element is current element + 

1. Previous $$maxSumSubArrayEndingAtK$$ if the previous value is positive.
2. 0 if the previous value is negative.

[GeeksForGeeks link](https://www.geeksforgeeks.org/dsa/largest-sum-contiguous-subarray/#expected-approach-using-kadanes-algorithm-on-time-and-o1-space)

#### Floyd's tortoise and hare algorithm for cycle detection
-- todo --

#### Two pointers
Sometimes, you may have to sort the data when using this. Also this can be extended to three pointers and more.

| Problem | Solution & Details                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Three sum problem](https://leetcode.com/problems/3sum) | [Solution]({{ site.code_path }}problems/miscellaneous/ThreeSum.java)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| [Next Permutation problem](https://leetcode.com/problems/next-permutation/description/) | [Solution]({{ site.code_path }}problems/miscellaneous/NextPermutationTwo.java)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [Longest substring without repeating characters 🔗](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | [My solution 🔗](https://leetcode.com/problems/longest-substring-without-repeating-characters/solutions/7369905/two-pointer-approach-boolean-array-to-st-1zgx)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [Minimum window substring Problem 🔗](https://leetcode.com/problems/minimum-window-substring/) | [Solution link 🔗](https://leetcode.com/problems/minimum-window-substring/solutions/7325527/beginner-friendly-solution-9976-beats-sl-gn3n/)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| [Minimum size subarray sum 🔗](https://leetcode.com/problems/minimum-size-subarray-sum/) | [Solution link 🔗](https://leetcode.com/problems/minimum-size-subarray-sum/solutions/7415019/beats-100-optimal-sliding-window-solutio-fi85) - Can be solved using cumulative sum method too. But it takes `O(n^2)` time.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/discuss/2468752/can-anyone-clarify-this-doubt) | [YT video](https://youtu.be/gqXU1UyA8pk?t=726)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [Longest substring with at least k repeating characters](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/description/) | [Explanation 1](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/discuss/2444441/Java-Solution-or-Recursive-or-Sliding-Window) - Find where you can break and apply divide and conquer.<br/>[Explanation 2](https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/solutions/170010/java-on-solution-with-detailed-explanati-d8q9/) - The trick is to identify the logic based on what you can move the left and right pointers. So we identify a new problem where we want to use only _T_ unique characters. We increase the T from 1 to 26 (or the maximum number of unique chars in the string). Find the max of all the solutions of various T's. |
| [Maximum score including k-th element](https://leetcode.com/problems/maximum-score-of-a-good-subarray/) | [My greedy solution]({{ site.code_path }}problems/miscellaneous/MaximumScoreTwoPointers.java) <br>Start from index k. Move to the left or right, depending on which is a bigger element.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |

#### Greedy

| Problem | Solution & Details                                                                                                                                                                      |
|---------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Jump Game](https://leetcode.com/problems/jump-game/) | [Recursive (very fast) and Greedy solutions]({{ site.code_path }}problems/miscellaneous/JumpGame.java)                                                                                  |
| [Jump Game 2](https://leetcode.com/problems/jump-game-ii/) | [Solution]({{ site.code_path }}problems/miscellaneous/JumpGame2.java) - Read the comments in the code.                                                                                  |
| [Wildcard Matching](https://leetcode.com/problems/wildcard-matching/description/?envType=problem-list-v2&envId=dynamic-programming) | [Greedy solution](ddd)                                                                                                                                                                  |
| [Maximum score including k-th element](https://leetcode.com/problems/maximum-score-of-a-good-subarray/) | [My greedy solution]({{ site.code_path }}problems/miscellaneous/MaximumScoreTwoPointers.java) <br>Start from index k. Move to the left or right, depending on which is a bigger element.|

#### Segment tree and Binary Search Tree variants

| Problem | Solution & Details                                                                                     |
|---------|--------------------------------------------------------------------------------------------------------|
| [My Calendar II](https://leetcode.com/problems/my-calendar-ii/) | [My published solution](https://leetcode.com/problems/my-calendar-ii/solutions/7530045/using-two-treemaps-and-merging-overlappi-q3cq/). Used treemap and merged ranges intelligently to reduce the tree size. |


#### Backtracking

| Problem | Solution & Details                                                                                                                                                                                                                                                                                                                           |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Word search II](https://leetcode.com/problems/word-search-ii/description/?) | [Solution](https://leetcode.com/problems/word-search-ii/solutions/7367107/simple-logic-based-dfs-trie-backtracking-z37o/?envType=problem-list-v2&envId=backtracking). Maintain a Trie to make the word search faster. Instead of searching for each word, you backtrack the entire board and note down whenever you find a word of interest. |
| [Word Ladder II]() | [Solution]({{ site.code_path }}problems/backtracking/WordLadderII.java). The solution used both BFS (to find all the parents till end word; see [BFS section](#breadth-first-search-bfs)) and DFS/backtracking to find the paths.                                                                                                                |

### Diff array technique
1. [Corporate flight bookings 🔗](https://leetcode.com/problems/corporate-flight-bookings) - [solution 🔗]({{ site.code_path }}problems/miscellaneous/CorpFlightBookings.java)

### Dynamic Programming (DP)

See the [Dynamic Programming page](dp/) for concepts and problems.

### Using original indexes array
1. [Couples holding hands](https://leetcode.com/problems/couples-holding-hands/) - [solution](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/Coupleholdinghands.java)

#### Bit manipulation

Some use cases:
- Use XOR to find duplicates, swap numbers etc.
- Efficient way to save whether a test case is covered or not. For example if there are a max of 32 elements in array and you want to track whether element is used, just set that bit to 1 in an integer.
- You can count the number of 1's and 0's at each bit index and find the odd one. For example, 
  - given a problem to find the number that comes only once while the rest of the numbers repeat thrice, you can by looking at each bit which has not occurred thrice.
  - Find the only duplicate number which might have repeated more than once in an array of integers 1 to `n+1`. Just find the expected set count at each bit(1 to 32). If the count at this particular bit is more than what is expected for 1 to `n+1` (all repeating only once), then that bit must be in the duplicate number. After you process all the bits you get the full number. This problem can also be solved by [Floyd's tortoise and hare algorithm](#floyds-tortoise-and-hare-algorithm-for-cycle-detection).
- Buckets concept: For the same problem you maintain buckets of ones, twos and threes while processing each element in the array. Using simple bit operations you can figure out if the number given is repeated once, twice or thrice (until now) and get the final answer after all the elements are processed. See [Striver's video solution](https://youtu.be/5Bb2nqA40JY?si=Uw6WGmTxE4l-oaYS&t=1419).

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
3. [Median of data stream](https://leetcode.com/problems/find-median-from-data-stream) - Use two heaps, maxHeap for the first half of smaller elements, and min heap for the second half of elements. Keep balancing the heaps, so that they are always of equal size or only one element greater than the other. Median is root of the max heap or the average of the roots of two heaps.   

#### Sliding window
1. [Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/)

#### No or constant extra space
See if you can manipulate the existing array/DS itself. For example, you can change the sign of the element etc., to remember whether visited or not.
1. [First Missing Positive](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/FirstMissingPositive.java)
