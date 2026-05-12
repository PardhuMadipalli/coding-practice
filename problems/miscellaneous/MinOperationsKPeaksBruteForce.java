package problems.miscellaneous;

// Brute force solution for: Minimum Operations to Achieve At Least K Peaks
// https://leetcode.com/problems/minimum-operations-to-achieve-at-least-k-peaks/
//
// Approach:
//   1. For each index, compute the cost to make it a peak (raise it above both neighbors).
//   2. Try all subsets of size k from the n indices.
//   3. For each subset, check that no two chosen indices are adjacent (circular).
//   4. Return the minimum total cost among all valid subsets.
//
// Time: O(n choose k) — exponential, only feasible for small n.
public class MinOperationsKPeaksBruteForce {

    // Cost to make index i a peak: raise nums[i] above both its circular neighbors.
    private int peakCost(int[] nums, int i) {
        int n = nums.length;
        int left = nums[(i - 1 + n) % n];
        int right = nums[(i + 1) % n];
        return Math.max(0, Math.max(left, right) + 1 - nums[i]);
    }

    // Check that no two indices in the chosen subset are adjacent in the circular array.
    private boolean isValid(int[] chosen, int k, int n) {
        for (int i = 0; i < k; i++) {
            int a = chosen[i];
            int b = chosen[(i + 1) % k];
            // Adjacent if they differ by 1, or are the two ends of the circular array
            if (Math.abs(a - b) == 1) return false;
            if ((a == 0 && b == n - 1) || (b == 0 && a == n - 1)) return false;
        }
        return true;
    }

    // Recursively enumerate all subsets of size k from indices [start..n-1].
    // chosen[] holds the indices picked so far (count of them filled).
    // best[0] tracks the minimum cost found.
    private void enumerate(int[] cost, int n, int k, int start, int count, int[] chosen, int[] best) {
        if (count == k) {
            if (isValid(chosen, k, n)) {
                int total = 0;
                for (int c : chosen) total += cost[c];
                best[0] = Math.min(best[0], total);
            }
            return;
        }
        // Pruning: not enough indices left to fill k slots
        if (n - start < k - count) return;

        for (int i = start; i < n; i++) {
            chosen[count] = i;
            enumerate(cost, n, k, i + 1, count + 1, chosen, best);
        }
    }

    public int minOperations(int[] nums, int k) {
        int n = nums.length;
        if (k > n / 2) return -1;
        if (k == 0) return 0;

        int[] cost = new int[n];
        for (int i = 0; i < n; i++) cost[i] = peakCost(nums, i);

        int[] best = {Integer.MAX_VALUE};
        enumerate(cost, n, k, 0, 0, new int[k], best);

        return best[0] == Integer.MAX_VALUE ? -1 : best[0];
    }

    public static void main(String[] args) {
        MinOperationsKPeaksBruteForce sol = new MinOperationsKPeaksBruteForce();

        // Example 1: expected 1
        System.out.println(sol.minOperations(new int[]{2, 1, 2}, 1));

        // Example 2: expected 0
        System.out.println(sol.minOperations(new int[]{4, 5, 3, 6}, 2));

        // Example 3: expected -1
        System.out.println(sol.minOperations(new int[]{3, 7, 3}, 2));

        // expected 11
        System.out.println(sol.minOperations(new int[]{6, -7, 11, 13}, 2));

        // k=0: expected 0
        System.out.println(sol.minOperations(new int[]{1, 2, 3}, 0));

        // All equal, k=1: expected 1
        System.out.println(sol.minOperations(new int[]{1, 1, 1, 1}, 1));

        // Circular peaks already exist: expected 0
        System.out.println(sol.minOperations(new int[]{1, 3, 1, 3}, 2));
    }
}
