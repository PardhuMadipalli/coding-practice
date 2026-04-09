---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: custom_page
parent: DSA
title: Dynamic Programming
---
## Dynamic Programming (DP)

- Check if the given problem can be solved using recursion. If so, then observe if DP can help or not.
- Even the maximum profit scheduling problem is a recursion: do you want to take current job or not. Some times, we may need to sort the data. Here we sorted in endTime order.

   {: .note }
   Use binary search if already sorted data exists, instead of using treemap just for the sake of _floorKey_ operation. It will help with time.


| Problem                                                                                                                                                                | Solution & Details                                                                                                                           |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| [Maximal Square](https://leetcode.com/problems/maximal-square/)                                                                                                        | Difficult to come up with the logic. [Solution]({ site.code_path }}problems/dp/MaximalSquare.java)                                           |
| [Largest rectangle Problem](https://leetcode.com/problems/maximal-rectangle/)                                                                                          | [Histogram based solution](https://leetcode.com/problems/maximal-rectangle/solutions/7042757/stack-histogram-trick-faang-favourite-an-n8p9/) |
| [Largest magic square](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestMagicSquare.java)                                    |                                                                                                                                              |
| [Edit Distance](https://leetcode.com/problems/edit-distance/)                                                                                                          |                                                                                                                                              |
| [Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling)                                                                     | [DP solution]({{ site.code_path }}problems/dp/MaximumProfitInJobSchedulingDp.java) - Sort it and get into Knapsack style problem             |
| [Palindrome partitioning](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/PalindromePartitioning.java)                                        |                                                                                                                                              |
| [Longest Valid Parentheses](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/LongestValidParentheses.java)                                     |                                                                                                                                              |
| [Best time to buy and sell stock IV](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/2558269/JAVA-oror-Recursive-DP-Tabulation-and-Space-Optimization) | A little tough                                                                                                                               |
| [Bursting balloons](https://leetcode.com/problems/burst-balloons/discuss/2446689/Simple-Java-Solution-with-Explanation-DP) | Choose `k` as the **last** balloon burst in range `[i, j]`. Boundaries `i-1` and `j+1` are guaranteed alive because they belong to an outer subproblem and are burst even later. Score = `dp(i, k-1) + nums[i-1]*nums[k]*nums[j+1] + dp(k+1, j)`. |
| [Wild card matching problem](https://leetcode.com/problems/wildcard-matching/description/?envType=problem-list-v2&envId=dynamic-programming)                           | [Solution]({{ site.code_path }}problems/dp/WildcardMatching.java)                                                                            |
| [Russian doll envelopes](https://leetcode.com/problems/russian-doll-envelopes/discuss/2521874/Java-DP-based-on-Longest-Increasing-Subsequence)                         | **LIS variant**: First sort the data based on one field, then find the LIS based on the second field.                                        |
| [Largest divisible subset](https://leetcode.com/problems/largest-divisible-subset/)                                                                                    | **LIS variant**: first sort and then check LIS property based on whether one is divisible by another.                                        |
| [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/discuss/2532411/JAVA-solution-using-(subset-sum-equals-k))                       | Similar to knapsack                                                                                                                          |
| [Minimum string containing both strings as subsequences](https://leetcode.com/problems/shortest-common-supersequence/) | Using DP solution for Longest Common Subsequnce, construct the DP table. Now come in the reverse order of the strings and DP table: `i=m-1, j=n-1`. If the chars at `i` and `j` are same, then our superstring will include only one of them. Else, if `dp[i][j-1] > dp[i-1][j]`, then take `str2(j--)`, else `str1(i--)` and add to the result string. Because, for constructing the original DP table, we would have deleted str2(j) and the remaining substrings have higher DP value. So we must include this uncommon char.  Keep proceeding. At the end add remaining chars if any from both strings. |
| Unlimited quantities knapsack | A slight modification of 0-1 knapsack. In the memoization manner for 0-1 knapsack, we do take or no-take. Modify the take where instead of incrementing the `i` value, keep using the same `i` for recursion. `int take = dp(i, capacity - wt[i])` instead of `take = dp(i+1, capacity - wt[i])`. See below for memoization .|
| [Minimum char additions to make a String palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/) | First calculate length of the longest plaindromic SUBSEQUENCE. Now you have to add the letters that are not forming the palindrome. Answer is `s.length() - lps`. |
| [Equal Partition or a target sum](https://leetcode.com/problems/partition-equal-subset-sum/) | The [solution]({ site.code_path }problems/dp/EqualPartition.java) uses memoization which needs $$O(n * sum)$$ space. But when using tabulation we can reduce it to one row $$O(sum)$$. We can loop over the capacity only from right to left. See [1D space DP code](#1d-space-dp-solution). |
| [Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/) | Fix two boundary vertices `i` and `j`. Try every vertex `k` between them as the third point of a triangle: `score = values[i]*values[k]*values[j] + dp(i,k) + dp(k,j)`. Memoize on `(i,j)`. [Solution]({{ site.code_path }}problems/dp/MinScoreTriangulation.java) |
| [Matrix Chain Multiplication](https://leetcode.com/discuss/post/1278305/all-about-matrix-chain-multiplication-ea-ebjk/) | Classic interval DP. For a chain of matrices `i..j`, try every split point `k`: `cost = dp(i,k) + dp(k+1,j) + dims[i-1]*dims[k]*dims[j]`. Memoize on `(i,j)`. Same pattern as polygon triangulation. |

### Unlimited quantities knapsack
```java
// Calculate maximum profit for each
// item index and knapsack weight.
for (int i = val.length - 1; i >= 0; i--) {
    for (int j = 1; j <= capacity; j++) {
        int take = 0;
        if (j - wt[i] >= 0) {
            take = val[i] + dp[i][j - wt[i]];
        }
        int noTake = dp[i + 1][j];
        dp[i][j] = Math.max(take, noTake);
    }
}
return dp[0][capacity];
```

### 1D Space DP solution
```python
for j from targetSum down to 0:
    for each num in nums:
        if j >= num: dp[j] = dp[j] || dp[j - num]
```
