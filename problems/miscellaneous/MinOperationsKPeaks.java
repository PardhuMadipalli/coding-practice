package problems.miscellaneous;

import java.util.Arrays;

// https://leetcode.com/problems/minimum-operations-to-achieve-at-least-k-peaks/
public class MinOperationsKPeaks {

    // Cost to make index i a peak using original neighbor values.
    // Since peaks are non-adjacent, no two peaks share a neighbor,
    // so each peak's cost is independent of others.
    private int peakCost(int[] nums, int i) {
        int n = nums.length;
        int left = nums[(i - 1 + n) % n];
        int right = nums[(i + 1) % n];
        int needed = Math.max(left, right) + 1;
        return Math.max(0, needed - nums[i]);
    }

    // DP on a linear subarray [start..end].
    // leftNeighbor: the value to the left of index `start` (for peak cost calculation).
    // rightNeighbor: the value to the right of index `end`.
    // Returns min cost to place exactly k non-adjacent peaks in [start..end].
    private int solveLinear(int[] nums, int start, int end, int leftNeighbor, int rightNeighbor, int k) {
        int len = end - start + 1;
        if (k == 0) return 0;
        if (len <= 0) return Integer.MAX_VALUE / 2;

        // cost[i] = cost to make position (start+i) a peak
        int[] cost = new int[len];
        for (int i = 0; i < len; i++) {
            int idx = start + i;
            int left = (i == 0) ? leftNeighbor : nums[idx - 1];
            int right = (i == len - 1) ? rightNeighbor : nums[idx + 1];
            int needed = Math.max(left, right) + 1;
            cost[i] = Math.max(0, needed - nums[idx]);
        }

        int INF = Integer.MAX_VALUE / 2;

        // dp[i][j] = min cost to place exactly j peaks in positions [0..i], with a peak AT position i
        int[][] dp = new int[len][k + 1];
        for (int[] row : dp) Arrays.fill(row, INF);

        // Base case: 1 peak at position i
        for (int i = 0; i < len; i++) {
            dp[i][1] = cost[i];
        }

        // Fill: dp[i][j] = cost[i] + min over all i' in [0..i-2] of dp[i'][j-1]
        // runMin tracks min(dp[0][j-1], ..., dp[i-2][j-1]) as we scan left to right.
        for (int j = 2; j <= k; j++) {
            int runMin = INF;
            for (int i = 0; i < len; i++) {
                // Position i-2 is now eligible as a non-adjacent predecessor for position i
                if (i >= 2) {
                    runMin = Math.min(runMin, dp[i - 2][j - 1]);
                }
                if (runMin < INF) {
                    dp[i][j] = cost[i] + runMin;
                }
            }
        }

        // Min cost to place exactly k peaks anywhere in [0..len-1]
        int ans = INF;
        for (int i = 0; i < len; i++) {
            ans = Math.min(ans, dp[i][k]);
        }
        return ans;
    }

    public int minOperations(int[] nums, int k) {
        int n = nums.length;

        // Max peaks in a circular array of length n is floor(n/2)
        if (k > n / 2) return -1;
        if (k == 0) return 0;

        int INF = Integer.MAX_VALUE / 2;
        int ans = INF;

        // Break the circular adjacency between index 0 and index n-1 by fixing
        // which of them (if any) is a peak. Three cases cover all possibilities:
        //
        // Case A: neither index 0 nor index n-1 is a peak.
        //   → solve linear on [1..n-2], k peaks.
        //
        // Case B: index n-1 IS a peak, index 0 is NOT.
        //   → cost for index n-1, then solve linear on [1..n-3] for k-1 peaks.
        //   (index n-2 is blocked as neighbor of n-1)
        //
        // Case C: index 0 IS a peak, index n-1 is NOT.
        //   → cost for index 0, then solve linear on [2..n-2] for k-1 peaks.
        //   (index 1 is blocked as neighbor of 0)
        //
        // Both index 0 and n-1 being peaks is impossible since they are adjacent.

        // Case A: peaks only in [1..n-2]
        if (n >= 3) {
            int caseA = solveLinear(nums, 1, n - 2, nums[0], nums[n - 1], k);
            ans = Math.min(ans, caseA);
        }

        // Case B: index n-1 is a peak
        {
            int costLast = peakCost(nums, n - 1);
            int remaining = k - 1;
            if (remaining == 0) {
                ans = Math.min(ans, costLast);
            } else {
                // subarray [1..n-3], left neighbor = nums[0], right neighbor = nums[n-2]
                // (index n-2 is blocked as neighbor of n-1; index 0 is not a peak)
                int subCost = solveLinear(nums, 1, n - 3, nums[0], nums[n - 2], remaining);
                if (subCost < INF) ans = Math.min(ans, costLast + subCost);
            }
        }

        // Case C: index 0 is a peak
        {
            int cost0 = peakCost(nums, 0);
            int remaining = k - 1;
            if (remaining == 0) {
                ans = Math.min(ans, cost0);
            } else {
                // subarray [2..n-2], left neighbor = nums[1], right neighbor = nums[n-1]
                // (index 1 is blocked as neighbor of 0; index n-1 is not a peak)
                int subCost = solveLinear(nums, 2, n - 2, nums[1], nums[n - 1], remaining);
                if (subCost < INF) ans = Math.min(ans, cost0 + subCost);
            }
        }

        return ans >= INF ? -1 : ans;
    }

    public static void main(String[] args) {
        MinOperationsKPeaks sol = new MinOperationsKPeaks();

        // Example 1: expected 1
        System.out.println(sol.minOperations(new int[]{2, 1, 2}, 1));

        // Example 2: expected 0
        System.out.println(sol.minOperations(new int[]{4, 5, 3, 6}, 2));

        // Example 3: expected -1
        System.out.println(sol.minOperations(new int[]{3, 7, 3}, 2));

        // Previously failing: expected 11
        System.out.println(sol.minOperations(new int[]{6, -7, 11, 13}, 2));

        // Edge: k=0, expected 0
        System.out.println(sol.minOperations(new int[]{1, 2, 3}, 0));

        // All equal, k=1: expected 1
        System.out.println(sol.minOperations(new int[]{1, 1, 1, 1}, 1));

        // Circular peaks: [1,3,1,3], k=2, expected 0
        System.out.println(sol.minOperations(new int[]{1, 3, 1, 3}, 2));

        // n=2, k=1: index 1 (9) already beats index 0 (5) on both sides, expected 0
        System.out.println(sol.minOperations(new int[]{5, 9}, 1));

        // n=2, k=1: index 1 (2) already beats index 0 (1) on both sides, expected 0
        System.out.println(sol.minOperations(new int[]{1, 2}, 1));
    }
}
