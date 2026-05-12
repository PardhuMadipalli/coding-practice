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
| [Maximal Square](https://leetcode.com/problems/maximal-square/)                                                                                                        | Difficult to come up with the logic. [Solution]({{ site.code_path }}problems/dp/MaximalSquare.java)                                           |
| [Largest rectangle Problem](https://leetcode.com/problems/maximal-rectangle/)                                                                                          | [Histogram based solution](https://leetcode.com/problems/maximal-rectangle/solutions/7042757/stack-histogram-trick-faang-favourite-an-n8p9/) |
| [Largest magic square](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/miscellaneous/LargestMagicSquare.java)                                    |                                                                                                                                              |
| [Edit Distance](https://leetcode.com/problems/edit-distance/)                                                                                                          |                                                                                                                                              |
| [Maximum Profit in Job Scheduling](https://leetcode.com/problems/maximum-profit-in-job-scheduling)                                                                     | [DP solution]({{ site.code_path }}problems/dp/MaximumProfitInJobSchedulingDp.java) - Sort it and get into Knapsack style problem             |
| [Palindrome partitioning](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/PalindromePartitioning.java)                                        |                                                                                                                                              |
| [Longest Valid Parentheses](https://github.com/PardhuMadipalli/coding-practice/blob/main/problems/dp/LongestValidParentheses.java)                                     |                                                                                                                                              |
| [Best time to buy and sell stock IV](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/discuss/2558269/JAVA-oror-Recursive-DP-Tabulation-and-Space-Optimization) | A little tough                                                                                                                               |
| [Bursting balloons](https://leetcode.com/problems/burst-balloons/discuss/2446689/Simple-Java-Solution-with-Explanation-DP) | Choose `k` as the **last** balloon burst in range `[i, j]`. Boundaries `i-1` and `j+1` are guaranteed alive because they belong to an outer subproblem and are burst even later. Score = `dp(i, k-1) + nums[i-1]*nums[k]*nums[j+1] + dp(k+1, j)`. |
| [Wild card matching problem](https://leetcode.com/problems/wildcard-matching/description/?envType=problem-list-v2&envId=dynamic-programming)                           | [Solution]({{ site.code_path }}problems/dp/WildcardMatching.java)                                                                            |
| [Russian doll envelopes](https://leetcode.com/problems/russian-doll-envelopes/discuss/2521874/Java-DP-based-on-Longest-Increasing-Subsequence)                         | **LIS variant**: First sort the data based on one field, then find the LIS based on the second field. Use the O(n log n) binary search / patience-sort approach (maintain a `tails` array, binary search for position to replace) — O(n²) TLEs for n=10⁵. |
| [Largest divisible subset](https://leetcode.com/problems/largest-divisible-subset/)                                                                                    | **LIS variant**: first sort and then check LIS property based on whether one is divisible by another. O(n²) DP suffices here since n≤1000, but the O(n log n) binary search approach also applies.                                        |
| [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/discuss/2532411/JAVA-solution-using-(subset-sum-equals-k))                       | Similar to knapsack                                                                                                                          |
| [Minimum string containing both strings as subsequences](https://leetcode.com/problems/shortest-common-supersequence/) | Using DP solution for Longest Common Subsequnce, construct the DP table. Now come in the reverse order of the strings and DP table: `i=m-1, j=n-1`. If the chars at `i` and `j` are same, then our superstring will include only one of them. Else, if `dp[i][j-1] > dp[i-1][j]`, then take `str2(j--)`, else `str1(i--)` and add to the result string. Because, for constructing the original DP table, we would have deleted str2(j) and the remaining substrings have higher DP value. So we must include this uncommon char.  Keep proceeding. At the end add remaining chars if any from both strings. |
| Unlimited quantities knapsack | A slight modification of 0-1 knapsack. In the memoization manner for 0-1 knapsack, we do take or no-take. Modify the take where instead of incrementing the `i` value, keep using the same `i` for recursion. `int take = dp(i, capacity - wt[i])` instead of `take = dp(i+1, capacity - wt[i])`. See below for memoization .|
| [Minimum char additions to make a String palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/) | First calculate length of the longest plaindromic SUBSEQUENCE. Now you have to add the letters that are not forming the palindrome. Answer is `s.length() - lps`. |
| [Equal Partition or a target sum](https://leetcode.com/problems/partition-equal-subset-sum/) | The [solution]({ site.code_path }problems/dp/EqualPartition.java) uses memoization which needs $$O(n * sum)$$ space. But when using tabulation we can reduce it to one row $$O(sum)$$. We can loop over the capacity only from right to left. See [1D space DP code](#1d-space-dp-solution). |
| [Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/) | Fix two boundary vertices `i` and `j`. Try every vertex `k` between them as the third point of a triangle: `score = values[i]*values[k]*values[j] + dp(i,k) + dp(k,j)`. Memoize on `(i,j)`. [Solution]({{ site.code_path }}problems/dp/MinScoreTriangulation.java) |
| [Matrix Chain Multiplication](https://leetcode.com/discuss/post/1278305/all-about-matrix-chain-multiplication-ea-ebjk/) | Classic interval DP. For a chain of matrices `i..j`, try every split point `k`: `cost = dp(i,k) + dp(k+1,j) + dims[i-1]*dims[k]*dims[j]`. Memoize on `(i,j)`. Same pattern as polygon triangulation. |
| [Maximize Fixed Points After Deletions](https://leetcode.com/contest/weekly-contest-500/problems/maximize-fixed-points-after-deletions/) | Reframe deletions as choosing a subsequence to keep. Element kept as the `k`-th element gets new index `k`, so it's a fixed point iff `nums[i] == k`. State: `(i, kept)` = max fixed points from index `i` onward given `kept` elements kept so far. At each step: keep (`nums[i]==kept` scores 1, kept+1) or delete (kept unchanged). [Memoization + 2D DP]({{ site.code_path }}problems/MaximiseFixedPointsAfterDeletion.java) \| [1D DP]({{ site.code_path }}problems/MaximizePointsAfterDeletions.java) |
| [Minimum Operations to Achieve At Least K Peaks](https://leetcode.com/problems/minimum-operations-to-achieve-at-least-k-peaks/) | <a name="minimum-operations-to-achieve-at-least-k-peaks"></a>Circular array + non-adjacent peak selection. **Greedy fails**: sorting by cost and picking cheapest first doesn't work because a zero-cost peak can block two cheaper neighbors, forcing an expensive choice (e.g. `[6,-7,11,13]` k=2: picking free index 3 blocks indices 0 and 2, forcing index 1 at cost 19; optimal is indices 0+2 at cost 11). **Circular → linear**: index 0 and n-1 are adjacent so can't both be peaks. Split into 3 cases: (A) neither is a peak → DP on `[1..n-2]`; (B) index n-1 is a peak → fix its cost, DP on `[1..n-3]`; (C) index 0 is a peak → fix its cost, DP on `[2..n-2]`. Take the min. **Tabulation DP**: `dp[i][j]` = min cost to place j peaks in `[0..i]` with peak at i. Transition: `dp[i][j] = cost[i] + min(dp[i'][j-1])` for `i' ≤ i-2`. See [running minimum trick](#running-minimum-trick) to compute this in O(nk). **Knapsack-style memoization**: same problem reframed as pick/skip — `solve(i, peaksLeft)` = min cost from index i onward with peaksLeft peaks still needed. Skip: `solve(i+1, peaksLeft)`. Pick: `cost[i] + solve(i+2, peaksLeft-1)` (jump by 2 to enforce non-adjacency — the only difference from classic 0/1 knapsack). **Complexity**: all three DP approaches are O(n·k) time and O(n·k) space (n·k distinct states, each computed once). Brute force is O(C(n,k)·k) — exponential. Tabulation without the running min trick degrades to O(n²·k). The tabulation space can be reduced to O(n) using two 1D rolling arrays. [Tabulation]({{ site.code_path }}problems/miscellaneous/MinOperationsKPeaks.java) \| [Knapsack memoization]({{ site.code_path }}problems/miscellaneous/MinOperationsKPeaksKnapsack.java) \| [Brute force]({{ site.code_path }}problems/miscellaneous/MinOperationsKPeaksBruteForce.java) |

---

### Running minimum trick

When a DP transition is `dp[i][j] = cost[i] + min(dp[i'][j-1])` for all `i' ≤ i-2`, the naive approach loops over all valid `i'` for each `i`, giving O(n²k). Instead, maintain a rolling minimum as you scan `i` left to right:

```java
for (int j = 2; j <= k; j++) {
    int runMin = INF;
    for (int i = 0; i < len; i++) {
        if (i >= 2) runMin = Math.min(runMin, dp[i - 2][j - 1]);
        if (runMin < INF) dp[i][j] = cost[i] + runMin;
    }
}
```

When processing position `i`, position `i-2` just became eligible as a predecessor — fold it into `runMin`. `runMin` always holds `min(dp[0][j-1], ..., dp[i-2][j-1])` at no extra cost. This reduces the complexity to O(nk).

**Why j must be the outer loop**: when j is outer, the entire `j-1` column is fully computed before the `j` pass begins, so `runMin` can be maintained incrementally. If you reverse the loops (i outer, j inner), you'd need an explicit O(n) scan over all predecessors for each cell, losing the optimization.

**Space**: two 1D arrays (`prev` and `curr`) suffice instead of the full `n × k` table, since each `j` pass only reads from `j-1`. You can't collapse to a single array because `runMin` reads `j-1` values at earlier positions that have already been overwritten in the same pass.

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
