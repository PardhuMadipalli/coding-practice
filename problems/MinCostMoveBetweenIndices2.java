package problems;

import java.util.Arrays;

public class MinCostMoveBetweenIndices2 {
    public int[] minCost(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] rightDiffs = new int[n];
        int[] leftDiffs = new int[n];
        int[] costs = new int[queries.length];
        rightDiffs[0] = 1;
        leftDiffs[n - 1] = 1;

        // starting at index 1
        int leftDiff = nums[1]-nums[0];
        for (int i = 1; i <= n-2; i++) {
            int rightDiff = nums[i+1] - nums[i];
            if (leftDiff <= rightDiff) {
                leftDiffs[i] = 1;
                rightDiffs[i] = rightDiff;
            } else {
                rightDiffs[i] = 1;
                leftDiffs[i] = leftDiff;
            }
            leftDiff = rightDiff;
        }

        // find prefix sums
        for (int i = 1; i < n; i++) {
            rightDiffs[i] += rightDiffs[i-1];
        }
        for (int i = n-2; i >= 0; i--) {
            leftDiffs[i] += leftDiffs[i+1];
        }

        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            if (left == right) {
                costs[i] = 0;
            } else if (left < right) {
                // move from left to right
                costs[i] = rightDiffs[right-1] - (left > 0 ? rightDiffs[left-1] : 0);
            } else {
                // move from bigger to lower
                costs[i] = leftDiffs[right+1] - (left < n-1 ? leftDiffs[left+1] : 0);
            }
        }
        return costs;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new MinCostMoveBetweenIndices2()
                .minCost(new int[]{-5, -2, 3}, new int[][]{{0,2},{2,0},{1,2}})));
    }
}

