package problems;

import java.util.Arrays;

public class MaximizePointsAfterDeletions {

    /**
     * After deletions, element at original index i gets new index = number of kept elements
     * before it. It's a fixed point iff nums[i] == (kept elements before it).
     *
     * DP: process elements left to right. State = number of elements kept so far.
     * dp[k] = max fixed points achievable when exactly k elements have been kept so far.
     *
     * For each element nums[i], we can:
     *   - Delete it: dp[k] stays dp[k] for all k
     *   - Keep it: dp[k+1] = max(dp[k+1], dp[k] + (nums[i] == k ? 1 : 0))
     *
     * We process in reverse order of k to avoid using the same element twice (like 0/1 knapsack).
     *
     * Initial state: dp[0] = 0, dp[k] = -infinity for k > 0.
     * Answer: max(dp[k]) over all k after processing all elements.
     *
     * Space: O(n). Time: O(n^2) worst case, but in practice fast.
     * For n=10^5 this may TLE; the O(n log n) approach requires deeper analysis.
     */
    public int maxFixedPoints(int[] nums) {
        int n = nums.length;
        // dp[k] = max fixed points when k elements kept so far; -1 means unreachable
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            // Traverse k in reverse to avoid using element i twice
            for (int k = i; k >= 0; k--) {
                if (dp[k] == -1) continue;
                // Keep nums[i]: it gets new index k, fixed point if nums[i] == k
                int gain = (nums[i] == k) ? 1 : 0;
                dp[k + 1] = Math.max(dp[k + 1], dp[k] + gain);
                // Delete nums[i]: dp[k] stays unchanged (already in place)
            }
        }

        int ans = 0;
        for (int k = 0; k <= n; k++) {
            if (dp[k] != -1) ans = Math.max(ans, dp[k]);
        }
        return ans;
    }

    public static void main(String[] args) {
        MaximizePointsAfterDeletions sol = new MaximizePointsAfterDeletions();
        System.out.println(sol.maxFixedPoints(new int[]{0, 2, 1}));     // 2
        System.out.println(sol.maxFixedPoints(new int[]{3, 1, 2}));     // 2
        System.out.println(sol.maxFixedPoints(new int[]{1, 0, 1, 2}));  // 3
        System.out.println(sol.maxFixedPoints(new int[]{0, 1, 1}));     // 2
    }
}