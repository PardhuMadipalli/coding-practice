package problems;

import java.util.Arrays;

/**
 * Same problem as MaximizePointsAfterDeletions, implemented two ways:
 *   1. Top-down memoization (recursive)
 *   2. Bottom-up 2D DP (iterative)
 *
 * State: solve(i, kept) = max fixed points we can get from index i onward,
 *        given that `kept` elements have already been kept before index i.
 *
 * At each index i we choose:
 *   - Delete nums[i]: solve(i+1, kept)
 *   - Keep  nums[i]: (nums[i] == kept ? 1 : 0) + solve(i+1, kept+1)
 */
public class MaximiseFixedPointsAfterDeletion {

    // -------------------------------------------------------------------------
    // Approach 1: Top-down memoization  (solve(i, kept) calls solve(i-1, ...))
    // -------------------------------------------------------------------------
    //
    // solve(i, kept) = max fixed points in nums[0..i] when exactly `kept` elements
    //                  are kept in nums[0..i].
    //
    // Base case: solve(-1, 0) = 0  (no elements, nothing kept → 0 fixed points)
    //            solve(-1, k) = -INF for k > 0  (impossible to have kept > 0 elements)
    //
    // Transition for element i:
    //   - Delete nums[i]: solve(i-1, kept)               (kept count unchanged)
    //   - Keep   nums[i]: it becomes the `kept`-th element (0-indexed new index = kept-1)
    //                     fixed point iff nums[i] == kept-1
    //                     → (nums[i] == kept-1 ? 1 : 0) + solve(i-1, kept-1)
    //
    // Answer: max over all kept in [0..n] of solve(n-1, kept)

    private int[] numsM;
    private int[][] memo;
    private static final int NEG_INF = Integer.MIN_VALUE / 2;

    public int maxFixedPointsMemo(int[] nums) {
        int n = nums.length;
        this.numsM = nums;
        // memo[i][kept]: Integer.MIN_VALUE = unvisited
        memo = new int[n][n + 1];
        for (int[] row : memo) Arrays.fill(row, Integer.MIN_VALUE);

        int ans = 0;
        for (int kept = 0; kept <= n; kept++) {
            int val = solve(n - 1, kept);
            if (val != NEG_INF) ans = Math.max(ans, val);
        }
        return ans;
    }

    // Returns max fixed points in nums[0..i] with exactly `kept` elements kept.
    private int solve(int i, int kept) {
        if (kept < 0 || kept > i + 1) return NEG_INF; // impossible
        if (i < 0) return (kept == 0) ? 0 : NEG_INF;
        if (memo[i][kept] != Integer.MIN_VALUE) return memo[i][kept];

        // Option 1: delete nums[i]
        int delete = solve(i - 1, kept);

        // Option 2: keep nums[i] — it is the kept-th element kept (1-indexed),
        // so its new 0-based index is kept-1
        int prev = solve(i - 1, kept - 1);
        int keep = (prev == NEG_INF) ? NEG_INF
                : prev + (numsM[i] == kept - 1 ? 1 : 0);

        memo[i][kept] = Math.max(delete, keep);
        return memo[i][kept];
    }

    // -------------------------------------------------------------------------
    // Approach 2: Bottom-up 2D DP  (same state as memoization above)
    // -------------------------------------------------------------------------
    //
    // dp[i][kept] = max fixed points in nums[0..i-1] with exactly `kept` elements kept.
    // (i is 1-indexed here so dp[0][*] is the base case)
    //
    // Base case: dp[0][0] = 0, dp[0][k>0] = NEG_INF
    //
    // Transition:
    //   dp[i+1][kept]   = max(dp[i][kept],                              // delete nums[i]
    //                         dp[i][kept-1] + (nums[i]==kept-1 ? 1 : 0)) // keep nums[i]
    //
    // Answer: max over all kept of dp[n][kept]

    public int maxFixedPoints2D(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 1][n + 1];
        // base case
        for (int k = 1; k <= n; k++) dp[0][k] = NEG_INF;

        for (int i = 0; i < n; i++) {
            for (int kept = 0; kept <= i + 1; kept++) {
                // delete nums[i]
                dp[i + 1][kept] = dp[i][kept];
                // keep nums[i]: came from kept-1 elements kept in 0..i-1
                if (kept > 0 && dp[i][kept - 1] != NEG_INF) {
                    int gain = (nums[i] == kept - 1) ? 1 : 0;
                    dp[i + 1][kept] = Math.max(dp[i + 1][kept], dp[i][kept - 1] + gain);
                }
            }
        }

        int ans = 0;
        for (int kept = 0; kept <= n; kept++) {
            if (dp[n][kept] != NEG_INF) ans = Math.max(ans, dp[n][kept]);
        }
        return ans;
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        MaximiseFixedPointsAfterDeletion sol = new MaximiseFixedPointsAfterDeletion();

        int[][] tests = {{0, 2, 1}, {3, 1, 2}, {1, 0, 1, 2}, {0, 1, 1}};
        int[] expected = {2, 2, 3, 2};

        for (int t = 0; t < tests.length; t++) {
            int memo = sol.maxFixedPointsMemo(tests[t]);
            int dp2d = sol.maxFixedPoints2D(tests[t]);
            System.out.printf("Test %d: memo=%d, 2D-dp=%d, expected=%d%n",
                    t + 1, memo, dp2d, expected[t]);
        }
    }
}
