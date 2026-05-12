package problems.miscellaneous;

import java.util.Arrays;

// https://leetcode.com/problems/minimum-operations-to-achieve-at-least-k-peaks/
// Knapsack-style memoization solution.
//
// At each index, two choices (just like 0/1 knapsack pick vs skip):
//   - Skip: don't make index i a peak, move to i+1
//   - Pick: make index i a peak, pay cost[i], move to i+2 (skip neighbor), use one peak slot
//
// State: solve(i, peaksLeft) = min cost to place peaksLeft peaks in [i..end]
//        assuming index i-1 was NOT picked (so i is free to be picked).
public class MinOperationsKPeaksKnapsack {

    private int[] cost;
    private int len;
    private int[][] memo;
    private static final int INF = Integer.MAX_VALUE / 2;

    // Cost to make index i a peak, given explicit left and right neighbor values.
    // (Used when solving a linear subarray with custom boundary neighbors.)
    private int peakCost(int[] nums, int i, int leftNeighbor, int rightNeighbor) {
        int left = (i == 0) ? leftNeighbor : nums[i - 1];
        int right = (i == len - 1) ? rightNeighbor : nums[i + 1];
        return Math.max(0, Math.max(left, right) + 1 - nums[i]);
    }

    // Knapsack-style recursion with memoization.
    // i     = current position in the linear subarray
    // peaks = number of peaks still needed
    private int solve(int i, int peaks) {
        if (peaks == 0) return 0;
        if (i >= len) return INF;  // ran out of positions but still need peaks
        if (memo[i][peaks] != -1) return memo[i][peaks];

        // Choice 1: skip index i (don't make it a peak)
        int skip = solve(i + 1, peaks);

        // Choice 2: pick index i as a peak
        //   - pay cost[i], then next eligible index is i+2 (i+1 is blocked as neighbor)
        int pick = INF;
        int c = cost[i];
        int sub = solve(i + 2, peaks - 1);
        if (sub < INF) pick = c + sub;

        return memo[i][peaks] = Math.min(skip, pick);
    }

    // Solve a linear subarray [start..end] with given boundary neighbors.
    private int solveLinear(int[] nums, int start, int end, int leftNeighbor, int rightNeighbor, int k) {
        if (k == 0) return 0;
        len = end - start + 1;
        if (len <= 0) return INF;

        // Build cost array for this subarray
        cost = new int[len];
        for (int i = 0; i < len; i++) {
            cost[i] = peakCost(nums, start + i,
                    (i == 0) ? leftNeighbor : nums[start + i - 1],
                    (i == len - 1) ? rightNeighbor : nums[start + i + 1]);
        }

        memo = new int[len][k + 1];
        for (int[] row : memo) Arrays.fill(row, -1);

        int result = solve(0, k);
        return result >= INF ? INF : result;
    }

    public int minOperations(int[] nums, int k) {
        int n = nums.length;
        if (k > n / 2) return -1;
        if (k == 0) return 0;

        int ans = INF;

        // Same 3-case circular decomposition as the tabulation solution:
        // Case A: neither index 0 nor index n-1 is a peak → solve [1..n-2]
        if (n >= 3) {
            ans = Math.min(ans, solveLinear(nums, 1, n - 2, nums[0], nums[n - 1], k));
        }

        // Case B: index n-1 is a peak → fix its cost, solve [1..n-3] for k-1 peaks
        {
            int costLast = Math.max(0, Math.max(nums[n - 2], nums[0]) + 1 - nums[n - 1]);
            int remaining = k - 1;
            if (remaining == 0) {
                ans = Math.min(ans, costLast);
            } else {
                int sub = solveLinear(nums, 1, n - 3, nums[0], nums[n - 2], remaining);
                if (sub < INF) ans = Math.min(ans, costLast + sub);
            }
        }

        // Case C: index 0 is a peak → fix its cost, solve [2..n-2] for k-1 peaks
        {
            int cost0 = Math.max(0, Math.max(nums[n - 1], nums[1]) + 1 - nums[0]);
            int remaining = k - 1;
            if (remaining == 0) {
                ans = Math.min(ans, cost0);
            } else {
                int sub = solveLinear(nums, 2, n - 2, nums[1], nums[n - 1], remaining);
                if (sub < INF) ans = Math.min(ans, cost0 + sub);
            }
        }

        return ans >= INF ? -1 : ans;
    }

    public static void main(String[] args) {
        MinOperationsKPeaksKnapsack sol = new MinOperationsKPeaksKnapsack();

        // Example 1: expected 1
        System.out.println(sol.minOperations(new int[]{2, 1, 2}, 1));

        // Example 2: expected 0
        System.out.println(sol.minOperations(new int[]{4, 5, 3, 6}, 2));

        // Example 3: expected -1
        System.out.println(sol.minOperations(new int[]{3, 7, 3}, 2));

        // Previously tricky: expected 11
        System.out.println(sol.minOperations(new int[]{6, -7, 11, 13}, 2));

        // k=0: expected 0
        System.out.println(sol.minOperations(new int[]{1, 2, 3}, 0));

        // All equal, k=1: expected 1
        System.out.println(sol.minOperations(new int[]{1, 1, 1, 1}, 1));

        // Circular peaks already exist: expected 0
        System.out.println(sol.minOperations(new int[]{1, 3, 1, 3}, 2));

        // n=2, k=1: index 1 (9) already beats index 0 (5) on both sides, expected 0
        System.out.println(sol.minOperations(new int[]{5, 9}, 1));

        // n=2, k=1: needs raising, expected 1
        System.out.println(sol.minOperations(new int[]{1, 2}, 1));
    }
}
