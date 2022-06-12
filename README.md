# Coding practice

## Search
- Binary search

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

-- TODO --

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
